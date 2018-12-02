package com.example.demo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DemoUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final String principal;
	private final String account;
	private final String email;
	private final Set<GrantedAuthority> authorities;

	private DemoUser(Builder builder) {
		this.principal = builder.principal;
		this.account = builder.account;
		this.email = builder.email;
		this.authorities = builder.authorities;
	}

	public String getAccount() {
		return account;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		// NOTE: preauth では getPreAuthenticatedPrincipal() の戻り値が String の場合、username.equals(principal) で認証済扱いとなる
		return principal;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String principal;
		private String account;
		private String email;
		private Set<GrantedAuthority> authorities = new HashSet<>();

		public Builder principal(String principal) {
			this.principal = principal;
			return this;
		}

		public Builder account(String account) {
			this.account = account;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder authorities(Collection<GrantedAuthority> authorities) {
			if (authorities != null) {
				this.authorities.addAll(authorities);
			}
			return this;
		}

		public DemoUser build() {
			return new DemoUser(this);
		}
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
