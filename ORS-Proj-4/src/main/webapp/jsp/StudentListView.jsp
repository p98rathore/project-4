<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ListIterator"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.bean.StudentBean"%>
<%@page import="in.co.sunrays.proj4.clt.StudentListCtl"%>
<%@page import="in.co.sunrays.proj4.clt.CollegeListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student List</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.StudentBean"
			scope="request"></jsp:useBean>
<%@include file="Header.jsp"%>
    <center>
        <h1>Student List</h1>

<div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<h3>
<font style="font: bold;" color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
</div>

<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">

 <%
         int pageNo = ServletUtility.getPageNo(request);
         int pageSize = ServletUtility.getPageSize(request);
         int index = ((pageNo - 1) * pageSize) + 1;
         int nextPageSize = DataUtility.getInt
        		 (request.getAttribute("nextListSize").toString());

         List list = ServletUtility.getList(request);
         ListIterator<StudentBean> it = list.listIterator();

         if(list.size()!=0){
      %>
    
   <table width="100%">
      
      <tr>
      <td align="center"><label> FirstName :</label> 
      <input type="text" placeholder="Enter First Name" name="firstName" value="<%=ServletUtility.getParameter("firstName", request)%>">
      <label>LastName :</label> 
      <input type="text" placeholder="Enter Last Name" name="lastName" value="<%=ServletUtility.getParameter("lastName", request)%>">
      <label>Email_Id :</label> 
      <input type="text" name="email" placeholder="Enter Email Id" value="<%=ServletUtility.getParameter("email", request)%>">
      <input type="submit"  name="operation" value="<%=StudentListCtl.OP_SEARCH %>">
      <input type="submit" value="<%= StudentListCtl.OP_RESET %>" name="operation"></td>
      </tr>
      </table>
      <br>
      
   <table border="1" width="100%" align="center" cellpadding=2px cellspacing=".2">
      <tr>
      <th width="10%"><input type="checkbox" id="select_all"
						name="Select">Select All</th>
      <th>S.NO</th>
      <th>College.</th>
      <th>First Name.</th>
      <th>Last Name.</th>
      <th>Date Of Birth.</th>
      <th>Mobile No.</th>
      <th>Email ID.</th>
      <th>Edit</th>
      </tr>
     
     <%
       while (it.hasNext()) 
       {
        StudentBean bean2 = it.next();
     %>
      <tr>
      <td align="center"><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean2.getId()%>"></td>
     <td><%=index++%></td>
      <td><%=bean2.getCollegeName()%></td>
      <td><%=bean2.getFirstName()%></td>
      <td><%=bean2.getLastName()%></td>
      <%
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
       String date = format.format(bean2.getDob());
      %>
      <td><%=date%></td>
      <td><%=bean2.getMobileNo()%></td>
      <td><%=bean2.getEmail()%></td>
      <td><a href="StudentCtl?id=<%=bean2.getId()%>">Edit</a></td>
      </tr>
      <%
      }
      %>
   </table>
   
   <table width="100%">
      <tr>
      <td align="left"><input type="submit" name="operation" value="<%=StudentListCtl.OP_PREVIOUS%>" 
       <%=pageNo > 1 ? "" : "disabled"%>></td>
      <td><input type="submit" name="operation" value="<%=StudentListCtl.OP_DELETE%>"></td>
      <td><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>"></td>
      <td align="right"><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEXT%>"
      <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
   </tr>
   </table>
      <%
       }if(list.size()==0){
      %>
      <input type="submit" name="operation" value="<%= StudentListCtl.OP_BACK%>">
      <%}%>
      <input type="hidden" name="pageNo" value="<%=pageNo%>">
      <input type="hidden" name="pageSize" value="<%=pageSize%>">


        </form>
    </center>
</body>
<br><br><br>
<%@ include file="Footer.jsp" %>
</html>