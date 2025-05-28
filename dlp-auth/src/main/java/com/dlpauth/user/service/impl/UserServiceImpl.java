package com.dlpauth.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.dlpauth.exception.ErrorDetails;
import com.dlpauth.model.DlpauthOutputResult;
import com.dlpauth.tenant.model.Tenant;
import com.dlpauth.tenant.repo.TenantRepo;
import com.dlpauth.user.service.UserService;

@Service
public class UserServiceImpl<T> implements UserService {

	@Value("${spring.security.oauth2.client.provider.azure.user-info-uri}")
	private String graphUrl;


	@Autowired
	private TenantRepo tenantRepo;
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@Override
	public DlpauthOutputResult getUsersFromGraphApi() {
		 DlpauthOutputResult output = new DlpauthOutputResult();

		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		    if (!(authentication.getPrincipal() instanceof OidcUser oidcUser)) {
		        output.setErrorInfo(new ErrorDetails("401", "User not authenticated or not OIDC user"));
		        output.setStatus("Failed");
		        return output;
		    }

		    // Retrieve the OAuth2 client using registrationId (like "azure")
		    OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
		            "azure", // Replace with your registration ID
		            authentication.getName()
		    );

		    if (client == null || client.getAccessToken() == null) {
		        output.setErrorInfo(new ErrorDetails("401", "Access token not found"));
		        output.setStatus("Failed");
		        return output;
		    }

		    String accessToken = client.getAccessToken().getTokenValue();

		    String usersUrl = "https://graph.microsoft.com/v1.0/users";

		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(accessToken);
		    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

		    HttpEntity<Void> request = new HttpEntity<>(headers);
		    RestTemplate restTemplate = new RestTemplate();

		    try {
		        ResponseEntity<String> response = restTemplate.exchange(
		                usersUrl, HttpMethod.GET, request, String.class
		        );

		        output.setData(response.getBody());
		        output.setStatus("All users retrieved successfully");

		    } catch (HttpClientErrorException | HttpServerErrorException ex) {
		        output.setErrorInfo(new ErrorDetails(String.valueOf(ex.getRawStatusCode()), ex.getResponseBodyAsString()));
		        output.setStatus("Failed to retrieve users");

		    } catch (Exception ex) {
		        output.setErrorInfo(new ErrorDetails("500", ex.getMessage()));
		        output.setStatus("Unexpected error");
		    }

		    return output;
		}
		


	@Override
	public DlpauthOutputResult saveTenantInfo(String tenantId, String email) {

		String subdomain = generateSubdomain(email);

		Tenant tenant = new Tenant();
		tenant.setTenantId(tenantId);
		tenant.setAdmin_email(email);
		tenant.setSubdomain(subdomain);
		tenant.setRegistered_date(new Date());

		tenantRepo.save(tenant);
		return null;
	}

	public boolean isTenantRegistered(String tenantId) {
		return tenantRepo.existsByTenantId(tenantId);
	}

	private String generateSubdomain(String email) {
		String domainPart = email.split("@")[1];
		return domainPart.split("\\.")[0];
	}

}
