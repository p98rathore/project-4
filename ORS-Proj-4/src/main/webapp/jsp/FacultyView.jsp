<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.clt.FacultyCtl"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty View</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>


<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    //$( "#datepicker" ).datepicker();
	  /* $( "#datepicker" ).datepicker({dateFormat:'dd/mm/yy'}).val(); */
	  $( "#datepicker" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'1980:2030',
		  dateFormat:'dd/mm/yy'
		  
	  });
  } );
  </script>
</head>
<body>
  <%@ include file="Header.jsp"%>
     
 <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.FacultyBean"
            scope="request"></jsp:useBean>
<form action="<%=ORSView.FACULTY_CTL %>" method="post">
<% List list = (List)request.getAttribute("collegeList") ;
List slist =(List) request.getAttribute("subjectList");
List clist = (List)request.getAttribute("courseList");
%>
   <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>"> 
           
   
			<h1 align="center" style="margin-bottom: -5; color: navy">
			<%
            if (bean != null && bean.getId() > 0) 
            {%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Update Faculty</font></th>
            </tr>
            <%}else{%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Add Faculty</font></th>
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
  
    <table align="center">

  <tr>
  <th align="left">First Name<span style="color: red">*</span></th>
  <td><input type="text" placeholder="Enter First Name" name="firstName" style="width: 173px;resize: none;"  value="<%= DataUtility.getStringData(bean.getFirstName())%>">
  </td>
  <td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font>
  </td>
  </tr>
  
  
  <tr>
  <th align="left"><label for="lname">Last Name<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Last Name" name="lastName" style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getLastName())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
  </td>
  </tr>
 

   <tr>
	  <th align="left">Gender<span style="color: red">*</span></th>
	  
	  <%
	  HashMap<String, String> map = new HashMap<String, String>();
	  map.put("Female", "Female");
	  map.put("Male", "Male");

	  String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
	  %> <td ><%=htmlList%>
     </td>
	 <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
	 </tr>


  <tr>
  <th align="left"><label for="Login">Login Id<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Login Id" name="email" style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getEmail())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
  </td>
  </tr>
  
  <tr>
  <th style="padding: 1px"></th>
  <td></td>
  </tr>
  
   
   <tr>
   <th align="left">Date Of Birth<span style="color: red">*</span></th>
   <td><input type="text" readonly="readonly" id="datepicker"  style="width: 173px;resize: none;"placeholder="Enter Date" name="dob" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
   <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
   </td>
   </tr>
 
   
   <tr>
   <th align="left"><label>Qualification<span style="color: red">*</span></label></th>
   <td ><input type="text" placeholder="Enter Qualification"  name="qualification" style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getQualification())%>">
   </td>
   <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("qualification", request)%></font>
   </td>
   </tr>   
      
   
   <tr>
   <th align="left"><label>Mobile<span style="color: red">*</span></label></th>
   <td ><input type="text" maxlength=10 placeholder="Enter Mobile No."  name="mobile" style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
   </td>
   <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font>
   </td>
   </tr>   
      
   
   <tr>
	<th align="left">College Name<span style="color: red">*</span></th>
	<td ><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), list)%></td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
	</tr>
	
	<tr>
   <tr>
	<th align="left">Course<span style="color: red">*</span></th>
	<td ><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), clist)%></td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
	</tr>
	
	<tr>
	<th align="left">Subject<span style="color: red">*</span></th>
	<td ><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), slist)%></td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>
	</tr>
    
   <tr>
   <th style="padding: 1px"></th>
   <td></td>
   </tr>
  
   
 <tr align="center">
 <th></th>
 <td colspan="2">
    <%
    if(bean.getId()>0){
    %>
    <input type="submit" value="Update" name="operation">
    <input type="submit" value="<%=FacultyCtl.OP_CANCEL %>" name="operation">
    <%}else {%>
   <input type="submit" value="<%= FacultyCtl.OP_SAVE %>" name="operation">
   <input type="submit" value="<%= FacultyCtl.OP_RESET %>" name="operation">
   <%}%>
     </td>
   </tr>
     
</table>
  </form>
  

</body>
<!-- <br><br><br> -->
<%@ include file="Footer.jsp" %>
</html>