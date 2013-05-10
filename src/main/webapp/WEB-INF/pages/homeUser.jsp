<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
<head>
<meta charset ="utf-8"/>
<!--  <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />  -->

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script src="../resources/account/js/changePassword.js"></script>

<title>Tempoplus User</title>

<script>
$(function() {
$( "#employeeAccess" ).tabs();

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
	</ul>
		
	<div id = "MyProfile">
		<table>
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
	<br></br>
	<br></br>
		<center>
			<table>
				 		<b>Change Password</b>
				
							
					
							<tr>
								<td><div style = "text-align: right">Current Password:</div> </td>
								<td><input type="password" id = "currentPassword" /></td>
							</tr>
							
							<tr>
								<td><div style = "text-align: right">New Password:</div></td>
								<td> <input type="password" id = "newPassword" /></td>
							</tr>
							
							<tr>
								<td><div style = "text-align: right">Re-enter New Password: </div></td>
								<td><input type="password" id = "repeatNewPassword" /></td>
							</tr>
							
							<tr>
								<td><div style = "text-align: right">
								<span style = "color:blue">*minimum of 6 characters</span>
								</div></td>
							
								<td><div style = "text-align: right"></div>
								<button id = "changePasswordBtn">Confirm </button>
							</tr>
				
				</table>
			</center>
		</div>
	

</div>

</body>
</html>