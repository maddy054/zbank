package com.zbank.persistence;

import java.util.List;
import java.util.Map;

import com.zbank.enums.Status;
import com.zbank.enums.UserType;
import com.zbank.exceptions.BankingException;

import com.zbank.models.Account;
import com.zbank.models.Branch;
import com.zbank.models.Customer;
import com.zbank.models.Employee;
import com.zbank.models.Transaction;
import com.zbank.models.TransactionReq;
import com.zbank.models.User;


public interface Connector {

	public  String getPassword(int userId) throws  BankingException;
	
	public UserType getRole(int userId) throws BankingException ;
	
	public void addEmployee(Employee employee) throws BankingException;
	
	public void addBranch(Branch branch) throws BankingException;
	
	public void addCustomer(Customer customer) throws BankingException;
	
	public void addAccount(Account account) throws BankingException  ;
	
	public int getUserId(long accNo) throws BankingException;
	
	public List<Long> getAccountNumbers(int userId) throws BankingException;
	
	public long getBalance(long accountNumber) throws BankingException ;
	
	public void changePassword(int userId,String password) throws BankingException;
	
	public Map<Integer,Branch> getAllBranches(int limit,long offset)  throws BankingException;
	
	public Customer getCustomerDetails(int userId) throws BankingException;
	
	public  Map<Long,Account> getAccountDetails(int userId) throws BankingException;
	
	public Map<Integer,Map<Long,Account>> getAllAccounts(int branchId,int limit, long offset2) throws BankingException;
	
	public boolean isActive(long accountNumber) throws BankingException ;
	
	public List<Transaction> getTransactionDetail(TransactionReq requirement) throws BankingException;
	
	public void updateTransaction(Transaction transaction) throws BankingException;
	
	public void setAccountStatus(long accountNumber,Status status) throws BankingException ;
	
	public void getUser(User user, int userId) throws BankingException ;
	
	public void setUserStatus(int userId, Status status) throws BankingException;
	
	public void verifyAccount(int userId,long accountNumber) throws BankingException ;

	public long getOverAllbalance(int userId) throws BankingException;

	public Employee getEmployeeDetails(int userId) throws BankingException;

	public int getBranchId(int userId) throws BankingException; 

	
	public List<Customer> getAllCustomer(int limit,long offset) throws  BankingException ;
	
	public List<Employee> getAllEmployee(int limit,long offset) throws BankingException ;
	
	public long getBranchCount() throws BankingException;
	
	public long getCustomerCount(int branchId) throws BankingException ;
	
	public long getAllCustomerCount() throws BankingException ;
	
	public long getEmployeeCount() throws BankingException;
	
	public String getBranchName(int branchId) throws BankingException;
	
	public Account getAccount(long accountNumber) throws BankingException;
	
	public long getAccountCount(int branchId) throws BankingException ;
	
	public Branch getBranch(int branchId) throws BankingException ;
	
	public void updateCustomer(Customer customer) throws BankingException;
	
	public void updateUser(User user) throws BankingException ;
}

