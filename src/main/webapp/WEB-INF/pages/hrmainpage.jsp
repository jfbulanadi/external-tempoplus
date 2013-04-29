<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css"
	href="../resources/account/css/jquery.tablesorter.pager.css"></link>
<link rel="stylesheet" type="text/css"
	href="../resources/account/css/theme.default.css"></link>
<link rel="stylesheet" type="text/css"
	href="../resources/account/css/viewhr.css"></link>	

<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<script src="../resources/account/js/jquery-1.9.1.js"></script>
<script src="../resources/account/js/jquery.tablesorter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.widgets.min.js"></script>
<script src="../resources/account/js/jquery-ui.js"></script>
<script
	src="../resources/account/js/jquery.tablesorter.widgets-filter-formatter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.pager.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mainpage</title>
<script>
  $(function() {
    $( "#tabouter" ).tabs({
      beforeLoad: function( event, ui ) {
        ui.jqXHR.error(function() {
          ui.panel.html(
            "Couldn't load this tab. We'll try to fix this as soon as possible. " +
            "If this wouldn't be a demo." );
        });
      }
    });
  });
</script>

<script>

$(function() {
	$( "#tabinner" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
	$( "#tabinner li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
    });
  </script>

</head>

<body>
 
<div id="tabouter">
  <ul>
    <li><a href="#tabs-1">Time Keeping</a></li>
    <li><a href="#tabs-1">Time Log</a></li>
    <li><a href="#tabs-2">Profile</a></li>
    <li><a href="#loadAdmin">Administration</a></li></li>
    
  </ul>
  <div id="tabs-1">
    <p>Proin elit arcu, rutrum commodo, </p>
  </div>
  
  <div id ="tabs-2">
	  
  </div>
  <!-- this div is for admin -->
  <div id="loadAdmin">
  		<div id="tabinner">
  			<ul>
			    <li><a href="../hr/employeemanager">Employee Manager</a></li>
			    <li><a href="#tabs-1">Shift Manager</a></li>
			    <li><a href="#tabs-1">BMN Manager</a></li>
			    <li><a href="#tabs-1">Download Timesheet</a></li>
			    <li><a href="#tabs-1">Email Manager</a></li>
			    <li><a href="#tabs-1">Holiday View</a></li>
  			</ul>
	  		<div id="tabs-1">
    			<h2>Content heading 1</h2>
    			<p>Proin elit arcu, rutrum commodo, </p>
  			</div>
  		</div>
  
</div>
 

</html>