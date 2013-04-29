$(document).ready(function() {
		var pagerOptions = {
			container : $(".pager"),
			output : '{startRow} - {endRow} / {filteredRows} ({totalRows})',
			fixedHeight : true,
			removeRows : false,
			cssGoto : '.gotoPage',
		};
		$("table").tablesorter({
			widgets : [ 'zebra', 'filter' ]
		}).tablesorterPager(pagerOptions);

		$("#create-user").click(function() {
			
			$("#dialog-form").dialog({

				maxWidth : 550,
				maxHeight : 600,
				width : 550,
				height : 600,
				modal : true
				
			});
		});
		
		
		$("#editBtn").click(function() {
			//if editbtn is equal to save
			if($("#editBtn").val() == 'Save') {
				
				disabledInput();
				
				var d = {firstName:$("#editfirstName").val(),
						middleName:$("#editMiddleName").val(),
						lastName:$("#editLastName").val(),
						employeeId:$("#editEmployeeId").val(),
						biometrics:$("#editBiometrics").val(),
						department:$("#editDepartment").val(),
						shift:$("#editShift").val(),
						level:$("#editLevel").val(),
						hireDate:$("#editHiredDate").val(),
						regularizationDate:$("#editRegularizationDate").val(),
						resignationDate:$("#editResignationDate").val(),
						supervisorName:$("#editSupervisorName").val(),
						supervisorEmail:$("#editSupervisorEmail").val(),
						locAssign:$("#editLocAssign").val(),
						employeeEmail:$("#editEmployeeEmail").val(),
						};
				var json = JSON.stringify(d);
				console.log(json);
				
				$.ajax({
					url: 'saveEditEmployee',
					type: 'POST',
					data: d
				})
				
				$("#editBtn").attr('value', 'Edit');
				$("#cancelBtn").attr('value', 'Exit');
			} else {
				enableInput();
				$("#editBtn").attr('value', 'Save');
				$("#cancelBtn").attr('value', 'Cancel');
			}
		});
		
		$("#exitBtn").click(function() {
			console.log("cancel button");
			if($("#cancelBtn").val() == 'Cancel') {
				$("#cancelBtn").attr('value', 'Cancel');
				$("#editBtn").attr('value', 'Edit');
			}
			
			disabledInput();
			$("#dialog-formFullInformation").dialog('close');
		});
		
		$("#createBtn").click(function() {
			
			var d = {firstName:$("#firstName").val(),
					middleName:$("#middleName").val(),
					lastName:$("#lastName").val(),
					employeeId:$("#employeeId").val(),
					biometrics:$("#biometrics").val(),
					department:$("#department").val(),
					shift:$("#shift").val(),
					level:$("#level").val(),
					hireDate:$("#hiredDate").val(),
					supervisorName:$("#supervisorName").val(),
					supervisorEmail:$("#supervisorEmail").val(),
					locAssign:$("#locAssign").val(),
					employeeEmail:$("#employeeEmail").val(),
					};
			var json = JSON.stringify(d);
			console.log(json);
			
			$.ajax({
				url: 'createEmployee',
				type: 'POST',
				data: d
			})
			
		});
		
		$("cancelBtn").click(function() {
			$("#dialog-formFullInformation").dialog('close');
		})
		
	});

function disabledInput() {
	$("#editfirstName").attr('disabled', 'disabled');
	$("#editMiddleName").attr('disabled', 'disabled');
	$("#editLastName").attr('disabled', 'disabled');
	$("#editBiometrics").attr('disabled', 'disabled');
	$("#editDepartment").attr('disabled', 'disabled');
	$("#editShift").attr('disabled', 'disabled');
	$("#editPosition").attr('disabled', 'disabled');
	$("#editLevel").attr('disabled', 'disabled');
	$("#editHiredDate").attr('disabled', 'disabled');
	$("#editRegularizationDate").attr('disabled', 'disabled');
	$("#editResignationDate").attr('disabled', 'disabled');
	$("#editSupervisorName").attr('disabled', 'disabled');
	$("#editSupervisorEmail").attr('disabled', 'disabled');
	$("#editLocAssign").attr('disabled', 'disabled');
	$("#editEmployeeEmail").attr('disabled', 'disabled');
}

function enableInput() {
	$("#editfirstName").removeAttr('disabled');
	$("#editMiddleName").removeAttr('disabled');
	$("#editLastName").removeAttr('disabled');
	$("#editBiometrics").removeAttr('disabled');
	$("#editDepartment").removeAttr('disabled');
	$("#editShift").removeAttr('disabled');
	$("#editPosition").removeAttr('disabled');
	$("#editLevel").removeAttr('disabled');
	$("#editHiredDate").removeAttr('disabled');
	$("#editRegularizationDate").removeAttr('disabled');
	$("#editResignationDate").removeAttr('disabled');
	$("#editSupervisorName").removeAttr('disabled');
	$("#editSupervisorEmail").removeAttr('disabled');
	$("#editLocAssign").removeAttr('disabled');
	$("#editEmployeeEmail").removeAttr('disabled');
	
}

function showToForm(empInfo) {
	var employeeId = empInfo.id;
	console.log(empInfo.id);
	
	$("#dialog-formFullInformation").dialog({
		maxWidth : 550,
		maxHeight : 600,
		width : 550,
		height : 600,
		modal : true,
		close: function() {
			alert("closing");
			disabledInput();
			
            $(this).dialog("close");
        }
		
	});
	
	$.ajax({
		url : "../hr/searchEmployee",
		data : {
			employeeId : employeeId
		},
		success : function(response) {
			console.log(response.firstName);
			$("#editfirstName").val(response.firstName)
			$("#editMiddleName").val(response.middleName)
			$("#editLastName").val(response.lastName)
			$("#editEmployeeId").val(response.employeeId)
			$("#editBiometrics").val(response.biometrics)
			$("#editDepartment").val(response.department)
			$("#editShift").val(response.shift)
			$("#editPosition").val(response.position)
			$("#editLevel").val(response.level)
			$("#editHiredDate").val(response.hiredDate)
			$("#editRegularizationDate").val(response.regularizationDate)
			$("#editResignationDate").val(response.resignationDate)
			$("#editSupervisorName").val(response.supervisorName)
			$("#editSupervisorEmail").val(response.supervisorEmail)
			$("#editLocAssign").val(response.locAssign)
			$("#editEmployeeEmail").val(response.employeeEmail)
		}
	})
}


