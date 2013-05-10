var user = "";
var Employee_Id = 0; //for hr
var view ="";
var id =0;
$(document).ready(function() {
	id = idExternal;
	$( "#from" ).datepicker({ 
		dateFormat: 'yy-mm-dd', 
		showOn: "button",
	    buttonImage: "../resources/timelog/css/images/img_calendar.png",
	    buttonImageOnly: true,
	    buttonText: "Calendar"
	});
	
	$( "#to" ).datepicker({ 
		dateFormat: 'yy-mm-dd',
		showOn: "button",
	    buttonImage: "../resources/timelog/css/images/img_calendar.png",
	    buttonImageOnly: true,
	    buttonText: "Calendar"
	}); 
	
	$( "#SearchButton" ).click(SearchButton);
	$( "#SearchTimeLog" ).click(SearchTimeLog);
	mylog();
	
});	
 
function SearchButton() {
		var newresponse = null;
		var empname = $('#empName').val();
				$.ajax({
					type: "POST",
			        url: "/tempoplus/timelog/searchEmployee",
			    	data: {'empName': empname},
			    	success: function(response){
			    		newresponse = response; 
			    		if(newresponse =="OK"){
			    			
			    			$('#tblSearch tbody').remove();
			    			$('#tblSearch thead').remove();
			    			var tblList = "<tbody><thead><th>Employee ID</th><th>Lastname</th><th>Firstname</th><th>Middlename</th></thead>";
			    			$.ajax({		
			    				
			    			 	type: "POST",
			    		        url: "/tempoplus/timelog/retrieveEmployee",
			    		       	data: {'empName': empname},
			    		    
			    		       	success: function(response) {
			    		       		
			    		        	$.each(response,function(keys, values){

			    		        		tblList += "<tr onclick='fetch(this)'  id =" + values.employeeId + ","+ values.lastname+ ","+values.firstname+ "," + values.middlename + ">";
			    						tblList +="<td>"+values.employeeId+"</td><td>"+values.lastname+"</td>";
			    						tblList +="<td>"+values.firstname+"</td><td>"+values.middlename+"</td>";
			    						tblList +="</tr>";		
		

			    		        	});
			    		        	tblList += "</tbody>";
			    		        	$('#tblSearch').append(tblList);
			    		        	
			    		        },
			    		        error: function(e) {
			    		            alert("Error: " + e);
			    		            
			    		        }
	    			
			    			}).done(
						    		
							    	function(){
							    		$("#tblSearch").tablesorter();
							    		$("#tblSearch tbody td").hover(function() {
											$(this).parents('tr').find('td').addClass('highlight');
										}, function() {
											$(this).parents('tr').find('td').removeClass('highlight');
										});
									});
			    			$("#HrSearch").dialog({
			    				
								maxWidth : 550,
								maxHeight : 600,
								width : 550,
								height : 600,
								modal : true
								
							});
			    			

			    			
			    			
			    		}else{
			    			$("#result").css({display: "none"});
			    			alert("No Employee Found");
			    		}
			    		
			    	},
					
				});

}


function fetch(d){
	
	var rowid = d.id;
	var delimited = rowid.split(",");
	Employee_Id = delimited[0];
	var firstname = delimited[2];
	var lastname = delimited[1]; 
	
	$("#result").css({display: "block"});
	
	
	$('#employee').val(lastname +", " +firstname);
	$("#HrSearch").dialog('destroy');
	
}
function mylog()
{	
	$("#tblTimeLog thead").find("th").addClass('sorter-false');
	$("#tblTimeLog thead").find("th").removeClass('headerSortUp');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerSortUp');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerAsc');
	$("#tblTimeLog thead").find("th").removeClass('headerSortDown');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerSortDown');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerDesc')
	
	$("#data").css({display: "none"});
	$("#SearchBox").css({display: "none"});
	$("#SearchSub").css({display: "none"});
	$("#HrSearch").css({display: "none"});
	$("#SearchRow").css({display:"none"});

	//hr search textbox and label
	$('#from').val(""); 
	$('#to').val(""); 
	$('empName').val("");
	$('#tbl').val("");
	$('#tblTimeLog tbody').remove();
	$('#pagesize').selectedIndex=0;
	$('#tblSearch tbody').remove();
	
	$("#result").css({display: "none"});

	//check if hr or manager
	var rponse ="";
	
	$.ajaxSetup({async:false});
	$.ajax(
	    		{
	        type: "POST",
	        url: "/tempoplus/timelog/checkUser",
	       	data: {'id': id},
	 
	       success: function(response) {
	        	user = response;
	        },
	        error: function(e) {
	            alert("Error: " + e);
	        }
	    });
	 view ='mylog';
	if(user == "hr" || user == "manager")
		{
		$('#mylog').html("MyLog");
		$('#others').href ="javascript:others()";
			if(user=="hr")
				{
					$('#others').html("View Others");
				}
			else if(user =="manager")
				{
					$('#others').html("MySubordinates");
				}
		}
	else
		{
		$('#others').href ="javascript:void(0)";
		$('#others').html("");
		$('#mylog').html("");
		}
	
}

function others()
{
	$("#tblTimeLog thead").find("th").addClass('sorter-false');
	$("#tblTimeLog thead").find("th").removeClass('headerSortUp');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerSortUp');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerAsc');
	$("#tblTimeLog thead").find("th").removeClass('headerSortDown');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerSortDown');
	$("#tblTimeLog thead").find("th").removeClass('tablesorter-headerDesc');
	
	$("#data").css({display: "none"});
	$('#from').val(""); 
	$('#to').val(""); 
	$('#tblTimeLog tbody').remove();
	$('#tbl').val(""); 
	$('#pagesize').selectedIndex=0;
	$('#empName').val("");
	$('#tblSearch tbody').remove();
	
	$("#result").css({display: "none"});

	if(user == "manager")
		{
		
		$("#SearchSub").css({display: "block"});
		$("#SearchBox").css({display: "none"});

		var dropDownStr = "";
		$('#sub option').remove();
		$.ajax(
	    		{
	        type: "POST",
	        url: "/tempoplus/timelog/retrieveSub",
	        data: {'id': id},
	        
	        success: function(response) {
	        	$.each(response, function(index,item) {
			 	 	dropDownStr += "<option value=" + item +">";
			 		dropDownStr += item;
			 		dropDownStr +="</option>"; 
				}); 
	        	$('#sub').append(dropDownStr);
	        },
	        
	        error: function(e) {
	            alert("Error: " + e);
	        }
	    })
		
		view = "manager";
		}
	else if(user == "hr")
		{
		$("#SearchSub").css({display: "none"});
		$("#SearchBox").css({display: "block"});

		view = "hr";
		}
}
function SearchTimeLog()
{		
		$("#tblTimeLog thead").find("th").removeClass('sorter-false');
		
		var name;
		var from = $('#from').val();
		var to = $('#to').val();
		var rponse;
		var idd;
		if(view == "manager")
			{
			idd = id;
			name = $("#sub option:selected").text();
			}
		
		else if(view =="mylog")
			{
			idd = id;
			name = id;
			}
		else if(view =="hr")
			{
			idd = Employee_Id;
			name = id;
			}
		$.ajaxSetup({async:false});
		$.ajax(
		    		{
		        type: "POST",
		        url: "/tempoplus/timelog/validateInput",
		       	data: {'id': idd,'name': name,'from': from , 'to' : to},
		 
		       success: function(response) {
		        	rponse = response;
		        	
		        },
		        error: function(e) {
		            alert("Error: " + e);
		        }
		    });
		if(rponse == "OK")
			{
			$("#data").css({display: "none"});

				if(view == "mylog")
					{
					$('#tblTimeLog tbody').remove();
					var tableStr = "<tbody>";
					$.ajax(
						    		{
						        type: "POST",
						        url: "/tempoplus/timelog/retrieveMylog",
						        data: {'id': id , 'from': from, 'to': to},
						        success: function(response) {
						        	$.each(response, function(index,item) {
								 	
								 		tableStr += "<tr>";
										tableStr +="<td>"+item.date+"</td><td>"+item.timeIn+"</td>";
										if(item.timeOut == null){
											tableStr +="<td>&nbsp;</td><td>"+item.duration+"</td>";
											tableStr +="</tr>";
										}else{
										
										tableStr +="<td>"+item.timeOut+"</td><td>"+item.duration+"</td>";
										tableStr +="</tr>";
										}
									}); 
								 	tableStr +="</tbody>";
								 	$('#tblTimeLog').append(tableStr);
						        },
						        error: function(e) {
						            alert("Error: " + e);
						        }
						    }).done(
						    		
							    	function(){
							    		$("#tblTimeLog").trigger('destroy');
							    		$("#tblTimeLog").trigger('update');
							    		$("#tblTimeLog")
							    		 .tablesorter({widgets: ['zebra']})
							    		.tablesorterPager({container: $(".pagers"),positionFixed: false,fixedHeight: true,page:0,output : '{page} / {totalPages}'}); 
							    		$("#tblTimeLog tbody tr").find("td").addClass('row');
							    		$("#tblTimeLog").trigger('update');
							   
							    	});
					}
				else if(view == "manager")
					{
					
					$('#tblTimeLog tbody').remove();
					var tableStr = "<tbody>";
					$.ajax(
						    		{
						        type: "POST",
						        url: "/tempoplus/timelog/retrieveTimelog",
						        data: {'name': name , 'from': from, 'to': to},
						        success: function(response) {
						        	$.each(response, function(index,item) {
								 	
								 		tableStr += "<tr>";
										tableStr +="<td>"+item.date+"</td><td>"+item.timeIn+"</td>";
										if(item.timeOut == null){
											tableStr +="<td>&nbsp;</td><td>"+item.duration+"</td>";
											tableStr +="</tr>";
										}else{
										
										tableStr +="<td>"+item.timeOut+"</td><td>"+item.duration+"</td>";
										tableStr +="</tr>";
										}
									}); 
								 	tableStr +="</tbody>";
								 	$('#tblTimeLog').append(tableStr);
						        },
						        error: function(e) {
						            alert("Error: " + e);
						        }
						    }).done(
							    	function(){
							    		$("#tblTimeLog").trigger('destroy');
							    		$("#tblTimeLog").trigger('update');
							    		$("#tblTimeLog")
							    		 .tablesorter({widgets: ['zebra']})
							    		.tablesorterPager({container: $(".pagers"),positionFixed: false,fixedHeight: true,page:0,output : '{page} / {totalPages}'});
							    		$("#tblTimeLog tbody tr").find("td").addClass('row');
							    		$("#tblTimeLog").trigger('update');
							    	});
					}
				else if(view == "hr")
					{ 
					$('#tblTimeLog tbody').remove();
					var tableStr = "<tbody>";
					$.ajax(
						    		{
						        type: "POST",
						        url: "/tempoplus/timelog/retrieveMylog",
						        data: {'id': idd , 'from': from, 'to': to},
						        success: function(response) {
						        	$.each(response, function(index,item) {
								 	
								 		tableStr += "<tr>";
										tableStr +="<td>"+item.date+"</td><td>"+item.timeIn+"</td>";
										if(item.timeOut == null){
											tableStr +="<td>&nbsp;</td><td>"+item.duration+"</td>";
											tableStr +="</tr>";
										}else{
										
										tableStr +="<td>"+item.timeOut+"</td><td>"+item.duration+"</td>";
										tableStr +="</tr>";
										}
									}); 
								 	tableStr +="</tbody>";
								 	$('#tblTimeLog').append(tableStr);
						        },
						        error: function(e) {
						            alert("Error: " + e);
						        }
						    }).done(
							    	function(){
							    		$("#tblTimeLog").trigger('destroy');
							    		$("#tblTimeLog").trigger('update');
							    		$("#tblTimeLog")
							    		 .tablesorter({widgets: ['zebra']})
							    		.tablesorterPager({container: $(".pagers"),positionFixed: false,fixedHeight: true,page:0,output : '{page} / {totalPages}'});
							    		$("#tblTimeLog tbody tr").find("td").addClass('row');
							    		$("#tblTimeLog").trigger('update');
							    	});
					}
			}
		else
			{
			if(rponse=="No Data")
				{
				$('#tblTimeLog tbody').remove();
				$("#data").css({display: "block"});
				}
			else
				{
				$('#tblTimeLog tbody').remove();
				alert(rponse);
				}
			
			}
		
	}