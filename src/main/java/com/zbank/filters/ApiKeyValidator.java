package com.zbank.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zbank.enums.ErrorCode;
import com.zbank.exceptions.BankingException;
import com.zbank.logics.ZBank;

public class ApiKeyValidator implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest HttpRequest, ServletResponse HttpResponse, FilterChain chain) throws IOException, ServletException {
    	 HttpServletRequest request = (HttpServletRequest) HttpRequest;
         HttpServletResponse response = (HttpServletResponse) HttpResponse;
        
         String api = request.getHeader("apiKey");
         System.out.println("api"+api);
         if(api == null) {
        	 PrintWriter out = response.getWriter();
				out.print("Include Api Key in parameter");
		        out.flush();
		        return;
         }
         
        	 ZBank zbank = new ZBank();
        	 try {
				zbank.validateApi(api);
			} catch (BankingException e) {
				if(e.getErrorCode() == ErrorCode.INVALID_API_KEY) {
					 	PrintWriter out = response.getWriter();
						out.print("invalid Api Key");
				        out.flush();
				        return;
				}
			
         }
        
         chain.doFilter(request, response); 
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}