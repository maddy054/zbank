<%@page import="com.zbank.enums.UserType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
   <script type="text/javascript" src="<%= request.getContextPath() %>/JavaScript/bankApp.js"></script>
</head>
<body>
 <% UserType userType = (UserType) request.getSession(false).getAttribute("userType");
 String type = (String) request.getAttribute("type");
 if(type == "customer"){ %>
 
         <form name="customer-create" action="create-customer" method="post">
        <% }else{ %>
       	<form name="create-employee" action="create-employee" method="post" >
        <%} %>

           <div class="input-container">
               
           	<div class="left-container">
                <div >
                    <label for="name">Name</label>
                    <input type="text" class="input" name="name" placeholder="Enter the Name"  pattern="[A-Za-z\s]{1,25}" title="Please enter a valid name (up to 25 characters)"  required>
                </div>
                <div >
                    <label for="mobile">Mobile</label>
                    <input type="text" class="input" name="mobile" placeholder="Enter Mobile Number"pattern="[0-9]{10}" title="Please enter a 10-digit mobile number" required>
                </div>
                
                <div >
                    <label for="email">Email</label>
                    <input  class="input" type="email" name="email" placeholder="Enter Email Id" pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}">
                </div>
                <div >
                    <label for="gender-text">Gender</label>
                    <select name="gender" class="input"  required>
                        <option disabled selected>Select Gender</option>
                        <option > MALE</option>
                        <option >FEMALE</option>
                        <option>OTHER</option>
                    </select>
                </div>
                <% if(type == "customer"){ %>
               
                <div >
                    <label for="address">Address</label>
                    <input type="text" class="input" name="address" pattern="{1,100}" placeholder="Enter the Address">
                </div>
    			<%} %>
            </div>
            <div class="right-container">
                <div >
                    <label for="age" >Age</label>
                    <input type="number" class="input" name="age"  min="18" max="100" title="Please enter a valid age between 18 and 100" placeholder="Enter the Age" required">
                </div>
                 <% if(type == "customer"){ %>
                <div >
                    <label for="aadhar">Aadhar Number</label>
                    <input type="text" class="input" name="aadhar" pattern="[0-9]{12}" title="Please enter a 12-digit Aadhar number"  placeholder="Enter the Aadhar" required>
                </div>
                <div >
                    <label for="pan">PAN Number</label>
                    <input type="text" class="input" name="pan"  pattern="[A-Z]{5}[0-9]{4}[A-Z]" title="Please enter a valid PAN number"  placeholder="Enter the Pan Number">
                                    </div>
				<%  }else{%>
				
				 <div > 
                    <label for="branchId">Branch Id</label>
                    <input type="text" class="input" name="branchId" pattern="[0-9]{3}" title="Please enter a valid branch Id" placeholder="Enter the Branch Id" required>
					 </div>
				<%} %>
               
                 <div > 
                    <label for="password">Password</label>
                    <input type="password" class="input" name="password" pattern="{5,16}" title="Length of password is between 5 and 16" placeholder="Create Temporary Password" required>
                </div>
                <title>Create/Modify</title>
            </div>
        </div>
        <div class="btn">
            <button  >Create Customer</button>
        </div>
     
    </form>

</body>
</html>