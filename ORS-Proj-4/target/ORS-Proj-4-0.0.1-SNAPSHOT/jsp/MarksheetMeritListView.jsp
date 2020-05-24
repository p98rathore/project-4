<%@page import="in.co.sunrays.proj4.clt.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.clt.MarksheetMeritListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.clt.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<title>Marksheet Merit List</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
</div>
<form action=<%=ORSView.MARKSHEET_MERIT_LIST_CTL%> method="post">


<h1 align="center">MarkSheet Merit List</h1>
<table id="alter" width="100%" align="center" border="1" cellpadding=2px cellspacing=".2">  
<tr>
<th>S.No</th>
<th>Roll No</th>
<th>Name</th>
<th>Physics</th>
<th>Chemistry</th>
<th>Maths</th>
<th>Total</th>
<th>Percentage</th>
</tr>  
<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;
List<MarksheetBean> list = (List<MarksheetBean>)request.getAttribute("list");
Iterator<MarksheetBean> itr = list.iterator();
while(itr.hasNext())
{
	MarksheetBean bean = (MarksheetBean)itr.next();
	long id = bean.getId();
	String roll = bean.getRollNo();
	String name = bean.getName();
	int phy = bean.getPhysics();
	int chem = bean.getChemistry();
	int math = bean.getMaths();
%>
<tr>
  <td align="center"><%= index++%></td>
  <td align="center"><%= roll%></td>
  <td align="center"><%= name%></td>
  <td align="center"><%= phy%></td>
  <td align="center"><%= chem%></td>
  <td align="center"><%= math%></td>
  <td align="center"><%=(phy+chem+math)%></td>
  <td align="center"><%=(phy+chem+math)/3+" %" %></td>
</tr>

<%}%> 
</table>

<table align="center">
<tr align="center">
 <td><input type="submit" value=<%=BaseCtl.OP_BACK%> name="op"></td>
 </tr>
</table>
<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
</form>


</body>
<%@ include file="Footer.jsp" %>

</html>