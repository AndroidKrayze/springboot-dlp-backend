package com.dlpauth.user.service;

import com.dlpauth.model.DlpauthOutputResult;

public interface UserService {

	public DlpauthOutputResult getUsersFromGraphApi();
	
	//public DlpauthOutputResult saveTenantInfo(String tenantId, String email);
	
}
