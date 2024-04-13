<%@page import="com.zbank.models.Account"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.zbank.models.Branch" %>
       
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head> 
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Branch Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/navigation.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/employee-account.css">
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
            <a class="this-detail">All Branch</a>
            <a class="other-detail" href="create-branch">New Branch</a>
           
        </div>
    </div>
	<div class="initial-input">
    <table class="table">
      <tr >
         <th>Branch Id</th>
         <th>Branch Name</th>
         <th>IFSC code</th>
         <th>Address</th>
      </tr>
      <% 
      Map<Integer,Branch > branches =(Map<Integer,Branch >) request.getAttribute("branches");
            
      for(Map.Entry<Integer,Branch> entry : branches.entrySet()){
        Branch branch = entry.getValue();%>
        <tr>
           <td><%= branch.getBranchId() %></td>
           <td><%= branch.getBranchName() %></td>
           <td><%= branch.getIfsc() %></td>
           <td><%= branch.getAddress() %></td>
         </tr>
          <% }   %>
    </table>
    <form action="all-branches">
		<div class = "pagination">
 		<%long count = (long)request.getAttribute("page");
 		long pageNo =(long) request.getAttribute("pageNo");
   		for(long i=1;i<=count;i++){ 
   		if(pageNo != i){
   		%>
            <button class="page" name="pageNo" value="<%= i%>"><%= i %></button>
            <%}else{%>
            
             <button class="this-page"><%= i %></button>
          <%  } }%>
        </div>
    </form>
    </div>
    </div>
      
</body>
</html>