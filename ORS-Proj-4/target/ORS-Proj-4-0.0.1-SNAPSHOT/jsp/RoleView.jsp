<%@page import="in.co.sunrays.proj4.clt.RoleCtl"%>
<%@page import="in.co.sunrays.proj4.clt.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<title>Role View</title>
</head>
<body>
 <%@include file="Header.jsp"%>
  <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.RoleBean"
            scope="request"></jsp:useBean>
         
   <form action="<%= ORSView.ROLE_CTL%>" method="post">

  <input type="hidden" name="id" value="<%=bean.getId()%>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
  <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
  <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
 
  <div align="center">

			<h1 align="center" style="margin-bottom: -5; color: navy">
			<%
            if (bean != null && bean.getId() > 0) 
            {%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Update Role</font></th>
            </tr>
            <%}else{%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Add Role</font></th>
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
 
  
<table>


  <tr>
  <th align="left"><label>Name<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Name." name="role"  style="width: 173px;resize: none;"value="<%=DataUtility.getStringData(bean.getName())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("role", request)%></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr> 
  
  <tr>
  <th align="left"><label>Description<span style="color: red">*</span></label></th>
  <td ><textarea placeholder="Enter Description." rows="3" style="width: 173px;resize: none;" name="desc">
  <%if(bean!=null && bean.getId()>0){ %>
  <%=DataUtility.getStringData(bean.getDescription())%><%}%></textarea>
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("desc", request)%></font>
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
    <input type="submit" value="<%=RoleCtl.OP_UPDATE %>" name="operation">
    <input type="submit" value="<%=RoleCtl.OP_CANCEL %>" name="operation"> 
     <%}else{%>
     <input type="submit" value="<%=RoleCtl.OP_SAVE %>" name="operation">
     <input type="submit" value="<%=RoleCtl.OP_RESET %>" name="operation">
     <%}%>   
   </td>
   </tr>
  
  
  
</table>
</form>
</body>
<%@ include file="Footer.jsp" %>
</html>