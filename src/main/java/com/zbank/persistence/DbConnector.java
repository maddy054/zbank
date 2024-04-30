package com.zbank.persistence;

import java.sql.SQLException;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zbank.enums.AccountType;
import com.zbank.enums.ErrorCode;
import com.zbank.enums.Gender;
import com.zbank.enums.Status;
import com.zbank.enums.Table;
import com.zbank.enums.TransactionDescription;
import com.zbank.enums.TransactionDetail;
import com.zbank.enums.TransactionPeriod;
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
import com.zbank.utilities.QueryBuilder;

public class DbConnector implements Connector {

	public String getPassword(int userId) throws BankingException {
		try {
			
			QueryBuilder queryBuilder = new QueryBuilder(Table.USER.get());
			String query =  queryBuilder.column(2).where(1).buildSelect();
			
			List<Map<Integer, Object>> values = queryBuilder.executeQuery(query, userId);
		if(values.isEmpty()) {
			throw new BankingException(ErrorCode.INVALID_USER);
		}
			return (String) values.get(0).get(2);
		
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		
		}
	}

	public UserType getRole(int userId) throws  BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.USER.get());
			String query =  queryBuilder.column(9).where(1).buildSelect();
			
			List<Map<Integer, Object>> values = queryBuilder.executeQuery(query, userId);
			return UserType.values()[(int) values.get(0).get(9)];
		}catch (SQLException e) {	
			throw new BankingException(e.getMessage());
		}
	}

	public void addEmployee(Employee employee) throws BankingException {
		addUser(employee);
		
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.EMPLOYEE.get());
			String query =  queryBuilder.buildInsert();
		    queryBuilder.execute(query,	getUsersId(employee.getMobile()),employee.getBranchId());
		    
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}
	
	public void addCustomer(Customer customer) throws BankingException {
		addUser(customer);
		
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.CUSTOMER.get());
			String query =  queryBuilder.buildInsert();
			queryBuilder.execute(query, getUsersId(customer.getMobile()),customer.getAadhar(),customer.getPan(),customer.getAddress());
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}

	public int getBranchId(int userId) throws BankingException{
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.EMPLOYEE.get());
			String query = queryBuilder.column(2).where(1).buildSelect();
			 return (int) queryBuilder.executeQuery(query, userId).get(0).get(2);
			
		}catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}
	public void addBranch(Branch branch) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.BRANCH.get());
			String query =  queryBuilder.column(2,3,4,5,7).buildInsert();
			List<Object> values = new ArrayList<Object>();
			values.add(branch.getBranchName());
			values.add(branch.getIfsc());
			values.add(branch.getAddress());
			values.add(branch.getCreatedTime());
			values.add(branch.getModifiedBy());
			queryBuilder.execute(query,values.toArray());
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}
	
	public void addAccount(Account account) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query =  queryBuilder.column(1,3,5,6,7,9).buildInsert();
			
			queryBuilder.execute(query, account.getUserId(),account.getBranchId(), account.getAccountStatus().ordinal(), account.getAccountType().ordinal(),account.getCreatedTime(),account.getModifiedBy());
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}
	

	public int getUserId(long accNo) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query =  queryBuilder.column(1).where(2).buildSelect();
			
			List<Map<Integer, Object>> values =queryBuilder.executeQuery(query, accNo);
			
			if(values.isEmpty()) {
				throw new BankingException("No such account number");
			}
			return (int) values.get(0).get(1);
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}

	
	public List<Long> getAccountNumbers(int userId) throws BankingException {
		List<Long> accountNumbers = new ArrayList<>();
		
		try{
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query =  queryBuilder.column(2).where(1).buildSelect();
			List<Map<Integer, Object>> values =queryBuilder.executeQuery(query, userId);
			
			if(values.isEmpty()) {
				throw new BankingException(ErrorCode.NO_ACCOUNT_FOUND);
			}
			for(Map<Integer, Object> map : values) {
				accountNumbers.add((Long) map.get(2));
			}
			
			return accountNumbers;
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}

	public void changePassword(int userId, String passWord) throws BankingException {

		try{
			QueryBuilder queryBuilder = new QueryBuilder(Table.USER.get());
			String query =  queryBuilder.column(2).where(1).buildUpdate();
			queryBuilder.execute(query, passWord,userId);

		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}

	public long getBalance(long accountNumber) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query =  queryBuilder.column(4).where(2).buildSelect();
			
			List<Map<Integer, Object>> values =queryBuilder.executeQuery(query, accountNumber);
			
			if(values.isEmpty()) {
				throw new BankingException("Account number not found!!");
			}
			
			return (long) values.get(0).get(4);
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}

	public long getOverAllbalance(int userId) throws BankingException {

		try  {
			long balance =0;
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query = queryBuilder.column(4).where(1).buildSelect();
			
			List<Map<Integer, Object>> resultList = queryBuilder.executeQuery(query, userId);
			for(Map<Integer, Object> map:resultList) {
				balance = balance + (long)map.get(4);
			}
			return balance;
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}

	public Map<Integer, Branch> getAllBranches(int limit,long offset) throws BankingException {
		Map<Integer, Branch> branchMap = new HashMap<>();
		
		try {
			
			QueryBuilder queryBuilder = new QueryBuilder(Table.BRANCH.get());
			String query =  queryBuilder.limit().offset().buildSelect();
			List<Map<Integer, Object>> values =queryBuilder. executeQuery(query,limit,offset);
			
			for(int i =0;i<values.size();i++) {
				
				Branch branch = new Branch();
				
				setBranch(branch,values.get(i));
				branchMap.put(branch.getBranchId(), branch);
			}
			
			return branchMap;
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}

	private void setBranch(Branch branch,Map<Integer, Object> map) {
		branch.setBranchId((int) map.get(1));
		branch.setBranchName((String) map.get(2));
		branch.setIfsc((long) map.get(3));
		branch.setAddress((String) map.get(4));
		
	}

	public Customer getCustomerDetails(int userId) throws BankingException {

		Customer customer = new Customer();
		

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.CUSTOMER.get());
			String query = queryBuilder.where(1).buildSelect();
			
			List<Map<Integer, Object>> customerList = queryBuilder.executeQuery(query,userId);
			if(customerList.size() == 0) {
				throw new BankingException(ErrorCode.INVALID_CUSTOMER);
			}
			setCustomer(customer,customerList.get(0));
			getUser(customer, userId);
			return customer;
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
		

	}
	
	
	private void setCustomer(Customer customer, Map<Integer, Object> map) {
		customer.setAadhar((long) map.get(2));
		customer.setPan((String) map.get(3));
		customer.setAddress((String) map.get(4));
		
	}

	public Employee getEmployeeDetails(int userId) throws BankingException{
		Employee employee = new Employee();
		
		
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.EMPLOYEE.get());
			String query = queryBuilder.where(1).buildSelect();
			
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, userId);
			
			if(result.size() == 0) {
				throw new BankingException(ErrorCode.INVALID_EMPLOYEE);
			}
			
			int branchId = (int) result.get(0).get(2);
			employee.setBranchId(branchId);
			getUser(employee, userId);
			
			return employee;
		}catch(SQLException e) {
			throw new BankingException(e.getMessage(),e);
		}
	}


	public Map<Long, Account> getAccountDetails(int userId) throws BankingException {
		
		Map<Long,Account> accountMap = new HashMap<>();
		
		try{
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			
			String query = queryBuilder.where(1).buildSelect();
			List<Map<Integer, Object>> values = queryBuilder.executeQuery(query, userId);
			
			for(Map<Integer, Object> value :values) {
				Account account = new Account();
				setAccounts(account,value);
				accountMap.put(account.getAccountNo(), account);
			}
			return accountMap;
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}
	
	private void setAccounts(Account account, Map<Integer, Object> value) throws BankingException {
		account.setUserId((int) value.get(1));
		account.setAccountNo((long) value.get(2));
		int branchId = (int) value.get(3);
		account.setBranchId(branchId);
		account.setBalance((long) value.get(4));
		account.setAccountStatus(Status.values()[(int) value.get(5)]);
		account.setAccountType( AccountType.values()[(int) value.get(6)]);
		account.setBranchName(getBranchName(branchId));
		
	}

	public Map<Integer, Map<Long, Account>> getAllAccounts(int branchId,int limit, long offset) throws BankingException {

		Map<Integer, Map<Long, Account>> accountMap = new HashMap<>();
		Map<Long,Account> individualAccount = new HashMap<>();
		
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query = queryBuilder.where(3).limit().offset().buildSelect();
		
			for(Map<Integer, Object> map : queryBuilder.executeQuery(query,branchId, limit,offset)) {
				Account account = new Account();
				setAccounts(account,map);
			
				individualAccount = accountMap.get(account.getBranchId());
				
				if(individualAccount == null) {
					individualAccount = new HashMap<>();
					accountMap.put(account.getBranchId(), individualAccount);
				}
				individualAccount.put(account.getAccountNo(), account);
			}
				return accountMap;
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}

	public void updateTransaction(Transaction transaction) throws BankingException {
		
		try {
	
			QueryBuilder queryBuilder = new QueryBuilder(Table.TRANSACTION.get());
			String query = queryBuilder.column(1,2,3,5,6,7,8,9,10,11,12,13).buildInsert();
			
			queryBuilder.execute(query,getTransactionValues(transaction).toArray());
			
			updateBalance(transaction.getAccountNo(), transaction.getCloseBalance());

		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}

	private List<Object> getTransactionValues(Transaction transaction) {
		List<Object> values = new ArrayList<Object>();
		values.add(transaction.getDateTime());
		values.add(transaction.getUserId());
		values.add(transaction.getAccountNo());
		values.add(transaction.getTransactionAccNo());
		values.add(transaction.getAmount());
		values.add(transaction.getType().ordinal());
		values.add(transaction.getDescription().ordinal());
		values.add(transaction.getOpenBalance());
		values.add(transaction.getCloseBalance());
		values.add(transaction.getStatus().ordinal());
		values.add(transaction.getCreatedTime());
		values.add(transaction.getModifiedBy());
	
		return values;
	}

	private void updateBalance(long accNo,long balance) throws SQLException, BankingException {
		QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
		String query = queryBuilder.column(4).where(2).buildUpdate();
		
		queryBuilder.execute(query, balance, accNo);
	}
	
	public boolean isActive(long accountNumber) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query = queryBuilder.column(5).where(2).buildSelect();
			
			List<Map<Integer, Object>> resultList = queryBuilder.executeQuery(query, accountNumber);
			return resultList.get(0).get(5).equals(0);
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}

	public void verifyAccount(int userId, long accountNumber) throws BankingException {
		
		try {
			
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query = queryBuilder.column(2).where(1,2).buildSelect();
			
			if (queryBuilder.executeQuery(query, userId,accountNumber).isEmpty()) {
				throw new BankingException("Wrong userid or account number!! ");
			}
		
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}

	public void setAccountStatus(long accountNumber,Status status) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query =  queryBuilder.column(5).where(2).buildUpdate();
			queryBuilder.execute(query, status.ordinal(),accountNumber);

		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}
	
	public void setUserStatus(int userId, Status status) throws BankingException{
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.USER.get());
			String query =  queryBuilder.column(8).where(1).buildUpdate();
			queryBuilder.execute(query, status.ordinal(),userId);

		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}
	
	
	public List<Transaction> getTransactionDetail(TransactionReq req) throws BankingException {

		List<Transaction> transactionList = new ArrayList<>();
		
		
		try  {
			String query = new String();
			
			QueryBuilder queryBuilder = new QueryBuilder(Table.TRANSACTION.get());
			
			List<Map<Integer, Object>> resultList = new ArrayList<>();
			long[] time = getTime(req.getTime());
			
				query = getAccTransactionQuery(req);
				if(req.getType() == TransactionDetail.ALL) {
					resultList = queryBuilder.executeQuery(query,req.getAccountNumber(),time[0],time[1],req.getLimit(),req.getOffset());
				}else {
					resultList = queryBuilder.executeQuery(query,req.getAccountNumber(),req.getType().ordinal(),time[0],time[1],req.getLimit(),0);
				}
				
			
			for(Map<Integer, Object> transactions : resultList) {
				Transaction transaction = new Transaction();
				setTransaction(transaction,transactions);
				transactionList.add(transaction);
			}
			return transactionList;

		} catch (SQLException e) {

			throw new BankingException(e.getMessage(), e);
		}
	}
	private void setTransaction(Transaction transaction, Map<Integer, Object> transactions) {
		transaction.setDateTime((long) transactions.get(1));
		transaction.setUserId((int) transactions.get(2));
		transaction.setAccountNo((long) transactions.get(3));
		transaction.setTransactionId((long) transactions.get(4));
		transaction.setTransactionAccNo((long) transactions.get(5));
		transaction.setAmount((int) transactions.get(6));
		transaction.setType(TransactionType.values()[(int) transactions.get(7)]);
		transaction.setDescription(TransactionDescription.values()[(int) transactions.get(8)]);
		transaction.setOpenBalance((long) transactions.get(9));
		transaction.setCloseBalance((long) transactions.get(10));
		transaction.setStatus(TransactionStatus.values()[(int) transactions.get(11)]);
		
	}

	private String getAccTransactionQuery(TransactionReq requirement) throws SQLException {
		QueryBuilder queryBuilder = new QueryBuilder(Table.TRANSACTION.get());
		int column = 7;

		
		if(requirement.getType() == TransactionDetail.ALL) {
			queryBuilder = queryBuilder.where(3);
		}else {
			if(requirement.getType() == TransactionDetail.SUCCESS || requirement.getType() == TransactionDetail.FAILED) {
				 column =11;	
				}
			queryBuilder = queryBuilder.where(3,column);
		}
		return queryBuilder.between(1).limit().offset().buildSelect();
		

	}

	private long[] getTime(TransactionPeriod time) throws SQLException {
		ZonedDateTime fromTime = ZonedDateTime.now();
		ZonedDateTime toTime = ZonedDateTime.now();

		switch (time) {
		case LAST_30_DAYS:
			fromTime = fromTime.minusMonths(1);
			break;
			
		case THIS_MONTH:
			fromTime = fromTime.minusDays(toTime.getDayOfMonth());
			break;
			
		case PREVIOUS_MONTH:
			fromTime = fromTime.minusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
			boolean isLeap = Year.of(fromTime.getYear()).isLeap();

			int totalDays = fromTime.getMonth().length(isLeap);
			toTime = toTime.minusDays(totalDays);
			break;
			
		case PAST_THREE_MONTH:
			fromTime = fromTime.minusMonths(3);
			break;
			
		case PAST_SIX_MONTH:
			fromTime = fromTime.minusMonths(6);
			break;
		}
		
		long[] result = {fromTime.toInstant().toEpochMilli(),toTime.toInstant().toEpochMilli()};
		return  result;

	}
	
	private void addUser(User user) throws BankingException {
		
		try  {
			QueryBuilder queryBuilder = new QueryBuilder(Table.USER.get());
			
			String query = queryBuilder.column(2,3,4,5,6,7,8,9,10,12).buildInsert();
			List<Object> userObj = getUserDetails(user);
			userObj.remove(0);
			queryBuilder.execute(query, userObj.toArray());
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}
	}
	public int getUsersId(long mobile) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.USER.get());
			String query = queryBuilder.column(1).where(4).buildSelect();
			List<Map<Integer, Object>> resultList = queryBuilder.executeQuery(query, mobile);
			
			if(resultList.isEmpty()) {
				throw new BankingException("User not found");
			}
			return (int) resultList.get(0).get(1);
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}
	public void getUser(User user, int userId) throws BankingException {

		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.USER.get());
			String query = queryBuilder.where(1).buildSelect();
			List<Map<Integer, Object>> resultList = queryBuilder.executeQuery(query, userId);
			if(resultList.size() == 0) {
				throw new BankingException(ErrorCode.WRONG_USER_ID);
			}
			setUser(user,resultList.get(0));
			
		} catch (SQLException e) {
			throw new BankingException(e.getMessage(), e);
		}

	}

	private void setUser(User user, Map<Integer, Object> map) {
		user.setUserId((int) map.get(1));
		user.setName((String) map.get(3));
		user.setMobile((long) map.get(4));
		user.setEmail((String) map.get(5));
		user.setAge((int) map.get(6));
		user.setGender(Gender.values()[(int) map.get(7)]);
		user.setStatus(Status.values()[(int) map.get(8)]);
		user.setRole(UserType.values()[(int) map.get(9)]);
		user.setCreatedTime((long) map.get(10));
		if(map.get(11) != null) {
			user.setModifiedTime((long) map.get(11));
		}
		user.setCreatedBy((int) map.get(12));
		
	}
	public List<Object> getUserDetails(User user) {
	List<Object> customer = new ArrayList<Object>();
		
		customer.add(user.getUserId());
		customer.add(user.getPassword());
		customer.add(user.getName());
		customer.add(user.getMobile());
		customer.add(user.getEmail());
		customer.add(user.getAge());
		customer.add(user.getGender().ordinal());
		customer.add(user.getStatus().ordinal());
		customer.add(user.getRole().ordinal());
		customer.add(user.getCreatedTime());
		customer.add(user.getModifiedBy());
		return customer; 
		
	}
	
	
	public List<Customer> getAllCustomer(int limit,long offset) throws  BankingException {
		try {
		List<Customer> customerList = new ArrayList<>();
		String query = "SELECT * FROM USER_DETAILS JOIN CUSTOMER_DETAILS ON USER_DETAILS.USER_ID = CUSTOMER_DETAILS.USER_ID LIMIT ? OFFSET ?";
		QueryBuilder queryBuilder = new QueryBuilder(Table.CUSTOMER.get());
		List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, limit,offset);
		
		for(Map<Integer,Object> resultMap : result ) {
			Customer customer = new Customer();
			
			customer.setUserId((int)resultMap.get(1));
			customer.setName((String) resultMap.get(3));
			customer.setMobile((long)resultMap.get(4));
			customer.setEmail((String) resultMap.get(5));
			customer.setAge((int) resultMap.get(6));
			customer.setGender(Gender.values()[(int) resultMap.get(7)]);
			customer.setStatus(Status.values()[(int) resultMap.get(8)]);
		
			customer.setAadhar((long) resultMap.get(14));
			customer.setPan((String) resultMap.get(15));
			customer.setAddress((String) resultMap.get(16));
			
			
			customerList.add(customer);
		}
		return customerList;
	}catch (SQLException e) {
		throw new BankingException(e.getMessage());
	}
	}
	
	public List<Employee> getAllEmployee(int limit,long offset) throws BankingException {
		try {
			List<Employee> emoloyeeList = new ArrayList<>();
			String query = "SELECT * FROM USER_DETAILS JOIN EMPLOYEE_DETAILS ON USER_DETAILS.USER_ID = EMPLOYEE_DETAILS.USER_ID LIMIT ? OFFSET ?";
			QueryBuilder queryBuilder = new QueryBuilder(Table.EMPLOYEE.get());
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, limit,offset);
			
			for(Map<Integer,Object> resultMap : result ) {
				Employee customer = new Employee();
				
				customer.setUserId((int)resultMap.get(1));
				customer.setName((String) resultMap.get(3));
				customer.setMobile((long)resultMap.get(4));
				customer.setEmail((String) resultMap.get(5));
				customer.setAge((int) resultMap.get(6));
				customer.setGender(Gender.values()[(int) resultMap.get(7)]);
				customer.setStatus(Status.values()[(int) resultMap.get(8)]);
				customer.setBranchId((int) resultMap.get(14));
				
				emoloyeeList.add(customer);
			}
			return emoloyeeList;
		}catch (SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	
	public long getBranchCount() throws BankingException {
		try {
		QueryBuilder queryBuilder = new QueryBuilder(Table.BRANCH.get());
		String query = queryBuilder.count(1).buildSelect();
		 List<Map<Integer, Object>> result = queryBuilder.executeQuery(query);
		 
		 return (long)result.get(0).get(1);
		
		
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	
	public long getCustomerCount(int branchId) throws BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.CUSTOMER.get());
			String query = queryBuilder.count(1).where(2).buildSelect();
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, branchId);
			
			return (long)result.get(0).get(1);
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	public long getAllCustomerCount() throws BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.CUSTOMER.get());
			String query = queryBuilder.count(1).buildSelect();
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query);
			
			return (long)result.get(0).get(1);
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	
	public long getEmployeeCount() throws BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.EMPLOYEE.get());
			String query = queryBuilder.count(1).buildSelect();
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query);
			
			return (long)result.get(0).get(1);
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}

	}
	public String getBranchName(int branchId) throws BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.BRANCH.get());
			String query = queryBuilder.column(2).where(1).buildSelect();
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, branchId);
			return (String) result.get(0).get(2);
			
		}catch(SQLException e) {
			
			throw new BankingException(e.getMessage());
		}
	}
	
	public Account getAccount(long accountNumber) throws BankingException {
		try {
			
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query = queryBuilder.where(2).buildSelect();
			
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, accountNumber);
			
			if(result.size() == 0) {
				throw new BankingException(ErrorCode.WRONG_ACCOUNT_NUMBER);
				
			}
			Account account = new Account();
			setAccounts(account, result.get(0));
			return account;
			
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	
	public long getAccountCount(int branchId) throws BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.ACCOUNTS.get());
			String query = queryBuilder.count(1).where(3).buildSelect();
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query,branchId);
			
			return (long)result.get(0).get(1);
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}

	}
	
	public Branch getBranch(int branchId) throws BankingException {
		try {
			QueryBuilder queryBuilder = new  QueryBuilder(Table.BRANCH.get());
			String query = queryBuilder.where(1).buildSelect();
			
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, branchId);
			
			if(result.size() == 0) {
				throw new BankingException(ErrorCode.INVALID_BRANCH);
			}
			Branch branch = new Branch();
			setBranch(branch, result.get(0));
			
			return branch;
		}catch(SQLException e) {
			throw new  BankingException(ErrorCode.INVALID_BRANCH);
		}
	}
	
	public void updateCustomer(Customer customer) throws BankingException {
		try {
				QueryBuilder  queryBuilder = new QueryBuilder(Table.CUSTOMER.get());
				String query = queryBuilder.column(2,3,4).where(1).buildUpdate();
				queryBuilder.execute(query,customer.getAadhar(),customer.getPan(),customer.getAddress(),customer.getUserId());
				updateUser(customer);
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	public void updateUser(User user) throws BankingException {
		try {
			QueryBuilder queryBuilder = new  QueryBuilder(Table.USER.get());
			String query = queryBuilder.column(3,4,5,6,7,11).where(1).buildUpdate();
			queryBuilder.execute(query, user.getName(),user.getMobile(),user.getEmail(),user.getAge(),user.getGender().ordinal(),user.getModifiedTime(),user.getUserId());
			
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	public void updateLog(OperationLog log) throws BankingException {
		try {	
			QueryBuilder queryBuilder = new QueryBuilder(Table.LOG.get());
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, log.getTarget(),log.getUser(),log.getTime(),log.getDescription());
			
		}catch(SQLException e) {
			throw new  BankingException(e.getMessage());
		}
	}
	public List<OperationLog> getLogs(int userId) throws BankingException {
		try {
			List<OperationLog> logList = new ArrayList<>();
			QueryBuilder queryBuilder = new QueryBuilder(Table.LOG.get());
			String query = queryBuilder.where(1).buildSelect();
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, userId);
			for(Map<Integer,Object> resultMap : result) {
				OperationLog log = new OperationLog();
				log.setTarget((int) resultMap.get(1));
				log.setUser((int) resultMap.get(2));
				log.setTime((long) resultMap.get(3));
				log.setDescription((String) resultMap.get(4));
				logList.add(log);
			}
			return logList;
			
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	public OperationLog getRecentLogs(int userId) throws BankingException {
		try {
			OperationLog log = new OperationLog();
			QueryBuilder queryBuilder = new QueryBuilder(Table.LOG.get());
			String query = "SELECT * FROM USER_DETAILS WHERE OPERATION_LOG = ? AND TIME = (SELECT MAX(TIME) FROM OPERATION_LOG)";
			List<Map<Integer, Object>> result = queryBuilder.executeQuery(query, userId);
			Map<Integer,Object> resultMap = result.get(0);
			if(resultMap != null) {
				log.setTarget((int) resultMap.get(1));
				log.setUser((int) resultMap.get(2));
				log.setTime((long) resultMap.get(3));
				log.setDescription((String)resultMap.get(4));
			}
			return log;
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	public void addApi(ApiData api) throws BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.API.get());
			String query = queryBuilder.buildInsert();
			queryBuilder.execute(query, api.getApiKey(),api.getCreatedAt(),api.getCreatedBy(),api.getAuthorization());
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
	public void validateApi(String api) throws BankingException {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(Table.API.get());
			String query = queryBuilder.where(1).buildSelect();
			if(queryBuilder.executeQuery(query, api).isEmpty()) {
				throw new BankingException(ErrorCode.INVALID_API_KEY);
			}
		}catch(SQLException e) {
			throw new BankingException(e.getMessage());
		}
	}
}
