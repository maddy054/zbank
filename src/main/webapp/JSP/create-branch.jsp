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
      	 	<a class="other-detail" href="search-branch">Search Branch</a>
            <a class="other-detail" href="all-branches">All Branch</a>
            <a class="this-detail">New Branch</a>
           
        </div>
    </div>
     <div class="initial-input">
    <div class="outer-container">
        <div class="image-container">
            <img class="branch-image" src="<%= request.getContextPath() %>/IMAGE/branch.jpg">
        </div>
        
            <div class="form-container">
                <form action="create-branch" method="post">  
                    <div class="singleInput">
                        <label for="BranchName" class="label" >Branch Name</label>
                        <input name="branchName" class="input" type="text" pattern="{3,50}" placeholder="Enter the Branch Name "required>
                    </div>
                   <div class="singleInput">
                        <label for="ifscCode" class="label">IFSC code</label>
                        <input name="ifsc" class="input" type="text" pattern="[0-9]{12}" title="Please enter a 12 digit IFSC code" placeholder="Enter the IFSC Code" required>
                   </div>
                   <div class="singleInput">
                        <label for="address" class="label" >Address</label>
                        <input name="address" class="input" type="text" min=3 max= 100 title="Maximum length is 100 character and minimum length is 3 character" placeholder="Enter the Branch Address"required>
                   </div>
                   <div class="btn">
                        <button >Create Branch</button>
                   </div>
                   <% Object message = request.getAttribute("message") ;
                   if(message != null){ %>
                   <div class="status">
                   		<p> <%=(String) message %></p>
                   </div>
                   <%} %>
                </form>
            </div>
    </div>
    </div>
    </div>

</body>
</html>