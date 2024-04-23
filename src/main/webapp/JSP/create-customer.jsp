 <%@page import="com.zbank.enums.UserType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Customer</title>
     <script type="text/javascript" src="<%= request.getContextPath() %>bankApp.js"></script>
    
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">
    
</head>
<body>
    <header>
        <div class="logo">
            <img src="<%= request.getContextPath() %>/IMAGE/zbi.png" alt="zbi logo" usemap="#logo">
        </div>
        <div class="top">
        
            <nav class="topnav">   
            
            <% UserType userType = (UserType) request.getSession(false).getAttribute("userType");
            String type = (String) request.getAttribute("type");
            
            if(userType == UserType.EMPLOYEE){%>
               <a class ="this-nav">Customer</a>
               <a class ="other-nav"href="search-account">Accounts</a>
               <a class="other-nav" href="transaction">Transaction</a>
               <a class="other-nav" href="statement">Statement</a>
               <a class="other-nav" href="profile">Profile</a> 
             <%} else{ 
             	if(type == "customer"){ %>
                <a class ="other-nav" href="search-branch" >Branch</a>
                <a class ="other-nav"href="search-employee">Employee</a>
                <a class="this-nav" >Customer</a>
                <a class="other-nav" href="profile">Profile</a>
                
                <% } else{%>
                
                <a class ="other-nav" href="search-branch" >Branch</a>
                <a class ="this-nav">Employee</a>
                <a class="other-nav"href="search-customer" >Customer</a>
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
        
        <% 
        if(type == "customer"){%>
        <a class="other-detail" href="search-customer">Search Customer</a>
            <a class="other-detail" href="customer-detail">All customers</a>
            <a class="this-detail" >New Customer</a>
            <a class="other-detail" href="deactivate-customer">Deactivate Customer</a>
            <%}else{ %>
            <a class="other-detail" href="search-employee">Search Employee</a>
            
             <a class="other-detail" href="employee-details">All Employee</a>
            <a class="this-detail" >New Employee</a>
            <a class="other-detail" href="deactivate-employee">Deactivate Employee</a>
            <%} %>
        </div>
    </div>
    <div class="initial-input">
    <jsp:include page="employee-form.jsp"></jsp:include>
    </div>
    </div>

   
</body>