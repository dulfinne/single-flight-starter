package com.dulfinne.singleflightstarter.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "app.common.single-flight")
public class SingleFlightProperties {

  /**
   * Enables single flight feature,
   * For java 21 don't forget to override
   * SingleFlightAspect bean with virtual executor
   */
  private boolean enabled;

  @PostConstruct
  public void init() {
    log.info("Single flight properties initialized: {}", this);
  }
}
