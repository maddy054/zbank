    
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">
    
    <script type="text/javascript">
    function getEditPage() {
  
	 	document.getElementById("editPage").style.display = "flex";
	 	document.getElementById("inputContainer").style.display="none";
    }
    function hideEditPage(){
    	document.getElementById("editPage").style.display = "none";
    }
    
    
    </script>
    
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
	
    <%if (type=="customer") {%>
     <form class="statement-form" action="search-customer" method="post">
     <%}else{ %>
     <form class="statement-form" action="search-employee" method="post">
     <%} %>
      
          <div>
            <label>User Id</label>
             <input class="input" type="number"  min=1 max=1000 title="Please Enter the User Id Between 1 and 1000" name="userId" placeholder="Enter the User Id"></input>
         </div > 
         <div class="btn">
            <button type="submit">Submit</button>
         </div>
    </form>
    
     <% Object messageObj = request.getAttribute("message");
    if(messageObj != null){%>
    
    <div class="status">
    	<p> <%= (String)messageObj %></p>
    </div>
     <%}%>
  
    
   
  
    <% Object userObj = request.getAttribute("user");
    
    if(userObj != null){%>
    
    <% User user = (User) userObj; 
    
    if(type == "customer"){%>
     <form action="edit-customer" method="post">
     <%}else{ %>
     <form action="edit-employee" method="post">
     <%} %>
             <div class="edit-container" id="editPage">
             <div class="input-container">
             
             
             <input style="display: none" value="<%=user.getUserId()%>" name="userId">
             <div class="left-container">
             	<div>
             		<label for="changeName" > Name</label>
             		<input class="input" id="changeName" name="name" value="<%=user.getName() %>" >
             	</div>
             	<div>
             		<label for="changeMobile">Mobile</label>
             		<input class="input" id="changeMobile" name="mobile" value="<%=user.getMobile() %>">
             		
             	</div>
             	<div>
             		<label for="changeMail">Email</label>
             		<input class="input" id="changeEmail" name = "email" value="<%=user.getEmail() %>">
             		
             	</div>
             	
   
             </div>
             	
           <%  if(type == "customer"){ 
             Customer customer = (Customer)user; 
          
             %>
                    
               <div class="right-container">
               <div>
             		<label for="changeAddress">Address</label>
             		<input class="input" id="changeAddress" name="address" value="<%=customer.getAddress() %>">
             	</div>
             	
             	<div>
             		<label for="changeAadhar">Aadhar</label>
             		<input class="input" id="changeAadhar" name="aadhar" value="<%=customer.getAadhar() %>">
             	</div>
             	
             	<div>
             		<label for="changePan">PAN </label>
             		<input class="input" id="changePan" name="pan" value="<%=customer.getPan() %>">
             	</div>
               </div>
             	
             <%} %>
             </div>
            <div onclick="hideEditPage();" class="btn">
            
            	<button>Change</button>
            </div>
             
             
             </div>
              </form>
    
    
    
          <div class="input-container" id="inputContainer">
               
               
               
           	<div class="left-container">
                <div >
                <i class="fa-solid fa-user"></i>
                    <label for="name">Name</label>
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
                     <span class="input"> <%= user.getGender() %></span>
                </div>
               <%} %>
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
	               
            </div>
           
        </div>
           
           	 <div onclick= "getEditPage();" id="inputContainer"class="btn">
				<button>Edit Details</button>
			</div>
         </div>
             </div>
             <%} %>
</body>