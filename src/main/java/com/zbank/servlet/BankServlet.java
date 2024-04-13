package com.zbank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbank.enums.ErrorCode;
import com.zbank.exceptions.BankingException;

import javax.servlet.RequestDispatcher;

public class BankServlet extends HttpServlet {
	
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestHandler handler = new RequestHandler();
		try {
			
		
		switch (request.getRequestURI()){
		
		case "/zbank/service/login":
			RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/login.jsp");
			dispatcher.forward(request, response);		
			break;
			 
		case "/zbank/service/dashboard":
			handler.getDashboard(request, response);
			break;
			
		case "/zbank/service/all-account":
			
			handler. getAllAccount(request, response);
		  break;

		case "/zbank/service/create-account":
			
			 dispatcher = request.getRequestDispatcher("/JSP/create-account.jsp");
			dispatcher.forward(request, response);				
			break;
			
		case "/zbank/service/deactivate-account":
			
			dispatcher = request.getRequestDispatcher("/JSP/deactivate-account.jsp");
			dispatcher.forward(request, response);
			break;

		case"/zbank/service/customer-detail":
			
			
			handler.setAllCustomerDetail(request);
			dispatcher = request.getRequestDispatcher("/JSP/customer-detail.jsp");
			dispatcher.forward(request, response);
			break;
		
		case "/zbank/service/create-customer":
			
			request.setAttribute("type", "customer");
			 dispatcher = request.getRequestDispatcher("/JSP/create-customer.jsp");
			dispatcher.forward(request, response);
			
			break;
			
		case "/zbank/service/deactivate-customer":
			
			request.setAttribute("type", "customer");
			dispatcher = request.getRequestDispatcher("/JSP/deactivate-customer.jsp");
			dispatcher.forward(request, response);
			break;

		case "/zbank/service/transaction":
		
			handler.setAccountNumber(request,response);
			
			request.setAttribute("transactionMethod", "transaction");
			 dispatcher = request.getRequestDispatcher("/JSP/transaction.jsp");
			dispatcher.forward(request, response);
			
			break;
			
		case "/zbank/service/deposit-withdraw":
			
			handler.setAccountNumber(request,response);
			request.setAttribute("transactionMethod", "deposit-withdraw");
			
			
			dispatcher = request.getRequestDispatcher("/JSP/transaction.jsp");
			dispatcher.forward(request, response);
			break;
			
		case "/zbank/service/statement":
			
			handler.setAccountNumber(request, response);
			request.setAttribute("page", 0l);
			request.setAttribute("pageNo",0l);
			dispatcher = request.getRequestDispatcher("/JSP/statement.jsp");
			dispatcher.forward(request, response);
		 	
			break;
			
		case "/zbank/service/profile":
			
			handler.getProfile(request, response);
			
			break;
			
		case "/zbank/service/accounts":
			
			handler.getCustomerAccounts(request,response);
			break;
			
		case "/zbank/service/logout":
			
	        request.getSession(false).invalidate();
	        System.out.println(request.getSession(false));
			response.sendRedirect("/zbank/service/login");
			break;
			
		case "/zbank/service/all-branches":
		
			handler.setBranchDetails(request);
			dispatcher = request.getRequestDispatcher("/JSP/branch-details.jsp");
			dispatcher.forward(request, response);
			
			break;
		case "/zbank/service/create-branch":
			
			dispatcher = request.getRequestDispatcher("/JSP/create-branch.jsp");
			dispatcher.forward(request, response);
			
			break;
		case "/zbank/service/employee-details":
			
			handler.setAllEmplyeeDetail(request);
			
			dispatcher = request.getRequestDispatcher("/JSP/customer-detail.jsp");
			dispatcher.forward(request, response);
			
			break;
			
		case "/zbank/service/create-employee":
			
			request.setAttribute("type", "employee");
			
			dispatcher = request.getRequestDispatcher("/JSP/create-customer.jsp");
			dispatcher.forward(request, response);
			break;
			
		case "/zbank/service/deactivate-employee":
			
			request.setAttribute("type", "employee");
			dispatcher = request.getRequestDispatcher("/JSP/deactivate-customer.jsp");
			dispatcher.forward(request, response);
			break;
		case "/zbank/service/change-password":
		
			dispatcher = request.getRequestDispatcher("/JSP/change-password.jsp");
			dispatcher.forward(request, response);
			break;
			
		case"/zbank/service/search-customer":
			
			request.setAttribute("type", "customer");

			request.getRequestDispatcher("/JSP/search-customer.jsp").forward(request, response);
			break;
			
		case "/zbank/service/search-account":
			
		
			request.getRequestDispatcher("/JSP/search-account.jsp").forward(request, response);
			break;
			
		case "/zbank/service/search-employee":
			
			request.setAttribute("type", "employee");
	
			request.getRequestDispatcher("/JSP/search-customer.jsp").forward(request, response);
			break;
			
		case "/zbank/service/search-branch":
			
		
			request.getRequestDispatcher("/JSP/search-branch.jsp").forward(request, response);
			break;
		}
	
		
		}catch (BankingException e) {
			ErrorCode error = e.getErrorCode();
			if(error == ErrorCode.INVALID_SESSION || error == ErrorCode.WRONG_PASSWORD) {
				response.sendRedirect("/zbank/service/login");
			}else if(error == ErrorCode.NO_ACCOUNT_FOUND) {
				
			}
			e.printStackTrace();
		}
		
			
		}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)  throws ServletException, IOException {
	   RequestHandler handler = new RequestHandler();
	
	   try {
		   
	
	   switch (request.getRequestURI()){
	   
	   case "/zbank/service/login":
		   handler.checkPassword(request);
		   response.sendRedirect("/zbank/service/dashboard");
		   break;
	   
	   case "/zbank/service/dashboard":
		  
		   handler. getDashboard(request, response);
		   break;
	   
	   case "/zbank/service/statement":
		 
		   handler.setAccountNumber(request, response);
		   handler.getStatement(request, response);
		   RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/statement.jsp");
		   dispatcher.forward(request, response);
		   
		break;
		
	   case "/zbank/service/create-customer":
		   handler.createCustomer(request, response);
			dispatcher = request.getRequestDispatcher("/JSP/create-customer.jsp");
			dispatcher.forward(request, response);
			
			break;
	   case "/zbank/service/create-account":
		   handler.createAccount(request,response);
			break;
	   case "/zbank/service/deactivate-account":
	
		   handler.deactivateAccount(request, response);
		   break;
			
	   case "/zbank/service/deactivate-customer":
		   request.setAttribute("type", "customer");
		   handler.deactivateCustomer(request, response);
		   break;
		   
	   case "/zbank/service/deactivate-employee":
		   request.setAttribute("type", "employee");
		
		   break;
		   
	   case "/zbank/service/deposit-withdraw":
		   handler.setAccountNumber(request,response);
			request.setAttribute("transactionMethod", "deposit-withdraw");
			handler.doTransaction(request, response);
		   break;
		   
	   case "/zbank/service/transaction":
		   handler.setAccountNumber(request,response);
			request.setAttribute("transactionMethod", "transaction");
			handler.doTransaction(request, response);
		   break;
		   
	   case "/zbank/service/create-employee":
		   handler.createEmployee(request, response);
		   break;
		   
	   case "/zbank/service/create-branch":
		   
		   handler.createBranch(request, response);
		   break;
		   
	   case "/zbank/service/change-password":
		   handler.handleChangePass(request, response);
		   
		   break;
		   
		case"/zbank/service/search-customer":
			
			handler.handleSearchCustomer(request, response);
			break;
			
		case "/zbank/service/search-account":
			handler.handleSearchAccount(request, response);
			break;
			
		case "/zbank/service/search-employee":
			handler.handleSearchEmployee(request, response);
			break;
			
		case "/zbank/service/search-branch":
			handler.handleSearchBranch(request, response);
			break;
		case "/zbank/service/edit-customer":
			handler.handleEditCustomer(request,response);
			break;
		case "/zbank/service/edit-employee":
			handler.handleEditEmployee(request, response);
			
			break;
	   }
	   
	   
	   } catch (BankingException e) {
		   ErrorCode error = e.getErrorCode();
			if(e.getErrorCode() == ErrorCode.INVALID_SESSION ) {
				response.sendRedirect("/zbank/service/login");
			}else if(error == ErrorCode.WRONG_PASSWORD) {
				request.setAttribute("message","Incorrect Password");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/login.jsp");
				dispatcher.forward(request, response);		
				
			}else if(error == ErrorCode.INVALID_USER) {
				request.setAttribute("message", "No such User");
				RequestDispatcher dispatcher = request.getRequestDispatcher("/JSP/login.jsp");
				dispatcher.forward(request, response);		
				
			}
			e.printStackTrace();
		}
	}
}
