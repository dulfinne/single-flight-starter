package com.dulfinne.singleflightstarter.support;

import lombok.RequiredArgsConstructor;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class SpelKeyResolver {
  private final ExpressionParser parser;
  private final DefaultParameterNameDiscoverer discoverer;

  public String resolve(String spel, Method method, Object target, Object[] args) {
    EvaluationContext context = new MethodBasedEvaluationContext(target, method, args, discoverer);
    return parser.parseExpression(spel)
                 .getValue(context, String.class);
  }
}
