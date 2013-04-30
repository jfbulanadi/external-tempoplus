var user = "";
var Employee_Id = 0; //for hr
var view =""
var id;
$(document).ready(function() {
		
		$( "#from" ).datepicker({ dateFormat: 'yy-mm-dd' });
		$( "#to" ).datepicker({ dateFormat: 'yy-mm-dd' }); 
		$( "#SearchTimeLog" ).click(SearchTimeLog); 
		$( "#SearchName" ).click(SearchName); 
		id = idExternal;
		
});

function SearchName()
{
	var newresponse;
	var empname = $('#empName').val();
	
	$.ajax({
		type: "POST",
        url: "/tempoplus/timelog/searchEmployee",
    	data: {'empName': empname},
    	success: function(response){
    		newresponse = response;
    		if(newresponse =="OK"){
    			document.getElementById('HrSearch').style.visibility = 'visible';
    			
    			$('#tblSearch tbody').remove();
    			var tblList = "<tbody>";
    			$.ajax({		
    				
    			 	type: "POST",
    		        url: "/tempoplus/timelog/retrieveEmployee",
    		       	data: {'empName': empname},
    		       
    		       	success: function(response) {
    		    
    		        	$.each(response,function(keys, values){

    		        		tblList += "<tr onclick='fetch(this)' id =" + values.employeeId + ","+ values.lastname+ ","+values.firstname+ "," + values.middlename + ">";
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
    			
    			
    			});
    			
    			
    		}else{
    	
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
	
	document.getElementById('result').style.visibility = 'visible';
	
	
	$('#employee').val(lastname +", " +firstname);
	
	
	var rows = $('#tblSearch tr');
	var length = $('#tblSearch tr').length;
	for(var i= 0; i< length; i++){
		rows[i].style.background = "";
	}
			
			d.style.background="gray";
}
function mylog()
{	
	document.getElementById('data').style.visibility = 'hidden';
	document.getElementById('SearchBox').style.visibility = 'hidden'; 
	document.getElementById('SearchSub').style.visibility = 'hidden'; 
	//hr search textbox and label
	document.getElementById('from').value = ""; 
	document.getElementById('to').value = ""; 
	document.getElementById('empName').value = "";

	$('#tblTimeLog tbody').remove();
	document.getElementById('tbl').value = ""; 
	document.getElementById('tbl').disabled=true;
	document.getElementById('pagesize').selectedIndex=0;
	$('#tblSearch tbody').remove();
	document.getElementById('result').style.visibility = 'hidden';
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
	document.getElementById('data').style.visibility = 'hidden';
	document.getElementById('from').value = ""; 
	document.getElementById('to').value = ""; 
	$('#tblTimeLog tbody').remove();
	document.getElementById('tbl').value = ""; 
	document.getElementById('pagesize').selectedIndex=0;
	document.getElementById('empName').value = "";
	$('#tblSearch tbody').remove();
	document.getElementById('result').style.visibility = 'hidden';
	if(user == "manager")
		{
		document.getElementById('SearchSub').style.visibility = 'visible'; 
		document.getElementById('SearchBox').style.visibility = 'hidden'; 
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
		document.getElementById('SearchSub').style.visibility = 'hidden'; 
		document.getElementById('SearchBox').style.visibility = 'visible'; 
		view = "hr";
		}
}
function SearchTimeLog()
{	
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
			document.getElementById('data').style.visibility = 'hidden';
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
			if(rponse=="No Data")
				{
				$('#tblTimeLog tbody').remove();
				document.getElementById('data').style.visibility = 'visible';
				}
			else
				{
				$('#tblTimeLog tbody').remove();
				alert(rponse);
				}
			
			}
	}