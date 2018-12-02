package com.example.demo.security;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.example.demo.external.ExternalAuthenticated;
import com.example.demo.external.ExternalAuthenticationService;

public class ExternalAuthService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private ExternalAuthenticationService delegate;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

		final Object principal = token.getPrincipal();
		final Object credentials = token.getCredentials();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("[token] " + ReflectionToStringBuilder.toString(token, ToStringStyle.JSON_STYLE));
		}
		if (!this.delegate.isAuthorized(principal, credentials)) {
			throw new UsernameNotFoundException("unknown user.");
		}

		final ExternalAuthenticated authenticated;
		try {
			authenticated = this.delegate.authenticate(principal, credentials);

		} catch (Throwable e) {
			throw new UsernameNotFoundException("authentication failed.");
		}
		return DemoUser.builder()
				.principal(String.valueOf(principal))
				.account(authenticated.getAccount())
				.email(authenticated.getEmail())
				.authorities(DemoRole.fromType(authenticated.getType()))
				.build();
	}

}
