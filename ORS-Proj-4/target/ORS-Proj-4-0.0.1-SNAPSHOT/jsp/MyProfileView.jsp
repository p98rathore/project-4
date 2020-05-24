<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.clt.MyProfileCtl"%>
<%@page import="in.co.sunrays.proj4.clt.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>
<title>MY Profile</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>My Profile</title>
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
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
            scope="request"></jsp:useBean>
<%@ include file="Header.jsp"%>
<form action="<%= ORSView.MY_PROFILE_CTL%>" method="post">
 <input type="hidden" name="id" value="<%=bean.getId()%>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
  <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
  <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
 
             <div align="center">

			<h1 align="center" style="margin-bottom: -5; color: navy">My Profile</h1>

			<div style="height: 10px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage("error1", request)%></font>
					</font>
				</H3>
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
  <th align="left"><label >Login Id<span style="color: red">*</span></label></th>
  <td ><input type="text" readonly="readonly" name="login"  style="width: 173px;resize: none;" value="<%= DataUtility.getString(bean.getLogin()) %>">
  </td>
  <td style="position: fixed;"><font color="red"> <%= ServletUtility.getErrorMessage("login", request) %></font>
  </td>
  </tr> 
  <tr>
                              <tr>
<th style="padding: 5px"></th>
<td></td>
</tr>
<tr>
  <th align="left"><label >First Name<span style="color: red">*</span></label></th>
  <td ><input type="text" name="firstName"  style="width: 173px;resize: none;" value="<%= DataUtility.getString(bean.getFirstName()) %>">
  </td>
  <td style="position: fixed;"><font color="red"> <%= ServletUtility.getErrorMessage("firstName", request) %></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 5px"></th>
  <td></td>
  </tr>
  <tr>
  <th align="left"><label for="fname">Last Name<span style="color: red">*</span></label></th>
  <td ><input type="text"  name="lastName"  style="width: 173px;resize: none;" value="<%= DataUtility.getString(bean.getLastName()) %>">
  </td>
  <td style="position: fixed;"><font color="red"> <%= ServletUtility.getErrorMessage("lastName", request) %></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 5px"></th>
  <td></td>
  </tr>
    <tr>
	  <th align="left">Gender<span style="color: red">*</span></th>
	  <td>
	  <%
	  HashMap<String, String> map = new HashMap<String, String>();
	  map.put("Female", "Female");
	  map.put("Male", "Male");

	  String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
	  %> <%=htmlList%>
     </td>
	 <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
	 </tr>
  <tr>
  <th style="padding: 5px"></th>
  <td></td>
  </tr>
      <tr>
  <th align="left"><label for="fname">Mobile<span style="color: red">*</span></label></th>
  <td ><input type="text" maxlength=10 name="mobile"  style="width: 173px;resize: none;" value="<%= DataUtility.getString(bean.getMobileNo()) %>">
  </td>
  <td style="position: fixed;"><font color="red"> <%= ServletUtility.getErrorMessage("mobile", request) %></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 5px"></th>
  <td></td>
  </tr>
      <tr>
   <th align="left">Date Of Birth<span style="color: red">*</span></th>
   <td><input type="text" readonly="readonly" id="datepicker" placeholder="Enter Date" name="dob"  style="width: 173px;resize: none;" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
   <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
  </td>
   </tr>
   <tr>
   <th style="padding: 3px"></th>
   <td></td>
   </tr>
   
   <tr>
   <th></th>
   <td colspan="2">
   <input type="submit" value=<%=MyProfileCtl.OP_SAVE %> name="operation">
   <input type="submit" value=<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %> name="operation">
   </td>
   </tr>
  
</td>
</tr>
</table>
</form>


<%@ include file="Footer.jsp" %>
</body>
</html>