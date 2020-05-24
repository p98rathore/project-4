<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.clt.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.clt.ChangePasswordCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.clt.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<title>Change Password</title>
</head>
<body>
<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>
		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">Change
				Password</h1>
			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

<table align="center">

  <tr>
  <th align="left"><label >Old Password<span style="color: red">*</span></label></th>
  <td align="center"><input type="password" placeholder="Enter Old Password." name="opass" value="<%= ServletUtility.getParameter("opass", request)%>">
  </td>
  <td style="position: fixed;"><font color="red"><%= ServletUtility.getErrorMessage("opass", request) %> </font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 5px"></th>
  <td></td>
  </tr>
  <tr>
  <tr>
  <th align="left"><label >New Password<span style="color: red">*</span></label></th>
  <td align="center"><input type="password" placeholder="Enter New Password." name="npass" value="<%= ServletUtility.getParameter("npass", request)%>">
  </td>
  <td style="position: fixed;"><font color="red"><%= ServletUtility.getErrorMessage("npass", request) %></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 5px"></th>
  <td></td>
  </tr>
  <tr>
  <tr>
  <th align="left"><label >Confirm Password<span style="color: red">*</span></label></th>
  <td align="center"><input type="password" placeholder="Enter Confirm Password." name="cpass" value="<%= ServletUtility.getParameter("cpass", request)%>">
  </td>
  <td style="position: fixed;"><font color="red"><%= ServletUtility.getErrorMessage("cpass", request) %></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 5px"></th>
  <td></td>
  </tr>
  <tr>
   
   <tr align="center">
   <th></th>
   <td colspan="2">
   <input type="submit" value=<%=BaseCtl.OP_SAVE%> name="operation">
   <input type="submit" value="Change My Profile" name="operation">
   
   </td>
   </tr>


</table>
</form>
</body>
<%@ include file="Footer.jsp" %>
</html>