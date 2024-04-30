package com.zbank.enums;

public enum Table {
	USER("USER_DETAILS"),
	EMPLOYEE("EMPLOYEE_DETAILS"),
	CUSTOMER("CUSTOMER_DETAILS"),
	ACCOUNTS("ACCOUNT_DETAILS"),
	BRANCH("BRANCH_DETAILS"),
	TRANSACTION("TRANSACTION_DETAILS"),
	LOG("OPERATION_LOG"),
	API("API_DETAILS");
 
	private final String tableName;
    private	Table(String tableName) {
		this.tableName = tableName;
	}
    
    public String get() {
    	return tableName;
    }
}
