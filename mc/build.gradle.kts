plugins {
	java
	war
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("dev.hilla") version "2.5.5"
	id("com.vaadin") version "24.3.11"
}

group = "dev.vulcanium.site"
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
extra["vaadinVersion"] = "24.3.11"

dependencies {
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
	implementation("com.vaadin:vaadin-spring-boot-starter")
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
