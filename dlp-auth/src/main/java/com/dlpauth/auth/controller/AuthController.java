package com.dlpauth.auth.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dlpauth.exception.ErrorDetails;
import com.dlpauth.model.DlpauthOutputResult;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.dlpauth.user.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	 @PostConstruct
  	public void init() {
        	log.info(" AuthController loaded");
  	  }

	@GetMapping("/userInfo")
	public DlpauthOutputResult getCurrentUser(@AuthenticationPrincipal OidcUser oidcUser) {
		log.info(" /auth/userInfo endpoint called");
	    DlpauthOutputResult output = new DlpauthOutputResult();

	    if (oidcUser == null) {
	        output.setErrorInfo(new ErrorDetails("401", "User not authenticated"));
	        output.setStatus("Failed");
	        return output;
	    }

	    Map<String, Object> userInfo = new HashMap<>();
	    userInfo.put("name", oidcUser.getFullName());
	    userInfo.put("email", oidcUser.getEmail());
	    userInfo.put("claims", oidcUser.getClaims());
	    userInfo.put("idToken", oidcUser.getIdToken().getTokenValue());

	    output.setData(userInfo);
	    output.setStatus("User Info retrieved successfully");

	    return output;
	}
	
	@Autowired
    private UserService userService;

    @PostMapping("/oauth2/callback")
    public ResponseEntity<?> oauth2Callback(
            @RequestParam("tenant_id") String tenantId,
            @RequestParam("access_token") String accessToken,
            @RequestParam("refresh_token") String refreshToken,
            @RequestHeader(value = "Host") String hostHeader) {
        log.info("/auth/oauth2/callback endpoint called");
        try {
            // Extract subdomain from Host header
            String subdomain = null;
            if (hostHeader != null && hostHeader.contains(".")) {
                subdomain = hostHeader.split("\\.")[0];
            }

            // Call Microsoft Graph API to get org_name
            String orgName = null;
            String url = "https://graph.microsoft.com/v1.0/organization";
            RestTemplate restTemplate = new RestTemplate();
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setBearerAuth(accessToken);
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Object value = response.getBody().get("value");
                if (value instanceof java.util.List && !((java.util.List<?>) value).isEmpty()) {
                    Object orgObj = ((java.util.List<?>) value).get(0);
                    if (orgObj instanceof Map) {
                        orgName = (String) ((Map<?, ?>) orgObj).get("displayName");
                    }
                }
            }

            // Call onboardTenant service method
            userService.onboardTenant(tenantId, accessToken, refreshToken, subdomain, orgName);
            Map<String, Object> result = new HashMap<>();
            result.put("status", "success");
            result.put("tenant_id", tenantId);
            result.put("org_name", orgName);
            result.put("subdomain", subdomain);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("OAuth2 callback error", e);
            return ResponseEntity.status(500).body("OAuth2 callback failed: " + e.getMessage());
        }
    }

}
