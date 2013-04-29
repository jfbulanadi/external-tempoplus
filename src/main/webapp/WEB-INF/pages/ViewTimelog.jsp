<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Time Log</title>
<link href="../resources/timelog/css/jPages.css" rel="stylesheet">
	<link href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" rel="stylesheet">
	<link href="../resources/timelog/css/ViewTimeLogCSS.css" rel="stylesheet">
	<link rel="stylesheet" href="../resources/timelog/css/style1.css" type="text/css" />
	<script src="../resources/timelog/js/jquery-1.9.1.js"></script>
	
	<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 
	<script>var my_jQuery = jQuery.noConflict();</script>
	<script src="../resources/timelog/js/jPages.js"></script>
	
	<script type="text/javascript" src="../resources/timelog/js/jquery-1.3.1.min.js"></script>
	<script>var my_jQuery3 = jQuery.noConflict();</script>
	
	<script type="text/javascript" src="../resources/timelog/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="../resources/timelog/js/jquery.tablesorter.pager.js"></script>
	<script type="text/javascript" src= "../resources/timelog/js/ViewTimelogJS.js"></script>
</head>
<body onload = "mylog()">

<table id = "tblNav">
<tr>
<td>
<a href= javascript:mylog()>MyLog</a>
<a id ="others" href= javascript:others()>MySubordinates</a>
</td>
</tr>
</table>
<input type ="text" value = "${id}" id = "empId"/>

<br><br><br><br>
<table id ="tblAll">
<tr>
<td>
<div id="SearchSub">
Subordinates: <select id ="sub"> </select>
</div>
<div id="SearchBox">
Employee Lastname/Firstname: <input type="text" id="empName" /> <input type="button" id="SearchName" value="Search">
</div>
</td>
</tr>


		<tr>
		<td>
		<div id ="HrSearch">
			<table id ="tblSearch">
			</table>
		</div>
		</td>
		</tr>
		<tr>
		<td>
		<div id = "result">
		Employee Name:<input type = "text" id ="employee" readonly="readonly"/>		
		</div>
		</td>
		</tr>
		

<tr>
<td>
<div id="Date">
	<table>
	<tr >
	<td>From: <input type="text" id="from" /> </td>
	<td>To: <input type="text" id="to" /> </td>
	<td> <input type="button" id="SearchTimeLog" value="Search"> </td>
	</tr>
	</table>
</div>	
</td>	
</tr>

<tr>
<td id ="tdTimeLogAll">
	<table id="tblTimeLog"  class="tablesorter">
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
<div id="pager" class="pager">
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
</body>
</html>