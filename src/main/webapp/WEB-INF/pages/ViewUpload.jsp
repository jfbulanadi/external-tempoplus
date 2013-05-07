<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<title>BMNManager</title>
</head>
<body>
	<form:form method="post" action="uploadfile"
		modelAttribute="uploadForm" enctype="multipart/form-data">

		<p>Select files to upload. Press Add button to add more file
			inputs.</p>

		<input id="addFile" type="button" value="Add File" />
		<table id="fileTable">
		<tr>
			<td><select name = "category">
			<option value=1>Biometric</option>
			<option value=2>Mantis</option>
			<option value=3>Nt3</option>
			<option value=4>Employees</option>
			<option value=5>Consolidate Timesheet</option>
			</select></td>
		</tr>
			<tr>
				<td><input name="file" type="file" /></td>
			</tr>
		</table>
		<br />
		<input type="submit" value="Upload" />
	</form:form>
	
	<form:form method="POST" action="/consolidation/create">
		Timesheet Description<input type="text" name="name"/>
		Period Start(yyyy-mm-dd)<input type="text" name="periodStart"/>
		Period End<input type="text" name="periodEnd"/>
		<input type="submit" value="Create" />
	</form:form>
	

</body>
</html>
