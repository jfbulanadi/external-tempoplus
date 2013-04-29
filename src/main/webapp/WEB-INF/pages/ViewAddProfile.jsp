<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Registration Page</title>
</head>

<body>

	<div id="personal">
		<h2>Personal Information</h2>
		<form:form method="POST" action="/addEmployee" modelAttribute="employeeData">
			<table>
				<tr>
					<td>First Name :</td>
					<td><form:input size="50" path="firstName" /></td>
				</tr>
				<tr>
					<td>Middle Name :</td>
					<td><form:input size="50" path="lastName" /></td>
				</tr>
				<tr>
					<td>Last Name :</td>
					<td><form:input size="50" path="middleName" /></td>
				</tr>
				<tr>
					<td>Department :</td>
					<td><form:input size="50" path="Department" /></td>
				</tr>
				<tr>
					<td>Biometrics :</td>
					<td><form:input size="10" path="biometrics" /></td>
				</tr>
				<tr>
					<td>Shift :</td>
					<td><form:input size="20" path="shift" /></td>
				</tr>
				<tr>
					<td>Employee ID :</td>
					<td><form:input size="10" path="employeeId" /></td>
				</tr>
				<tr>
					<td>Position :</td>
					<td><form:input size="50" path="position" /></td>
				</tr>
				<tr>
					<td>Level :</td>
					<td><form:input size="2" path="level" /></td>
				</tr>
				<tr>
					<td>Employee Email :</td>
					<td><form:input size="50" path="employeeEmail" /></td>
				</tr>
				<tr>
					<td>Hired Date :</td>
					<td><form:input size="50" path="hiredDate" /></td>
				</tr>
				<tr>
					<td>Regularization Date :</td>
					<td><form:input size="50" path="regularizationDate" /></td>
				</tr>
				<tr>
					<td>Resignation Date :</td>
					<td><form:input size="50" path="resignationDate" /></td>
				</tr>
				<tr>
					<td>Supervisor in NT3 :</td>
					<td><form:input size="50" path="supervisorName" /></td>
				</tr>
				<tr>
					<td>Supervisor email :</td>
					<td><form:input size="50" path="supervisorEmail" /></td>
				</tr>
				<tr>
					<td>Location Assignment :</td>
					<td><form:input size="50" path="locAssign" /></td>
				</tr>
				<tr>
					<td>Payroll Name :</td>
					<td><form:input size="50" path="payrollName" /></td>

				</tr>
				<tr>
					<td><input type="submit" value="Submit"/></td>
				</tr>
			</table>
		</form:form>
	</div>
	
</body>
</html>