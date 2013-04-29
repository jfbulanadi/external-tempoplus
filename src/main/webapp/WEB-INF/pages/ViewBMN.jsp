<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>BMN Manager</title>
<link rel="stylesheet" type="text/css"
	href="../resources/bmn/css/jquery.tablesorter.pager.css"></link>
<link rel="stylesheet" type="text/css"
	href="../resources/bmn/css/theme.default.css"></link>
 
<script src="../resources/bmn/js/jquery.1.4.1-min.js"></script>
<script src="../resources/bmn/js/jquery.tablesorter.min.js"></script>
<script src="../resources/bmn/js/jquery.tablesorter.widgets.min.js"></script>
<script
	src="../resources/bmn/js/jquery.tablesorter.widgets-filter-formatter.min.js"></script>
<script src="../resources/bmn/js/jquery.tablesorter.pager.min.js"></script>

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
		widgets: ['zebra', 'filter']
	}).
	tablesorterPager(pagerOptions);	
	
	
	$("#btnUpdate").click(function() {
		var firstname = $("#txtfirstname").val();
		var employeeid = $("#txtemployeeid").val();
		$.ajax({
			type: "POST",
			url: "/tempoplus/consolidation/update",
			data: {
				firstname: firstname,
				employeeid: employeeid
			},
			success: function() {
				window.location.replace("/tempoplus/consolidation/view");
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
	var trId = myId.id;
	var firstname = null;
	var timein = null;
	var timeout = null;
	console.log(trId);
	 
	  var delimited = trId.split(",");
	  trId = delimited[0];
	  firstname = delimited[1];
	  timein = delimited[2];
	  timeout = delimited[3];
	  
		
	console.log(trId);
	console.log(firstname);
	
	 $("#txtemployeeid").val(trId);
	 $("#txtfirstname").val(firstname);
	 $("#txttimein").val(timein);
	 $("#txttimeout").val(timeout);
}
</script>




</head>
<body>
	<div>

		<div>Employee ID</div>
		<div>
			<input type="text" id="txtemployeeid" />
		</div>

		<div>First name:</div>
		<div>
			<input type="text" id="txtfirstname" />
		</div>

		<div>Time in</div>
		<div>
			<input type="text" id="txttimein" />
		</div>

		<div>Time out</div>
		<div>
			<input type="text" id="txttimeout" />
		</div>

		<button id="btnUpdate">Update</button>
	</div>

	<hr />
	<button>Destroy</button>
	<div class="pager" align="right">
	
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
				<th>Employee ID</th>
				<th>Biometric ID</th>
				<th>First name</th>
				<th>Middle name</th>
				<th>Last name</th>
				<th>Level</th>
				<th>Position</th>
				<th>Department</th>
				<th>Email</th>
				<th>Time in</th>
				<th>Time out</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${content}" var="tableConsolidated_contents">
				<tr
					id="${tableConsolidated_contents.employeeId},${tableConsolidated_contents.firstname},7:00,10:00"
					onClick=showToForm(this)>
					<td>${tableConsolidated_contents.employeeId}</td>
					<td>100</td>
					<td>${tableConsolidated_contents.firstname}</td>
					<td>${tableConsolidated_contents.middlename}</td>
					<td>${tableConsolidated_contents.lastname}</td>
					<td>1</td>
					<td>SE1</td>
					<td>SEG</td>
					<td>${tableConsolidated_contents.firstname}@novare.com.hk</td>
					<td>7:00</td>
					<td>10:00</td>
				</tr>
			</c:forEach>


		</tbody>
	</table>

	


</body>
</html>