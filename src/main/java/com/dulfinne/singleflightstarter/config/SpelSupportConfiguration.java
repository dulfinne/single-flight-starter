package com.dulfinne.singleflightstarter.config;

import com.dulfinne.singleflightstarter.support.SpelKeyResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
@ConditionalOnClass(SingleFlightProperties.class)
@ConditionalOnProperty(prefix = "app.common.single-flight", name = "enabled", havingValue = "true")
public class SpelSupportConfiguration {
  @Bean
  public SpelKeyResolver spelKeyResolver() {
    return new SpelKeyResolver(expressionParser(), parameterNameDiscoverer());
  }

  @Bean
  public ExpressionParser expressionParser() {
    return new SpelExpressionParser();
  }

  @Bean
  public DefaultParameterNameDiscoverer parameterNameDiscoverer() {
    return new DefaultParameterNameDiscoverer();
  }
}
