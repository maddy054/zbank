<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Branch Details</title>
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
                <a class ="other-nav" href="all-branches" >Branch</a>
                <a class ="other-nav"href="employee-details">Employee</a>
                <a class="other-nav" href="customer-detail">Customer</a>
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
    </body>
    </html>