

$(document).ready(function(){
    $("#search_branch").click(function(){
        var branchId = $('#branchId').val();
        if(branchId != ''){
            $.ajax({
            method:"GET",
            url:"https://localhost:8443/zbank/api/branch?branchId="+$('#branchId').val(),
            headers:{
                apiKey:"KN7iRWZ1wgqZWOQvz9pWMM5wWEGYfnNk"
            },
            dataType:"json",

            success: function(branch){
   
                if(branch.errorCode != undefined){
                    $('#messageDiv').css('display','flex');
                    $("#details").css('display','none');
                    $("#message").html('No Such Branch');
                } else{
                    $('#messageDiv').css('display','none');
                    $("#details").css('display','flex');
                    $("#branchid").html(branch.branchId);
                    $("#branchName").html(branch.branchName);
                    $('#address').html(branch.address);
                    $('#ifscCode').html(branch.ifsc);
            
                }

            },
            error:function(xhr,status,error){
                alert(xhr.responseText);
            }
        });
        }else{
            $('#messageDiv').css('display','flex');
            $("#details").css('display','none');
            $("#message").html('Enter Branch Id to Search');
        }
        
    });
});