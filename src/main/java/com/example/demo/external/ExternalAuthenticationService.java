package com.example.demo.external;

import org.springframework.stereotype.Component;

import com.example.demo.security.DemoRole;

@Component
public class ExternalAuthenticationService {

	public boolean isAuthorized(Object principal, Object credentials) {
		// dummy code.
		return principal != null && credentials != null;
	}

	public ExternalAuthenticated authenticate(Object principal, Object credentials) {
		// dummy code.
		return ExternalAuthenticated.builder()
				.account(String.valueOf(principal))
				.email("demo@example.com")
				.type(DemoRole.ADMIN.getType())
				.build();
	}
}
