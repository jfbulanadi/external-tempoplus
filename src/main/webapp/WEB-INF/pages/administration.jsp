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
	href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" />
<script src="../resources/account/js/jquery-1.9.1.js"></script>
<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>
<script src="../resources/account/js/jquery.tablesorter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.widgets.min.js"></script>
<script src="../resources/account/js/jquery-ui.js"></script>
<script
	src="../resources/account/js/jquery.tablesorter.widgets-filter-formatter.min.js"></script>
<script src="../resources/account/js/jquery.tablesorter.pager.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="../resources/account/css/administration.css"></link>
<script src="../resources/account/js/viewhr.js"></script>
<title>Insert title here</title>

<script>

$(function() {
	$( "#tabs" ).tabs().addClass( "ui-tabs-vertical ui-helper-clearfix" );
	$( "#tabs li" ).removeClass( "ui-corner-top" ).addClass( "ui-corner-left" );
    $( "#tabs" ).tabs({
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
</head>
<body>
<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Nunc tincidunt</a></li>
    <li><a href="../hr/employeemanager">Employee Manager</a></li>
  </ul>
  <div id="tabs-1">
    <h2>Content heading 1</h2>
    <p>Proin elit arcu, rutrum commodo, </p>
  </div>
  
</div>
</body>
</html>