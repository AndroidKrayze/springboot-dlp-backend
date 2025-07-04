package com.dlpauth.user.service;

import com.dlpauth.model.DlpauthOutputResult;

public interface UserService {

	public DlpauthOutputResult getUsersFromGraphApi();
	
	//public DlpauthOutputResult saveTenantInfo(String tenantId, String email);
	
	DlpauthOutputResult onboardTenant(String tenantId, String accessToken, String refreshToken, String subdomain, String orgName);
}
