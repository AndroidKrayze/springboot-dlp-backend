package com.dlpauth.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SecurityServiceUtil {

	@Value("${spring.security.oauth2.client.registration.azure.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.azure.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.provider.azure.token-uri}")
	private String tokenUrl;

	public String getAccessToken() {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "client_id=" + clientId + "&scope=https%3A%2F%2Fgraph.microsoft.com%2F.default"
				+ "&client_secret=" + clientSecret + "&grant_type=client_credentials";

		HttpEntity<String> request = new HttpEntity<>(body, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

		return response.getBody().get("access_token").toString();
	}

}
