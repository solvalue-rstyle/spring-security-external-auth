package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

@Configuration
@EnableWebSecurity
public class DemoAuthConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilter(this.preAuthenticatedProcessingFilter());
		http.authenticationProvider(this.preAuthenticatedAuthenticationProvider());
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**/css/**", "/**/favicon.ico");
	}

	public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
		final ExternalAuthFilter filter = new ExternalAuthFilter();
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}

	@Bean
	public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
		PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
		provider.setPreAuthenticatedUserDetailsService(this.authenticationUserDetailsService());
		provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
		return provider;
	}

	@Bean
	public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
		return new ExternalAuthService();
	}
}
