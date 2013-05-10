$(document)
		.ready(
				function() {
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

					$("#create-user")
							.click(
									function() {
										$('#selectPosition option').remove();
										$('#selectSupervisorName option')
												.remove();

										$("#dialog-form")
												.dialog(
														{

															maxWidth : 550,
															maxHeight : 600,
															width : 550,
															height : 600,
															modal : true,
															buttons : {
																"Create Employee" : function() {

																	var d = {
																		firstName : $(
																				"#firstName")
																				.val(),
																		middleName : $(
																				"#middleName")
																				.val(),
																		lastName : $(
																				"#lastName")
																				.val(),
																		employeeId : $(
																				"#employeeId")
																				.val(),
																		biometrics : $(
																				"#biometrics")
																				.val(),
																		department : $(
																				"#selectDepartment")
																				.val(),
																		position : $(
																				"#selectPosition")
																				.val(),
																		shift : $(
																				"#selectShift")
																				.val(),
																		level : $(
																				"#level")
																				.val(),
																		hireDate : $(
																				"#hiredDate")
																				.val(),
																		supervisorName : $(
																				"#selectSupervisorName")
																				.val(),
																		employeeEmail : $(
																				"#employeeEmail")
																				.val(),
																	};

																	var json = JSON
																			.stringify(d);
																	console
																			.log(json);

																	$(
																			"#dialog-confirm-create")
																			.dialog(
																					{
																						resizable : false,
																						height : 140,
																						modal : true,
																						buttons : {
																							"Save Profile" : function() {
																								$
																										.ajax({
																											url : '../hr/createEmployee',
																											type : 'POST',
																											data : d
																										})

																								$(
																										':input',
																										'#dialog-form')
																										.not(
																												':button, :submit, :reset, :hidden')
																										.val(
																												'')
																								$(
																										this)
																										.dialog(
																												"close");

																							},
																							"Cancel" : function() {
																								$(
																										':input',
																										'#dialog-form')
																										.not(
																												':button, :submit, :reset, :hidden')
																										.val(
																												'')
																								$(
																										this)
																										.dialog(
																												"close");
																							}
																						}
																					});

																},
																"Cancel" : function() {
																	$(this)
																			.dialog(
																					"close");
																}
															}

														});
									});

					$("#createBtn").click(function() {

						var d = {
							firstName : $("#firstName").val(),
							middleName : $("#middleName").val(),
							lastName : $("#lastName").val(),
							employeeId : $("#employeeId").val(),
							biometrics : $("#biometrics").val(),
							department : $("#selectDepartment").val(),
							position : $("#selectPosition").val(),
							shift : $("#shift").val(),
							level : $("#level").val(),
							hireDate : $("#hiredDate").val(),
							supervisorName : $("#supervisorName").val(),
							supervisorEmail : $("#supervisorEmail").val(),
							locAssign : $("#locAssign").val(),
							employeeEmail : $("#employeeEmail").val(),
						};
						var json = JSON.stringify(d);
						console.log(json);

						$.ajax({
							url : '../hr/createEmployee',
							type : 'POST',
							data : d
						})

					});

					$("cancelBtn").click(function() {
						$("#dialog-form").dialog('close');
					})

				});

// this is for drop down function in select upload file
$(function() {
	$("#accordion").accordion({
		collapsible : true,
		heightStyle : "content",
		active : false
	});
});

// this is to remove previous inputs in create employee form
$(function() {
	$("#create-user").click(
			function() {
				$(':input', '#dialog-form').not(
						':button, :submit, :reset, :hidden').val('')
			});
})

function showToForm(empInfo) {
	$('#editSelectDepartment option').remove();
	$('#editSelectPosition option').remove();
	$('#editSelectSupervisorName option').remove();

	var employeeId = empInfo.id;
	console.log(empInfo.id);

	$("#dialog-formFullInformation")
			.dialog(
					{
						maxWidth : 550,
						maxHeight : 630,
						width : 550,
						height : 630,
						modal : true,
						buttons : {
							"Save" : function() {
								$("#dialog-confirm")
										.dialog(
												{
													resizable : false,
													height : 140,
													modal : true,
													buttons : {
														"Save Profile" : function() {
															disabledInput();

															var d = {
																firstName : $(
																		"#editfirstName")
																		.val(),
																middleName : $(
																		"#editMiddleName")
																		.val(),
																lastName : $(
																		"#editLastName")
																		.val(),
																employeeId : $(
																		"#editEmployeeId")
																		.val(),
																biometrics : $(
																		"#editBiometrics")
																		.val(),
																departmentId : $(
																		"#editSelectDepartment")
																		.val(),
																positionId : $(
																		"#editSelectPosition")
																		.val(),
																shiftId : $(
																		"#editSelectShift")
																		.val(),
																level : $(
																		"#editLevel")
																		.val(),
																hireDate : $(
																		"#editHiredDate")
																		.val(),
																regularizationDate : $(
																		"#editRegularizationDate")
																		.val(),
																resignationDate : $(
																		"#editResignationDate")
																		.val(),
																supervisorId : $(
																		"#editSelectSupervisorName")
																		.val(),
																locAssign : $(
																		"#editLocAssign")
																		.val(),
																employeeEmail : $(
																		"#editEmployeeEmail")
																		.val(),
															};
															var json = JSON
																	.stringify(d);
															console.log(json);

															$
																	.ajax({
																		url : '../hr/saveEditEmployee',
																		type : 'POST',
																		data : d
																	})

															$(this).dialog(
																	"close");

														},
														Cancel : function() {
															$(this).dialog(
																	"close");
															disabledInput();
														}
													}
												});
							},
							"Edit Profile" : function() {
								enableInput();

							},
							Cancel : function() {
								$(this).dialog("close");
							}
						},
						close : function() {
							disabledInput();
							$("#editBtn").attr('value', 'Edit');
							$("#exitBtn").attr('value', 'Exit');
							$(this).dialog("close");
						}

					});

	var department = '';
	$.ajax({
		async : false,
		url : "../hr/retrieveDepartmentJSON",
		success : function(response) {
			$.each(response, function(key, value) {
				department += '<option value=' + key + '> ' + value
						+ '</option>';
				});
				$('#editSelectDepartment').append(department);
			}
		})
			});
			$('#editSelectDepartment').append(department);
		}
	})

	$.ajax(
			{
				url : "../hr/searchEmployee",
				data : {
					employeeId : employeeId
				},
				success : function(response) {
					var department = response.departmentId;
					var position = '';

					$.ajax({
						async : false,
						url : "../hr/retrievePositionJSON",
						data : {
							department : department
						},
						success : function(responsePosition) {
							$.each(responsePosition, function(key, value) {
								position += '<option value=' + key + '> '
										+ value + '</option>';
							});
							$('#editSelectPosition').append(position);
						}
					})

					var supervisor = '';

					$.ajax({
						async : false,
						url : "../hr/retrieveSupervisorJSON",
						data : {
							department : department
						},
						success : function(responseSupervisor) {
							$.each(responseSupervisor, function(key, value) {
								supervisor += '<option value=' + key + '> '
										+ value + '</option>';
							});
							$('#editSelectSupervisorName').append(supervisor);
						}
					})

				}
			}).done(function(response) {
		var department = response.departmentId;
		var position = response.positionId;
		var supervisor = response.supervisorId;

		$("#editfirstName").val(response.firstName)
		$("#editMiddleName").val(response.middleName)
		$("#editLastName").val(response.lastName)
		$("#editSelectDepartment").val(department)
		$("#editEmployeeId").val(response.employeeId)
		$("#editBiometrics").val(response.biometricId)
		$("#editSelectShift").val(response.shiftId)
		$("#editSelectPosition").val(position)
		$("#editLevel").val(response.level)
		$("#editHiredDate").val(response.hiredDate)
		$("#editRegularizationDate").val(response.regularizationDate)
		$("#editResignationDate").val(response.resignationDate)
		$("#editSelectSupervisorName").val(supervisor)
		$("#editSupervisorEmail").val(response.supervisorEmail)
		$("#editLocAssign").val(response.locAssign)
		$("#editEmployeeEmail").val(response.employeeEmail)
		$("#editSelectPosition").val(position)
		$("#editEmployeeId").val(response.employeeId)
		$("#editBiometrics").val(response.biometricId)
		$("#editSelectShift").val(response.shiftId)
		$("#editSelectPosition").val(position)
		$("#editLevel").val(response.level)
		$("#editHiredDate").val(response.hiredDate)
		$("#editRegularizationDate").val(response.regularizationDate)
		$("#editResignationDate").val(response.resignationDate)
		$("#editSelectSupervisorName").val(supervisor)
		$("#editSupervisorEmail").val(response.supervisorEmail)
		$("#editLocAssign").val(response.locAssign)
		$("#editEmployeeEmail").val(response.employeeEmail)
		$("#editSelectPosition").val(position)
	})

}

// add attribute 'disabled' to input of dialog-formFullInformation
function disabledInput() {
	$("#editEmployeeId").attr('disabled', 'disabled');
	$("#editfirstName").attr('disabled', 'disabled');
	$("#editMiddleName").attr('disabled', 'disabled');
	$("#editLastName").attr('disabled', 'disabled');
	$("#editBiometrics").attr('disabled', 'disabled');
	$("#editSelectDepartment").attr('disabled', 'disabled');
	$("#editSelectShift").attr('disabled', 'disabled');
	$("#editSelectPosition").attr('disabled', 'disabled');
	$("#editLevel").attr('disabled', 'disabled');
	$("#editHiredDate").attr('disabled', 'disabled');
	$("#editRegularizationDate").attr('disabled', 'disabled');
	$("#editResignationDate").attr('disabled', 'disabled');
	$("#editSelectSupervisorName").attr('disabled', 'disabled');
	$("#editSupervisorEmail").attr('disabled', 'disabled');
	$("#editLocAssign").attr('disabled', 'disabled');
	$("#editEmployeeEmail").attr('disabled', 'disabled');

}

// remove attribute 'disabled' to input of dialog-formFullInformation
function enableInput() {
	$("#editEmployeeId").removeAttr('disabled');
	$("#editfirstName").removeAttr('disabled');
	$("#editMiddleName").removeAttr('disabled');
	$("#editLastName").removeAttr('disabled');
	$("#editBiometrics").removeAttr('disabled');
	$("#editSelectDepartment").removeAttr('disabled');
	$("#editSelectShift").removeAttr('disabled');
	$("#editSelectPosition").removeAttr('disabled');
	$("#editLevel").removeAttr('disabled');
	$("#editHiredDate").removeAttr('disabled');
	$("#editRegularizationDate").removeAttr('disabled');
	$("#editResignationDate").removeAttr('disabled');
	$("#editSelectSupervisorName").removeAttr('disabled');
	$("#editSupervisorEmail").removeAttr('disabled');
	$("#editLocAssign").removeAttr('disabled');
	$("#editEmployeeEmail").removeAttr('disabled');
}

// this is for create dialog
$(function() {
	var selectValues = {
		"2" : {
			"HR Recruiter" : 1,
			"HR Officer" : 2,
			"HR Director" : 3
		},
		"1" : {
			"Software Engineer I" : 1,
			"Software Engineer II" : 2
		}
	};

	var $department = $('select.department');
	var $position = $('select.position');
	$department.change(function() {
		$position.empty().append(function() {
			var output = '';
			$.each(selectValues[$department.val()], function(key, value) {
				output += '<option value=' + value + '>' + key + '</option>';
			});
			return output;
		});
	}).change();
});

$(function() {
	var details = '';
	$.ajax({
		url : "../hr/selectAllJSON",
		success : function(response) {
			$.each(response, function(index, employee) {
				details += '<tr id=' + employee.employeeId
						+ ' onClick=showToForm(this)>'
				details += '<td> ' + employee.biometrics + '</td>'
				details += '<td> ' + employee.employeeId + '</td>'
				details += '<td> ' + employee.firstName + '</td>'
				details += '<td> ' + employee.middleName + '</td>'
				details += '<td> ' + employee.lastName + '</td>'
				details += '<td> ' + employee.department + '</td>'
				details += '</tr>'
			});
			$('#employeeTbl').append(details);
		}
	})
});

$(function() {
	var department = '';
	$.ajax({
		url : "../hr/retrieveDepartmentJSON",
		success : function(response) {
			$.each(response, function(key, value) {
				department += '<option value=' + key + '> ' + value
						+ '</option>';
			});
			$('#selectDepartment').append(department);
		}
	})

});

$(function() {
	$("#selectDepartment").click(
			function() {
				$('#selectPosition option').remove();
				$('#selectSupervisorName option').remove();
				var data = {
					department : $("#selectDepartment").val()
				};

				var json = JSON.stringify(data);
				console.log(json);
				var position = '';
				$.ajax({
					url : "../hr/retrievePositionJSON",
					data : data,
					success : function(response) {
						$.each(response, function(key, value) {
							position += '<option value=' + key + '> ' + value
									+ '</option>';
						});
						$('#selectPosition').append(position);
					}
				})

				var supervisor = '';

				$.ajax({
					url : "../hr/retrieveSupervisorJSON",
					data : data,
					success : function(response) {
						$.each(response, function(key, value) {
							supervisor += '<option value=' + key + '> ' + value
									+ '</option>';
						});
						$('#selectSupervisorName').append(supervisor);
					}
				})
			});

});

$(function() {
	$("#editSelectDepartment").click(
			function() {
				$('#editSelectPosition option').remove();
				$('#editSelectSupervisorName option').remove();
				var data = {
					department : $("#editSelectDepartment").val()
				};

				var json = JSON.stringify(data);
				console.log(json);
				var position = '';
				$.ajax({
					url : "../hr/retrievePositionJSON",
					data : data,
					success : function(response) {
						$.each(response, function(key, value) {
							position += '<option value=' + key + '> ' + value
									+ '</option>';
						});
						$('#editSelectPosition').append(position);
					}
				})

				var supervisor = '';

				$.ajax({
					url : "../hr/retrieveSupervisorJSON",
					data : data,
					success : function(response) {
						$.each(response, function(key, value) {
							supervisor += '<option value=' + key + '> ' + value
									+ '</option>';
						});
						$('#editSelectSupervisorName').append(supervisor);
					}
				})
			});

});

$(function() {
	var shift = '';
	$.ajax({
		url : "../hr/retrieveShiftJSON",
		success : function(response) {
			$.each(response, function(key, value) {
				shift += '<option value=' + key + '> ' + value + '</option>';
			});
			$('#selectShift').append(shift);
			$('#editSelectShift').append(shift);
		}
	})

});

$(function() {
	$("#hiredDate").datepicker("destroy");
	$("#hiredDate").datepicker();
	$("#hiredDate").change(function() {
		$("#hiredDate").datepicker("option", "dateFormat", "yy-mm-dd");
	});

});

$(function() {
	$("#editHiredDate").datepicker();
	$("#editHiredDate").change(function() {
		$("#editHiredDate").datepicker("option", "dateFormat", "yy-mm-dd");
	});
});

$(function() {
	$("#editRegularizationDate").datepicker();
	$("#editRegularizationDate").change(
			function() {
				$("#editRegularizationDate").datepicker("option", "dateFormat",
						"yy-mm-dd");
			});
});

$(function() {
	$("#editResignationDate").datepicker();
	$("#editResignationDate").change(
			function() {
				$("#editResignationDate").datepicker("option", "dateFormat",
						"yy-mm-dd");
			});
});
/*
$(function() {
	$("#uploadStatus").click(function() {
		$.ajax({
			type: "POST",
			url:"../hr/uploadUserDB",
			fileElementId: 'files',
			DataType: "json",
			success: function() {
				alert(1);
			}	
		});
		
	});
	
});*/
	$("#uploadStatus").click(function() {
		$.ajax({
			type: "POST",
			url:"../hr/uploadUserDB",
			fileElementId: 'files',
			DataType: "json",
			success: function() {
				alert(1);
			}	
		});
		
	});
	
});*/
