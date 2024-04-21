<%@page import="com.zbank.models.Customer"%>
<%@page import="java.time.ZoneId"%>
<%@page import="java.time.Instant"%>
<%@page import="com.zbank.models.Employee"%>
<%@page import="com.zbank.models.User"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
  
               
        <% User user = (User)request.getAttribute("user"); 
        String type = (String) request.getAttribute("type");%>       
               
           	<div class="left-container">
                <div >
                <i class="fa-solid fa-user"></i>
                    <label >Name</label>
                    <span class="input"> <%=user.getName() %></span>
                </div>
                
                <div >
                <i class="fa-solid fa-mobile"></i>
                    <label for="mobile">Mobile</label>
                    <span class="input"><%=user.getMobile() %> </span>
                </div>
                
                <div >
                <i class="fa-solid fa-envelope"></i>	
                
                    <label for="email">Email</label>
                     <span class="input"> <%= user.getEmail() %></span>
                </div>
                <% if(type == "customer"){ %>
                <div >
                <i class="fa-solid fa-person-half-dress"></i>
                    <label for="gender-text">Gender</label>
                     <span class="input" id="gender-input"> <%= user.getGender() %></span>
                </div>
               <%} %>
               <div>
               <i class="fa-solid fa-square-plus"></i>
               		<label> Created By</label>
               		<span class="input" ><%= user.getCreatedBy() %></span>
               </div>
            </div>
            <div class="right-container">
            
            
                <div >
                <i class="fa-solid fa-person"></i>
                    <label for="age" >Age</label>
                   <span class="input"> <%= user.getAge() %></span>
                </div>
                 <% if(type == "customer"){ 
                 Customer customer = (Customer)user; %>
                 
                  <div >
                  <i class="fa-solid fa-location-dot"></i>
                    <label for="address">Address</label>
                     <span class="input"> <%= customer.getAddress() %></span>
                </div>
                
                <div >
                <i class="fa-solid fa-address-card"></i>
                    <label for="aadhar">Aadhar Number</label>
                     <span class="input"> <%= customer.getAadhar() %></span>
                </div>
                <div >
                <i class="fa-regular fa-id-card"></i>
                    <label for="pan">PAN Number</label>
                    
                    <span class="input" id="pan"> <%= customer.getPan() %></span>
                </div>
				<%  }else if(type== "employee"){
				
				Employee employee = (Employee)user;%>
				 <div >
				 <i class="fa-solid fa-person-half-dress"></i>
                    <label for="gender-text">Gender</label>
                     <span class="input"> <%= user.getGender() %></span>
                </div>
				 <div >
				 <i class="fa-solid fa-code-branch"></i> 
                    <label for="branchId">Branch Id</label>
                    <span class="input"> <%= employee.getBranchId() %></span>
				</div>
				
				<%} %>
				  <div>
				  <i class="fa-solid fa-calendar-days"></i>
               		<label> Created On</label>
               		
               		<span class="input" ><%= Instant.ofEpochMilli( user.getCreatedTime()).atZone(ZoneId.of("Asia/Kolkata")).toLocalDate() %></span>
               </div>
				
	               
            </div>
          
           	 <div  id="inputContainer"class="btn">
				<button onclick= "getEditPage();" id="btn">Edit Details</button>
			</div>
        
</body>
</html>