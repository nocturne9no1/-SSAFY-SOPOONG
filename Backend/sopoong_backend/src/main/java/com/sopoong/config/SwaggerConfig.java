package com.sopoong.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:8080/swagger-ui.html#!
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("A404")
			.description("API")
			.version("1.0")
			.build();
	}
	
	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}
	
	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}
	
	@Bean
	public Docket commonApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes())
				.apiInfo(apiInfo()).securityContexts(Lists.newArrayList(securityContext()))
				.securitySchemes(Lists.newArrayList(authorizationKey(), httpRequestKey()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/**"))
				.build();
	}
	private ApiKey authorizationKey() {
		return new ApiKey("JWT_TOKEN", "X-AUTH-TOKEN", "header");
	}
	private ApiKey httpRequestKey() {
		return new ApiKey("HTTP_REQUEST","sopoong", "header");
	}
	
	/**
	 * securityContext
	 * 
	 * @return
	 */
	private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
		
		return springfox.documentation.spi.service.contexts.SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.any())
				.build();
	}
	
	/**
	 * defaultAuth
	 * 
	 * @return
	 */
	private List<SecurityReference> defaultAuth() {

		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		
		return Lists.newArrayList(new SecurityReference("JWT_TOKEN", authorizationScopes),
				new SecurityReference("HTTP_REQUEST", authorizationScopes));
	}
	
	
}
