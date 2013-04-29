<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Mail TimeSheet to Employees</title>

  <style type="text/css">
  #popupbox{
  margin: 0; 
  margin-left: 40%; 
  margin-right: 40%;
  margin-top: 50px; 
  padding-top: 10px; 
  width: 25%; 
  height: 150px; 
  position: absolute; 
  background: #FBFBF0; 
  border: solid #000000 2px; 
  z-index: 9; 
  font-family: arial; 
  visibility: hidden; 
  }
  </style>
  
<!--  RichText  -->
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>

<script src="../resources/bmn/ckeditor/ckeditor.js">
	
</script>
<link rel="stylesheet"
	href="../resources/bmn/ckeditor/samples/sample.css" />
<script type="text/javascript" src="../resources/bmn/ckeditor/config.js">
	
</script>
<link rel="stylesheet" type="text/css"
	href="../resources/bmn/ckeditor/skins/moono/editor_gecko.css" />
<script type="text/javascript"
	src="../resources/bmn/ckeditor/lang/en.js"></script>
<script type="text/javascript" src="../resources/bmn/ckeditor/styles.js"></script>
<!-- End of Rich Text -->

<script>
	function validateLogin() {
		var selectedDepartment = $("#DEPARTMENTS option:selected").text();
		var username = $("#username").text();
		var password = $("password").text();
		console.log("Logging in | username: " + username + " | password: " + password);
	}
</script>



<script>
	function sendMail() {
		var selectedDepartment = $("#DEPARTMENTS option:selected").text();
		console.log("callService: sending mail to: " + selectedDepartment
				+ " department");

		var msgBody = CKEDITOR.instances['editor1'].getData();
		console.log(msgBody);

		 $("#status").html("Sending");

		$.ajax({
			url : "/tempoplus/sendMail/send",
			data : {
				selectedDepartment : selectedDepartment,
				msgBody : msgBody
			},
			success : function(data) {
				console.log("data: " + data.status);
				$("#status").html(data.status);
			}
		}); 
	}
</script>

  <script language="JavaScript" type="text/javascript">
  function login(showhide){
    if(showhide == "show"){
        document.getElementById('popupbox').style.visibility="visible";
    }else if(showhide == "hide"){
        document.getElementById('popupbox').style.visibility="hidden"; 
    }
  }
  </script>
  
</head>
<body>


	<center>
		<h2>Send TimeSheet to Employees</h2>
	</center>
	
	<b> Select Department:</b>
	<select id="DEPARTMENTS">
		<option value="ALL">ALL</option>
		<option value="HR">HR</option>
		<option value="SEG">SEG</option>
	</select>


	<!-- RichText Editor Implementation -->

	<div>
		<textarea cols="80" id="editor1" name="editor1" rows="10">
			
<p>Hi</p>

<p>We are providing you with your attendance summary for the period of Month Day Year to Month Day Year.  The summary includes overtime and leaves availed for the same period.  The summary include requests made in Mantis and NT3 as of March 25.  Requests made and approved after March 25, 2013 will be made part of the next cut off.</p>

<p>This summary report will be routed to you semi-monthly.</p>

<p>What we request of you?  Please go over the summary:</p>

<p>1. If you spot missing overtime and/or rest day or holiday hours for payment, please do check the details of your ticket and check for accuracy of the same.  If there are errors on the ticket filed, please log a new request.  For proper administration, all tickets made part of this summary will be tagged as resolved.</p>

<p>2.  Look in columns Q, R and S as these are usually for deduction.  Immediately log appropriate tickets to ensure that hours for deduction is corrected.  Like filing for a regular request in Mantis and NT3, the requests should be assigned to your immediate supervisor.</p>

<p>If you are deployed to the Client's site, from Sales Team or a level 5 and up, please ensure that leaves are properly filed.  This is very useful for benefits administration that you may want to avail at a later time.</p>

<p>Should you have questions or clarifications, please feel free to reach out to hr@novare.com.hk.</p>

<p>Thank you</p>
<p>HR Team</p>

		</textarea>
		<script>
			// This call can be placed at any point after the
			// <textarea>, or inside a <head><script> in a
			// window.onload event handler.

			// Replace the <textarea id="editor"> with an CKEditor
			// instance, using default configurations.

			CKEDITOR.replace('editor1');
		</script>
	</div>
	<br>
	<button id="sendMail" onclick="sendMail()">Send TimeSheet</button>
	Status:
	<span id="status"> Hold </span>
	</br>
	
	
<div id="popupbox"> 
<form>
<center>Username:</center>
<center><input name="username" size="40" id="username"/></center>
<center>Password:</center>
<center><input name="password" type="password" size="40" id="password" /></center>
<center><input type="submit" onclick="validateLogin()" value="Log-In"/></center>
</form>
<br />
<center><a href="javascript:login('hide');">close</a></center> 
</div> 

</p>
<p><a href="javascript:login('show');">login</a></p>
	
</body>
</html>