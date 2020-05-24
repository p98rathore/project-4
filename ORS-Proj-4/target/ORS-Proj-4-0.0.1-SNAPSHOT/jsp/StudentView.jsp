<%@page import="in.co.sunrays.proj4.bean.CollegeBean"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.clt.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.clt.StudentCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student View</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/src/main/webapp/img/dp.jpg" sizes="16x16" />
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>jQuery UI Datepicker - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() {
    /* $( "#datepicker" ).datepicker(); */
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
<script type="text/javascript" src="/js/Calender.js"></script>
    <%@include file="Header.jsp"%>
  <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.StudentBean"
            scope="request"></jsp:useBean>
 
 <%
 String op = DataUtility.getString(request.getParameter("operation"));
 long id= DataUtility.getLong(request.getParameter("id"));
List l = (List) request.getAttribute("collegeList");
 List l2 = (List) request.getAttribute("subjectL");
 %>
 
 <div align="center">

			<h1 align="center" style="margin-bottom: -5; color: navy">
			<%
            if (bean != null && bean.getId() > 0) 
            {%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Update Student</font></th>
            </tr>
            <%}else{%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Add Student</font></th>
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
  

 <form action=<%=ORSView.STUDENT_CTL %> method="post">

<table align="center" >
 <input type="hidden" name="id" value="<%=bean.getId()%>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
  <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
  <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
  <tr>
  <th align="left"><label>First Name<span style="color: red">*</span></label></th>
  <td ><input type="text" name="fname"placeholder="Enter First Name."  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getFirstName())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("fname", request)%></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  <tr>
  <th align="left"><label>Last Name<span style="color: red">*</span></label></th>
  <td ><input type="text" name="lname"placeholder="Enter Last Name."  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getLastName())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lname", request)%></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  
  <tr>
  <th align="left" style="position: fixed;"><label>College<span style="color: red">*</span></label></th>
  <td><%=HTMLUtility.getList("college", String.valueOf(bean.getCollegeID()),l)%></td>
  <td style="position: fixed;"><font color="red"> <%= ServletUtility.getErrorMessage("college", request) %></font>
  </td>
  </tr>
  
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  
  <tr>
   <th align="left">Date Of Birth<span style="color: red">*</span></th>
   <td><input type="text" readonly="readonly" id="datepicker" placeholder="Enter Date"  style="width: 173px;resize: none;" name="dob" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
   <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
  </td>
   </tr>
   <tr>
   <th style="padding: 3px"></th>
   <td></td>
   </tr>
<tr>
  <th align="left"><label>Mobile<span style="color: red">*</span></label></th>
  <td ><input type="text" maxlength=10 placeholder="Enter Mobile No." name="mobile"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
<tr>
  <th align="left"><label>Email Id<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Email Id." name="email"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getEmail())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
    
   <tr align="center">
   <th></th>
   <td colspan="2">
    <%if (bean.getId() >0 && bean!=null) 
    	
    {%> &emsp;
    <input type="submit" value="<%=StudentCtl.OP_UPDATE %>" name="operation">
    <input type="submit" value="<%=StudentCtl.OP_CANCEL %>" name="operation"> 
     <% System.out.print("id student ki"+ bean.getId());} else { System.out.print("id student ki"+ bean.getId());%>
     <input type="submit" value="<%=StudentCtl.OP_SAVE %>" name="operation">
     <input type="submit" value="<%=StudentCtl.OP_RESET %>" name="operation">
     <%}%>   
   </td>
   </tr>
   
     
</table>
</div>
  </form>
  </body>
  
<%@ include file="Footer.jsp" %>
</html>