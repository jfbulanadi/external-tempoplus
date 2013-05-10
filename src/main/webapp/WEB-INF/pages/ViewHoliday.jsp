<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<title>Holidays</title>

</head>
<body>
<center>
	<table border="1">
		<c:forEach items="${holidayPojo}" var="content">
			<tr>
				<td>${content.date}</td>
				<td>${content.day}</td>
				<td>${content.name}</td>
				<td>${content.code}</td>
				<td>${content.procNo}</td>
			</tr>
		</c:forEach>
	</table>
</center>
</body>
</html>