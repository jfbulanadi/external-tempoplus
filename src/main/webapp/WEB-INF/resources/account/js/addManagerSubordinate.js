$(function(){
	$("#editSubordinates").button().click(function(){
		 $( "#AddSubordinateForm" ).dialog( "open" );
	});
});

$('#AddSubordinateForm').dialog({
	maxWidth : 300,
	maxHeight : 300,
	width : 300,
	height : 300
	modal: true
});
