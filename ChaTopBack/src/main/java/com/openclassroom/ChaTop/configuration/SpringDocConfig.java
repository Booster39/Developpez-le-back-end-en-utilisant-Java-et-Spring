package com.openclassroom.ChaTop.configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
  name = "Bearer Authentication",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  scheme = "bearer"
)
@Configuration
public class SpringDocConfig {
  @Bean
  public OpenAPI apiInfo() {
    return new OpenAPI().info(new Info().title("Api Docs").version("1.0.0"));
  }

  @Bean
  public GroupedOpenApi httpApi() {
    return GroupedOpenApi.builder()
      .group("http")
      .pathsToMatch("/**")
      .build();
  }
}
