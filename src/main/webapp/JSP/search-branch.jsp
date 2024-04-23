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
     <form class="statement-form" action="search-branch" method="post">
  
      
          <div>
            <label>Branch Id</label>
             <input class="input" type="number" name="branchId" placeholder="Enter the Branch Id"></input>
         </div > 
         <div class="btn">
            <button type="submit">Search</button>
         </div>
    </form>
    
    <% Object branchObj = request.getAttribute("branch");
    
    if(branchObj != null){
    Branch branch = (Branch)branchObj;%>
    
  
    <div class="outer-container">
       
            <div class="form-container">
                <form action="create-branch" method="post">  
                <div class="singleInput">
                       <label for="BranchId" class="label">Branch Name</label>
                       <span class="input"><%= branch.getBranchId() %> </span>
                    </div>
                    <div class="singleInput">
                        <label for="BranchName" class="label" >Branch Name</label>
                       <span class="input"><%= branch.getBranchName() %></span>
                    </div>
                   <div class="singleInput">
                        <label for="ifscCode" class="label">IFSC code</label>
                      	<span class="input"><%= branch.getIfsc() %></span>
                   </div>
                   <div class="singleInput">
                        <label for="address" class="label" >Address</label>
                       	<span class="input"><%= branch.getAddress() %></span>
                   </div>
                   
                </form>
            </div>
    </div>
    <%} %>
    <% Object message = request.getAttribute("message") ;
        if(message != null){ %>
         <div class="status">
                <p> <%=(String) message %></p>
         </div>
        <%} %>
        </div>
        </div>

</body>
</html>