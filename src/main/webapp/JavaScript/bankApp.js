const $ = require('jquery');
 process.env.NODE_TLS_REJECT_UNAUTHORIZED = 0;

function getBranch() {

	var branchId = document.getElementById("branchId");
	fetch( 'https://localhost:8443/zbank/api/branch?branchId='+branchId.value, {
		method: 'GET',
		headers:{
			apiKey : 'KN7iRWZ1wgqZWOQvz9pWMM5wWEGYfnNk',
		},
	})
		.then(response => {
			if (response.ok) {
				return response.text();
			}
			throw new Error('Network response was not ok.');
		})
		.then(json => {
		
         var jsonObj = JSON.parse(json);
         document.getElementById("details").style.display = 'flex';
         document.getElementById("branchid").innerHTML = jsonObj.branchId;
         document.getElementById("branchName").innerHTML = jsonObj.branchName;
         document.getElementById("address").innerHTML = jsonObj.address;
         document.getElementById("ifscCode").innerHTML = jsonObj.ifsc;
        
		})
		.catch(error => {
			console.error('Error:', error.message);
		});
}


function getEmployee(){
	var userId = document.getElementById("search_user_id").value;
	fetch('https://localhost:8443/zbank/api/employee?userId='+userId, {
		method:"GET",
		headers:{
			apikey:'KN7iRWZ1wgqZWOQvz9pWMM5wWEGYfnNk',
		},
	} )
	.then(response => {
		if(response.ok){
			return response.json();
		}
		throw new Error('Response not ok');
	})
	.then(json =>{
		document.getElementById("inputContainer").style.display = 'flex';
		document.getElementById('user_name').innerHTML = json.name;
		document.getElementById('user_mobile').innerHTML = json.mobile;
		document.getElementById('user_email').innerHTML = json.email;
		document.getElementById('user_created_by').innerHTML = json.createdBy;
		document.getElementById('user_age').innerHTML = json.age;
		document.getElementById('employee_gender').innerHTML = json.gender;
		document.getElementById('employee_branch').innerHTML = json.branchId;
		document.getElementById('user_created-on').innerHTML = json.createdOn;
		
	})
	.catch(error => {
		console.error('Error: ',error.message);
	})	
}

 function getEditPage() {
	
	 	document.getElementById("editPage").style.display = "flex";
	 	document.getElementById("inputContainer").style.display="none";
	 	document.getElementById("btn").style.display="none";
    }
    function hideEditPage(){
    	document.getElementById("editPage").style.display = "none";
    }


$(document).ready(function(){
	$("#search_branch").click(function(){
		$.ajax({
			method:"GET",
			url:"https://localhost:8443/zbank/api/branch?branchId=100",
			headers:{
				apiKey:"KN7iRWZ1wgqZWOQvz9pWMM5wWEGYfnNk"
			},
			dataType:"json",
			success: function(response){

				alert(response);
			},
			error:function(xhr,status,error){
				alert(xhr.responseText);
			}
		});
	});
});

