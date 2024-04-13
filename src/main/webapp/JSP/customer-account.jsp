<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <%@ page import="java.util.Map" %>
    <%@ page import="com.zbank.models.Account" %>
   <title>Accounts</title>
    
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
        <div class="top">
        
       <nav class="topnav">   
          <a class="this-nav">Accounts</a>
          <a class="other-nav" href="transaction">Transaction</a>
          <a class="other-nav" href="statement">Statement</a>
          <a class="other-nav" href="profile">Profile</a>
        </nav>
        </div>
      
      <div class="logout">
			<img src="<%= request.getContextPath() %>/IMAGE/log-out.png" usemap="#logout-map">
        </div>
        <map name="logout-map">
       <area shape="rect" coords="0,0,100,200" href="logout" alt="logout">
        </map>
        
    </header>
    
     <div class="outer-container">
    <% Map<Long,Account> accounts = (Map<Long,Account>)request.getAttribute("accountDetails");
    for (Map.Entry<Long, Account> entry : accounts.entrySet()) {
    	
    	Account account = entry.getValue();%>
     
        <div class="account-container">
            <h3> <%= account.getAccountType() %> A/c </h3>
            <h4>Rs. <%= account.getBalance() %></h4>
            <p> A/c - <%= account.getAccountNo() %></p>
            <p > <%= account.getBranchName() %></p>
        </div>
    
       <% } %>
           </div>
       

</body>