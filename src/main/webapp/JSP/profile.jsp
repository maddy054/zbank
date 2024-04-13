<%@page import="com.zbank.models.Customer"%>
<%@page import="com.zbank.models.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>

<%@ page import="com.zbank.models.User" %>
<%@ page import="com.zbank.enums.UserType" %>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/profile.css">
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
        <% UserType userType = (UserType)request.getSession().getAttribute("userType");
        
        if(userType == UserType.EMPLOYEE){ %>
         
             	<a class ="other-nav"href="search-customer">Customer</a>
            	<a class ="other-nav"href="search-account">Accounts</a>
             	<a class="other-nav" href="transaction">Transaction</a>
             	<a class="other-nav" href="statement">Statement</a>
             	<a class="this-nav">Profile</a>
             
             <%} else if(userType == UserType.CUSTOMER){ %>
             
           		<a class="other-nav" href="accounts">Accounts</a>
            	<a class="other-nav" href="transaction">Transaction</a>
            	<a class="other-nav" href="statement" >Statement</a>
            	<a class="this-nav">Profile</a>
            <% }else if(userType == UserType.ADMIN){ %>
           		<a class ="other-nav" href="search-branch" >Branch</a>
                <a class ="other-nav"href="search-employee">Employee</a>
                <a class="other-nav" href="search-customer">Customer</a>
                <a class="this-nav" >Profile</a>
             <%} %>
 
           </nav>
        </div>
        <div class="logout">
			<img src="<%= request.getContextPath() %>/IMAGE/log-out.png" usemap="#logout-map">
        </div>
        <map name="logout-map">
       <area shape="rect" coords="0,0,100,200" href="logout" alt="logout">
        </map>
        
    </header>
    <map name="logo">
        <area shape="rect" coords="0,0,200,100" href="dashboard" alt="zbi-logo">
    </map>
     <% User user = (User)request.getAttribute("details");%>
        
    
    <div class="outer-container">
    
      <div class="profile-left-container">
            <div class="user-image" >
                <img src="<%= request.getContextPath() %>/IMAGE/user.png" alt="user">
            </div>
            <div class="profile-name">
                <h4><%= user.getName() %></h4>
                <p> <%= request.getSession(false).getAttribute("userType") %></p>
                <p> Id : <%= user.getUserId() %></p>
                <h5> <%= user.getStatus() %></h5>
            </div>
    	</div>
    	 <div class="profile-right-container">
        	
        <div class="profile-info">
        
            <div>
                <label for="email">Email:</label>
                <span id="email"><%= user.getEmail() %></span>
            </div>
            
            <div>
                <label for="mobile">Mobile number:</label>
                <span id="mobile"><%=user.getMobile() %></span>
            </div>
            
            <div>
                <label for="age">Age:</label>
                <span id="age"><%= user.getAge() %></span>
            </div>
            
            <div>
                <label for="gender">Gender:</label>
                <span id="gender"><%= user.getGender() %></span>
            </div>
            
            <%
           
            
            if( userType == UserType.EMPLOYEE ){
            Employee employee =(Employee) request.getAttribute("details");
            %>
             <div>
                <label for="aadhar">Branch Id:</label>
                <span id="aadhar"><%= employee.getBranchId() %></span>
            </div>
            
        <%    }else if(userType == UserType.CUSTOMER) {
 			Customer customer =  (Customer) request.getAttribute("details");   
        %>
        
        	<div>
                <label for="aadhar">Aadhar number:</label>
                <span id="aadhar"><%= customer.getAadhar() %></span>
            </div>
        	  <div>
                <label for="pan">PAN number:</label>
                <span id="pan"><%= customer.getPan() %></span>
            </div>
           
        <% }%>
       
         <a class="password" href="change-password">Change Password</a>
      </div>
  </div>
 
</body>
</html>
