
$(function() {
	$('#uploadForm').fileupload({
		start : function(e) {
			alert('upload started');
		},
		progressall : function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			$('#progress .bar').css('width', progress + '%');
		},
		done : function(response) {
			alert("done upload");
		}

	});
}).success(function() {
	alert(1);
});
