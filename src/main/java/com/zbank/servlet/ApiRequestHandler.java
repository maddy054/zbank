package com.zbank.servlet;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.zbank.enums.Gender;
import com.zbank.enums.TransactionDetail;
import com.zbank.enums.TransactionPeriod;
import com.zbank.enums.UserType;
import com.zbank.exceptions.BankingException;
import com.zbank.logics.ZBank;
import com.zbank.models.Account;
import com.zbank.models.Branch;
import com.zbank.models.Customer;
import com.zbank.models.Employee;
import com.zbank.models.Transaction;
import com.zbank.models.TransactionReq;
import com.zbank.utilities.JSONConverter;

public class ApiRequestHandler {
	
	public String handleGetCustomer(HttpServletRequest request) {
		ZBank zbank = new ZBank();
    	String userId = request.getParameter("userId");
    	String json = new String();
    	try {
    		if(userId != null) {
    			Customer customer  = zbank.getCustomerDetails(Integer.parseInt(userId));	
    			json = JSONConverter.getJson(customer);
    		}else {
    			List<Customer> allCustomer = zbank.getAllCustomer((int) zbank.getAllCustomercount(), 0);
    			json = JSONConverter.getJson(allCustomer);
    		}
    		
		} catch (NumberFormatException | BankingException e) {
				e.printStackTrace();
		}
    	return json;
    	
    			
	}
	
	public String handleGetEmployee(HttpServletRequest request) {
		ZBank zbank = new ZBank();
    	String userId = request.getParameter("userId");
    	String json = new String();
    	try {
    		if(userId != null) {
    			Employee employee  = zbank.getEmployeeDetails(Integer.parseInt(userId));	
    			json = JSONConverter.getJson(employee);
    		}else {
    			List<Employee> allEmployee = zbank.getAllEmployees((int) zbank.getemployeeCount(), 0);
    			json = JSONConverter.getJson(allEmployee);
    		}
    		
		} catch (NumberFormatException | BankingException e) {
				e.printStackTrace();
		}
    	return json;
    	
	}
	public String handleGetAccount(HttpServletRequest request) {
		ZBank zbank = new ZBank();
    	String accountNumber = request.getParameter("accountNumber");
    	String branchId = request.getParameter("branchId");
    	String json = new String();
    	
    	try{
    		if(accountNumber != null) {
    			Account account = zbank.getAccount(Long.parseLong(accountNumber));
    			json = JSONConverter.getJson(account);
    			
    		}else if(branchId != null){
    			
    			Map<Integer, Map<Long, Account>> accounts = zbank.getAllAccounts(Integer.parseInt(branchId),(int) zbank.getAccountCount(Integer.parseInt(branchId)), 0);
    			
    			json = JSONConverter.getJson(accounts);
    		}
    	} catch (NumberFormatException | BankingException e) {
			e.printStackTrace();
	}
    	return json;
	}
	
	public String handleGetBranch(HttpServletRequest request) {
		ZBank zbank = new ZBank();
    	String branchId = request.getParameter("branchId");
    	String json = new String();
    	
    	try{
    		if(branchId != null) {
    			Branch branch = zbank.getBranch(Integer.parseInt(branchId));
    			json = JSONConverter.getJson(branch);
    			
    		}else {
    			
    			Map<Integer, Branch> branches = zbank.getAllBranch((int)zbank.getBranchCount(), 0);
    			
    			json = JSONConverter.getJson(branches);
    		}
    	} catch (NumberFormatException | BankingException e) {
			e.printStackTrace();
	}
    	return json;
	}
	
	public String handleGetStatement(HttpServletRequest request) {
		ZBank zbank = new  ZBank();
		String json = new String();
		String accountNumber = request.getParameter("accountNumber");
		try {
			if(accountNumber != null) {
				TransactionReq requirement = new TransactionReq();
				requirement.setAccountNumber(Long.parseLong(accountNumber));
				requirement.setTime(TransactionPeriod.PAST_THREE_MONTH);
				requirement.setType(TransactionDetail.ALL);
				
				List<Transaction> statement = zbank.getAccountTransaction(requirement);
				json = JSONConverter.getJson(statement);
			}
			
			
		}catch (NumberFormatException | BankingException e) {
			e.printStackTrace();
		}
		return json;
		
	}

	public void handleAddEmployee(HttpServletRequest request) {
		HashMap<String, Object> json = JSONConverter.getMapFromJson(request);
		Employee employee = new Employee();
		employee.setName( (String)json.get("name"));
		employee.setMobile( ((BigInteger)json.get("mobile")).longValue());
		employee.setBranchId(((BigInteger) json.get("branchId")).intValue());
		employee.setEmail((String)json.get("email"));
		employee.setGender(Gender.valueOf((String)json.get("gender")));
		employee.setCreatedBy( ((BigInteger) json.get("createdBy")).intValue());
		employee.setCreatedTime(System.currentTimeMillis());
		employee.setPassword((String) json.get("password"));
		employee.setModifiedBy(((BigInteger)json.get("createdBy")).intValue());
		employee.setRole(UserType.valueOf((String) json.get("role")));
		ZBank zbank = new ZBank();
		try {
			zbank.addEmployees(employee);
		} catch (BankingException e) {
			e.printStackTrace();
		}
	}
	
	
}
