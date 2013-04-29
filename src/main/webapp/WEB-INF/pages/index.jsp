<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script> 
 <script type="text/javascript">
	/* function validate(){
	if ($('#ifValid') == true)
		window.location = "UserProfile.jsp";	
	}  */
	</script>         
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Profile</title>
</head>
<body>

<table>

 <form name="login" method="POST" action="<c:url value='/user/login'/>">
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
</form>
</table>
${outputMsg} 
	
</body>
</html>