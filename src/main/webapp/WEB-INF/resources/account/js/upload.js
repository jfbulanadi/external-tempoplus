<<<<<<< HEAD
/*$(function() {
=======
$(function() {
>>>>>>> ff706300618d3d6cc545d8849418b7b5e5a4c726
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
<<<<<<< HEAD
*/

/*$(function() {
	$('#uploadForm').fileupload({
		start : function(e) {
			alert('upload started');
		},
		progressall : function(e, data) {
			 var progress = parseInt(data.loaded / data.total * 100, 10);
		        $('#progress .bar').css(
		            'width',
		            progress + '%'
		        );
		},
		processdone : function(e, data) {
			alert('Processing done.');
		}
	});
});*/

/*$(function() {
	$('#fileupload').fileupload({
		dataType : 'json',
		add : function(e, data) {
			alert('upload started');
			data.submit();
		}
	});
});*/

/*$(function() {
	$('<button/>').text('Upload Text').appendTo(document.body);
	$('<p/>').text('Uploading... Text').appendTo(document.body);
})

$(function () {
    $("#fileupload").fileupload({
        add: function (e, data) {
            alert(data.name);
        }
    });
});
*/
/*$(function() {
=======

$(function() {
>>>>>>> ff706300618d3d6cc545d8849418b7b5e5a4c726
	var control = $("#control");
    $("#reset").click(function() {
        control.replaceWith( control = control.clone( true ) );
    });  
});
<<<<<<< HEAD
*/
=======
>>>>>>> ff706300618d3d6cc545d8849418b7b5e5a4c726
