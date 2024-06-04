<%@page import="com.zbank.enums.UserType"%>
<%@ page import="com.zbank.enums.UserType" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>

<html lang="en">
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction</title>
    
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
        	
        	<% UserType userType = (UserType) request.getSession(false).getAttribute("userType");
        	
        	if( userType == UserType.EMPLOYEE){ %>
            <a class="other-nav" href="search-customer">Customer</a>
           
            <a class="other-nav"href="search-account">Accounts</a>
             <a class="this-nav">Transaction</a>
            <a class="other-nav" href="statement">Statement</a>
            <a class="other-nav" href="profile">Profile</a>
            
           <%  } else if(userType == UserType.CUSTOMER){ %>
           
          	 <a class="other-nav" href="accounts">Accounts</a>
             <a class="this-nav">Transaction</a>
             <a class="other-nav" href="statement">Statement</a>
             <a class="other-nav" href="profile">Profile</a>
            <% } %>
            
        </nav>
        </div>
         <map name="logo">
        <area shape="rect" coords="0,0,200,100" href="dashboard" alt="zbi-logo">
    </map>
    
     <div class="logout">
			<img src="<%= request.getContextPath() %>/IMAGE/log-out.png" usemap="#logout-map">
     </div>
        <map name="logout-map">
    		<area shape="rect" coords="0,0,100,200" href="logout" alt="logout">
        </map>
    </header>
    
    <% if((boolean)request.getAttribute("isHaveAccounts")){ %>
        <div class="overall">
    
     <div class="main-container">
		 <% if(request.getAttribute("transactionMethod") == "transaction"){ %>
			 <div class="selection"> 
			 <a class="this-detail" >Bank Transfer</a>
		 	 <a class="other-detail" href="deposit-withdraw"> Deposit - Withdraw</a>
		 	 </div>
			 <%}if(request.getAttribute("transactionMethod") == "deposit-withdraw"){ %>
			 <div class="selection"> 
			 <a class="other-detail" href="transaction" >Bank Transfer</a>
		 	 <a class="this-detail"> Deposit - Withdraw</a>
		 	</div>
			 <%}%>
			 </div>
	<div class="initial-input">
		 <div class="outer-container">
		
		 <div class="image-container">
		 	<img alt="money-transfer" src="<%= request.getContextPath() %>/IMAGE/moneyTransfer.jpg">
		 </div>
        <% String method =(String) request.getAttribute("transactionMethod");
        
        if(method == "transaction"){
        %>
        <form action="transaction" method="post">
 		<% } else if(method == "deposit-withdraw"){ %>
       	
       	
       	 <form action="deposit-withdraw" method="post">
       	 
       	 <% } %>
            <div>
            	 <label for="account-number" > Account Number </label>
            
            <% if(userType == UserType.EMPLOYEE){%>
            	
            	  <input class="input" name="accountNumber" type="number" placeholder="Enter the Account Number" required="required">
          
           <%  } else if(userType == UserType.CUSTOMER){ %>
            	
            	<select class="input" name="accountNumber" required="required" >
                    <option disabled selected>Select Account</option>
                    
                    <% List<Long > accounts =(List<Long>) request.getAttribute("accounts"); 
                    for(Long account: accounts){ %>
                    	<option ><%= account %> </option>
                    
                    <% }
            	} %>
                 </select>
            </div>
            
             <% if(method == "transaction"){ %>
             
            <div >
                <label for="receiver account number">Receiver Account Number</label>
                <input class="input" name="receiverAccount" type="text" pattern="[0-9]{10}" title="Please Enter 10 Digit Account Number" placeholder="Enter the Receiver Account Number" required>
            </div>
            
            <div>
            	 <label for="account-select" >Receiver Bank </label>
            	 <select class="input" name="description" required="required" >
                    <option disabled selected>Select Account </option>
                    <option value="INTRA_BANK">ZBI</option>
                    <option value="INTER_BANK"> Other Bank </option>
                 </select>
            </div>
            
           	<%  } else if(method == "deposit-withdraw"){ %>
            	<div>
            	 	<label for="account-select" >Transaction  Type</label>
            		<select class="input" name="description" required="required">
                   		 <option disabled selected>Select Type</option>
                   		<option > DEPOSIT </option>
                   		<option >WITHDRAW</option>
                 	</select>
            	</div>
            	
         <%    }  %>
            
          
           
           <div>
            	<label for="amount">Amount to send </label>
            	<input class="input"  name="amount" type="number" min=1 title="Please Enter the Amount Greater than 1" placeholder="Enter the Amount to Send" required>
           </div >

           <div class="btn">
           		<button type="submit"> Transfer Money</button>
           </div>
           <% String status = (String)request.getAttribute("status");
             if(status != null){ %>
            	 <div  class="status">
           
           	  <p><%= status %></p>
           </div>
             <% }%>
           
         </form>
      </div>
    </div>
   
    </div>
     <% } %>
 	    
  
</body>
</html>