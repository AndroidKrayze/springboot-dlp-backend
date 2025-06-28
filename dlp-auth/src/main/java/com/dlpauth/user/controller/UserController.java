package com.dlpauth.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dlpauth.model.DlpauthOutputResult;
import com.dlpauth.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/getUsers")
	public DlpauthOutputResult getAllUsers() {
		return userService.getUsersFromGraphApi();
	}

	/*
	 * @PostMapping("/saveTenant") public DlpauthOutputResult
	 * saveTenantInfo(@RequestParam String tenantId, @RequestParam String email) {
	 * return userService.saveTenantInfo(tenantId, email); }
	 */

}
