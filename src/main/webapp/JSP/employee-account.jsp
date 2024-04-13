
<%@page import="com.zbank.enums.UserType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head> 
 <meta charset="UTF-8">
 <%@ page import="java.util.Map" %>
<%@ page import="com.zbank.models.Account" %>
<%@ page import="java.lang.reflect.Type" %>
<%@ page import="java.util.HashMap" %>

    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customers</title>
      <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
      
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/employee-account.css">
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
        <div class="top">
        <nav class="topnav">   
             <% UserType userType = (UserType) request.getSession(false).getAttribute("userType");
            String type = (String) request.getAttribute("type");
            
            if(userType == UserType.EMPLOYEE){%>
               <a class ="other-nav" href="search-customer">Customer</a>
               <a class ="this-nav"href="all-account">Accounts</a>
               <a class="other-nav" href="transaction">Transaction</a>
               <a class="other-nav" href="statement">Statement</a>
               <a class="other-nav" href="profile">Profile</a> 
             <%} else{ 
             	if(type == "customer"){ %>
               <a class ="other-nav" href="all-branches" >Branch</a>
                <a class ="other-nav"href="search-employee">Employee</a>
                <a class="this-nav" >Customer</a>
                <a class="other-nav" href="profile">Profile</a>
                
                <% } else{%>
                
                <a class ="other-nav" href="all-branches" >Branch</a>
                <a class ="this-nav">Employee</a>
                <a class="other-nav"href="customer-detail" >Customer</a>
                <a class="other-nav" href="profile">Profile</a>
                <%}} %>
     
        </nav>
        </div>
         <div class="logout">
			<img src="<%= request.getContextPath() %>/IMAGE/log-out.png" usemap="#logout-map">
        </div>
        <map name="logout-map">
       <area shape="rect" coords="0,0,100,200" href="logout" alt="logout">
        </map>
    </header>
     <div class="overall">
    <div class="main-container">
        <div class="selection"> 
  	      <a class="other-detail" href="search-account">Search Account</a>
            <a class="this-detail">All Accounts</a>
            <a class="other-detail" href="create-account">Create Account</a>
            <a class="other-detail" href="deactivate-account">Deactivate Account</a>
        </div>
    </div>
      <div class="initial-input">
<table class="table">
    <tr>
        <th>User Id</th>
        <th>Account Number</th>
        <th>Account Type</th>

        <th>Balance </th>
        <th>Status</th>
    </tr>
      
    

   <% Map<Long, Account> accounts =   (Map<Long, Account> )request.getAttribute("accounts");
	if(accounts != null){
	    for (Map.Entry<Long, Account> entry : accounts.entrySet()) {
	    	Account account = entry.getValue();  %>
	  
	    <tr>
	    	<td><%= account.getUserId() %>
	        <td><%= entry.getKey() %></td>
	        <td><%= account.getAccountType() %></td>
	        <td><%= account.getBalance() %></td>
	        <td><%= account.getAccountStatus() %></td>
	      </tr>
	 
	     <% }} %>
	     

  
</table>

 <form action="all-account">
		<div class = "pagination">
 		<%long count = (long)request.getAttribute("page");
 		if(count >1){
 		long pageNo =(long) request.getAttribute("pageNo");
   		for(long i=1;i<=count;i++){ 
   		if(pageNo != i){
   		%>
            <button class="page" name="pageNo" value="<%= i%>"><%= i %></button>
            <%}else{%>
            
             <button class="this-page"><%= i %></button>
          <%  }} }%>
        </div>
    </form>
    </div>
    </div>
</body>