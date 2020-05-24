<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.sunrays.proj4.clt.TimetableCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time-Table</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>


<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  
  var d = new Date();
  function disableSunday(d){
	  var day = d.getDay();
	  if(day==0)
	  {
	   return [false];
	  }else
	  {
		  return [true];
	  }
  }
  
  $( function() {
	  $( "#datepicker" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'1980:2030',
		  dateFormat:'dd/mm/yy',
		   minDate : 0 ,
		  
		  beforeShowDay : disableSunday	  ,
	  });
  } );
  </script>
</head>
<body>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.TimetableBean"
			scope="request"></jsp:useBean>
<%@ include file="Header.jsp"%>

<% List courseList=(List) request.getAttribute("courseList") ;
List subjectList=(List) request.getAttribute("subjectList") ;
  
%>
		
<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
<div align="center">

			<h1 align="center" style="margin-bottom: -5; color: navy">
			<%
            if (bean != null && bean.getId() > 0) 
            {%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Update TimeTable</font></th>
            </tr>
            <%}else{%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Add TimeTable</font></th>
            </tr>
            <%}%>
			</h1>

			<div style="height: 10px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</font>
				</H3>
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div> 


<table >
	<tr>
	<th align="left">Course<span style="color: red">*</span></th>
	<td ><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%></td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
	</tr>
	<tr>
	
	<th align="left">Subject<span style="color: red">*</span></th>
	<td ><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubId()), subjectList)%></td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>
	</tr>
	
	<tr>
	<th align="left">Semester<span style="color: red">*</span></th>
	<td>
	<%
	LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	map.put("1", "1");
	map.put("2", "2");
	map.put("3", "3");
	map.put("4", "4");
	map.put("5", "5");
	map.put("6", "6");
	map.put("7", "7");
	map.put("8", "8");
	map.put("9", "9");
	map.put("10", "10");

	String htmlList = HTMLUtility.getList("semester", bean.getSemester(), map);
	%> <%=htmlList%>
	</td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("semester", request)%></font></td>
	</tr>
	
  <tr>
   <th align="left">Exam Date<span style="color: red">*</span></th>
   <td><input type="text" readonly="readonly" id="datepicker" placeholder="Enter Date" name="examDate"  style="width: 173px;resize: none;"value="<%=DataUtility.getDateString(bean.getExamDate())%>"></td>
   <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("examDate", request)%></font>
  </td>
   </tr>
   <tr>
   <th style="padding: 3px"></th>
   <td></td>
   </tr>
  
  <tr>
  <th align="left">Exam Time<span style="color: red">*</span></th>
   <td >
	<%
	LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
	map1.put("08:00 AM to 11:00 AM", "08:00 AM to 11:00 AM");
	map1.put("12:00 PM to 03:00 PM", "12:00 PM to 03:00 PM");
	map1.put("04:00 PM to 07:00 PM", "04:00 PM to 07:00 PM");

	String htmlList1 = HTMLUtility.getList("examTime", bean.getExamTime(), map1);
	%> <%=htmlList1%>
	</td>
	
    <td style="position: fixed;"><td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("examTime", request)%></font></td>
	</tr>

 <tr>
  <th align="left"><label>Description<span style="color: red">*</span></label></th>
  <td ><textarea rows="3" placeholder="Enter Description." style="width: 173px;resize: none;" name="description">
   <%if(bean!=null && bean.getId()>0){ %>
  <%=DataUtility.getStringData(bean.getDescription())%><%} %>  </textarea>
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>

     
	 <tr>
		<th></th>
		<td></td>
	 </tr>
	 
	<tr align="center">
	 <th></th>
	 <%
	 if (bean != null && bean.getId() > 0) {
	 %>
		<td colspan="2">
		<input type="submit" name="operation" value="<%=TimetableCtl.OP_UPDATE%>"> 
		<input type="submit" name="operation" value="<%=TimetableCtl.OP_CANCEL%>">
	 </td>
	 <%} else{%>
	 <td colspan="2">
	 <input type="submit" name="operation" value="<%=TimetableCtl.OP_SAVE%>"> 
	 <input type="submit" name="operation" value="<%=TimetableCtl.OP_RESET%>">
	 </td>
	 <%}%>
				</tr>
			</table>
		</div>
	</form>
</body>
	<%@ include file="Footer.jsp"%>

</html>