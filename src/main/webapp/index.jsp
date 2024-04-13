<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to ZBI</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">

    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">
    
</head>
<body>
    <header>
        <div class="logo">
            <img src="<%= request.getContextPath() %>/IMAGE/zbi.png" alt="zbi logo" usemap="#logo">
          
        </div>
        <div class="top">
            <div class="topnav">
                <h2 class="other-nav">Z Bank Of India</h2>
            </div>
        </div>
        
       
        
	</header>
	
    <div class="dash-container">
        <div>
            <div class="para">
                <p>"Providing exceptional banking services<br> tailored to your needs"</p>
            </div>
          <form class="login-button" action="service/login">
            <button >Login</button>
          </form>
        </div>
        
        <div class="image-container-dash">
            <img src="<%= request.getContextPath() %>/IMAGE/dash-bank.jpg" alt="bank-image">
        </div>
    </div>	
	
</body>
</html>