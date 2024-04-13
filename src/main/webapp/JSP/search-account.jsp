    
    <%@page import="com.zbank.models.Account"%>
<%@page import="com.zbank.models.Employee"%>
<%@page import="com.zbank.models.Customer"%>
<%@page import="com.zbank.enums.UserType"%>
        
    <%@page import="com.zbank.models.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Customer</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">
    
</head>
<body>
    <header>
        <div class="logo">
            <img src="<%= request.getContextPath() %>/IMAGE/zbi.png" alt="zbi logo" usemap="#logo">
        </div>
        <div class="top">
        <map name="logo">
      	 	 <area shape="rect" coords="0,0,100,200" href="dashboard" alt="logo">
        </map>
             <nav class="topnav">   
                <a class ="other-nav" href="search-customer">Customer</a>
                <a class ="this-nav">Accounts</a>
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
      <div class="overall">
    <div class="main-container">
        <div class="selection"> 
        	<a class="this-detail" >Search Account</a>
            <a class="other-detail" href="all-account">All Accounts</a>
            <a class="other-detail" href="create-account">Create Account</a>
            <a class="other-detail" href="deactivate-account">Deactivate Account</a>
        </div>
    </div>
    

         <div class="initial-input">
     <form class="statement-form" action="search-account" method="post">
  
      
          <div>
            <label>	Account number</label>
             <input class="input" type="number" name="accountNumber" placeholder="Enter the Account Number"></input>
         </div > 
         <div class="btn">
            <button type="submit">Search</button>
         </div>
    </form>
    
    <% Object messageObj = request.getAttribute("message");
    if(messageObj != null){%>
    
    <div class="status">
    	<p> <%= (String)messageObj %></p>
    </div>
     <%}%>
  
    <% Object accountObj = request.getAttribute("account");
    
    if(accountObj != null){%>
          <div class="input-container">
               <% Account account = (Account) accountObj; %>
               
               
           	<div class="right-container">
                <div >
                    <label for="user-id">User id</label>
                    <span class="input"> <%=account.getUserId() %></span>
                </div>
                <div >
                    <label for="acc-no">Account Number</label>
                    <span class="input"><%=account.getAccountNo()%> </span>
                </div>
                
                <div >
                    <label for="branch">Branch</label>
                     <span class="input"> <%=account.getBranchName()%></span>
                </div>
                <div >
                    <label for="status">status</label>
                     <span class="input"> <%= account.getAccountStatus() %></span>
                </div>
                
                 <div >
                    <label for="acc-type">Account type</label>
                     <span class="input"> <%= account.getAccountType()  %></span>
                </div>
               
            </div>
            
        </div>
           <%} %>
        </div>
        </div>
           
          
</body>