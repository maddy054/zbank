package com.zbank.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zbank.utilities.NullChecker;
import com.zbank.utilities.Validation;

import java.io.IOException;

public class ServletFilter implements Filter {
    private HttpSession session;

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
		
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
    	 HttpServletRequest httpRequest = (HttpServletRequest) request;
         HttpServletResponse httpResponse = (HttpServletResponse) response;
         httpResponse.setHeader("Cache-Control","no-cache"); 
         httpResponse.setHeader("Cache-Control","no-store"); 
         httpResponse.setDateHeader("Expires", 0); 
         httpResponse.setHeader("Pragma","no-cache");
                
         session = httpRequest.getSession(false);
          System.out.println(httpRequest.getRequestURI());
          String uri = httpRequest.getRequestURI();
          
          Object userId = null;
          if(!NullChecker.check(session)) {
        	   userId = session.getAttribute("userId");
          }
        
          switch (uri) {
		case "/zbank/service/login":
			if (!NullChecker.check(session) && !NullChecker.check(userId) ) {
				
				httpResponse.sendRedirect("/zbank/service/profile");
				return;
			}
			break;
		case "/zbank/service/search-customer":
			
			if(!NullChecker.check(userId) && !Validation.isNumber(userId.toString())) {
				httpRequest.setAttribute("message", "User Id is Empty");
				httpResponse.sendRedirect("/zbank/service/search-customer");
				return;
				
			}
			break;
			
		default:
			 if( NullChecker.check(session) || NullChecker.check(userId)) {
				 	
	        	httpResponse.sendRedirect("/zbank/service/login");
	        	return;
	        }   
			break;
		}       
                 
       chain.doFilter(request, response); 
    }

    @Override
    public void destroy() {
    }
}