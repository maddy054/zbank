    
<%@page import="com.zbank.enums.UserType"%> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Customer</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">
    <script type="text/javascript" src="<%= request.getContextPath() %>/JavaScript/bankApp.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>    
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
        	<a class="this-detail">Search Customer</a>
            <a class="other-detail" href="customer-detail">All Customers</a>
            <a class="other-detail" href="create-customer">New Customer</a>
            <a class="other-detail" href="deactivate-customer">Deactivate Customer</a>
            <%}else{ %>
            <a class="this-detail">Search Employee</a>
             <a class="other-detail" href="employee-details">All Employee</a>
            <a class="other-detail" href="create-employee" >New Employee</a>
            <a class="other-detail" href="deactivate-employee">Deactivate Employee</a>
            <%} %>
        </div>
    </div>
    
	<div class="initial-input">
		<div class="form-container ">
		
          <div>
            <label>User Id</label>
             <input class="input" type="number"  id="search_user_id" min=1 max=1000 title="Please Enter the User Id Between 1 and 1000" name="userId" placeholder="Enter the User Id"></input>
         </div > 
         <div class="btn">
            <button type="submit">Submit</button>
         </div>
         
       </div>
    
  
     <div id="edit-container" >
          <div class="input-container" id="inputContainer">
           	<div class="left-container">
                <div >
                    <i class="fa-solid fa-user"></i>
                    <label for="name">Name</label>
                    <span  id="user_name"class="input"> </span>
                </div>
                
                <div >
                    <i class="fa-solid fa-mobile"></i>
                    <label for="mobile">Mobile</label>
                    <span id="user_mobile"class="input"> </span>
                </div>
                
                <div >
                	<i class="fa-solid fa-envelope"></i>	
                    <label for="email">Email</label>
                    <span class="input" id="user_email"> </span>
                </div>
                <%if(type == "customer"){ %>
                <div >
            	    <i class="fa-solid fa-person-half-dress"></i>
                    <label for="gender-text">Gender</label>
                    <span class="input" id="user_gender"> </span>
                </div>
                <%} %>
               <div>
                    <i class="fa-solid fa-square-plus"></i>
               		<label> Created By</label>
               		<span class="input" id="user_created_by" ></span>
               </div>
            </div>
            <div class="right-container">
            
            
                <div >
                	<i class="fa-solid fa-person"></i>
                    <label for="age" >Age</label>
                   <span class="input" id="user_age"></span>
                </div>
                <%if(type == "customer"){ %>
                  <div >
               	    <i class="fa-solid fa-location-dot"></i>
                    <label for="address">Address</label>
                     <span class="input" id="user_address"></span>
                </div>
                
                <div >
                	<i class="fa-solid fa-address-card"></i>
                    <label for="aadhar">Aadhar Number</label>
                     <span class="input"id="user_aadhar"> </span>
                </div>
                <div >
                	<i class="fa-regular fa-id-card"></i>
                    <label for="pan">PAN Number</label>
                    
                    <span class="input" id="user_pan"> </span>
                </div>
					<%} else{%>
				 <div >
				     <i class="fa-solid fa-person-half-dress"></i>
                     <label for="employee_gender">Gender</label>
                     <span class="input"> </span>
                
               </div>
			
				 <div >
				 
				    <i class="fa-solid fa-code-branch"></i> 
                    <label for="branchId">Branch Id</label>
                    <span id="employee_branch"class="input"> </span>
				</div>
				<%} %>
				  <div>
				    <i class="fa-solid fa-calendar-days"></i>
               		<label> Created On</label>
               		<span id="user_created-on"class="input" ></span>
               </div>
				
            </div>
        </div>
        <div  id="inputContainer"class="btn">
			<button onclick= "getEditPage();" id="btn">Edit Details</button>
		</div>
       </div>
     </div>
   </div>
</body>