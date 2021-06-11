<%
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache"); 
	response.setDateHeader ("Expires", -1); 
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome to Leave Management System</title>
<script src="https://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function () {
		$.ajax({
		    type: "GET",
		    url: "LeaveMgntController",
		    data:{operation:'all'},
		    contentType: "application/json",
		    async: false,
		    success: function (data) {
		    	for (var key in data) {
		    		$("#drop-down").append("<option value='"+key+"'>"+key+"</option>"); 
		    	}
		    }
	    });
	});
	
	function getEmployeeData(empId) {
		$.ajax({
		    type: "GET",
		    url: "LeaveMgntController",
		    data:{operation:'byId',id:empId},
		    contentType: "application/json",
		    async: false,
		    success: function (data) {
		    	if (data !== null) 
		    	{
		    		$("#name").text(data.name);
		    		$("#balance").text(data.availableLeaves);
		    	}
		    	else
		    	{
			    	$("#name").text("");
					$("#balance").text("");
		    	}
		    }
		});
	}
	
</script>
</head>
<body>
	<form action="${pageContext.request.contextPath}/LeaveMgntController" method="post" name="leave">
		Select Your Employee ID: <select name='empId' id="drop-down" onchange="getEmployeeData(this.value);"><option value="0" selected="selected">---Select Employee ID---</option></select><br><br>
		Employee Name: <label id="name"></label><br><br>
		Employee Leave Balance: <label id="balance"></label><br><br>
		Enter No of Days: <input type="text" name="noOfDays"/><br><br>
		<input type="submit" name="Submit" value="Submit" />
	</form>
</body>
</html>