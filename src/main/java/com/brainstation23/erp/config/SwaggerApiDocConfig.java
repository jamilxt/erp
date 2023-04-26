package com.brainstation23.erp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerApiDocConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		final Info info = new Info().title("Java Trainees 2023").version("1.0.0")
				.license(new License().name("© Brain Station 23 Ltd.").url("https://brainstation-23.com"));
		final OpenAPI openAPI = new OpenAPI()
				.addServersItem(new Server().url("/"))
				.info(info);
		return openAPI;
	}
}
