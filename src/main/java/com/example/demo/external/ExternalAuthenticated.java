package com.example.demo.external;

public class ExternalAuthenticated {

	private final String account;
	private final String email;
	private final Integer type;

	private ExternalAuthenticated(Builder builder) {
		this.account = builder.account;
		this.email = builder.email;
		this.type = builder.type;
	}

	public String getAccount() {
		return account;
	}

	public String getEmail() {
		return email;
	}

	public Integer getType() {
		return type;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String account;
		private String email;
		private Integer type;

		public Builder account(String account) {
			this.account = account;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder type(Integer type) {
			this.type = type;
			return this;
		}

		public ExternalAuthenticated build() {
			return new ExternalAuthenticated(this);
		}
	}
}
