<%@page import="java.util.ListIterator"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.clt.CourseListCtl"%>
<%@page import="in.co.sunrays.proj4.clt.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course List</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>



<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
 <%@include file="Header.jsp"%>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CourseBean"
		scope="request"></jsp:useBean>
    <center>
        <h1>Course List</h1>

<div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<h3>
<font style="font: bold;" color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
</div>

<%
List CoutLi = (List)request.getAttribute("clist");
%>

<form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">
    
    <%
         int pageNo = ServletUtility.getPageNo(request);
         int pageSize = ServletUtility.getPageSize(request);
         int index = ((pageNo - 1) * pageSize) + 1;
         int nextPageSize = DataUtility.getInt
        		 (request.getAttribute("nextListSize").toString());

         List list = ServletUtility.getList(request);
         ListIterator<CourseBean> it = list.listIterator();

         if(list.size()!=0){
      %>
    
   <table width="100%">
      <tr>
	<td align="center"><label>Course</label>
	<%=HTMLUtility.getList("courseId", String.valueOf(bean.getId()), CoutLi)%>
	<input type="submit" value="<%=CourseListCtl.OP_SEARCH%>">
	<input type="submit" value="<%= CourseListCtl.OP_RESET %>" name="operation"></td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
	</tr>
	<tr>
      </table>
     
      
      <br>
      
   <table border="1" width="100%" align="center" cellpadding=2px cellspacing=".2">
      <tr>
      <th width="10%"><input type="checkbox" id="select_all"
						name="Select">Select All</th>
      <th>S.No.</th>
      <th>Course Name.</th>
      <th>Duration.</th>
      <th>Description.</th>
      <th>Edit</th>
      </tr> 
      <%
      while (it.hasNext()) 
      {
     	 CourseBean bean2 = it.next();
      %>
      <tr>
      <td align="center"><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean2.getId()%>"></td>
      <td><%=index++%></td>
      <td><%=bean2.getName()%></td>
      <td><%=bean2.getDuration()%></td>
      <td><%=bean2.getDescription()%></td>
      <td><a href="CourseCtl?id=<%=bean2.getId()%>">Edit</a></td>
      </tr>
      <%
      }
      %>
   </table>
   
   <table width="100%">
      <tr>
      <td align="left"><input type="submit" name="operation" value="<%=CourseListCtl.OP_PREVIOUS%>" 
       <%=pageNo > 1 ? "" : "disabled"%>></td>
      <td><input type="submit" name="operation" value="<%=CourseListCtl.OP_DELETE%>"></td>
      <td><input type="submit" name="operation" value="<%=CourseListCtl.OP_NEW%>"></td>
      <td align="right"><input type="submit" name="operation" value="<%=CourseListCtl.OP_NEXT%>"
      <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
   </tr>
   </table>
      
      <%
         }
         if(list.size()==0){
      %>
      <input type="submit" name="operation" value="<%= CourseListCtl.OP_BACK%>">
      <%} %>
      <input type="hidden" name="pageNo" value="<%=pageNo%>">
      <input type="hidden" name="pageSize" value="<%=pageSize%>">


        </form>
    </center>
    <br><br><br>
    </body>
  <%@ include file="Footer.jsp" %>

</html>