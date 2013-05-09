$(function() {
	$('#uploadForm').fileupload(
			{
				basic: true,
				maxNumberOfFiles: 1,
				dataType : 'json',
				progressall: function (e, data) {
			        var progress = parseInt(data.loaded / data.total * 100, 10);
			        $('#progress .bar').css(
			            'width',
			            progress + '%'
			        );
			    },
			    done: function() {
			    	alert("done uploading");
			    }
			})
});

$(function() {
	var control = $("#control");
    $("#reset").click(function() {
        control.replaceWith( control = control.clone( true ) );
    });  
});
