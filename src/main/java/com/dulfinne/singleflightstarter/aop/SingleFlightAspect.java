package com.dulfinne.singleflightstarter.aop;

import com.dulfinne.singleflightstarter.annotation.SingleFlight;
import com.dulfinne.singleflightstarter.support.SpelKeyResolver;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@Aspect
@RequiredArgsConstructor
public class SingleFlightAspect {
  private final SpelKeyResolver spelKeyResolver;
  private final Executor blockingExecutor;
  private final Map<String, CompletableFuture<Object>> inFlightRequests =
      new ConcurrentHashMap<>();

  @Pointcut("@annotation(com.dulfinne.singleflightstarter.annotation.SingleFlight)")
  public void targetMethod() {
  }

  @Around(value = "targetMethod()", argNames = "pjp")
  public Object actWithSingleFLight(ProceedingJoinPoint pjp) throws Throwable {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();
    String keySpell = method.getAnnotation(SingleFlight.class)
                            .key();
    String key = spelKeyResolver.resolve(keySpell, method, pjp.getTarget(), pjp.getArgs());

    return inFlightRequests.computeIfAbsent(key, k -> {
                             CompletableFuture<Object> future = getInvocationFuture(pjp);
                             future.whenComplete((res, ex) -> inFlightRequests.remove(k));
                             return future;
                           })
                           .get();
  }

  private CompletableFuture<Object> getInvocationFuture(ProceedingJoinPoint pjp) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        return pjp.proceed();
      } catch (Throwable e) {
        throw new RuntimeException(e);
      }
    }, blockingExecutor);
  }
}
