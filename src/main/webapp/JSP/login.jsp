<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Z Bank - Login</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    
</head>

<body>
    <header>
        <div class="logo">
            <img src="<%= request.getContextPath() %>/IMAGE/zbi.png" alt="zbi logo" usemap="#logo">
        </div>   
    </header>
     <div class="outer-container">
        <div class="image-container">
            <img class="front" src="<%= request.getContextPath() %>/IMAGE/login.jpg" alt="image">
        </div>
       
		       
        <div class="login-right-container">
        <div class="intro-container">
       
            <h1 class ="welcome-text"> Welcome to ZBI </h1>
            <h2 class="login-text">Sign in</h2>
        </div>
           
        <div class="form-container">
           <form action="login" method="post">
                <div>
                    <label for="userId">User Id</label>
                    <input type="number" class="input" name="userId"  min=1 max= 1000 title="Please enter user id between 1 and 1000"required="required" >
                </div>
                <div class="input-group">
                    <label for="password" >Password</label>
                    <input type="password" class="input" pattern="{8,16}" title="Please enter a valid password" name="password" required="required">
                </div>
                <div class="btn">
                  <button type="submit">Sign in</button>
                </div>
                <% Object messageObj = request.getAttribute("message");
                
                if(messageObj != null){%>
                <div class="status">
                	<p><%=(String )messageObj %> </p>
                </div>
                <%} %>
            </form>
           </div> 
           
        </div>
    </div>
</body>
</html>
