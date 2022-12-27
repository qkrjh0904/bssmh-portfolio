package com.bssmh.portfolio.web.config.swagger;

import com.bssmh.portfolio.web.domain.path.ApiPath;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.DESCRIPTION;
import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.JWT;
import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.LICENSE;
import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.LICENSE_URL;
import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.SERVER_URL;
import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.TITLE;
import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.TOKEN;
import static com.bssmh.portfolio.web.config.swagger.SwaggerConfigConstants.VERSION;
import static com.bssmh.portfolio.web.config.swagger.SwaggerGroupName.BSSMH_API;

@Configuration
public class SwaggerConfig {

	static {
		SpringDocUtils.getConfig()
				.replaceWithClass(LocalDateTime.class, String.class)
				.replaceWithClass(LocalDate.class, String.class)
				.replaceWithClass(LocalTime.class, String.class)
				.addAnnotationsToIgnore(AuthenticationPrincipal.class, CookieValue.class);
	}

	@Bean
	public GroupedOpenApi kafkaApi() {
		return GroupedOpenApi.builder()
				.group(BSSMH_API)
				.pathsToMatch(getPaths(ApiPath.class))
				.addOpenApiCustomiser(openApiCustomiser())
				.build();
	}

	private <T> String[] getPaths(Class<T> reflection) {
		return Arrays.stream(reflection.getDeclaredFields())
				.map(this::getPath)
				.toArray(String[]::new);
	}

	private String getPath(Field field) {
		try {
			return (String) field.get(field);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public OpenAPI getOpenApi() {
		return new OpenAPI()
				.info(getApiInfo())
				.components(getComponents());
	}

	private Components getComponents() {
		return new Components()
				.addSecuritySchemes(JWT, getJwtSecurityScheme());
	}

	private SecurityScheme getJwtSecurityScheme() {
		return new SecurityScheme()
				.type(SecurityScheme.Type.APIKEY)
				.in(SecurityScheme.In.HEADER)
				.name(TOKEN);
	}

	@Bean
	public OpenApiCustomiser openApiCustomiser() {
		return OpenApi -> OpenApi
				.addSecurityItem(getSecurityItem())
				.addServersItem(getServersItem());
	}

	@Bean
	public OpenApiCustomiser resumeOpenApiCustomiser() {
		return OpenApi -> OpenApi
				.addSecurityItem(getSecurityItem())
				.addServersItem(getServersItem());
	}

	private SecurityRequirement getSecurityItem() {
		return new SecurityRequirement()
				.addList(JWT);
	}

	private Server getServersItem() {
		return new Server().url(SERVER_URL);
	}

	private Info getApiInfo() {
		return new Info()
				.title(TITLE)
				.description(DESCRIPTION)
				.version(VERSION)
				.license(new License().name(LICENSE).url(LICENSE_URL));
	}

}
