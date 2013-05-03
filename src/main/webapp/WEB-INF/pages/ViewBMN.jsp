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
	$(document)
			.ready(
					function() {

						var htmlstr = null;
						var pagerOptions = {
							container : $(".pager"),
							output : '{startRow} - {endRow} / {filteredRows} ({totalRows})',
							fixedHeight : true,
							removeRows : false,
							cssGoto : '.gotoPage',
						};

						$("#tablesorter").tablesorter({
							widgets : [ 'zebra', 'filter' ]
						}).tablesorterPager(pagerOptions);

						$
								.ajax(
										{
											type : "GET",
											url : "<c:url value="/consolidation/ajaxFetchEmployees"/>",
											success : function(data) {
											
												$.each(data, function(keys,
														values) {
													htmlstr += "<tr id='"+ values.employeeId + "," + values.firstname + "," + values.middlename + "," + values.lastname + "," + values.timeIn + "," + values.timeOut + "' onclick=showToForm(this)>";
													htmlstr += "<td>" + values.employeeId + "</td>";
													htmlstr += "<td>" + values.biometricId +"</td>";
													htmlstr += "<td>" + values.firstname + "</td>";
													htmlstr += "<td>" + values.middlename + "</td>";
													htmlstr += "<td>" + values.lastname + "</td>";
													htmlstr += "<td>" + values.email + "</td>";
													htmlstr += "<td>" + values.position + "</td>";
													htmlstr += "<td>" + values.timeIn + "</td>";
													htmlstr += "<td>" + values.timeOut + "</td>";
													htmlstr += "</tr>";
														
												});

											}

										}).done(function() {

									$("#tablesorter").append(htmlstr);
									var resort = true;
									$("#tablesorter").trigger("update", [ resort ]);
									var sorting = [ [ 0, 0 ], [ 0, 0 ] ];
									$("table").trigger("sorton", [ sorting ]);

								});

						$("button:contains(Destroy)").click(function() {
							var $t = $(this);
							if (/Destroy/.test($t.text())) {
								$('table').trigger('destroy.pager');
								$t.text('Restore Pager');
							} else {
								$("#tablesorter").tablesorterPager(pagerOptions);
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
	  timeout = delimited[5];
	  	
	//console.log(trId);
	//console.log(firstname);
	
	 $("#txtemployeeid").val(trId);
	 $("#txtfirstname").val(firstname + " " + middlename + " " + lastname);
	 $("#txttimein").val(timein);
	 $("#txttimeout").val(timeout);
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
	<table id="tablesorter" class="tablesorter">
		<thead>
			<th>Employee ID</th>
			<th>Biometric ID</th>
			<th>First name</th>
			<th>Middle name</th>
			<th>Last name</th>
			<th>Email</th>
			<th>Position</th>
			<th>Time in</th>
			<th>TIme out</th>
			
			
		</thead>
		<tbody>
		</tbody>
	</table>
	<div>
	<button>Destroy</button>
	<div class="pager">
		<img src="../resources/bmn/css/images/first.png" class="first" /> <img
			src="../resources/bmn/css/images/prev.png" class="prev" /> <span
			class="pagedisplay"></span>
		<!-- this can be any element, including an input -->
		<img src="../resources/bmn/css/images/next.png" class="next" /> <img
			src="../resources/bmn/css/images/last.png" class="last" /> <select
			class="pagesize">
			<option value="10" selected="selected">10</option>
			<option value="30">30</option>
			<option value="50">50</option>
		</select>
	</div>
	</div>
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