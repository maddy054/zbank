package com.zbank.servlethandler;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.zbank.enums.Gender;
import com.zbank.enums.Status;
import com.zbank.enums.TransactionDetail;
import com.zbank.enums.TransactionPeriod;
import com.zbank.enums.TransactionStatus;
import com.zbank.enums.UserType;
import com.zbank.exceptions.BankingException;
import com.zbank.logics.ZBank;
import com.zbank.models.Account;
import com.zbank.models.Branch;
import com.zbank.models.Customer;
import com.zbank.models.Employee;
import com.zbank.models.ResponseMessage;
import com.zbank.models.Transaction;
import com.zbank.models.TransactionReq;
import com.zbank.models.User;
import com.zbank.utilities.JSONConverter;

public class ApiRequestHandler {
	
	private long limit =15;
	public String handleGetCustomer(HttpServletRequest request) {
		
		
		ZBank zbank = new ZBank();
    	String userId = request.getParameter("userId");
    	String json = new String();
    	try {
    		if(userId != null) {
    			Customer customer  = zbank.getCustomerDetails(Integer.parseInt(userId));	
    			json = JSONConverter.getJson(customer);
    		}else {
    			
    			int pageNo = Integer.parseInt(request.getParameter("pageno"));
    			List<Customer> allCustomer = zbank.getAllCustomer(15, getOffset(pageNo-1));
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

	public String handleAddEmployee(HttpServletRequest request) {
		HashMap<String, Object> json = JSONConverter.getMapFromJson(request);
		Employee employee = new Employee();
		String status = "successfully added";
		setUser(employee, json);
		setEmployee(employee,json);
		ZBank zbank = new ZBank();
		try {
			zbank.addEmployees(employee);
			
		} catch (BankingException e) {
			e.printStackTrace();
			status = "Customer creation failed";
		}
		return status;
	}
	public String handleAddCustomer(HttpServletRequest request) throws BankingException  {
		
	HashMap<String, Object> json = JSONConverter.getMapFromJson(request);
	Customer customer = new Customer();
	
	setUser(customer, json);
	setCustomer(customer,json);
	
	int userId = customer.getUserId();
	ResponseMessage responseMessage = new ResponseMessage();
	responseMessage.setUserId(userId);
	responseMessage.setStatus(TransactionStatus.SUCCESS);
	responseMessage.setMessage("User Created Successfully");
	
	ZBank zBank = new ZBank();
	try {
		zBank.addCustomer(customer);
		
	} catch (BankingException e) {
		responseMessage.setStatus(TransactionStatus.FAILED);
		responseMessage.setMessage("User Creation Failed");
		e.printStackTrace();
	}
	return  JSONConverter.getJson(responseMessage);
	}
	
	private void setUser(User user,	HashMap<String, Object> json) {
		user.setName( (String)json.get("name"));
		user.setMobile(Long.parseLong(json.get("mobile").toString()));
		user.setAge(Integer.parseInt(json.get("age").toString()));	
		user.setEmail(json.get("email").toString());
		user.setGender(Gender.valueOf((String)json.get("gender")));
		user.setCreatedBy( 1);
		user.setCreatedTime(System.currentTimeMillis());
		user.setPassword( json.get("password").toString());
		user.setModifiedBy(1);
	
	}
	private void setCustomer(Customer customer,	HashMap<String, Object> json) {
		
		customer.setRole(UserType.CUSTOMER);
		customer.setAadhar(Long.parseLong(json.get("aadhar").toString()));
		customer.setPan(json.get("pan").toString());
		customer.setAddress(json.get("address").toString());
	}
	
	private void setEmployee(Employee employee , HashMap<String, Object> json) {
		
		employee.setBranchId(((BigInteger) json.get("branchId")).intValue());
		employee.setRole(UserType.EMPLOYEE);
	}
	
	
	public String addBranch(HttpServletRequest request) throws BankingException {
		HashMap<String, Object> json = JSONConverter.getMapFromJson(request);
		ResponseMessage responseMessage = new ResponseMessage();
		
		responseMessage.setStatus(TransactionStatus.SUCCESS);
		responseMessage.setMessage("User Activated Successfully");
		Branch branch = new Branch();
		
		branch.setBranchName(json.get("branchName").toString());
		
		branch.setIfsc(Long.parseLong(json.get("ifsc").toString()));
		branch.setAddress(json.get("address").toString());
		branch.setModifiedBy(1);
		
		ZBank zBank = new ZBank();
		try {
			zBank.addBranch(branch);
			
		}catch(BankingException e) {
			
		}
		return JSONConverter.getJson(responseMessage);
	}
	public String handleDeactivateEmployee(HttpServletRequest request) throws BankingException {
		int userId = Integer.parseInt( request.getParameter("userId"));
		
		String userStatus = request.getParameter("status");
		
	
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setUserId(userId);
		responseMessage.setStatus(TransactionStatus.SUCCESS);
		responseMessage.setMessage("User Activated Successfully");
		
		ZBank zBank =  new ZBank();
		Status statusToAchieve = Status.ACTIVE;
		if(userStatus.equals("ACTIVE")) {
			statusToAchieve = Status.INACTIVE;
			responseMessage.setMessage("User Deactivated Successfully");
		}
		try {
			zBank.userDeactivate(userId, statusToAchieve );
			
		}catch(BankingException e) {
			responseMessage.setStatus(TransactionStatus.FAILED);
			responseMessage.setMessage("Filed !! Try Again Later");
		}
		return JSONConverter.getJson(responseMessage);
		
	}
	
	public String handleUserEdit(HttpServletRequest request,UserType userType) throws BankingException {
		HashMap<String, Object> json = JSONConverter.getMapFromJson(request);
		int userId = Integer.parseInt( json.get("userId").toString());

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setUserId(userId);
		responseMessage.setStatus(TransactionStatus.SUCCESS);
		responseMessage.setMessage("Details Updated Successfully");
		ZBank zBank = new ZBank();
		try {
		if(userType == UserType.CUSTOMER) {
			Customer customer = new Customer();
			customer.setUserId(userId);
			setUser(customer, json);	
			setCustomer(customer,json);
			
			zBank.updateCustomer(customer);
		}else {
			Employee employee = new Employee();	
			employee.setUserId(userId);
			setUser(employee, json);
			zBank.updateEmployee(employee);
		}
		
		} catch (BankingException e) {
			responseMessage.setStatus(TransactionStatus.FAILED);
			responseMessage.setMessage("Failed to Update !! Try Again");
			e.printStackTrace();
		}
		return  JSONConverter.getJson(responseMessage);
		
	}
	   private long getPages(long l) {
		   long pages = l/limit;
		   
		   if(l%limit != 0) {
			   pages = pages+1;
		   }
		   System.out.println(l);
		   System.out.println(pages);
		   return pages;
	   }
	   
	   private long getOffset(int page) {
		
		   return page*limit;
	   }
	   
	   public 	String getPageCount(HttpServletRequest request) { 
		   int type = Integer.parseInt(request.getParameter("type"));
		   ZBank zBank = new  ZBank();
		   long count = 0;
		   try {
			   switch(type) {
			   case 1:
				   count = zBank.getBranchCount();
				   break;
			   case 2:
				   count = zBank.getemployeeCount();
				   break;
			   case 3:	
				 String branch = request.getParameter("branchId");
				 count = zBank.getAllCustomercount();
				
				   break;
			   }
			
		} catch (BankingException e) {
			e.printStackTrace();	
		}
		   
		   return "{\"count\":"+getPages(count)+"}";
	   }
	
	
}
