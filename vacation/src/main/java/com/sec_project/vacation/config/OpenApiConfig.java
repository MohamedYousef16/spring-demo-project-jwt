package com.sec_project.vacation.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(

								info = @Info(
												contact = @Contact(
															name = "Mohamed Yousef",
															email = "m3220580@gmail.com",
															url="https://github.com/MohamedYousef16"
														),
												description = "Spring Boot App For Vacation System",
												title = "Docker and Security Specification",
												version = "1.0",
												license = @License(
														name = "Elshalaksh",
														url = "https://www.youtube.com/@elbalf"		
														),
												termsOfService = "Terms of service"
										),
								servers = {
										@Server(
												description = "Local ENV",
												url = "http://localhost:8088"
												),
										@Server(
												description = "PROD ENV",
												url = "https://www.youtube.com/@elbalf"
												)
								},
								security = {
										@SecurityRequirement(
												name = "bearer auth"
												)
								}
		
		)
@SecurityScheme(
		
		name = "bearer auth",
		description = "JWT Token Auth",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
		
		)
public class OpenApiConfig {

}
