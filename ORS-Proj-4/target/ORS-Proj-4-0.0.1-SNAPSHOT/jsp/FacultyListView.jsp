<%@page import="java.util.ListIterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.sunrays.proj4.clt.FacultyListCtl"%>
<%@page import="in.co.sunrays.proj4.clt.FacultyCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.bean.FacultyBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Faculty List</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>



<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
<%@include file="Header.jsp"%>
<hr>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.FacultyBean"
            scope="request"></jsp:useBean>
 <center>          
         <h1>Faculty List</h1>
<div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<h3>
<font style="font: bold;" color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
</div>

<form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">
    
    <%
         int pageNo = ServletUtility.getPageNo(request);
         int pageSize = ServletUtility.getPageSize(request);
         int index = ((pageNo - 1) * pageSize) + 1;
         int nextPageSize = DataUtility.getInt
        		 (request.getAttribute("nextListSize").toString());

         List list1 = ServletUtility.getList(request);
         ListIterator<FacultyBean> it = list1.listIterator();

        if(list1.size()!=0){
      %>
    
   <table width="100%">
      <tr>
      <td align="center"><label> FirstName :</label> 
      <input type="text" placeholder="Enter First Name" name="firstName" value="<%=ServletUtility.getParameter("firstName", request)%>">
      <label>LastName :</label> 
      <input type="text" placeholder="Enter Last Name" name="lastName" value="<%=ServletUtility.getParameter("lastName", request)%>">
      <label>Login_Id :</label> 
      <input type="text" placeholder="Enter Login Id" name="email" value="<%=ServletUtility.getParameter("email", request)%>">
      <input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH %>">
      <input type="submit" value="<%= FacultyListCtl.OP_RESET %>" name="operation"></td>
      </tr>
      </table>
     
      
      <br>
      
  <table border="1" width="100%" align="center" cellpadding=2px cellspacing=".2">
      <tr>
      <th width="10%"><input type="checkbox" id="select_all"
						name="Select">Select All</th>
      <th>S.NO</th>
      <th>First Name.</th>
      <th>Last Name.</th>
      <th>Login Id.</th>
      <th>Qualification.</th>
      <th>Mobile No.</th>
      <th>Data Of Birth.</th>
      <th>College Name.</th>
      <th>Subject Name.</th>
      <th>Course Name.</th>
      <th>Edit</th>
      </tr>
      <%
      while (it.hasNext()) 
      {
     	 FacultyBean bean1 = it.next();
      %>
      <tr>
      <td align="center"><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean1.getId()%>"></td>
      <td><%=index++%></td>
      <td><%=bean1.getFirstName()%></td>
      <td><%=bean1.getLastName()%></td>
      <td><%=bean1.getEmail()%></td>
      <td><%=bean1.getQualification()%></td>
      <td><%=bean1.getMobileNo()%></td>
      <%
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
       String date = format.format(bean1.getDob());
      %>
      <td><%=date%></td>
      <td><%=bean1.getCollegeName()%></td>
      <td><%=bean1.getSubjectName()%></td>
      <td><%=bean1.getCourseName()%></td>
      <td><a href="FacultyCtl?id=<%=bean1.getId()%>">Edit</a></td>
      </tr>
      <%
      }
      %>
   </table>
   
   <table width="100%">
      <tr>
      <td align="left"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_PREVIOUS%>" 
       <%=pageNo > 1 ? "" : "disabled"%>></td>
      <td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
      <td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></td>
      <td align="right"><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEXT%>"
      <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
   </tr>
   </table>
      
      <%
        } if(list1.size()==0){
      %>
      <input type="submit" name="operation" value="<%= FacultyListCtl.OP_BACK%>">
      <%}%>
      
      <input type="hidden" name="pageNo" value="<%= pageNo%>">
      <input type="hidden" name="pageSize" value="<%= pageSize%>">


        </form>
    </center>
</body>
<br><br><br>
<%@ include file="Footer.jsp" %>
</html>