<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <script src="../resources/account/css/jquery-fileupload/jquery.fileupload-ui.css"></script>
<script src="../resources/account/css/jquery-fileupload/style.css"></script>
<script src="../resources/account/css/jquery-fileupload/jquery.fileupload-ui-noscript.css"></script>
 -->

<script src="../resources/account/js/jquery-1.9.1.js"></script>

<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>
<script
	src="../resources/account/js/jquery-fileupload/vendor/jquery.ui.widget.js"></script>
<script
	src="../resources/account/js/jquery-fileupload/jquery.fileupload.js"></script>
<script
	src="../resources/account/js/jquery-fileupload/jquery.iframe-transport.js"></script>
<!-- <script src="../resources/account/js/upload.js"></script> -->
<script src="../resources/account/js/fileupload.js"></script>
<!-- -
<script src="../resources/account/js/jquery-fileupload/jquery.fileupload-ui.js"></script>

<!-- <script src="../resources/account/js/jquery-fileupload/app.js"></script> -->
<!-- <script src="../resources/account/js/jquery-fileupload/jquery.fileupload-angular.js"></script> 
<script src="../resources/account/js/jquery-fileupload/jquery.fileupload-process.js"></script>
<script src="../resources/account/js/jquery-fileupload/jquery.fileupload-resize.js"></script>
<script src="../resources/account/js/jquery-fileupload/jquery.fileupload-validate.js"></script>

 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.bar {
	height: 18px;
	
	background: green;
}
</style>


</head>
<body>

	<!-- <div id="uploadfile">
		<form id='uploadForm'>
			<fieldset>
				<legend>Files</legend>

				<span id='filename'></span><br /> <input name="file" type="file"
					id="control" /> <br /> <input type='button' value='Reset'
					id="reset" /> <input type='button' value='Upload' id='submit' />
			</fieldset>
		</form>
		<div id="progress">
				<div class="bar" style="width: 0%;"></div>
			</div>

	</div> -->

	<div id="uploadfile">
		<form id='uploadForm'>



			<span id='filename'></span><br />
			 <input name="file" type="file"	id="control" /> <br />
			 <input type='button' value='Reset' id="reset" /> 
			 <input type='button' value='Upload' id='submit' />

		</form>
		<div id="progress">
			<div class="bar" style="width: 0%;"></div>
		</div>

	</div>

	<p/>

</body>
</html>