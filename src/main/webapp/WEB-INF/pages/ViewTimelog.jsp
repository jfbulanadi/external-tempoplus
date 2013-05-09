<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Time Log</title>

<!-- <link href="../resources/timelog/css/ViewTimeLogCSS.css" rel="stylesheet">
<link rel="stylesheet" href="../resources/timelog/css/style1.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="../resources/timelog/css/jquery.tablesorter.pager.css"></link>
<link rel="stylesheet" type="text/css" href="../resources/timelog/css/theme.default.css"></link>
<link rel="stylesheet" href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" />

<script src="../resources/timelog/js/jquery-1.9.1.js"></script>
<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>
<script type="text/javascript">var idExternal =${id}</script>
<script type="text/javascript" src= "../resources/timelog/js/ViewTimelogJS.js"></script>

<script src="../resources/timelog/js/jquery.tablesorter.min.js"></script>
<script src="../resources/timelog/js/jquery.tablesorter.widgets.min.js"></script>
<script src="../resources/timelog/js/jquery-ui.js"></script>
<script src="../resources/timelog/js/jquery.tablesorter.widgets-filter-formatter.min.js"></script>
<script src="../resources/timelog/js/jquery.tablesorter.pager.min.js"></script> -->

<link href="../resources/timelog/css/ViewTimeLogCSS.css" rel="stylesheet">
<link rel="stylesheet" href="../resources/timelog/css/style1.css" type="text/css" />
<script type="text/javascript">var idExternal =${id}</script>
<script type="text/javascript" src= "../resources/timelog/js/ViewTimelogJS.js"></script>

</head> 
<body id = "bodyTimelog">

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
		Employee Name: <input type = "text" id ="employee" readonly="readonly" class= "label"/>		
		</div>
		</td>
		</tr>
		
<tr>
<td>
<div id="Date">
	<table>
	<tr >
	<td>From: <input type="text" id="from" readonly="readonly"/> </td>
	<td>To: <input type="text" id="to"  readonly="readonly" /> </td>
	<td> <input type="button" id="SearchTimeLog" value="Search"> </td>
	</tr>
	</table>
</div>	
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
<input type = "text" id ="data" value = "No Data" readonly="readonly" class= "label"/>
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
</body>
</html>