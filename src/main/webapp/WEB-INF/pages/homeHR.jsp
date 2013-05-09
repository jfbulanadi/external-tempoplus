<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
<head>
<meta charset ="utf-8"/>

<link rel="stylesheet" type="text/css" href="../resources/account/css/theme.default.css"></link>
<link rel="stylesheet" type="text/css" href="../resources/account/css/viewhr.css"></link>
<link rel="stylesheet" href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" />
<link rel="stylesheet" type="text/css" href="../resources/account/css/jquery.tablesorter.pager.css"></link>

<script src="../resources/account/js/changePassword.js"></script>
 
<script src="../resources/account/js/jquery-1.9.1.js"></script>
<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>
<script src="../resources/account/js/jquery-ui.js"></script>

<script src="../resources/account/js/jquery.tablesorter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.widgets.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.widgets-filter-formatter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.pager.min.js"></script>
<title>Tempoplus HR</title>

<script>
$(function() {
$( "#employeeAccess" ).tabs();
$( "#adminTab" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
$( "#adminTab li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
});
</script> 
<style>
div#dialog {
display: none;
font-size: 68.5%;   
font-family : Arial, Helvetica, sans-serif;
}
</style>
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
		<li><a href = <c:url value='../timelog/userLog'/>/>View Time Log</a></li>
		<li><a href = "#MyProfile" >My Profile</a></li>
		<li><a href = "#AccountSettings">Account Settings</a></li>
		<li><a href = "#Administration">Administration</a></li>
		
	</ul>
	<div id = "MyProfile">
	<br></br>
	<br></br>
	<center>
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
		</center>
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
	

	
	<div id = "Administration">
		<div id="adminTab">
	  			<ul>
				    <li><a href="../hr/employeemanager">Employee Manager</a></li>
				    <li><a href="#ShiftManager">Shift Manager</a></li>
				  	<li><a href="../consolidation/view">BMN Manager</a></li>
				  	<li><a href="#DownloadTimesheet">Download Timesheet</a></li>
				  	<li><a href="../sendMail/mail">Email Manager</a></li> 
				  	<li><a href="#HolidayView">Holiday View</a></li> 	
	  			</ul>
		  		<div id="ShiftManager">
		  			<!-- Shift Manager for HR -->
	    			<h2>Shift Manager</h2>
	    		</div>
	  			
	  			<div id="BMNManager">
	    			<h2>BMN Manager</h2>
	    			
	  			</div>
	  			
	  			<div id="DownloadTimesheet">
	    			<h2>Download Timesheet</h2>
	    			
	  			</div>
	  			
	  			<div id="EmailManager">
	    			<h2>Shift Manager</h2>
	    			
	  			</div>
	  			
	  			<div id="HolidayView">
	    			<h2>Shift Manager</h2>
	    			
	  			</div>
	  			
		</div>
	</div>

</div>

</body>
</html>