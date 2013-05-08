<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
<head>
<meta charset ="utf-8"/>
<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" /> -->
<!-- <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script> -->

<link rel="stylesheet" type="text/css"
href="../resources/account/css/theme.default.css"></link>

<link rel="stylesheet"
    href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" />

<script src="../resources/account/js/jquery-1.9.1.js"></script>
<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>
<script src="../resources/account/js/jquery-ui.js"></script>

<title>Tempoplus Supervisor</title>

<script>
$(function() {
$( "#employeeAccess" ).tabs();
$( "#adminTab" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
$( "#adminTab li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
});
</script>

</head>


<body>
<div id ="employeeAccess">
	<div align = "right"> 
		<table>  
			<tr>
			<td>Hi ${userFirstName}!</td>
			</tr>
			<tr>
			<td><a href = "logout">Logout</a></td>
			</tr>
		</table>
	</div>
	<ul>
		<li><a href = "../timelog/userLog">View Time Log</a></li>
		<li><a href = "#MyProfile" >My Profile</a></li>
		<li><a href = "#AccountSettings">Account Settings</a></li>
		<li><a href = "#Administration">Administration</a></li>
	</ul>
		

	<div id = "MyProfile">
		<table align = "center">
		<tbody id = "UserDetailsTable">
				<tr>
					<td>  </td>
					<td>  </td>
				</tr>
						
						<tr align = "center">
							<th><b>
							${employeeDetailsList.firstname} ${employeeDetailsList.middlename} ${employeeDetailsList.lastname} 
							</b></th>
							
							<tr align = "center">
								<td><b>${employeeDetailsList.email}</b></td>
								<td></td>
							</tr>
						</tr>
					
						
						<tr>
							<td>  </td>
							<td>  </td>
						</tr>
							<tr>
							<td>  </td>
							<td>  </td>
						</tr>
						<tr>
							<td>Employee ID: ${employeeDetailsList.employeeId} </td>
							<td>  </td>
						</tr>
						<tr>
							<td>Biometrics ID: ${employeeDetailsList.biometricId} </td>
						</tr>
						
						<tr>
							<td>  </td>
							<td>  </td>
						</tr>
						
						<tr>
							
							<td>Department: ${employeeDetailsList.department} </td>	
						</tr>
						
						<tr>
							<td>Position: ${employeeDetailsList.position} </td>
							<td>Level: ${employeeDetailsList.level} </td>
						</tr>
						
						<tr></tr>
						
						<tr>
							<td>Hire Date:  ${employeeDetailsList.hireDate} </td>
							<td>Regularization Date: ${employeeDetailsList.regularizationDate} </td>
						</tr>
							<tr>
									<td>Supervisor: ${supervisorDetails.firstname} ${supervisorDetails.lastname}</td>
									<td>Supervisor's Email: ${supervisorDetails.email}</td>
							</tr>
			
				</tbody>	
		
			</table>
		
	</div>


	<div id = "AccountSettings">
		<table>
				 	<form name="changePassword" method="POST" action="<c:url value='/user/changePassword'/>">
						
						
						<b>Change Password</b>
						
							<tr>
								<td><div style = "text-align: right">Current Password:</div> </td>
								<td><input type="password" name = "currentPassword" /></td>
							</tr>
							
							<tr>
								<td><div style = "text-align: right">New Password:</div></td>
								<td> <input type="password" name = "newPassword" /></td>
							</tr>
							
							<tr>
								<td><div style = "text-align: right">Re-enter New Password: </div></td>
								<td><input type="password" name = "repeatNewPassword" /></td>
							</tr>
							
							<tr>
								<td></td>
								<td><div style = "text-align: right"></div>
								<input type= "submit" value="Confirm"/></td>
							</tr>
						
					</form>
				</table>
		<div>${passwordMsg}</div>
	</div>
	
	<div id = "Administration">
	<div id="adminTab">
  			<ul>
			    <li><a href="../manager/viewSubordinates">Employee Manager</a></li>
			    <li><a href="#ShiftManager">Shift Manager</a></li>
			  
  			</ul>
	  		<div id="ShiftManager">
	  		<!-- Shift Manager for Supervisors -->
    			<h2>Shift Manager</h2>
    			
  			</div>
	</div>


</div>

</body>
</html>