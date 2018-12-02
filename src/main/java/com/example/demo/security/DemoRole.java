package com.example.demo.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum DemoRole {

	ADMIN(9), //
	OPERATOR(2), //
	READ_ONLY(1), //
	ANONYMOUS(0), //
	;

	private final int type;

	private DemoRole(final int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	private static DemoRole from(final int type) {
		for (DemoRole role : values()) {
			if (role.type == type) {
				return role;
			}
		}
		return ANONYMOUS;
	}

	public static Collection<GrantedAuthority> fromType(final int type) {
		final Set<GrantedAuthority> authorities = new HashSet<>();
		switch (DemoRole.from(type)) {
		case ADMIN:
			authorities.add(new SimpleGrantedAuthority(ADMIN.name()));
		case OPERATOR:
			authorities.add(new SimpleGrantedAuthority(OPERATOR.name()));
		case READ_ONLY:
			authorities.add(new SimpleGrantedAuthority(READ_ONLY.name()));
		case ANONYMOUS:
			break;
		}
		return authorities;
	}

	public static boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
		if (authorities == null) {
			return false;
		}
		return authorities.contains(new SimpleGrantedAuthority(DemoRole.ADMIN.name()));
	}

	public static boolean isOperator(Collection<? extends GrantedAuthority> authorities) {
		if (authorities == null) {
			return false;
		}
		return authorities.contains(new SimpleGrantedAuthority(DemoRole.OPERATOR.name()));
	}
}
