package com.zbank.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbank.enums.UserType;
import com.zbank.exceptions.BankingException;
import com.zbank.servlethandler.ApiRequestHandler;


public class ApiServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
    	
 
    	ApiRequestHandler handler = new ApiRequestHandler();
    	
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, APIKEY");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        	response.setStatus(HttpServletResponse.SC_OK);
			return;
        }
      
        System.out.println("url "+request.getRequestURI());
        String json = new String();
        switch (request.getRequestURI()){
        
        case "/zbank/api/customer":
        	 json = handler.handleGetCustomer(request);
        	break;
        case "/zbank/api/employee":
        	json = handler.handleGetEmployee(request);
        	break;
        	
        case "/zbank/api/accounts":
        	json = handler.handleGetAccount(request);
        	break;
        	
        case "/zbank/api/branch":
        	json = handler.handleGetBranch(request);
        	break;
        	
        case "/zbank/api/statement":
        	json = handler.handleGetStatement(request);
        	
        	break;
        	
        case"/zbank/api/count":
        	json = handler.getPageCount(request);	
        	break;
        		

        }
        
        PrintWriter out = response.getWriter();
		out.print(json);
        out.flush();		
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, APIKEY");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        	response.setStatus(HttpServletResponse.SC_OK);
			return;
        }
        	
    	ApiRequestHandler handler = new ApiRequestHandler();
        response.setContentType("application/json");
        
       String json = new String();
        switch (request.getRequestURI()){
        
     
        case "/zbank/api/createCustomer":
        	try {
				json = handler.handleAddCustomer(request);
			} catch (BankingException e) {
				
				e.printStackTrace();
			}
        	break;
        case "/zbank/api/createEmployee":
        	json = handler.handleAddEmployee(request);
        	break;
        case "/zbank/api/createBranch":
       
        	try {
				json = 	handler.addBranch(request);
			} catch (BankingException e) {
				e.printStackTrace();
			}
        	break;
        	
        case "/zbank/api/userDeactivate":
        	try {
        		json = handler.handleDeactivateEmployee(request);
        		System.out.println(json);
        	}catch (BankingException e) {
				
			}
        	break;
        	
        case "/zbank/api/editCustomer":
        	try {
				json = handler.handleUserEdit(request,UserType.CUSTOMER);
			
			} catch (BankingException e) {
		
				e.printStackTrace();
			}
        	break;
        	
        case "/zbank/api/editEmployee":
        	try {
				json = handler.handleUserEdit(request,UserType.EMPLOYEE);
			} catch (BankingException e) {
				e.printStackTrace();
			}
        	break;
        	
        case "/zbank/api/editBranch":
        	
        	break;
        }
        
        System.out.println(json);
        PrintWriter out = response.getWriter();
		out.print(json);
        out.flush();
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	System.err.println("Put");
    	 response.setContentType("application/json");
         response.setHeader("Access-Control-Allow-Origin", "*");
         response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
         response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, APIKEY");

         if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
         	response.setStatus(HttpServletResponse.SC_OK);
 			return;
         }
         
    	ApiRequestHandler handler = new ApiRequestHandler();
        response.setContentType("application/json");
        
       String json = new String();
        switch (request.getRequestURI()){
        
        case "/zbank/api/customer":
        	json = handler.handleAddEmployee(request);
        	break;
        }

        
        PrintWriter out = response.getWriter();
		out.print(json);
        out.flush();
	}
    
    

}