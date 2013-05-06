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
<script src="../resources/account/js/addManagerSubordinate.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subordinate's List</title>


</head>
<body>
	<!-- Table viewer -->
	<br>
	<div>
		<div>
			<h1>Subordinate's List</h1>
		</div>
		<div align="right">
			<button id="addSubordinates">Add Subordinates</button>
		</div>
	</div>

	<table class="tablesorter">
		<thead>
			<tr>
		
				<th class="sorter-false">Employee ID</th>
				<th class="sorter-false">First Name</th>
				<th class="sorter-false">Middle Name</th>
				<th class="sorter-false">Last Name</th>
				<th class="sorter-false">Department</th>
				<th class="sorter-false">Position</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${subordinatesList}" var="subordinate">
				<tr <%-- id="${subordinate.employeeId} " onClick=showToForm(this) --%>>
					<td>${subordinate.employeeId }</td>
					<td>${subordinate.firstName }</td>
					<td>${subordinate.middleName }</td>
					<td>${subordinate.lastName }</td>
					<td>${subordinate.department }</td>
					<td>${subordinate.position }</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div class="pager" align="right">
	
		<img src="../resources/bmn/css/images/first.png" class="first" /> 
		<img src="../resources/bmn/css/images/prev.png" class="prev" /> 
		<span class="pagedisplay"></span>
		<!-- this can be any element, including an input -->
		<img src="../resources/bmn/css/images/next.png" class="next" /> 
		<img src="../resources/bmn/css/images/last.png" class="last" /> 
		<select	class="pagesize">
			<option value="2">2</option>
			<option value="5">5</option>
			<option value="10" selected="selected">10</option>
		</select>
	</div>
	
	<div id="AddSubordinateForm" title="Add Subordinate" style = "display:none">
		
			<table>
				<tr>
					<td>Search Employee</td>
					<td><input size="12" id="employeeName" /></td>
					<td><button id = "searchEmployee" >Search Employee Name</button></td>
				</tr>
				
			</table>
				<div id = "foundEmployeeDiv">
					<table id ="foundEmployeeTable" class = "tablesorter" align = "center">
										
					</table>

					<button id = "addNewSubordinates">Add Subordinate/s</button>

				</div>
				
			
			
	</div>
</body>
</html>