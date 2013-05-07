<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang = "en">
<head>
<meta charset ="utf-8"/>
<!-- <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" /> -->
<link href="../resources/timelog/css/ViewTimeLogCSS.css" rel="stylesheet"/>
<link rel="stylesheet" href="../resources/timelog/css/style1.css" type="text/css" />	
<link rel="stylesheet" type="text/css" href="../resources/timelog/css/jquery.tablesorter.pager.css"></link>
<link rel="stylesheet" type="text/css" href="../resources/timelog/css/theme.default.css"></link>
<link rel="stylesheet" href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" />

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>
<script src="../resources/timelog/js/jquery.tablesorter.min.js"></script>
<script src="../resources/timelog/js/jquery.tablesorter.widgets.min.js"></script>

<script type="text/javascript">var idExternal =${userEmployeeId}</script>
<script type="text/javascript" src= "../resources/timelog/js/ViewTimelogJS.js"></script>

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
		<li><a href = "#ViewTimelog">View Time Log</a></li>
		<li><a href = "#MyProfile" >My Profile</a></li>
		<li><a href = "#AccountSettings">Account Settings</a></li>
		<li><a href = "#Administration">Administration</a></li>
	</ul>
	
	<div id = "ViewTimelog">
		<table id = "tblNav">
			<tr>
			<td>
			<a id ="mylog" href= javascript:mylog()>MyLog</a>
			<a id ="others" href= javascript:others()>MySubordinates</a>
			</td>
			</tr>
		</table>
		<br><br><br><br>
			<table id ="tblAll">
			<tr>
			<td>
			<div id="SearchSub">
			Subordinates: <select id ="sub"> </select>
			</div>
			<div id="SearchBox">
			Lastname/Firstname: <input type="text" id="empName" /> <button id="SearchButton">Search</button>
			</div>
			</td>
			</tr>
					<tr id = "SearchRow">
					<td  id ="tdTimeLogAll">
					<div id ="HrSearch" title="List of Employees">
						<center>
							<table id ="tblSearch" class="tablesorter" style="cursor:pointer">
							</table>
						</center>
					</div>
					</td>
					</tr>
					
					<tr>
					<td>
					<div id = "result">
					Employee Name: <input type = "text" id ="employee" readonly="readonly" class = "label"/>		
					</div>
					</td>
					</tr>
					
			<tr>
			<td>
			<!-- <div id="Date"> -->
				<table>
				<tr >
				<td>From: <input type="text" id="from" readonly="readonly"/> </td>
				<td>To: <input type="text" id="to"  readonly="readonly" /> </td>
				<td> <input type="button" id="SearchTimeLog" value="Search"> </td>
				</tr>
				</table>
			<!-- </div> -->	
			</td>	
			</tr>
			
			<tr>
			<td id ="tdTimeLogAll">
				<table  id="tblTimeLog" class="tablesorter">
				<thead>
				<th>Date</th>
				<th>Time IN</th>
				<th>Time OUT</th>
				<th>Total Hours</th>
				</thead>
				</table>
			</td>
			</tr>
			
			<tr>
			<td>
			
			<table id ="tblID">
			
				<tbody>
				<tr>
				<td>
				<center>
			<input type = "text" id ="data" value = "No Data" readonly="readonly" class = "label"/>
				</center>
				</td>
				</tr>
				</tbody>
				
			</table>
			
			</td>
			</tr>
			
			<tr>
			<td>
			<div id="pagers" class="pagers">
				<form>
					<img src="../resources/timelog/css/images/images/first.png" class="first"/>
					<img src="../resources/timelog/css/images/images/prev.png" class="prev"/>
					<input type="text" id="tbl" class="pagedisplay"/>
					<!-- <span class="pagedisplay"></span> -->
					<img src="../resources/timelog/css/images/images/next.png" class="next"/>
					<img src="../resources/timelog/css/images/images/last.png" class="last"/>
					<select class="pagesize" id="pagesize">
					<option value="10">10 per page</option>
					<option value="20">20 per page</option>	
					<option value="30">30 per page</option>
					</select>
				</form>
			</div>
			</td>
			</tr>
			
		</table>
	</div>
	
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
</div>

</body>
</html>