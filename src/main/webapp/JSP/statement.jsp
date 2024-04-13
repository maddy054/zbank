<%@page import="java.net.Authenticator.RequestorType"%>
<%@page import="java.time.ZoneId"%>
<%@ page import="com.zbank.models.Transaction" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.ZonedDateTime" %>
<%@ page import="java.time.Instant" %>
<%@ page import="com.zbank.enums.UserType" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Statement</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/statement-style.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">
    
</head>
<body>
    <header>
        <div class="logo">
            <img src="<%= request.getContextPath() %>/IMAGE/zbi.png" alt="zbi logo" usemap="#logo">
        </div>
        <map name="logo">
      	 	 <area shape="rect" coords="0,0,100,200" href="dashboard" alt="logo">
        </map>
        
        <% boolean isHaveAccounts = (boolean)request.getAttribute("isHaveAccounts");
        UserType userType = (UserType)request.getSession(false).getAttribute("userType"); %>
        
        <div class="top">
            <nav class="topnav"> 
            
            <% 
            if(userType == UserType.EMPLOYEE){ %>  
                <a class="other-nav" href="search-customer">Customer</a>
                <a class ="other-nav"href="search-account">Accounts</a>
                <a class ="other-nav"href="transaction">Transaction</a>
                <a class="this-nav">Statement</a>
                <a class ="other-nav"href="profile">Profile</a>
                <% } else if(userType == UserType.CUSTOMER){ %>
                
                 <a class="other-nav" href="accounts">Accounts</a>
            	 <a class="other-nav" href="transaction">Transaction</a>
           		 <a class="this-nav" >Statement</a>
                 <a class="other-nav" href="profile">Profile</a>
            
               <% } %>
              </nav>
         </div>
      
        <map name="logo">
            <area shape="rect" coords="0,0,200,100" href="dashboard" alt="zbi-logo">
        </map>
        
         <div class="logout">
			<img src="<%= request.getContextPath() %>/IMAGE/log-out.png" usemap="#logout-map">
        </div>
        <map name="logout-map">
           <area shape="rect" coords="0,0,100,200" href="logout" alt="logout">
        </map>
    </header>
    <% if(isHaveAccounts){ %>
   
   <form class="statement-form" action="statement" method="post">
       <div>
         <label for="accountNumber"> Account Number</label>
                         
          <% if(userType == UserType.EMPLOYEE){ %>
          <input class="input" type="text" pattern="[0-9]{10}" title="Enter 10 Digit Account Number" name="accountNumber" placeholder="Enter Account Number" required></input>
                         
          <% } else if(userType == UserType.CUSTOMER){ 
             List<Long> accounts = (List<Long>)request.getAttribute("accounts");%>
             <select class="input" name="accountNumber"> 
                 <option disabled selected>Select Account Number</option>
                         	 	 
                  <% for(long account : accounts){ %>
                  <option ><%= account %></option>
                   <%}} %>
                     
              </select>
                         
          </div>
          <div>
            <label for="options2">Duration</label>
            <select class="input" id="optionDuration2" name="period" required>
                <option value="" disabled selected>Select Duration</option>
                <option value="THIS_MONTH">This Month</option>
                <option value="PREVIOUS_MONTH">Previous Month</option>
                <option value="LAST_30_DAYS">Last 30 Days</option>
                <option value="PAST_THREE_MONTH">Past 3 Months</option>
            </select>
         </div > 
         <div class="btn">
            <button type="submit">Submit</button>
         </div>
    </form>
    
      <% List<Transaction> statement =(List<Transaction>) request.getAttribute("statement");
            
       if(statement != null){
    	   if(statement.isEmpty()){ %>
    		   <div class="status" >
       		<p> No transaction Found</p>
       	       </div>
    	  <% }else{ %>
    		  <table class="table">
    	      <tr>
    	        <th>Date</th>
    	        <th>Time</th>
    	        <th>Description </th>
    	        <th>Transaction with</th>
    	        <th>Amount</th>
    	      	<th>Opening Balance
    	        <th>Closing Balance</th>
    	        <th>Status</th>
    	        </tr>
    		<% for(Transaction transaction : statement){%>
    		      
    	          <tr >
    	            <td><%= Instant.ofEpochMilli(transaction.getDateTime()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate() %></td>
    	            <td><%= Instant.ofEpochMilli(transaction.getDateTime()).atZone(ZoneId.of("Asia/Kolkata")).toLocalTime() %></td>
    	            <td><%= transaction.getDescription() %></td>
    	            <td><%= transaction.getTransactionAccNo() %></td>
    	            <td><%= transaction.getAmount() %></td>
    	            <td><%= transaction.getOpenBalance() %></td>
    	            <td><%= transaction.getCloseBalance() %></td>
    	            <td><%= transaction.getStatus() %></td>
    	           </tr>
    	         
    	        <% } %>
    	          </table>
    	        <%} }else{%>
        	<div class="status" >
        		<p> Select Account to View Statement</p>
        	</div>
        <%}%>
     
        
        <form action="statement">
		<div class = "pagination">
 		<%long count = (long)request.getAttribute("page");
 		long pageNo =(long) request.getAttribute("pageNo");
 		if(count >1 ){
 			for(long i=1;i<=count;i++){ 
 		   		if(pageNo != i){
 		   		%>
 		            <button class="page" name="pageNo" value="<%= i%>"><%= i %></button>
 		            <%}else{%>
 		            
 		             <button class="this-page"><%= i %></button>
 		          <%  } } } %>
        </div>
    </form>
        <%} else{%>
       <h4> You don't have Account </h4>	
        <%} %>
    </body>

    
</html>
