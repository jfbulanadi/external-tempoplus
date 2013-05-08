	
$(function(){

		$("#changePasswordBtn").button().click(function(){
					
		var currentPassword = $('#currentPassword').val();
		var newPassword = $('#newPassword').val();
		var repeatNewPassword = $('#repeatNewPassword').val();

						$.ajax({
						type:"POST",
						url: "/tempoplus/user/changePassword",
						data: {
								'currentPassword': currentPassword,
								'newPassword': newPassword,
								'repeatNewPassword': repeatNewPassword
							  },
						success: function(response){
							alert(response);
							$('#currentPassword').val("")
							$('#newPassword').val("");
							$('#repeatNewPassword').val("");
						}
					});
					
		});
});