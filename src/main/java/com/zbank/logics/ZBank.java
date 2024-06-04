package com.zbank.logics;


import java.util.List;
import java.util.Map;

import com.zbank.cache.CachePool;
import com.zbank.enums.ErrorCode;
import com.zbank.enums.Status;
import com.zbank.enums.TransactionDescription;
import com.zbank.enums.TransactionStatus;
import com.zbank.enums.TransactionType;
import com.zbank.enums.UserType;
import com.zbank.exceptions.BankingException;
import com.zbank.models.Account;
import com.zbank.models.ApiData;
import com.zbank.models.Branch;
import com.zbank.models.Customer;
import com.zbank.models.Employee;
import com.zbank.models.OperationLog;
import com.zbank.models.Transaction;
import com.zbank.models.TransactionReq;
import com.zbank.models.User;
import com.zbank.persistence.Connector;
import com.zbank.persistence.DbConnector;
import com.zbank.utilities.ApiKeyGenerator;
import com.zbank.utilities.SHAHash;
import com.zbank.utilities.Validation;

public class ZBank {
	private Connector dbConnector = new DbConnector();
	
	public UserType getUser(int userId) throws BankingException {
		return dbConnector.getRole(userId);
	}
	
	public void checkPassword(int userId, String password) throws BankingException {

			String originalPassword = dbConnector.getPassword(userId);
			System.out.println(originalPassword);
			String enteredPassword = SHAHash.getHash(password);
			
			boolean isCorrect = originalPassword.equals(enteredPassword);
			if(!isCorrect) {
				throw new BankingException(ErrorCode.WRONG_PASSWORD);
			}
	}

	public void addEmployees(Employee emploee) throws BankingException{
		Integer lock = emploee.getUserId();
		synchronized (lock) {
			String password =  SHAHash.getHash(emploee.getPassword());
		    emploee.setPassword(password);
		    emploee.setRole(UserType.EMPLOYEE);
			dbConnector.addEmployee(emploee);	
		}
		
	}
	
	public int getUserId(long accountNumber) throws BankingException {
		return dbConnector.getUserId(accountNumber);
	}
	
	public void addBranch(Branch branch) throws BankingException {

        dbConnector.addBranch(branch);	
	}
	
	public int getBranchId(int userId) throws BankingException {
		 return dbConnector.getBranchId(userId);
	}
	public int addCustomer(Customer customer) throws BankingException {
		
		 String password =  SHAHash.getHash(customer.getPassword());
		customer.setPassword(password);
	  return 	dbConnector.addCustomer(customer);
	}
	
	public void addAccount(Account account) throws BankingException{

		dbConnector.addAccount(account);
	}
	
	public void changePassword(int userId,String oldPassword,String newPassword) throws BankingException {
		checkPassword(userId, oldPassword);
		
		Validation.isvalidPassword(newPassword);
		dbConnector.changePassword(userId, SHAHash.getHash(newPassword));
	}
	
	public List<Long> getAccountNumbers(int userId) throws BankingException {
		return dbConnector.getAccountNumbers(userId);
	}
	
	public void transferMoney(Transaction transaction) throws BankingException {
		
		TransactionDescription description = transaction.getDescription();
		
		long accountNumber = transaction.getAccountNo();
		transaction.setUserId(dbConnector.getUserId(accountNumber));
		
		boolean state = dbConnector.isActive(accountNumber);
		
		if(!state) {
			
			throw new BankingException("Your account is inactive ");
		}
		
		
		long balance = dbConnector.getBalance(accountNumber);
		int amount = transaction.getAmount();
		
		long closingBalance = balance - amount;
		TransactionType transactionType = TransactionType.DEBIT ;
		
		transaction.setStatus(TransactionStatus.SUCCESS);
		transaction.setOpenBalance(balance);
		transaction.setDateTime(System.currentTimeMillis());
	
		if(description != TransactionDescription.DEPOSIT) {
			if(balance < amount) {	
				
				transaction.setType(transactionType);
		        transaction.setCloseBalance(balance);
		        transaction.setStatus(TransactionStatus.FAILED);
		        dbConnector.updateTransaction(transaction);
		        
    			throw new BankingException("Insufficient balance");
   			}
		}
        switch(description) {

        case DEPOSIT:
        	transactionType = TransactionType.CREDIT;
        	closingBalance = balance + amount;
   		  	break;   
   		  	
        case INTRA_BANK:
        	
        	transaction.setType(transactionType);
            transaction.setCloseBalance(closingBalance);
        	dbConnector.updateTransaction(transaction);
        	
        	long receiverAccount = transaction.getTransactionAccNo();
        	
        	transaction.setUserId(dbConnector.getUserId(receiverAccount));
        	
        	long receiverBalance = dbConnector.getBalance(receiverAccount);
        	transaction.setOpenBalance(receiverBalance);
        	
        	transaction.setTransactionAccNo(accountNumber);
        	transaction.setAccountNo(receiverAccount);
        	
        	transactionType = TransactionType.CREDIT;
        	closingBalance = receiverBalance + amount;
        	
        	break;
		default:
			break;
        	
   		}
        transaction.setType(transactionType);
        transaction.setCloseBalance(closingBalance);
        dbConnector.updateTransaction(transaction);
   }
		

	
	public void accountDeactivate(long accountNumber,Status status) throws BankingException {
		dbConnector.setAccountStatus(accountNumber, status);
	}
	
	public void userDeactivate(int userId,Status status) throws BankingException {
		dbConnector.setUserStatus(userId, status);
	}
	
	public Map<Integer, Branch> getAllBranch(int limit,long offset) throws BankingException {
		return dbConnector.getAllBranches(limit,offset);
	}
	
	public  Customer getCustomerDetails(int userId) throws BankingException {
		
	return	CachePool.getCustomerCache().getValue(userId);
		//return dbConnector.getCustomer(userId);
	}
	
	public Employee getEmployeeDetails(int userId) throws BankingException {
		return CachePool.getEmployeeCache().getValue(userId);
		//return dbConnector.getEmployeeDetails(userId);
	}
	
	public User getAdminDetail(int userId) throws BankingException {
		User user = new User();
	
		dbConnector.getUser(user, userId);
		return user;
	}
	public  Map<Long, Account> getAccountDetails(int userId) throws BankingException {
		return dbConnector.getAccountDetails(userId);
	}
	public Map<Integer,Map<Long,Account>> getAllAccounts(int branchId,int limit,long offset) throws BankingException{
		return dbConnector.getAllAccounts(branchId,limit, offset);
	}
	
   public List<Transaction> getAccountTransaction(TransactionReq requirement) throws BankingException{
	   
	   return dbConnector.getTransactionDetail(requirement);
   }
  
   public long getAccountBalance(int userId,long accountNumber) throws BankingException {
	   dbConnector.verifyAccount(userId,accountNumber);
	   
	   return dbConnector.getBalance(accountNumber);
   }
   public long getOverAllBalance(int userId) throws BankingException {
	   return dbConnector.getOverAllbalance(userId);
   }
   
   public List<Customer> getAllCustomer(int limit,long offset) throws BankingException{
	   return dbConnector.getAllCustomer(limit, offset);
   }
   
   public List<Employee> getAllEmployees(int limit,long offset) throws BankingException{
	   return dbConnector.getAllEmployee(limit, offset);
   }
   
   public long getBranchCount() throws BankingException {
	   return dbConnector.getBranchCount();
   }
   
   public long getCustomerCount(int branchId) throws BankingException {
	   return dbConnector.getCustomerCount(branchId);
   }
   
   public long getAllCustomercount() throws BankingException {
	   return dbConnector.getAllCustomerCount();
   }
   public long getemployeeCount() throws BankingException {
	   return dbConnector.getEmployeeCount();
	   
   }
   
   public String getBranchName(int branchId) throws BankingException {
	   return dbConnector.getBranchName(branchId);
   }
   
   public Account getAccount(long accountNumber) throws BankingException {
	   return dbConnector.getAccount(accountNumber);
   }
   
   public long getAccountCount(int branchId) throws BankingException {
	   return dbConnector.getAccountCount(branchId);
   }
   public Branch getBranch(int branchId) throws BankingException {
	   return dbConnector.getBranch(branchId);
   }
   public void updateCustomer(Customer customer) throws BankingException {
	   dbConnector.updateCustomer(customer);
   }
   public void updateEmployee(Employee employee) throws BankingException {
	   dbConnector.updateUser(employee);
   }
   public void updateLog(OperationLog log) throws BankingException {
	   dbConnector.updateLog(log);
   }
   public OperationLog getRecentLog(int userId) throws BankingException {
	  return  dbConnector.getRecentLogs(userId);
   }
   public List<OperationLog> getAllLogs(int userId) throws BankingException {
	   return dbConnector.getLogs(userId);
   }
   public int getUsersId(long mobile) throws BankingException {
	   return dbConnector.getUsersId(mobile);
   }
   public String setApiData(ApiData api) throws BankingException {
	   String apiKey = ApiKeyGenerator.generateApiKey(); 
	   api.setApiKey(SHAHash.getHash(apiKey));
	   dbConnector.addApi(api);
	   return apiKey;
   }
   public void validateApi(String api) throws BankingException {
	   dbConnector.validateApi(api);
   }
}
