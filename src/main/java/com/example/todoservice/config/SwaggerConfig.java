package com.example.todoservice.config;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String SERVICE_NAME = "ToDo Service";
	private static final String BASE_PACKAGE = "com.example.todoservice";

	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2).ignoredParameterTypes(Authentication.class)
				.groupName("todo-service").select().apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
				.paths(PathSelectors.regex("/.*")).build().apiInfo(apiInfo())
				.directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(SERVICE_NAME).version("2.0").build();
	}

}
