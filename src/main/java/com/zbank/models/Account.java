package com.zbank.models;

import java.io.Serializable;

import com.zbank.enums.AccountType;
import com.zbank.enums.Status;

public class Account implements Serializable{
	private int userId;
	private long accountNo;
	private long balance;
	private AccountType accountType;
	private int BranchId;
	private String branchName;
	private long createdTime;
	private long modifiedTime;
	private int modifiedBy;
	
	
	public long getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}
	public long getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	private Status accountStatus = Status.ACTIVE;
	
	public Status getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(Status accountStatus) {
		this.accountStatus = accountStatus;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public int getBranchId() {
		return BranchId;
	}
	public void setBranchId(int branchId) {
		BranchId = branchId;
	}
}
