<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customers</title>
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
              <a class ="other-nav" href="search-customer">Customer</a>
                <a class ="this-nav"href="employee-account">Accounts</a>
                <a class="other-nav" href="transaction">Transaction</a>
                <a class="other-nav" href="statement">Statement</a>
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
        <a class="other-detail" href="search-account">Search Account</a>
            <a class="other-detail" href="all-account">All Accounts</a>
            <a class="other-detail" href="create-account">Create Account</a>
            <a class="this-detail">Deactivate Account</a>
        </div>
    </div>
	     <div class="initial-input">
    <div class="outer-container">
        <div class="image-container">
            <img src="<%= request.getContextPath() %>/IMAGE/No-account-found.jpeg" alt="account-delete" >
        </div>
        <div class="form-container">
            <form action="deactivate-account" method="post">
               
                <div >
                    <label  for="account">Account Number</label>
                    <input type="text" name="accountNumber" pattern="[0-9]{10}" title="Please Enter a 10 Digit Account Number" class="input" placeholder="Enter Account Number">
                </div>
                <div class="btn">
                    <button type="submit">Deactivate Account</button>
                </div>
            </form>
        </div>
        </div>
        </div>

    </div>
</body>

    