package com.dlpauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.dlpauth.user.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuth2UserService customOAuth2UserService)
			throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").authenticated().anyRequest().permitAll())
				.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService) // <--
																															// use
																															// Graph
																															// instead
				).defaultSuccessUrl("/auth/userInfo", true).failureUrl("/login?error"));
		return http.build();
	}
}
