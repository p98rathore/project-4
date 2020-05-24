<%@page import="java.util.ListIterator"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.clt.SubjectListCtl"%>
<%@page import="in.co.sunrays.proj4.bean.SubjectBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.clt.SubjectCtl"%>
<%@page import="in.co.sunrays.proj4.clt.CourseListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Subject List</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>


<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
<%@include file="Header.jsp"%>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.SubjectBean"
		scope="request"></jsp:useBean>
   <center>
        <h1>Subject List</h1>
        <div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<h3>
<font style="font: bold;" color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
</div>

<%
List list = (List)request.getAttribute("courseList");
List list2 = (List)request.getAttribute("subjectList");
%>

<form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">
 
   <%
         int pageNo = ServletUtility.getPageNo(request);
         int pageSize = ServletUtility.getPageSize(request);
         int index = ((pageNo - 1) * pageSize) + 1;
         int nextPageSize = DataUtility.getInt
        		 (request.getAttribute("nextListSize").toString());

         List list1 = ServletUtility.getList(request);
         ListIterator<SubjectBean> it = list1.listIterator();

        if(list1.size()!=0){
      %>
     
   <table width="100%">
      <tr align="center">
      <td>
      <label> CourseName :</label>
      <%= HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), list) %>
      <font color="red"><%=ServletUtility.getErrorMessage("courseId", request) %></font>
      
      <label> SubjectName :</label>
      <%= HTMLUtility.getList("subjectId", String.valueOf(bean.getId()), list2) %>
      <font color="red"><%=ServletUtility.getErrorMessage("subjectId", request) %></font>
      <input type="submit" name="operation" value="<%=SubjectListCtl.OP_SEARCH %>">
      <input type="submit" value="<%= SubjectListCtl.OP_RESET %>" name="operation">
      </td>
      </tr>
      </table>
     
      
      <br>
      
   <table border="1" width="100%" align="center" cellpadding=2px cellspacing=".2">
      <tr>
      <th width="10%"><input type="checkbox" id="select_all"
						name="Select">Select All</th>
      <th>S.NO</th>
      <th>Course Name.</th>
      <th>Subject Name.</th>
      <th>Description.</th>
      <th>Edit</th>
      </tr>
      <tr>
      <%
      while (it.hasNext()) 
      {
     	 SubjectBean bean1 = it.next();
      %>
      <td align="center"><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean1.getId()%>"></td>
      <td><%=index++%></td>
      <td><%=bean1.getCourseName()%></td>
      <td><%=bean1.getName()%></td>
      <td><%=bean1.getDescription()%></td>
      <td><a href="SubjectCtl?id=<%=bean1.getId()%>">Edit</a></td>
      </tr>
      <%
      }
      %>
   </table>
   
   <table width="100%">
     <tr>
      <td align="left"><input type="submit" name="operation" value="<%=SubjectListCtl.OP_PREVIOUS%>" 
       <%=pageNo > 1 ? "" : "disabled"%>></td>
      <td><input type="submit" name="operation" value="<%=SubjectListCtl.OP_DELETE%>"></td>
      <td><input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEW%>"></td>
      <td align="right"><input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEXT%>"
      <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
   </tr>
   </table>
   
   <%}
     if(list1.size()==0){
     %>
     <input type="submit" name="operation" value="<%= SubjectListCtl.OP_BACK%>">
     <%}%>
      
      <input type="hidden" name="pageNo" value="<%= pageNo%>">
      <input type="hidden" name="pageSize" value="<%= pageSize%>">


        </form>
    </center>
</body>
<br><br><br>
<%@ include file="Footer.jsp" %>
</html>