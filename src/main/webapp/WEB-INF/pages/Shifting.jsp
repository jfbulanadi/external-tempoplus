<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Shifting</title>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="../css/style.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
<script>
	function doAjaxPost() {

		var shiftname = $('#shiftname').val();
		var timein = $('#timein').val();
		var timeout = $('#timeout').val();
     if(($('#shiftname').val())==''){
    	 $('#addwarning').text("shifting name shoud not be empty");
    	 $('#addwarning').css('color', 'red');
    	 $('#shiftname').css('background-color', '#ff0000');
    	 
     }
     else{
    	 
     
		$.ajax({
			type : "POST",
			url : "<c:url value= '/shift/addshift' />",
			data : {
				shiftname : shiftname,
				timein : timein,
				timeout : timeout
			},
			success : function(response) {
			 
				$('#shiftname').val("");
				$('#addshift').dialog("close");
				location.reload(true);
			 
			},
			error : function(e) {
				alert("Error: " + e);
				$('#addshift').dialog("close");
			}
		});
     }
	}
	$(document).ready(function() {
		var dialogOpts = {
			bgiframe : true,
			autoOpen : false,
			modal : true,
			width : "260px"
		};
		$('#addshift').dialog(dialogOpts);
		$('#AddShifting').click(function() {
			$('#addshift').dialog('open');
			return false;
		});
		
		var dialogEdit = {
				bgiframe : true,
				autoOpen : false,
				modal : true,
				width : "260px"
			};
			$('#edit').dialog(dialogEdit);
			$('#shifts tbody tr').on('click', function() {
				 bid = (this.id) ; // button ID 
		         trid = $(this).closest('tr').attr('id'); 
				 var shiftid = trid;
				 
				 $.ajax({
					 type : "POST",
					  url : "<c:url value= '/shift/editshift' />",
					  data : {
							shiftid : shiftid,
						},
						success : function(response) {
							$.each(response, function(index, item) {
								$('#shiftid').val(shiftid);
								$('#editshiftname').val(item.shiftname);
								$('#edittimein').val(item.timein);
								$('#edittimeout').val(item.timeout);
							});
							
							$("#editshiftname").attr('disabled', true);
							$("#edittimein").attr('disabled', true);
							$("#edittimeout").attr('disabled', true);
							
						},	 
				 });
				$('#edit').dialog('open');
				return false;
			});
			
		$("#editbutton").click(function(){
			 
		   if(($('#editbutton').val()=="Save")){
			   var newshiftname = $('#editshiftname').val();
			   var newtimein = $('#edittimein').val();
			   var newtimeout = $('#edittimeout').val();
			   var shiftid = $('#shiftid').val();
			 
			   if (($('#editshiftname').val())=='') {
				   $('#warning').text("shifting name shoud not be empty.");
				  $('#warning').css('color', 'red');
				  $('#editshiftname').css('background-color', '#ff0000');
				  }
			   else {
				$.ajax({
					type : "POST",
					url : "<c:url value= '/shift/editshifting' />",
					data : {
						newshiftname : newshiftname,
						newtimein : newtimein,
						newtimeout : newtimeout,
						shiftid : shiftid
					},
					success : function(response) {
					 
						$('#edit').dialog("close");
						location.reload(true);		 
					},
					error : function(e) {
						alert("Error: " + e);
						location.reload(true);
						$('#edit').dialog("close");
					}
				});			     
			   }
		   
		   }
		   
		   else{
			   $("#editshiftname").removeAttr('disabled');
				$("#edittimein").removeAttr('disabled');
				$("#edittimeout").removeAttr('disabled');
			   $("#editbutton").attr('value', 'Save');
		   }
				
			});
		

	});
	
</script>
</head>
<body>
	<div id="addshift" title="Add new shifting">
		<p id="addwarning">Fill out field</p>
		<label>Shifting Name</label><input type="text" id="shiftname" value="" />
		<label>&nbsp; Time In: </label><select id="timein"><option>08:00:00</option>
			<option>09:00:00</option>
			<option>10:00:00</option>
			<option>11:00:00</option>
			<option>13:00:00</option>
			<option>14:00:00</option>
			<option>15:00:00</option>
			<option>16:00:00</option>
			<option>17:00:00</option>
			<option>18:00:00</option>
			<option>19:00:00</option>
			<option>20:00:00</option>
			<option>21:00:00</option>
			<option>22:00:00</option>
			<option>23:00:00</option>
			<option>24:00:00</option>
			<option>01:00:00</option>
			<option>02:00:00</option>
			<option>03:00:00</option>
			<option>04:00:00</option>
			<option>05:00:00</option></select> <label>Time Out:</label>&nbsp;<select
			id="timeout"><option>08:00:00</option>
			<option>09:00:00</option>
			<option>10:00:00</option>
			<option>11:00:00</option>
			<option>13:00:00</option>
			<option>14:00:00</option>
			<option>15:00:00</option>
			<option>16:00:00</option>
			<option>17:00:00</option>
			<option>18:00:00</option>
			<option>19:00:00</option>
			<option>20:00:00</option>
			<option>21:00:00</option>
			<option>22:00:00</option>
			<option>23:00:00</option>
			<option>24:00:00</option>
			<option>01:00:00</option>
			<option>02:00:00</option>
			<option>03:00:00</option>
			<option>04:00:00</option>
			<option>05:00:00</option></select> <input type="button" onClick=doAjaxPost()
			value="Add Shift">
	</div>

	<div id="edit" title="Edit Shifting">
		<p id="warning">Fill out field</p>
		<input type="hidden" id="shiftid" /> <label>Shifting Name</label> <input
			type="text" id="editshiftname" disabled /> <label>&nbsp;
			Time In: </label><select id="edittimein" disabled><option>08:00:00</option>
			<option>09:00:00</option>
			<option>10:00:00</option>
			<option>11:00:00</option>
			<option>13:00:00</option>
			<option>14:00:00</option>
			<option>15:00:00</option>
			<option>16:00:00</option>
			<option>17:00:00</option>
			<option>18:00:00</option>
			<option>19:00:00</option>
			<option>20:00:00</option>
			<option>21:00:00</option>
			<option>22:00:00</option>
			<option>23:00:00</option>
			<option>24:00:00</option>
			<option>01:00:00</option>
			<option>02:00:00</option>
			<option>03:00:00</option>
			<option>04:00:00</option>
			<option>05:00:00</option></select> <label>Time Out:</label>&nbsp;<select
			id="edittimeout" disabled><option>08:00:00</option>
			<option>09:00:00</option>
			<option>10:00:00</option>
			<option>11:00:00</option>
			<option>13:00:00</option>
			<option>14:00:00</option>
			<option>15:00:00</option>
			<option>16:00:00</option>
			<option>17:00:00</option>
			<option>18:00:00</option>
			<option>19:00:00</option>
			<option>20:00:00</option>
			<option>21:00:00</option>
			<option>22:00:00</option>
			<option>23:00:00</option>
			<option>24:00:00</option>
			<option>01:00:00</option>
			<option>02:00:00</option>
			<option>03:00:00</option>
			<option>04:00:00</option>
			<option>05:00:00</option></select> <input type="button" id="editbutton"
			value="Edit Shift" />
	</div>

	<table id="shifts" border="1" class="tablesorter">
		<thead>
			<tr>
				<th>Shift Description</th>
				<th>Start Time</th>
				<th>End Time</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${shiftlist}" var="shiftlist">
				<tr id="${shiftlist.shiftid}">
					<td>${shiftlist.shiftname}</td>
					<td>${shiftlist.timein}</td>
					<td>${shiftlist.timeout}</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>
	<input type="button" id="AddShifting" value="add shift" />
</body>
</html>