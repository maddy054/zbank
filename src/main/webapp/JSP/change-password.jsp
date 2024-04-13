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
    
    
    <script>
     function validatePassword() {
      var password = document.getElementById("password").value;
      var confirmPassword = document.getElementById("confirmPassword").value;
  
      if (password != confirmPassword) {
        document.getElementById("message").innerHTML = "Passwords do not match!";
        return false;
    } else {
        document.getElementById("message").innerHTML = "";
        return true;
    }
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
    
     <div class="outer-container">
        <div class="image-container">
            <img class="branch-image" src="<%= request.getContextPath() %>/IMAGE/forget-password.jpg">
        </div>
        
            <div class="form-container">
                <form action="change-password" method="post" onsubmit="return validatePassword()">  
                    <div class="singleInput">
                        <label class="label" >Old Password</label>
                        <input name="oldPass" class="input" type="password" placeholder="Enter the Old Password "required>
                    </div>
                    
                   <div class="singleInput">
                        <label class="label">New Password</label>
                        <input name="newPass" class="input" type="password" id= "password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}" 
         title="Password must contain at least one digit, one lowercase and one uppercase letter, and be at least 8 characters long" 
         placeholder="Enter the New Password" required>
         
         			 <div class="singleInput">
                        <label class="label" >Confirm Password</label>
                        <input name="oldPass" class="input" type="password" id="confirmPassword" placeholder="Re-Enter the New Password "required>
                    </div>
                   </div>
                   <div>
						<p>Requirement</p>                   
                   </div>
                   <ul>
                   		<li>Must contain a Alphabet, Number <br>and a special character.</li> 
                   		<li>Must contain a upper case and <br>lower case letter.
                   		<li>8 to 16 character in length.</li>
                   		
                   </ul>
 					<% Object message = request.getAttribute("message");
 					
 					if(message != null){%>
 					
 					<div class="status" id="message">
 						<p><%= message %>
 					</div>
 				<%	}%>
                  
                   <div class="btn">
                        <button >Change Password</button>
                   </div>
                </form>
            </div>
    </div>
    </body>
    </html>
    