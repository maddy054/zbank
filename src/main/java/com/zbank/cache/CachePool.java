package com.zbank.cache;

import com.zbank.models.Account;
import com.zbank.models.Branch;
import com.zbank.models.CacheType;
import com.zbank.models.Customer;
import com.zbank.models.Employee;

public class CachePool {
	private static Cache<Long,Account> accountCache =  new LruCache<Long, Account>(CacheType.ACCOUNT);
	private static  Cache<Integer,Customer> customerCache= new LruCache<>(CacheType.CUSTOMER);
	private static Cache<Integer,Branch> branchCache = new LruCache<Integer, Branch>(CacheType.BRANCH);
	private static Cache<Integer,Employee> employeeCache =  new LruCache<Integer, Employee>(CacheType.EMPLOYEE);
	
	public static Cache<Integer,Customer> getCustomerCache(){
		
		return customerCache;
	}
	
	public static Cache<Integer, Employee> getEmployeeCache(){
		return employeeCache;
	}
	public static Cache<Integer, Branch> getBranchCache(){
		return branchCache;
	}
	public static Cache<Long, Account> getAccountCache(){
		return accountCache;
	}
	public void clearCache() {
		customerCache.clear();
		employeeCache.clear();
		accountCache.clear();
		branchCache.clear();
		 
	}
	
	
	
}
