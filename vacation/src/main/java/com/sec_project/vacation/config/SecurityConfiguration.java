package com.sec_project.vacation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.sec_project.vacation.user.Permission;
import com.sec_project.vacation.user.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableMethodSecurity
public class SecurityConfiguration {
	
    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

	private final JwtAuthenticationFilter jwtAuthFilter;

	private final AuthenticationProvider authenticationProvider;

	private final LogoutHandler logoutHandler;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll()

						.requestMatchers("/api/v1/dept/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())

						.requestMatchers(HttpMethod.GET, "/api/v1/dept/**")
						.hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MANAGER_READ.name())
						.requestMatchers(HttpMethod.POST, "/api/v1/dept/**")
						.hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.MANAGER_CREATE.name())

						.requestMatchers("/api/v1/emp/**").hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name())

						.requestMatchers(HttpMethod.GET, "/api/v1/emp/**")
						.hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MANAGER_READ.name())
						.requestMatchers(HttpMethod.POST, "/api/v1/emp/**")
						.hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.MANAGER_CREATE.name())
						.requestMatchers(HttpMethod.DELETE, "/api/v1/emp/**")
						.hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.MANAGER_DELETE.name())

						.requestMatchers("/api/v1/vac/**")
						.hasAnyRole(Role.ADMIN.name(), Role.MANAGER.name(), Role.USER.name())

						.requestMatchers(HttpMethod.GET, "/api/v1/vac/**")
						.hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MANAGER_READ.name(),
								Permission.User_READ.name())
						.requestMatchers(HttpMethod.POST, "/api/v1/vac/**")
						.hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.MANAGER_CREATE.name(),
								Permission.USER_CREATE.name())
						.requestMatchers(HttpMethod.PUT, "/api/v1/vac/**")
						.hasAnyAuthority(Permission.ADMIN_UPDATE.name(), Permission.MANAGER_UPDATE.name())
						.requestMatchers(HttpMethod.DELETE, "/api/v1/vac/**")
						.hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.MANAGER_DELETE.name())

						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.logout(logout -> logout.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler)
						.logoutSuccessHandler(
								(request, response, authentication) -> SecurityContextHolder.clearContext()));
		return http.build();
	}
}
