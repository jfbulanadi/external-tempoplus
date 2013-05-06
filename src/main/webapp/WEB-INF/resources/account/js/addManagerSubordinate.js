$(function(){
	$("#addSubordinates").button().click(function(){
		 $( "#AddSubordinateForm" ).dialog({
				maxWidth : 700,
				maxHeight : 400,
				width : 700,
				height : 400,
				modal: true
			});
	});
			 
			$("#searchEmployee").button().click(function(){
				var employeeName= $('#employeeName').val();
				var tableList = "<tbody><thead><th>Employee ID</th><th>Last Name</th>" +
						"<th>First Name</th><th>Department</th><th>Position</th></thead>";
				
		
					$.ajax({
						type:"POST",
						url: "/tempoplus/manager/searchEmployeeSubordinates",
						data: {'employeeName': employeeName},
						success: function(response){

							if(response == true){
								alert(response);
								/*retrieve employees found*/
								$('#foundEmployeeTable tbody').remove();
				    			$('#foundEmployeeTable thead').remove();
				    			
								$.ajax({
									type:"POST",
									url: "/tempoplus/manager/retrieveFoundEmployees",
									data:{'employeeName': employeeName},
									success:function(response){
										
										$.each(response, 
											function(keys, values){
											
											tableList += "<tr><td><input type='checkbox' name='add'  ";
											tableList += "id='" + values.employeeId + "'/>";
											tableList +=  values.employeeId + "</td>";
											tableList += "<td>" + values.lastName + "</td>";
											tableList += "<td>" + values.firstName + "</td>";
											tableList += "<td>" + values.department + "</td>";
											tableList += "<td>" + values.position + "</td>";
											tableList += "</tr>";
											}	
											
										);
										tableList += "</tbody>";	
										$('#foundEmployeeTable').append(tableList);
										
										  
										/*Add employees as manager's subordinates
*/										 $("#addNewSubordinates").button().click(function(){
												/* Get employee ids to add as subordinates*/
	                                            var checkedEmployeeIds = [];
												 $("input[name = 'add']:checked").each(function() {
														 checkedEmployeeIds.push($(this).attr("id"));
													 });
												 
										
												 var employeeIdsJSON = {'ids':checkedEmployeeIds};
												 var jsonString = JSON.stringify(employeeIdsJSON);
													alert(jsonString);
												 /* Update employees' supervisor*/
												   $.ajax({
														type:"POST",
														url: "/tempoplus/manager/saveNewSubordinates",
														data: {'employeeIdsJSON':jsonString},
														success:function(response){
															alert(response);
														}
												   });
												  
											 
										});
									}
								}).done(
							    		
								    	function(){
								    		
								    		$("#foundEmployeeTable").tablesorter();
								    		$("#foundEmployeeTable td").hover(function() {
												$(this).parents('tr').find('td').addClass('highlight');
											}, function() {
												$(this).parents('tr').find('td').removeClass('highlight');
											});
										});
								
								
							}
							else{
								alert(response);
							}
						}
					});
						
							
			});
			
			
	
	
	
});



