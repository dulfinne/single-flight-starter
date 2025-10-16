package com.dulfinne.singleflightstarter.config;

import com.dulfinne.singleflightstarter.aop.SingleFlightAspect;
import com.dulfinne.singleflightstarter.support.SpelKeyResolver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.Executors;

@Slf4j
@Configuration
@EnableConfigurationProperties(SingleFlightProperties.class)
@ConditionalOnClass(SingleFlightProperties.class)
@ConditionalOnProperty(prefix = "app.common.single-flight", name = "enabled", havingValue = "true")
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class SingleFlightAutoConfiguration {
  private final SpelKeyResolver spelKeyResolver;

  @Bean
  @ConditionalOnMissingBean(SingleFlightAspect.class)
  public SingleFlightAspect singleFlightAspect() {
    return new SingleFlightAspect(spelKeyResolver,
                                  Executors.newFixedThreadPool(Runtime.getRuntime()
                                                                      .availableProcessors()));
  }

  @PostConstruct
  public void init() {
    log.info("SingleFlightAutoConfiguration initialized");
  }
}
