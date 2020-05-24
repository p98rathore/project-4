<%@page import="java.text.SimpleDateFormat"%>
<%@page import="in.co.sunrays.proj4.model.RoleModel"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.ListIterator"%>
<%@page import="in.co.sunrays.proj4.clt.UserListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>	
</head>
<body>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
            scope="request"></jsp:useBean>
<%@include file="Header.jsp"%>
    <center>
        <h1>User List</h1>

<div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<h3>
<font style="font: bold;" color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
</div>

<%
List l = (List) request.getAttribute("rolelist");
%>
<form action="<%=ORSView.USER_LIST_CTL%>" method="post">

 <%
         int pageNo = ServletUtility.getPageNo(request);
         int pageSize = ServletUtility.getPageSize(request);
         int index = ((pageNo - 1) * pageSize) + 1;
         int nextPageSize = DataUtility.getInt
        		 (request.getAttribute("nextListSize").toString());

         List list = ServletUtility.getList(request);
         ListIterator<UserBean> it = list.listIterator();

         if(list.size()!=0){
      %>

       <table width="100%">
      <tr>
      <td align="center"><label> FirstName :</label> 
      <input type="text" placeholder="Enter First Name" name="firstName" value="<%=ServletUtility.getParameter("firstName", request)%>">
     
      <label>Role :</label> 
      <%=HTMLUtility.getList("rolelist", String.valueOf(bean.getRoleId()), l)%>
      
      <label>Login_Id :</label> 
      <input type="text" placeholder="Enter Login Id" name="login" value="<%=ServletUtility.getParameter("login", request)%>">
      <input type="submit" name="operation" value="<%=UserListCtl.OP_SEARCH %>">
      <input type="submit" value="<%= UserListCtl.OP_RESET %>" name="operation"></td>
      </tr>
      </table>
      <br>
      
   <table border="1" width="100%" align="center" cellpadding=2px cellspacing=".2">
      <tr>
      <th width="10%"><input type="checkbox" id="select_all" name="Select">
      Select All</th>
      <th width="2%">S.No.</th>
      <th width="8%">First Name.</th>
      <th>Last Name.</th>
      <th>Role.</th>
      <th width="15%">Login</th>
      <th>Gender</th>
      <th>Date Of Birth</th>
      <th>Mobile No</th>
      <th>Edit</th>
      </tr>
   <%
   while (it.hasNext()) 
   {
  	 UserBean bean2 = it.next();
  	 RoleModel model = new RoleModel();
  	 RoleBean rolebean = new RoleBean();
  	 rolebean= model.findByPK(bean2.getRoleId());
   %>
      <tr>
      <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=bean2.getId()%>"
      <% if(userbean.getId()==bean2.getId() || bean2.getRoleId()==RoleBean.ADMIN){%>
      <%= "disabled"%> <%}%> ></td>
      <td><%=index++%></td>
      <td><%=bean2.getFirstName()%></td>
      <td><%=bean2.getLastName()%></td> 
      <td><%=rolebean.getName()%></td>
      <td><%=bean2.getLogin()%></td>
      <td><%=bean2.getGender()%></td>
      <%
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
       String date = format.format(bean2.getDob());
      %>
      <td><%=date%></td>
      <td><%=bean2.getMobileNo()%></td>
      <td><a href="UserCtl?id=<%=bean2.getId()%>" 
      <% if(userbean.getId()==bean2.getId() || bean2.getRoleId()==RoleBean.ADMIN){%>
      onclick="return false;"<%}%>>Edit</a> </td>
      </tr>
      <%
   
      }
      %>
   </table>
   
   <table width="100%">
      <tr>
      <td align="left" ><input type="submit" name="operation" value="<%=UserListCtl.OP_PREVIOUS%>" 
       <%=pageNo > 1 ? "" : "disabled"%>></td>
      <td><input type="submit" name="operation" value="<%=UserListCtl.OP_DELETE%>"></td>
      <td><input type="submit" name="operation" value="<%=UserListCtl.OP_NEW%>"></td>
      <td align="right"><input type="submit" name="operation" value="<%=UserListCtl.OP_NEXT%>"
      <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
   </tr>
   </table>
      
      <%}
        if(list.size()==0){  
       %>
       <input type="submit" name="operation" value="<%= UserListCtl.OP_BACK%>">
       <%} %>
      
      <input type="hidden" name="pageNo" value="<%=pageNo%>">
      <input type="hidden" name="pageSize" value="<%=pageSize%>">


        </form>
    </center>
</body>
<br><br><br>
<%@ include file="Footer.jsp" %>

</html>