<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Time Log</title>

	
	




	

<title>TempoPlus Login</title>
</head>
<body>

<div align ="center">

	 <form name="login" method="POST" action="<c:url value='/user/login'/>">
		 <table>
				<tr>
					<td>Log In</td>
				</tr>
				<tr>
					<td>Email Address:</td>
					<td><input type = "text" name ="userName" /></td> 
				</tr>
				
				<tr>
					<td>Password:</td> 
					<td><input type="password" name = "password" /></td>
				</tr>
				
				<tr>
					<td><input type="submit" value ="Log in" /></td>
				
				</tr>
		</table>
	</form>

${outputMsg} 
	</div>
</body>
</html>