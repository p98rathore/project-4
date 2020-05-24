<%@page import="in.co.sunrays.proj4.clt.MarksheetCtl"%>
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
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<title> Marksheet View</title>
</head>
<body>

        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.MarksheetBean"
            scope="request"></jsp:useBean>
<%
 System.out.println("bean id is  on view "+bean.getId());
  List l = (List)  request.getAttribute("studentList");
  
%>

<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">  

<%-- hidden feilds always should be inside of Form --%>
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
            <th align="left" style="padding: 7px"><font size="5px">Update MarkSheet</font></th>
            </tr>
            <%}else{%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Add MarkSheet</font></th>
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
  

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

           <table align="center">
<tr>
<%
if (bean.getId() >0 && bean!=null) 
{
%>
<th align="left"><label>Role No<span style="color: red">*</span></label></th>
  <td ><input type="text" readonly="readonly" placeholder="Ex:- EC1020."  style="width: 173px;resize: none;" name="rollNo" value="<%=DataUtility.getStringData(bean.getRollNo())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font>
</td>
<%}else{%>
<th align="left"><label>Roll No<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Ex:- EC1020." maxlength="6" name="rollNo"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getRollNo())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font>
</td>
<%}%>
</tr>  
  
  
  
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  
  <tr>
  <th align="left"><label>Name<span style="color: red">*</span></label></th>
  <td ><%=HTMLUtility.getList("studentId", String.valueOf(bean.getStudentId()),l)%>
  </td>
  <td style="position: fixed;"><font color="red"> <%= ServletUtility.getErrorMessage("studentId", request) %></font>
  </td>
  </tr>
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  <tr>
  <th align="left"><label>Physics<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Physics Marks." name="physics"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getPhysics()).equals("0")?"":DataUtility.getStringData(bean.getPhysics())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font>
  </td>
  </tr> 
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  <tr>
  <th align="left"><label>Chemistry<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Chemistry Marks." name="chemistry"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getChemistry()).equals("0")?"":DataUtility.getStringData(bean.getChemistry())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font>
  </td>
  </tr>
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  
  <tr>
  <th align="left"><label>Maths<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Maths Marks." name="maths"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getMaths()).equals("0")?"":DataUtility.getStringData(bean.getMaths())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font>
  </td>
  </tr>
  
  <tr>
   <th style="padding: 3px"></th>
   <td></td>
   </tr>
    s
   <tr align="center">
   <th></th>
   <td colspan="2">
    <%if (bean.getId() >0 && bean!=null) 
    {
    System.out.println("id is in side if "+bean.getId());
    %> &emsp;
    <input type="submit" value="<%=MarksheetCtl.OP_UPDATE %>" name="operation">
    <input type="submit" value="<%=MarksheetCtl.OP_CANCEL %>" name="operation">
    <%
     }else{%>
     <input type="submit" value="<%=MarksheetCtl.OP_SAVE %>" name="operation">
     <input type="submit" value="<%=MarksheetCtl.OP_RESET %>" name="operation">
     <%}%>   
   </td>
   </tr>


</table>
</form>
</body>
<%@ include file="Footer.jsp" %>
</html>