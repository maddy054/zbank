package com.zbank.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbank.utilities.Validation;
import com.zbank.enums.AccountType;
import com.zbank.enums.ErrorCode;
import com.zbank.enums.Gender;
import com.zbank.enums.Status;
import com.zbank.enums.TransactionDescription;
import com.zbank.enums.TransactionDetail;
import com.zbank.enums.TransactionPeriod;
import com.zbank.enums.TransactionType;
import com.zbank.enums.UserType;
import com.zbank.exceptions.BankingException;
import com.zbank.logics.ZBank;
import com.zbank.models.Account;
import com.zbank.models.Branch;
import com.zbank.models.Customer;
import com.zbank.models.Employee;
import com.zbank.models.Transaction;
import com.zbank.models.TransactionReq;
import com.zbank.models.User;

public class RequestHandler {
	
	int limit = 15;
	public void doTransaction(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		ZBank zbank = new ZBank();
		Transaction transaction = new Transaction();
		transaction.setCreatedTime(System.currentTimeMillis());
		transaction.setModifiedBy((int)request.getSession(false).getAttribute("userId"));
		if(request.getParameter("accountNumber") != null) {
			long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
			
			if(request.getParameter("receiverAccount") != null) {
				long receiverAccount = Long.parseLong(request.getParameter("receiverAccount"));
				transaction.setTransactionAccNo(receiverAccount);
			}
			
			int amount = Integer.parseInt(request.getParameter("amount"));
			
			TransactionDescription description = TransactionDescription.valueOf(request.getParameter("description"));
			
			
			transaction.setAccountNo(accountNumber);
			transaction.setAmount(amount);
			
			transaction.setDescription(description);
			
			TransactionType type = TransactionType.DEBIT;
			
			if(description == TransactionDescription.WITHDRAW) {
				type = TransactionType.CREDIT;
			}
			transaction.setType(type);
			
			try {
				zbank.transferMoney(transaction);
				request.setAttribute("status", "Transaction Successful");
			} catch (BankingException e) {
				request.setAttribute("status", "Transaction Failed");
				e.printStackTrace();
			}finally {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/transaction.jsp");
				dispatcher.forward(request, response);	
			}
		}
		
	}
	
	public void createCustomer(HttpServletRequest request,HttpServletResponse response) {
	
	if(request.getParameter("name") != null) {
		
		ZBank zbank = new ZBank();
		Customer customer = new Customer();
		
		customer.setName(request.getParameter("name"));
		customer.setMobile(Long.parseLong( request.getParameter("mobile")));
		customer.setEmail(request.getParameter("email"));
		customer.setAge(Integer.parseInt(request.getParameter("age")));
		customer.setGender(Gender.valueOf(request.getParameter("gender")));
		customer.setAddress(request.getParameter("address"));
		customer.setAadhar(Long.parseLong ((request.getParameter("aadhar"))));
		customer.setPan(request.getParameter("pan"));
		customer.setPassword(request.getParameter("password"));
		customer.setRole(UserType.CUSTOMER);
		
		customer.setCreatedTime(System.currentTimeMillis());
		customer.setModifiedBy((int)request.getSession().getAttribute("userId"));
		request.setAttribute("type", "customer");
		try {
			zbank.addCustomer(customer);
		} catch (BankingException e) {
		
			e.printStackTrace();
		}
	}
	
	}
	
	public void deactivateCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		ZBank zbank = new ZBank();
		if(request.getParameter("userId") != null) {
			
			int userId =Integer.parseInt(request.getParameter("userId"));
			try {
				zbank.userDeactivate(userId, Status.INACTIVE);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/deactivate-customer.jsp");
				dispatcher.forward(request, response);
			} catch (BankingException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
	public void getStatement(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ZBank zbank = new ZBank();
		try {
		long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
		TransactionPeriod period = TransactionPeriod.valueOf(request.getParameter("period"));
		
		TransactionReq requirement = new  TransactionReq();
		requirement.setAccountNumber(accountNumber);
		requirement.setTime(period);
		requirement.setType(TransactionDetail.ALL);
		requirement.setUserId(zbank.getUserId(accountNumber));
		requirement.setLimit(limit);
		requirement.setOffset(getOffset(request));
		
		List<Transaction> transactions = zbank.getAccountTransaction(requirement);
		request.setAttribute("statement", transactions);
		 request.setAttribute("page",getPages(zbank.getBranchCount()));

		}catch (BankingException e) {
			e.printStackTrace();
		}
 	}
	
	public void getProfile(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException, BankingException {
		
		UserType user =  (UserType) request.getSession().getAttribute("userType");
		ZBank zbank = new ZBank();
		
		int userId = (int) request.getSession().getAttribute("userId");
		switch (user) {
		case EMPLOYEE:
			
			Employee employee = zbank.getEmployeeDetails(userId);
			request.setAttribute("details", employee);
		
			break;
		case CUSTOMER :
			Customer customer = zbank.getCustomerDetails(userId);
			request.setAttribute("details", customer);
			
			break;
		case ADMIN:
			
			User admin = zbank.getAdminDetail(userId);
			request.setAttribute("details",  admin);
			break;
			
		default:
			break;
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/profile.jsp");
		
		dispatcher.forward(request, response);
	}
	
	public void isValidSession(HttpServletRequest request) throws BankingException {
		
		if((request.getSession() == null || request.getSession(false).getAttribute("userId") == null) ) {
			throw new BankingException(ErrorCode.INVALID_SESSION);
		}
	}
	
	public void invalidateSession(HttpServletRequest request) {
		request.getSession(false).invalidate();
		
	}
	

	
	public void createAccount(HttpServletRequest request,HttpServletResponse response) throws BankingException, ServletException, IOException {
		
		ZBank zbank = new ZBank();
		int userId = (int) request.getSession(false).getAttribute("userId");
		int branchId = zbank.getBranchId(userId);
		
	Account account = new Account();
	
	account.setUserId( Integer.parseInt( request.getParameter("userId")));
	account.setBranchId(branchId);
	account.setAccountType(AccountType.valueOf(request.getParameter("type")));
	account.setCreatedTime(System.currentTimeMillis());
	account.setModifiedBy((int)request.getSession().getAttribute("userId"));
	
	zbank.addAccount(account);
	RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/create-account.jsp");
	dispatcher.forward(request, response);
	
	}
	
	public void deactivateAccount(HttpServletRequest request, HttpServletResponse response) throws BankingException, ServletException, IOException {
		ZBank zbank = new ZBank();
		long accountNumber = Long.parseLong(request.getParameter("accountNumber"));
		
		zbank.accountDeactivate(accountNumber,Status.INACTIVE);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/deactivate-account.jsp");
		dispatcher.forward(request, response);

	}
	
	public void getAllAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, BankingException {
		ZBank zbank = new ZBank();
		int userId = (int) request.getSession(false).getAttribute("userId");
		
		int branchId = zbank.getBranchId(userId);
		
		  long offset = getOffset(request);
		
	 Map<Long, Account> employeeAccounts = zbank.getAllAccounts(branchId,limit,offset).get(branchId);
		request.setAttribute("accounts", employeeAccounts);
		request.setAttribute("page",getPages(zbank.getAccountCount((int)request.getSession(false).getAttribute("branchId") )));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/employee-account.jsp");
		dispatcher.forward(request, response);
		
	}
	
	public void checkPassword(HttpServletRequest request) throws BankingException {
		ZBank zbank = new ZBank();
		int userId =  Integer.parseInt( request.getParameter("userId"));
		String password = (String) request.getParameter("password");

		zbank.checkPassword(userId, password);

		UserType type = zbank.getUser(userId);
		
		System.out.println("user "+type);
		request.getSession(true).setAttribute("userId", userId);
		
		request.getSession(false).setAttribute("userType",type);
		if(type != UserType.CUSTOMER) {
			request.getSession(false).setAttribute("branchId",zbank.getBranchId(userId) );
		}
		
	
	}
	
	public void getDashboard(HttpServletRequest request, HttpServletResponse response) throws BankingException,ServletException, IOException {

	    UserType type = (UserType)request.getSession(false).getAttribute("userType");
		switch (type) {
			
			case CUSTOMER:
				response.sendRedirect("accounts");
				break;
			case EMPLOYEE:
				response.sendRedirect("search-customer");
				break;
				
			case ADMIN:
				response.sendRedirect("search-branch");
				break;
			default:
				break;
			}
	}
	
	public void getLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher( "/JSP/login.jsp");
		dispatcher.forward(request, response);
	}
	
	
	public void setAccountNumber(HttpServletRequest request, HttpServletResponse response) throws BankingException {
		try {
			request.setAttribute("isHaveAccounts", true);
		if(request.getSession(false).getAttribute("userType") == UserType.CUSTOMER) {
			ZBank zbank =  new ZBank();
			List<Long> accounts = zbank.getAccountNumbers((int) request.getSession(false).getAttribute("userId"));
			
			request.setAttribute("accounts", accounts);
		}
		}catch (BankingException e) {
			if(e.getErrorCode() == ErrorCode.NO_ACCOUNT_FOUND) {
				request.setAttribute("isHaveAccounts", false);
			}
			else {
				throw e;
			}
		}
	}
	
	
	   public void getCustomerAccounts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, BankingException {
		   ZBank zbank = new ZBank();
		   Map<Long, Account> account = zbank.getAccountDetails((int)request.getSession().getAttribute("userId"));
		   request.setAttribute("accountDetails", account);
		   RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/customer-account.jsp");
		   dispatcher.forward(request, response);
			
		}
	   
	   public void setBranchDetails(HttpServletRequest request) throws BankingException {
		   ZBank zbank = new ZBank();
		   
		  
		   long offset = getOffset(request);
		   Map<Integer, Branch> branch = zbank.getAllBranch(limit,offset);
	
		  
		   request.setAttribute("page",getPages(zbank.getBranchCount()));
		   request.setAttribute("branches", branch);
	   }
	   
	   public void setAllCustomerDetail(HttpServletRequest request) throws BankingException {
		   ZBank zbank = new ZBank();
		   long offset = getOffset(request);
		  List<Customer> Customer = zbank.getAllCustomer(limit, offset);
		  request.setAttribute("page",getPages(zbank.getAllCustomercount()));
		  request.setAttribute("customer", Customer);
		  request.setAttribute("type", "customer");
	   }
	   
	   public void setAllEmplyeeDetail(HttpServletRequest request) throws BankingException {
		   ZBank zbank =  new  ZBank();
		   
		   long offset = getOffset(request);
		   List<Employee> employees = zbank.getAllEmployees(limit, offset);
		   
		   request.setAttribute("page",getPages(zbank.getemployeeCount()));
		   request.setAttribute("emolyees", employees);
		   request.setAttribute("type", "employee");
	   }
	   
	   public void createEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   ZBank zbank = new ZBank();
		   
		   Employee employee = new Employee();
		   	employee.setName(request.getParameter("name"));
			employee.setMobile(Long.parseLong( request.getParameter("mobile")));
			employee.setEmail(request.getParameter("email"));
			employee.setAge(Integer.parseInt(request.getParameter("age")));
			employee.setGender(Gender.valueOf(request.getParameter("gender")));
			employee.setBranchId(Integer.parseInt(request.getParameter("branchId")));
			employee.setPassword(request.getParameter("password"));
			employee.setRole(UserType.EMPLOYEE);
			employee.setCreatedTime(System.currentTimeMillis());
			employee.setModifiedBy((int)request.getSession().getAttribute("userId"));
			request.setAttribute("type", "employee");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/create-customer.jsp");
			dispatcher.forward(request, response);
			try {
				zbank.addEmployees(employee);
			}catch(BankingException e) {
				e.printStackTrace();
			}
		   
		   
	   }
	   public void createBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   ZBank zbank = new ZBank();
		   Branch branch = new Branch();
		   String branchName = request.getParameter("branchName");
		   branch.setBranchName(branchName);
		   branch.setIfsc(Long.parseLong( request.getParameter("ifsc")));
		   
		   String address = request.getParameter("address");
		   
		   branch.setAddress(address);
		   branch.setCreatedTime(System.currentTimeMillis());
		   branch.setModifiedBy((int)request.getSession().getAttribute("userId"));
		   
		   Validation validition = new Validation();
		    validition.deleteTag(address);
		    validition.deleteTag(branchName);
		   
		   try {
		   zbank.addBranch(branch);
		   request.setAttribute("message", "Branch created successfully");
		   }catch (BankingException e) {
			   request.setAttribute("message", "Branch creation failed");
			e.printStackTrace();
		}finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/create-branch.jsp");
			dispatcher.forward(request, response);
		}
		   
	   }
	   private long getOffset(HttpServletRequest request) {
		   long offset = 0;
		   String page = request.getParameter("pageNo");
		   long pageNo =1;
		   if(page != null) {
			   
			   pageNo = Long.parseLong(page);
			   offset = (pageNo-1)*limit;
		   }
		   
		   request.setAttribute("pageNo", pageNo);
		   return offset;
	   }
	   
	   private long getPages(long l) {
		   long pages = l/limit;
		   
		   if(l%limit != 0) {
			   pages = pages+1;
		   }
		   return pages;
	   }
	   public void handleChangePass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String oldPass = request.getParameter("oldPass");
		   String newPass = request.getParameter("newPass");
		   int userId = (int)request.getSession(false).getAttribute("userId");
		   
		   ZBank zbank = new ZBank();
		   try {
			zbank.changePassword(userId,oldPass, newPass);
			request.setAttribute("message", "Password changed successfully!");
		} catch (BankingException e) {
			ErrorCode error = e.getErrorCode();
			
			if(error == ErrorCode.WRONG_PASSWORD) {
				request.setAttribute("message", "You Entered a wrong passwords");
			}else if(error == 	ErrorCode.INVALID_PATTERN) {
				request.setAttribute("message", "Invalid password pattern");
			}
			e.printStackTrace();
		}finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/change-password.jsp");
			dispatcher.forward(request, response);
		}
		   
	   }
	   
	   public void handleSearchCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   ZBank zbank =  new ZBank();
		   Customer customer = new  Customer();
		   try {
			   System.out.println("test");
			   request.setAttribute("type", "customer");
			   customer = zbank.getCustomerDetails(Integer.parseInt(request.getParameter("userId")));
			   request.setAttribute("user", customer);
			
		} catch ( BankingException e) {
			
			ErrorCode error = e.getErrorCode();
			if(error == ErrorCode.WRONG_USER_ID || error == ErrorCode.INVALID_CUSTOMER) {
				request.setAttribute("message","No such Customer");
			}
		
			e.printStackTrace();
		}finally {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/search-customer.jsp");
			dispatcher.forward(request, response);
		}
		   
	   }
	   public void handleSearchAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   ZBank zbank =  new ZBank();
		   try {
			   Account account = zbank.getAccount(Long.parseLong( request.getParameter("accountNumber")));
			   request.setAttribute("account", account);
			   
		   }catch(BankingException e) {
			   if(e.getErrorCode() == ErrorCode.WRONG_ACCOUNT_NUMBER) {
				   request.setAttribute("message", "Wrong Account number");
			   }
		   }finally {
			   RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/search-account.jsp");
				dispatcher.forward(request, response);
		   }
	   }
	   public void handleEmployeeDash(HttpServletRequest request, HttpServletResponse response) {
		   ZBank zbank = new ZBank();
		   
		   try {
			   
			   int branchId = (int)request.getSession(false).getAttribute("branchId");
			   
			   request.setAttribute("branchName", zbank.getBranchName(branchId));
			long customerCount = zbank.getCustomerCount(branchId);
			request.setAttribute("customerCount", customerCount);
			
			long accountCount = zbank.getAccountCount(branchId);
			request.setAttribute("accountCount", accountCount);
			
			
		} catch (BankingException e) {
			
			e.printStackTrace();
		}
		   
	   }
	   
	   public void handleSearchEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   ZBank zbank = new ZBank();
		   
		   try {
			   Employee employee =  zbank.getEmployeeDetails(Integer.parseInt( request.getParameter("userId")));
			  request.setAttribute("type", "employee");
			  request.setAttribute("user", employee);
			   
		   }catch(BankingException e) {
			   ErrorCode error = e.getErrorCode();
			   if(error == ErrorCode.INVALID_EMPLOYEE || error == ErrorCode.WRONG_USER_ID) {
				   request.setAttribute("message", "No such Employee");
			   }
			   e.printStackTrace();
		   }finally {
			   RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/search-customer.jsp");
				dispatcher.forward(request, response);
		   }
	   }
	   public void handleSearchBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   ZBank zbank = new ZBank();
		   
		   try {
			 Branch branch=   zbank.getBranch(Integer.parseInt( request.getParameter("branchId")));
			  request.setAttribute("branch", branch);
		   }catch(BankingException e) {
			   if(e.getErrorCode() == ErrorCode.INVALID_BRANCH) {
				   request.setAttribute("message", "No such Branch");
			   }
			   e.printStackTrace();
		   }finally {
			   RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/search-branch.jsp");
				dispatcher.forward(request, response);
		   }
		   
	   }
	   public void handleEditCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		   ZBank zbank = new ZBank();
		   try {
			   int userId =Integer.parseInt(request.getParameter("userId"));
			   Customer customer =  zbank.getCustomerDetails(userId);
			   customer.setName(request.getParameter("name"));
			   customer.setEmail(request.getParameter("email"));
			   customer.setMobile(Long.parseLong(request.getParameter("mobile")));
			   customer.setAadhar(Long.parseLong( request.getParameter("aadhar")));
			   customer.setPan(request.getParameter("pan"));
			   customer.setAddress(request.getParameter("address"));
			   customer.setModifiedTime(System.currentTimeMillis());
			   customer.setModifiedBy((int) request.getSession(false).getAttribute("userId"));
			   zbank.updateCustomer(customer);
			   handleSearchCustomer(request, response);
		   }catch(BankingException e) {
			   e.printStackTrace();
			  request.setAttribute("message", "Update Failed");
		   }finally {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/search-customer.jsp");
				dispatcher.forward(request, response);
			}
		   
		   
	   }
	   
	   public void handleEditEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		   ZBank zbank = new ZBank();
		   try {
			   int userId =Integer.parseInt(request.getParameter("userId"));
			   Employee employee = zbank.getEmployeeDetails(userId);
			   employee.setName(request.getParameter("name"));
			   employee.setMobile(Long.parseLong( request.getParameter("mobile")));
			   employee.setEmail(request.getParameter("email"));
			   employee.setModifiedTime(System.currentTimeMillis());
			   employee.setModifiedBy((int)request.getSession(false).getAttribute("userId"));
			   
			   zbank.updateEmployee(employee);
			   handleSearchEmployee(request, response);
		   }catch(BankingException e) {
			   e.printStackTrace();
			   request.setAttribute("message", "Update Failed ");
		   }finally {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/search-employee.jsp");
				dispatcher.forward(request, response);
			}
		   
	   }
}
