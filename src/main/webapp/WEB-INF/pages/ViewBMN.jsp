<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>BMN Manager</title>

 
<link rel="stylesheet" type="text/css"
	href="../resources/bmn/css/jquery.tablesorter.pager.css"></link>
<link rel="stylesheet" type="text/css"
	href="../resources/bmn/css/theme.default.css"></link>
<link rel="stylesheet" type="text/css"
	href="../resources/account/css/jquery-ui.css"></link>
 
 
<script src="../resources/bmn/js/jquery-1.9.1.js"></script>
<script src="../resources/bmn/js/jquery-ui.js"></script>
<script src="../resources/bmn/js/jquery.tablesorter.min.js"></script>
<script src="../resources/bmn/js/jquery.tablesorter.widgets.min.js"></script>
<script
	src="../resources/bmn/js/jquery.tablesorter.widgets-filter-formatter.min.js"></script>
<script src="../resources/bmn/js/jquery.tablesorter.pager.min.js"></script>
<style>
div#dialog {
display: none;
font-size: 62.5%;   
</style>

<script>
$(document).ready(function() {
	
	
	var pagerOptions = {
		container: $(".pager"),
		output: '{startRow} - {endRow} / {filteredRows} ({totalRows})',
		fixedHeight: true,
		removeRows: false,
		cssGoto:   '.gotoPage',		
	};
	$("table").tablesorter({
		widgets: ['zebra', 'filter'],
		headers: { 0: '1px' }
	}).
	tablesorterPager(pagerOptions);	

	
	$("#btnUpdate").click(function() {
		var firstName = $("#txtfirstname").val();
		var employeeId = $("#txtemployeeid").val();
		var timeIn = $("#txttimein").val();
		var timeOut = $("#txttimeout").val();
		
		//Still on going
		//var date = $("#txtdate")
		console.log(timeIn + "<>" + timeOut);
		$.ajax({
			type: "POST",
			url: "/tempoplus/consolidation/update",
			data: {
				firstName: firstName,
				employeeId: employeeId,
				timeIn : timeIn,
				timeOut: timeOut
			},
			success: function() {
				console.log(timeIn);
				console.log(timeOut);
				console.log(employeeId);
				console.log("succeeded update");
				//window.location.replace("/tempoplus/consolidation/view");
			}
		});
	});
	
	
	$("button:contains(Destroy)").click(function(){
		var $t = $(this);
		 if (/Destroy/.test( $t.text() )){
		        $('table').trigger('destroy.pager');
		        $t.text('Restore Pager');
		      } else {
		        $('table').tablesorterPager(pagerOptions);
		        $t.text('Destroy Pager');
		      }
		
	});
	
});
</script>
<script>
function showToForm(myId) {
	$("#dialog").dialog({
		width:330,
		height:280
		
	});
	var trId = myId.id;
	var firstname = null;
	var middlename = null;
	var lastname = null;
	var timein = null;
	var timeout = null;
	console.log(trId);
	 
	  var delimited = trId.split(",");
	  trId = delimited[0];
	  firstname = delimited[1];
	  middlename = delimited[2];
	  lastname = delimited[3];
	  timein = delimited[4];
	  	console.log(timein);
	  	var delimitTimein = timein.split(" ");
	  timeout = delimited[5];
	  	var delimitTimeout = timeout.split(" ");
		
	//console.log(trId);
	//console.log(firstname);
	
	 $("#txtemployeeid").val(trId);
	 $("#txtfirstname").val(firstname + " " + middlename + " " + lastname);
	 $("#txttimein").val(delimitTimein[1]);
	 $("#txttimeout").val(delimitTimeout[1]);
}
</script>



</head>
<body>
	
	
	
	
	<!-- FORM IS HIDDEN BY DIALOG -->
	<div id="dialog" title="Update time">
		<hr/>
		<div>Employee ID</div>
		<div>
			<input type="text" id="txtemployeeid" size="8" disabled class="text ui-widget-content ui-corner-all" />
		</div>

		<div>Full name:</div>
		<div>
			<input type="text" id="txtfirstname" size="45" disabled class="text ui-widget-content ui-corner-all" />
		</div>

		<div>Time in</div>
		<div>
			<input type="text" id="txttimein" class="text ui-widget-content ui-corner-all" />
		</div>

		<div>Time out</div>
		<div>
			<input type="text" id="txttimeout" class="text ui-widget-content ui-corner-all" />
		</div>
		<hr/>
		<button id="btnUpdate">Update</button>
		<hr/>
	</div>
	<!-- FORM IS HIDDEN BY DIALOG -->
	<hr/>
	<div>Select consolidated sheet</div>
	
	Month
	<select>
		<option>Jan</option>
		<option>Feb</option>
		<option>Mar</option>
	</select>
	Period
	<select>
		<option>1</option>
		<option>2</option>
	</select>
	<button>Select</button>
	
	<hr />
	<div class="pager" align="right">
		<button>Destroy</button>	
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
	<hr />
	<table class="tablesorter">
		<thead>
			<tr>
				<th>Emp ID</th>
				<th>Bio ID</th>
				<th>First name</th>
				<th>Middle name</th>
				<th>Last name</th>
				<th>Email</th>
				<th>Date</th>
				<th>Time in</th>
				<th>Time out</th>
				<th>Duration</th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${content}" var="timesheet">	
		  	<tr id="${timesheet.employee.employeeId},${timesheet.employee.firstname},${timesheet.employee.middlename},${timesheet.employee.lastname},${timesheet.timelog.timeIn},${timesheet.timelog.timeOut}" onclick=showToForm(this)>
					<td>${timesheet.employee.employeeId}</td>
					<td>${timesheet.employee.biometricId}</td>
					<td>${timesheet.employee.firstname}</td>
					<td>${timesheet.employee.middlename}</td>
					<td>${timesheet.employee.lastname}</td>
					<td>${timesheet.employee.email}</td>
					<td>${timesheet.timelog.date}</td>
					<td>${timesheet.timelog.timeIn}</td>
					<td>${timesheet.timelog.timeOut}</td>
					<td>${timesheet.timelog.duration}</td>
				</tr>
			</c:forEach>
		

		</tbody>
	</table>
<hr/>	
	<div>
	<form:form method="post" action="uploadfile"
		modelAttribute="uploadForm" enctype="multipart/form-data">

		<p>Select File to Upload</p>

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
	</div>
	


</body>
</html>