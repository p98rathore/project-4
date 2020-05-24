<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.bean.CollegeBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.clt.CollegeListCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>College List</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>


<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
</head>
<body>
 <%@include file="Header.jsp"%>
   <center>
   <h1>College List</h1>
<div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>

<div style="height: 10px; margin-bottom: 12px">
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div>
</div>

   <form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST">

    <%
     int pageNo = ServletUtility.getPageNo(request);
     int pageSize = ServletUtility.getPageSize(request);
     int index = ((pageNo - 1) * pageSize) + 1;
     int nextPageSize = DataUtility.getInt
    		 (request.getAttribute("nextListSize").toString());

     List list = ServletUtility.getList(request);
     Iterator<CollegeBean> it = list.iterator();

     if(list.size()!=0){
     %>

   <table width="100%">
   
   <tr>
   <td align="center"><label> Name :</label> 
   <input type="text" placeholder="Enter Name" name="name" value="<%=ServletUtility.getParameter("name", request)%>">
   <label>City :</label> 
   <input type="text" placeholder="Enter City" name="city" value="<%=ServletUtility.getParameter("city", request)%>">
   <input type="submit" name="operation" value="<%=CollegeListCtl.OP_SEARCH%>">
   <input type="submit" value="<%= CollegeListCtl.OP_RESET %>" name="operation"></td>
   </tr> 
 </table>
 
   <br>
   
   <table border="1" width="100%" align="center" cellpadding=2px cellspacing=".2">
   <tr>
      <th width="10%"><input type="checkbox" id="select_all"
						name="Select">Select All</th>
      <th>S.No.</th>
      <th>Name.</th>
      <th>Address.</th>
      <th>State.</th>
      <th>City.</th>
      <th>PhoneNo.</th>
      <th>Edit</th>
   </tr>
   <%
   while (it.hasNext()) 
   {
      CollegeBean bean = it.next();
   %>
     <tr>
       <td align="center"><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
       <td><%=index++%></td>
       <td><%=bean.getName()%></td>
       <td><%=bean.getAddress()%></td>
       <td><%=bean.getState()%></td>
       <td><%=bean.getCity()%></td>
       <td><%=bean.getPhoneNo()%></td>
       <td><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
     </tr>
     <%}%>
     
     </table>
      <table width="100%">
       <tr>
      <td align="left"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>" 
       <%=pageNo > 1 ? "" : "disabled"%>></td>
      <td><input type="submit" name="operation" value="<%=CollegeListCtl.OP_DELETE%>"></td>
      <td><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEW%>"></td>
      <td align="right"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEXT%>"
      <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
   </tr>
      </table>
      
      <%
      } if(list.size()==0){
      %>
      <input type="submit" name="operation" value="<%= CollegeListCtl.OP_BACK%>">
      <%}%>
      
        <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
        <input type="hidden" name="pageSize" value="<%=pageSize%>">
        
     </form>
  </center>
</body>
<br><br><br>
<%@ include file="Footer.jsp" %>
</html>