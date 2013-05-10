<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sending Timesheet Logs</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>

<script>
$(document).ready(function() {  
$("#logs").change(function(){
	var selectedLogs = $("#logs option:selected").text();
	
	$("#tableLogs tbody").remove();
	
	var tblStr = "<tbody>";
	
	$.ajax({
		type : "GET",
		url : "/tempoplus/sendMail/getLogs",
		data : {
			selectedLogs : selectedLogs,
		},

	success : function(response) {

		$.each(response, function (keys, values){
		tblStr += "<tr><td>" + values.date + "</td><td>" + values.recipient +"</td><td>"+values.status+"</td></tr>"		
		});
		
		tblStr += "</tbody>"
		$("#tableLogs").append(tblStr);
	}
});
});
});
</script>
</head>
<body>


	<select id="logs">
	<option selected="selected">Select Date</option>
			<c:forEach items="${dateLogs}" var="content">
				<option>${content}</option>
		</c:forEach>
	</select>

<table id="tableLogs" border="1">

</table>


</body>
</html>