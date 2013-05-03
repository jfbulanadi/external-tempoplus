<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="../resources/account/css/jquery.tablesorter.pager.css"></link>
<link rel="stylesheet" type="text/css"
	href="../resources/account/css/theme.default.css"></link>
<link rel="stylesheet" type="text/css"
	href="../resources/account/css/viewhr.css"></link>

<link rel="stylesheet"
	href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" />

<script src="../resources/account/js/jquery-1.9.1.js"></script>
<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>
<script src="../resources/account/js/jquery.tablesorter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.widgets.min.js"></script>
<script src="../resources/account/js/jquery-ui.js"></script>
<script
	src="../resources/account/js/jquery.tablesorter.widgets-filter-formatter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.pager.min.js"></script>
<script src="../resources/account/js/viewhr.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>HR Administration</title>

</head>
<body>

	<!-- Table viewer -->
	<br>
	<br>
	<div>
		<div>
			<h1>Employee List</h1>
		</div>
		<div align="right">
			<button id="create-user">Create new user</button>
		</div>
	</div>

	<table class="tablesorter">
		<thead>
			<tr>
				<th class="sorter-false">Biometrics Number</th>
				<th class="sorter-false">Employee ID</th>
				<th class="sorter-false">First Name</th>
				<th class="sorter-false">Middle Name</th>
				<th class="sorter-false">Last Name</th>
				<th class="sorter-false">Department</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${employeeList}" var="employee">
				<tr id="${employee.employeeId} " onClick=showToForm(this)>
					<td>${employee.biometrics }</td>
					<td>${employee.employeeId }</td>
					<td>${employee.firstName }</td>
					<td>${employee.middleName }</td>
					<td>${employee.lastName }</td>
					<td>${employee.department }</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pager" align="right">
	
		<img src="../resources/bmn/css/images/first.png" class="first" /> <img
			src="../resources/bmn/css/images/prev.png" class="prev" /> <span
			class="pagedisplay"></span>
		<!-- this can be any element, including an input -->
		<img src="../resources/bmn/css/images/next.png" class="next" /> <img
			src="../resources/bmn/css/images/last.png" class="last" /> <select
			class="pagesize">
			<option value="2">2</option>
			<option value="5">5</option>
			<option value="10" selected="selected">10</option>
		</select>
	</div>
	<br>
	<br>

	<div></div>
	<br>
	<br>

	<!-- this one uses Excel Controller -->
	<div id="accordion">
		<h2>Select files to upload.</h2>
		<div>
			<form:form method="post" action="uploadUserDB"
				modelAttribute="uploadForm" enctype="multipart/form-data">

				<input name="file" type="file" />
				<br />
				<input type="submit" value="Upload" id="uploadStatus"
					onclick="uploadStatus()" />

			</form:form>
		</div>
	</div>

	<!-- popup registration -->
	<div id="dialog-form" title="Add new Employee">
		<h2>Personal Information</h2>
		<form method="POST" action="/addEmployee">
			<table>
				<tr>
					<td>First Name :</td>
					<td><input size="50" id="firstName" /></td>
				</tr>
				<tr>
					<td>Middle Name :</td>
					<td><input size="50" id="middleName" /></td>
				</tr>
				<tr>
					<td>Last Name :</td>
					<td><input size="50" id="lastName" /></td>
				</tr>
				<tr>
					<td>Department :</td>
					<td><select class="department" id=selectDepartment>
						
					</select>
				</tr>
				<tr>
					<td>Biometrics :</td>
					<td><input size="10" id="biometrics" /></td>
				</tr>
				<tr>
					<td>Shift :</td>
					<td><input size="20" id="shift" /></td>
				</tr>
				<tr>
					<td>Employee ID :</td>
					<td><input size="10" id="employeeId" /></td>
				</tr>
				<tr>
					<td>Position :</td>
					<td><select id ="selectPosition">

					</select></td>
				</tr>
				<tr>
					<td>Level :</td>
					<td><input size="2" id="level" /></td>
				</tr>
				<tr>
					<td>Employee Email :</td>
					<td><input size="50" id="employeeEmail" /></td>
				</tr>
				<tr>
					<td>Hired Date :</td>
					<td><input class = "datepicker" size="50" id="hiredDate" /></td>
				</tr>
				<tr>
					<td>Supervisor Name :</td>
					<td><select id ="selectSupervisorName">

					</select></td>
				</tr>
				<tr>
					<td>Supervisor Email :</td>
					<td><input size="50" id="supervisorEmail" /></td>
				</tr>
				<tr>
					<td>Location Assignment :</td>
					<td><input size="50" id="locAssign" /></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- popup view -->
	<div id="dialog-formFullInformation" title="Employee Full Information"
		style="display: none">
		<table>
			<tr>
				<td>First Name :</td>
				<td><input size="50" id="editfirstName" name="firstname"
					disabled /></td>
			</tr>
			<tr>
				<td>Middle Name :</td>
				<td><input size="50" id="editMiddleName" disabled /></td>
			</tr>
			<tr>
				<td>Last Name :</td>
				<td><input size="50" id="editLastName" disabled /></td>
			</tr>
			<tr>
				<td>Department :</td>
				<td><input size="50" id="editDepartment" disabled /></td>
			</tr>
			<tr>
				<td>Biometrics :</td>
				<td><input size="10" id="editBiometrics" disabled /></td>
			</tr>
			<tr>
				<td>Shift :</td>
				<td><input size="20" id="editShift" disabled /></td>
			</tr>
			<tr>
				<td>Employee ID :</td>
				<td><input size="10" id="editEmployeeId" disabled /></td>
			</tr>
			<tr>
				<td>Position :</td>
				<td><input size="50" id="editPosition" disabled /></td>
			</tr>
			<tr>
				<td>Level :</td>
				<td><input size="2" id="editLevel" disabled /></td>
			</tr>
			<tr>
				<td>Employee Email :</td>
				<td><input size="50" id="editEmployeeEmail" disabled /></td>
			</tr>
			<tr>
				<td>Hired Date :</td>
				<td><input size="50" id="editHiredDate" disabled /></td>
			</tr>
			<tr>
				<td>Regularization Date :</td>
				<td><input size="50" id="editRegularizationDate" disabled /></td>
			</tr>
			<tr>
				<td>Resignation Date :</td>
				<td><input size="50" id="editResignationDate" disabled /></td>
			</tr>
			<tr>
				<td>Supervisor in NT3 :</td>
				<td><input size="50" id="editSupervisorName" disabled /></td>
			</tr>
			<tr>
				<td>Supervisor email :</td>
				<td><input size="50" id="editSupervisorEmail" disabled /></td>
			</tr>
			<tr>
				<td>Location Assignment :</td>
				<td><input size="50" id="editLocAssign" disabled /></td>
			</tr>
		</table>
		<div id="buttons">
				<div class="button_n">
					<input type="button" id="editBtn" value="Edit" />
				</div>
				<div class="button_n">
					<input type="button" id="exitBtn" value="Exit" />
				</div>
			</div>

	</div>

	<div id="dialog-confirm" title="Do you want to save the profile?" style="display: none">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> Are you sure?
		</p>
	</div>
	
	<div id="dialog-confirm-create" title="Do you want to create this profile?" style="display: none">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> Are you sure?
		</p>
	</div>
</body>
</html>