package com.dlpauth.auth.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dlpauth.exception.ErrorDetails;
import com.dlpauth.model.DlpauthOutputResult;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

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
	
    
}
