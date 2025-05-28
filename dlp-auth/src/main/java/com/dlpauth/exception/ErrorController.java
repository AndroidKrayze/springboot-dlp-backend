package com.dlpauth.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String message = (String) request.getAttribute("javax.servlet.error.message");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

		String errorMessage = (message != null) ? message : "N/A";
		String exceptionMessage = (throwable != null) ? throwable.getMessage() : "No exception available";

		return String.format("Error Code: %d\nMessage: %s\nException: %s", statusCode, errorMessage, exceptionMessage);
	}
}
