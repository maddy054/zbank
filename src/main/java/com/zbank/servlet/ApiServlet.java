package com.zbank.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ApiServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
    	
    	ApiRequestHandler handler = new ApiRequestHandler();
        response.setContentType("application/json");
        
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
        }
        
        PrintWriter out = response.getWriter();
		out.print(json);
        out.flush();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    	ApiRequestHandler handler = new ApiRequestHandler();
        response.setContentType("application/json");
        
      //  String json = new String();
        switch (request.getRequestURI()){
        
        case "/zbank/api/createCustomer":
        	handler.handleAddEmployee(request);
        	break;
        }
    	
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		
	}
    
    

}