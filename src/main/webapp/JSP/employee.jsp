<!DOCTYPE html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
	 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="icon" type="image/x-icon" href="zbi.png">
    
</head>
<body>
    <header>
        <div class="logo">
            <img src="<%= request.getContextPath() %>/IMAGE/zbi.png" alt="zbi logo" usemap="#logo">
        </div>
        <div class="top">
        <nav class="topnav">   
            <a class ="other-nav"href="search-customer">Customer</a>
            <a class ="other-nav"href="all-account">Accounts</a>
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
 <div class="dash-containers">
        <div class="dash-left-container">
            <div>
                <i class="fa-solid fa-building-columns fa-8x"></i>
            </div>
            <div class="detail-container">
                <i class="fa-solid fa-location-dot"></i>
                <label for="value">Branch - </label>
                <span class="value" id="branch"> -<%= request.getAttribute("branchName") %></span>
            </div>

            <div class="detail-container">
                <i class="fa-solid fa-money-check"></i>
                <label for="value"> Branch Id - </label>
                <span class="value" id="id">- <%= request.getSession(false).getAttribute("branchId") %></span>
            </div>
            <div class="detail-container">
                <i class="fa-solid fa-piggy-bank"></i>
                <label for="value"> Total Accounts - </label>
                <span class="value" id="account">- <%= request.getAttribute("accountCount") %></span>
            </div>

         <%--    <div class="detail-container">
                <i class="fa-solid fa-user"></i>
                <label for="value"> Total Customers - </label>
                <span class="value" id="customer">- <%= request.getAttribute("customerCount") %></span>
            </div> --%>

           
            
            
        </div>
        <div class="dash-right-container">
           
         
            <div class="employee-dash-container">
                <h3 >New Accounts</h3>
                <p>76514976134 - Madhavan</p>
                <p> 64457697465 - Mukesh</p>
                <p>76514976134 - Madhavan</p>
                <p> 64457697465 - Mukesh</p>
                <div class="btn">
                    <button>All Accounts</button>

                  
                </div>
            </div>
        </div>
    </div>
    
</body>

