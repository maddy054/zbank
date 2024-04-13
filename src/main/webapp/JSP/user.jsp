<!DOCTYPE html>
<html lang="en">

<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Z Bank Home</title>
    
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/user-style.css">
<link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/IMAGE/zbi.png">

</head>
<body>
    <div class="topnav">
        <img class="logo" src="<%= request.getContextPath() %>/IMAGE/zbi.png" alt="zbi-logo">
    </div>
    <div class="welcome-text">
        <h1> Welcome Back!! Madhavan K </h1> 
    </div>
    <div class="Details">
       
    </div>
    <div class="image-container">
        <div class="transaction-image">
            <img src="<%= request.getContextPath() %>/IMAGE/tra.jpg" alt="transaction-image" usemap="#transactionMap">
        </div>
        <map name="transactionMap">
            <area shape="rect" coords="0,0,1000,2000" href="transaction" alt="statement-image">
        </map>
        <div class="statement-image">
            <img src="<%= request.getContextPath() %>/IMAGE/Statement.png" alt="statement-image" usemap="#statementMap">
        </div>
        <map name="statementMap">
            <area shape="rect" coords="0,0,1000,2000" href="statement" alt="statement-image">
        </map>
        <div class="account-image">
            <img src="<%= request.getContextPath() %>/IMAGE/account.jpg" alt="account-image" usemap="#accountMap">
        </div>
        <map name="accountMap" >
            <area shape="rect" coords="0,0,1000,2000" href="accounts">
        </map>
    </div>
    <div class="footer">
        <h2>Make a transaction</h2>
        
        <h2>Transaction statement</h2>
        <h2>Accounts Details</h2>
    </div>
    
</body>
</html>