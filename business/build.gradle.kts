plugins {
    java
    war
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("dev.hilla") version "2.5.5"
}

group = "dev.vulcanium.business"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["hillaVersion"] = "2.5.5"
extra["solaceSpringBootVersion"] = "2.0.0"
extra["vaadinVersion"] = "24.4.1"

dependencies {
    implementation("jakarta.faces:jakarta.faces-api:latest.release")
    implementation("org.owasp.antisamy:antisamy:latest.release")
    implementation("com.vaadin:vaadin-spring-boot-starter")
    implementation("net.sf.ehcache:ehcache:latest.release")
    implementation("com.maxmind.geoip2:geoip2:latest.release")
    implementation("com.google.maps:google-maps-services:0.1.6")
    implementation("com.stripe:stripe-java:latest.release")
    implementation("com.braintreepayments.gateway:braintree-java:latest.release")
    implementation("jakarta.mail:jakarta.mail-api:latest.release")
    implementation("jakarta.platform:jakarta.jakartaee-web-api:latest.release")
    implementation("com.paypal.sdk:merchantsdk:latest.release")
    implementation("com.google.cloud:google-cloud-storage:latest.release")
    implementation("org.infinispan:infinispan-core:latest.release")
    implementation("org.infinispan:infinispan-cachestore-jdbc:latest.release")
    implementation("org.infinispan:infinispan-tree:latest.release")
    implementation("com.amazonaws:aws-java-sdk-s3:latest.release")
    implementation("com.amazonaws:aws-java-sdk-ses:latest.release")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("org.drools:drools-decisiontables:latest.release")
    implementation("org.drools:drools-core:latest.release")
    implementation("org.drools:drools-compiler:latest.release")
    implementation("org.kie:kie-ci:latest.release")
    implementation("org.kie:kie-spring:latest.release")
    implementation("com.zaxxer:HikariCP:latest.release")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:latest.release")
    implementation("commons-validator:commons-validator:latest.release")
    implementation("org.hibernate:hibernate-annotations:latest.release")
    implementation("org.springframework.boot:spring-boot-starter-activemq")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-artemis")
    implementation("org.springframework.boot:spring-boot-starter-data-ldap")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-integration")
    implementation("org.springframework.boot:spring-boot-starter-jersey")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-pulsar")
    implementation("org.springframework.boot:spring-boot-starter-pulsar-reactive")
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.okta.spring:okta-spring-boot-starter:3.0.6")
    implementation("com.solace.spring.boot:solace-spring-boot-starter")
    implementation("dev.hilla:hilla-react-spring-boot-starter")
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:4.5.0")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.springframework.amqp:spring-rabbit-stream")
    implementation("org.springframework.data:spring-data-rest-hal-explorer")
    implementation("org.springframework.integration:spring-integration-amqp")
    implementation("org.springframework.integration:spring-integration-http")
    implementation("org.springframework.integration:spring-integration-jms")
    implementation("org.springframework.integration:spring-integration-kafka")
    implementation("org.springframework.integration:spring-integration-rsocket")
    implementation("org.springframework.integration:spring-integration-security")
    implementation("org.springframework.integration:spring-integration-stomp")
    implementation("org.springframework.integration:spring-integration-webflux")
    implementation("org.springframework.integration:spring-integration-websocket")
    implementation("org.springframework.integration:spring-integration-ws")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.security:spring-security-messaging")
    implementation("org.springframework.security:spring-security-rsocket")
    implementation("org.springframework.session:spring-session-core")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("io.swagger:swagger-annotations:latest.release")
    implementation("org.json:json:latest.release")
    implementation("jakarta.persistence:jakarta.persistence-api:latest.release")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    annotationProcessor("org.projectlombok:lombok")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.integration:spring-integration-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
    imports {
        mavenBom("com.vaadin:vaadin-bom:${property("vaadinVersion")}")
        mavenBom("dev.hilla:hilla-bom:${property("hillaVersion")}")
        mavenBom("com.solace.spring.boot:solace-spring-boot-bom:${property("solaceSpringBootVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
