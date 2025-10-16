plugins {
  java
  id("org.springframework.boot") version "3.5.4"
  id("io.spring.dependency-management") version "1.1.7"
  id("maven-publish")
}

group = "com.dulfinne"
version = "0.0.1-SNAPSHOT"
description = "single-flight-starter"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

repositories {
  mavenCentral()
}

val lombokVersion = "1.18.38"
val configurationProcessorVersion = "3.5.4"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-configuration-processor:$configurationProcessorVersion")
  compileOnly("org.projectlombok:lombok:$lombokVersion")
  annotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.bootJar {
  enabled = false
}

tasks.jar {
  enabled = true
}
