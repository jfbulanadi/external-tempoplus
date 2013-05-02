var user = "";
var Employee_Id = 0; //for hr
var view =""
var id;
$(document).ready(function() {
		
		$( "#from" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#to" ).datepicker({ dateFormat: 'yy-mm-dd' }); 
		$( "#SearchTimeLog" ).click(SearchTimeLog); 
		id = idExternal;
		
		
	$("#SearchButton").click(function() {
		var newresponse;
		var empname = $('#empName').val();
							
				$.ajax({
					type: "POST",
			        url: "/tempoplus/timelog/searchEmployee",
			    	data: {'empName': empname},
			    	success: function(response){
			    		newresponse = response;
			    		if(newresponse =="OK"){
			    			$("#HrSearch").dialog({
			    				
								maxWidth : 550,
								maxHeight : 600,
								width : 550,
								height : 600,
								modal : true
								
							});
			    			
			    			$('#tblSearch tbody').remove();
			    			$('#tblSearch thead').remove();
			    			var tblList = "<tbody><thead><th>Employee ID</th><th>Lastname</th><th>Firstname</th><th>Middlename</th></thead>";
			    			$.ajax({		
			    				
			    			 	type: "POST",
			    		        url: "/tempoplus/timelog/retrieveEmployee",
			    		       	data: {'empName': empname},
			    		    
			    		       	success: function(response) {
			    		       		
			    		        	$.each(response,function(keys, values){

			    		        		tblList += "<tr ondblclick='fetch(this)'  id =" + values.employeeId + ","+ values.lastname+ ","+values.firstname+ "," + values.middlename + ">";
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
							    		my_jQuery3("#tblSearch")
							    		 .tablesorter({widthFixed: false, widgets: ['zebra']});
									});
			    			
			    		}else{
			    	
			    			alert("No Employee Found");
			    		}
			    		
			    	},
					
				});
	});

});


function fetch(d){
	$("#HrSearch").dialog('close');
	var rowid = d.id;
	var delimited = rowid.split(",");
	Employee_Id = delimited[0];
	var firstname = delimited[2];
	var lastname = delimited[1]; 
	
	$("#result").css({display: "block"});
	
	
	$('#employee').val(lastname +", " +firstname);
	
	var rows = $('#tblSearch tbody tr');
	var length = $('#tblSearch tbody tr').length;
	for(var i= 0; i< length; i++){
		rows[i].style.background = "";
	}
			
			d.style.background="gray";
}
function mylog()
{	
	$("#data").css({display: "none"});
	$("#SearchBox").css({display: "none"});
	$("#SearchSub").css({display: "none"});
	$("#HrSearch").css({display: "none"});
	$("#SearchRow").css({display:"none"});
	//hr search textbox and label
	document.getElementById('from').value = ""; 
	document.getElementById('to').value = ""; 
	document.getElementById('empName').value = "";

	$('#tblTimeLog tbody').remove();
	document.getElementById('tbl').value = ""; 
	document.getElementById('tbl').disabled=true;
	document.getElementById('pagesize').selectedIndex=0;
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
	//user = "hr";
	//else user = ""
	if(user == "hr" || user == "manager")
		{
		document.getElementById('others').href ="javascript:others()";
			if(user=="hr")
				{
					document.getElementById('others').innerHTML = "View Others";
				}
			else if(user =="manager")
				{
					document.getElementById('others').innerHTML = "MySubordinates";
				}
		}
	else
		{
		document.getElementById('others').href ="javascript:void(0)";
		}
}

function others()
{
	$("#data").css({display: "none"});
	document.getElementById('from').value = ""; 
	document.getElementById('to').value = ""; 
	$('#tblTimeLog tbody').remove();
	document.getElementById('tbl').value = ""; 
	document.getElementById('pagesize').selectedIndex=0;
	document.getElementById('empName').value = "";
	$('#tblSearch tbody').remove();
	
	$("#result").css({display: "none"});;
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
		document.getElementById('pagesize').selectedIndex=0;
		var name;
		var from = $('#from').val();
		var to = $('#to').val();
		var rponse;
		var idd;
		if(view == "manager")
			{
			idd = id;
			var sub = document.getElementById("sub");
			if(sub.options.length > 0)
				{
				name =  sub.options[sub.selectedIndex].text;
				}
			else
				{
				name = "";
				}
			}
		else if(view =="mylog")
			{
			idd = id;
			name = id;
			}
		else if(view =="hr")
			{
			idd = Employee_Id;
//			Employee_Id = 0;
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
											tableStr +="<td></td><td>"+item.duration+"</td>";
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
							    		my_jQuery3("#tblTimeLog")
							    		 .tablesorter({widthFixed: false, widgets: ['zebra']})
							    		.tablesorterPager({container: my_jQuery3("#pager"),positionFixed: false}); 
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
											tableStr +="<td></td><td>"+item.duration+"</td>";
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
							    		my_jQuery3("#tblTimeLog")
							    		 .tablesorter({widthFixed: false, widgets: ['zebra']})
							    		.tablesorterPager({container: my_jQuery3("#pager"),positionFixed: false});
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
											tableStr +="<td></td><td>"+item.duration+"</td>";
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
							    		my_jQuery3("#tblTimeLog")
							    		 .tablesorter({widthFixed: false, widgets: ['zebra']})
							    		.tablesorterPager({container: my_jQuery3("#pager"),positionFixed: false});
									});
					}
			}
		else
			{
			document.getElementById('tbl').value = ""; 
			if(rponse=="No Data")
				{
				$('#tblTimeLog tbody').remove();
				$("#data").css({display: "block"});
				}
			else
				{
				$("#data").css({display: "none"});
				$('#tblTimeLog tbody').remove();
				alert(rponse);
				}
			
			}
	}