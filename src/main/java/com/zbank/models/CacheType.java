package com.zbank.models;

public enum CacheType {
	CUSTOMER("Customer"),
	BRANCH("Branch"),
	ACCOUNT("Account"),
	EMPLOYEE("Employee"),
	STATUS("UserStatus");

	private CacheType(String value) {
		this.value = value;
	}
	private String value;
	
	public String get() {
		return this.value;
	}
}
