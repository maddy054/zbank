
<%@page import="com.zbank.models.Customer"%>
<%@page import="com.zbank.models.Account"%>
<%@page import="java.util.List"%>
<%@page import="com.zbank.models.Employee"%>
<%@page import="com.zbank.enums.UserType"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   
    <title>Customers</title>
    
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/customer-detail.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">
    <script type="text/javascript">
    	function getDetails(){
    		document.getElementById("table").style.display = "none";
    		document.getElementById("inputContainer").style.display = "flex";
    	}
    	
    
    </script>
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
            <% UserType userType = (UserType)request.getSession(false).getAttribute("userType");
            String type =(String) request.getAttribute("type");
            
            if( userType == UserType.EMPLOYEE ){ %>
                <a class ="this-nav">Customer</a>
                <a class ="other-nav"href="search-account">Accounts</a>
                <a class="other-nav" href="transaction">Transaction</a>
                <a class="other-nav" href="statement">Statement</a>
                <a class="other-nav" href="profile">Profile</a>
                
              <% }else{
            	 
            	  if(type == "customer"){%>
              
              	<a class ="other-nav" href="search-branch" >Branch</a>
                <a class ="other-nav"href="search-employee">Employee</a>
                <a class="this-nav" >Customer</a>
                <a class="other-nav" href="profile">Profile</a>
                
                <%} else{ %>
               	<a class ="other-nav" href="search-branch" >Branch</a>
                <a class ="this-nav">Employee</a>
                <a class="other-nav" href="search-customer" >Customer</a>
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
        <% if( type == "customer"){ %>
        	<a class="other-detail" href="search-customer">Search Customer</a>
            <a class="this-detail">All Customers</a>
            <a class="other-detail" href="create-customer">New Customer</a>
            <a class="other-detail" href="deactivate-customer">Deactivate Customer</a>
            <%} else{%>
            <a class="other-detail" href="search-employee">Search Employee</a>
            <a class="this-detail">All Employee</a>
            <a class="other-detail" href="create-employee">New Employee</a>
            <a class="other-detail" href="deactivate-employee">Deactivate Employee</a>
            
            <%} %>
        </div>
    </div>
     
       <div class="initial-input" id="table">
    <table class="table">
       <% if(type == "customer"){
            
            List<Customer> customers = (List<Customer>)request.getAttribute("customer");%>
            
            
            <tr>
            	
                <th>User Id</th>
                <th>Name</th>
                <th>Email id</th>
                <th>Mobile</th>
                <th>Aadhar</th>
                <th>PAN Number</th>
                <th>Status</th>
             </tr>
            <% for(Customer customer : customers){%>
           
            
               <tr >
                <td><%= customer.getUserId() %></td>
                <td ><%= customer.getName() %></td>
                <td><%= customer.getEmail() %></td>
                <td><%= customer.getMobile() %></td>
                <td><%= customer.getAadhar() %></td>
                <td><%= customer.getPan() %></td>
                <td><%= customer.getStatus() %></td>
            </tr>
            <%}}else if(type == "employee"){ 
            	List<Employee> employees = (List<Employee>)request.getAttribute("emolyees");%>
            	
           	<tr>
                <th>User Id</th>
                <th>Name</th>
                <th>Email id</th>
                <th>Mobile</th>
                <th>Branch Id</th>
                <th>Status</th>
            </tr>
               <%  for(Employee employee : employees){%>
            <tr>
                <td><%= employee.getUserId() %></td>
                <td><%= employee.getName() %></td>
               	<td><%= employee.getEmail() %></td>
                <td><%= employee.getMobile() %></td>
                <td><%= employee.getBranchId() %>
                <td><%= employee.getStatus() %></td>
              </tr>
                <%}} %>
        </table>
     <%  if(type == "customer"){ %>
         <form action="customer-detail">
         <%}else{ %>
         	<form action="employee-details">
         <%} %>
		<div class = "pagination">
 		<%long count = (long)request.getAttribute("page");
 		long pageNo =(long) request.getAttribute("pageNo");
   		for(long i=1;i<=count;i++){ 
   		if(pageNo != i){
   		%>
            <button class="page" name="pageNo" value="<%= i%>"><%= i %></button>
            <%}else{%>
            
             <button class="this-page"><%= i %></button>
          <%  } }%>
        </div>
    </form>
   </div>
    </div>
   </body>
</html>

        

    