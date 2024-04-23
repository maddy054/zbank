package com.zbank.models;

import com.zbank.enums.ApiAuthorization;

public class ApiData {
	private String ApiKey;
	private long createdAt;
	private int createdBy;
	private ApiAuthorization authorization;
	
	public String getApiKey() {
		return ApiKey;
	}
	public void setApiKey(String apiKey) {
		ApiKey = apiKey;
	}
	public long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public ApiAuthorization getAuthorization() {
		return authorization;
	}
	public void setAuthorization(ApiAuthorization authorization) {
		this.authorization = authorization;
	}
	
	

}
