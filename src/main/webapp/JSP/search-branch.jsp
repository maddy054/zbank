<%@page import="com.zbank.models.Branch"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Branch Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>    
    <script type="text/javascript" src="<%= request.getContextPath() %>/JavaScript/BankJQuery.js"></script>
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
                <a class ="this-nav" >Branch</a>
                <a class ="other-nav"href="search-employee">Employee</a>
                <a class="other-nav" href="search-customer">Customer</a>
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
			<a class="this-detail" >Search Branch</a>    	
            <a class="other-detail" href="all-branches">All Branch</a>
            <a class="other-detail" href="create-branch">New Branch</a> 
           
        </div>
    </div>
    <div class="initial-input">

   		<div class="statement-form">
   			<label>Branch Id</label>
      		<input id="branchId" class="input" type="number" name="branchId" placeholder="Enter the Branch Id"></input>
    	</div > 
        <div class="btn" id="search_branch">
            <button type="submit">Search</button>
        </div>
        <div  style="display: none;" id="messageDiv" class="status">
            <p id="message"></p>
        </div>
   
        <div>
    
            <div style="display:none;" id="details" class="outer-container">
       
                <div class="form-container">
                    <form action="create-branch" method="post">  
                        <div class="singleInput">
                            <label  class="label">Branch Id</label>
                            <span id="branchid" class="input"> </span>
                        </div>
                        <div class="singleInput">
                            <label  class="label" >Branch Name</label>
                            <span  id="branchName" class="input"></span>
                        </div>
                        <div class="singleInput">
                            <label  class="label">IFSC code</label>
                            <span id="ifscCode" class="input"></span>
                        </div>
                        <div class="singleInput">
                            <label  class="label" >Address</label>
                            <span id="address" class="input"></span>
                        </div>
                   
                     </form>
                </div>
   		    </div>
        </div>
    </div>
    </div>

</body>

</html>