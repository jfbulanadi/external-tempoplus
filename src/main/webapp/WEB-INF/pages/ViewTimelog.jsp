<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Time Log</title>
<link href="../resources/timelog/css/jPages.css" rel="stylesheet">
	<link href="../resources/timelog/css/jquery-ui-1.10.2.custom.css" rel="stylesheet">
	<link href="../resources/timelog/css/ViewTimeLogCSS.css" rel="stylesheet">
	<link rel="stylesheet" href="../resources/timelog/css/style1.css" type="text/css" />
	<script src="../resources/timelog/js/jquery-1.9.1.js"></script>
	<script src="../resources/timelog/js/jquery-ui-1.10.2.custom.js"></script>	
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 
	<script>var my_jQuery = jQuery.noConflict();</script>
	<script src="../resources/timelog/js/jPages.js"></script>
	
	<script type="text/javascript" src="../resources/timelog/js/jquery-1.3.1.min.js"></script>
	<script>var my_jQuery3 = jQuery.noConflict();</script>
	<script type="text/javascript" src="../resources/timelog/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="../resources/timelog/js/jquery.tablesorter.pager.js"></script>

	<script src= "../resources/timelog/js/ViewTimelogJS.js"></script>
	<script type="text/javascript">
	/* var view ="";
	var user = "";
	var Employee_Id = 0; //for hr

	var id=${id}; //session empid
	$(document).ready(function() {
			$( "#from" ).datepicker({ dateFormat: 'yy-mm-dd' });
			$( "#to" ).datepicker({ dateFormat: 'yy-mm-dd' }); 
			$( "#SearchTimeLog" ).click(SearchTimeLog); 
			$( "#SearchName" ).click(SearchName); 
			var id = ${id};
	
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
						document.getElementById('others').text = "View Others";
					}
				else if(user =="manager")
					{
						document.getElementById('others').text = "MySubordinates";
					}
			}
		else
			{
			document.getElementById('others').href ="javascript:void(0)";
			}
	}

	function others()
	{
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
				Employee_Id = 0;
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
											tableStr +="<td>"+item.timeOut+"</td><td>"+item.duration+"</td>";
											tableStr +="</tr>";
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
											tableStr +="<td>"+item.timeOut+"</td><td>"+item.duration+"</td>";
											tableStr +="</tr>";
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
											tableStr +="<td>"+item.timeOut+"</td><td>"+item.duration+"</td>";
											tableStr +="</tr>";
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
				$('#tblTimeLog tbody').remove();
					alert(rponse);
				}

		}
 */


	</script>
</head>
<body onload = "mylog()">

<table id = "tblNav">
<tr>
<td>
<a href= javascript:mylog()>MyLog</a>
<a id ="others" href= javascript:others()>MySubordinates</a>
</td>
</tr>
</table>

<br><br><br><br>
<table id ="tblAll">
<tr>
<td>
<div id="SearchSub">
Subordinates: <select id ="sub"> </select>
</div>
<div id="SearchBox">
Employee Lastname/Firstname: <input type="text" id="empName" /> <input type="button" id="SearchName" value="Search">
</div>
</td>
</tr>

		<tr>
		<td>
		<div id ="HrSearch">
			<table id ="tblSearch">
			</table>
		</div>
		</td>
		</tr>
		<tr>
		<td>
		<div id = "result">
		Employee Name:<input type = "text" id ="employee" readonly="readonly"/>		
		</div>
		</td>
		</tr>
		

<tr>
<td>
<div id="Date">
	<table>
	<tr >
	<td>From: <input type="text" id="from" /> </td>
	<td>To: <input type="text" id="to" /> </td>
	<td> <input type="button" id="SearchTimeLog" value="Search"> </td>
	</tr>
	</table>
</div>	
</td>	
</tr>

<tr>
<td id ="tdTimeLogAll">
	<table id="tblTimeLog"  class="tablesorter">
	<thead>
	<th>Date</th>
	<th>Time IN</th>
	<th>Time OUT</th>
	<th>Total Hours</th>
	</thead>
	</table>
</td>
</tr>
<tr>
<td>
<div id="pager" class="pager">
	<form>
		<img src="../resources/timelog/css/images/images/first.png" class="first"/>
		<img src="../resources/timelog/css/images/images/prev.png" class="prev"/>
		<input type="text" id="tbl" class="pagedisplay"/>
		<!-- <span class="pagedisplay"></span> -->
		<img src="../resources/timelog/css/images/images/next.png" class="next"/>
		<img src="../resources/timelog/css/images/images/last.png" class="last"/>
		<select class="pagesize" id="pagesize">
		<option value="10">10 per page</option>
		<option value="20">20 per page</option>	
		<option value="30">30 per page</option>
		</select>
	</form>
</div>
</td>
</tr>

</table>
</body>
</html>