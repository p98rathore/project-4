<%@page import="java.util.ListIterator"%>
<%@page import="in.co.sunrays.proj4.clt.TimetableListCtl"%>
<%@page import="in.co.sunrays.proj4.clt.FacultyCtl"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.proj4.bean.TimetableBean"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Time Table List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>


<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>

var d = new Date();
function disableSunday(d){
	  var day = d.getDay();
	  if(day==0)
	  {
	   return [false];
	  }else
	  {
		  return [true];
	  }
}

$( function() {
	  $( "#datepicker" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'1980:2030',
		  dateFormat:'dd/mm/yy',
		  
		  beforeShowDay : disableSunday
		  
	  });
} );
</script>
</head>
<body>
<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.TimetableBean"
			scope="request"></jsp:useBean>
  <center>
        <h1>TimeTable List</h1>

<div align="center">
<h3>
<font style="font: bold;" color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<h3>
<font style="font: bold;" color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
</div>

<%
List cList = (List)request.getAttribute("courseList");
List sList = (List)request.getAttribute("subjectList");
%>
<form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="post">
    
     <%
         int pageNo = ServletUtility.getPageNo(request);
         int pageSize = ServletUtility.getPageSize(request);
         int index = ((pageNo - 1) * pageSize) + 1;
         /* int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString()); */
         int nextPageSize = DataUtility.getInt
        		 (request.getAttribute("nextListSize").toString());
         
         List list1 = ServletUtility.getList(request);
         ListIterator<TimetableBean> it = list1.listIterator();
         
         if(list1.size()!=0)
         {
      %>
    
   <table width="100%">
      <tr>
      <td align="center">
      <label> Course Name :</label> 
      <%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), cList) %>
      
      <label> Subject Name :</label> 
      <%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubId()), sList) %>
      
      <label>Date Of Exam</label>
      <input type="text" id="datepicker" name="examDate" readonly="readonly" placeholder="Enter Date" value="<%=DataUtility.getDateString(bean.getExamDate())%>">
      <input type="submit" name="operation" value="<%=TimetableListCtl.OP_SEARCH %>">
      <input type="submit" value="<%= TimetableListCtl.OP_RESET %>" name="operation">
      <%-- <font color="red"> <%=ServletUtility.getErrorMessage("Exdate", request)%></font> --%>
      </td>
      </tr>
      
      </table>
      <br>
  
  <table border="1" width="100%" align="center"  >
      <tr>
       <th width="10%"><input type="checkbox" id="select_all"
						name="Select">Select All</th>
      <th>S.NO</th>
      <th>Course Name.</th>
      <th>Subject NAME.</th>
      <th>DESCRIPTION.</th>
      <th>SEMESTER.</th>
      <th>Exam Date.</th>
      <th>Exam Time.</th>
      <th>Edit</th>
      </tr>
     
   <%
   while (it.hasNext()) 
   {
       TimetableBean bean1 = it.next(); %>
      <tr>
      <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=bean1.getId()%>"></td>
      <td><%=index++%></td>
      <td><%=bean1.getCourseName()%></td>
      <td><%=bean1.getSubName()%></td>
      <td><%=bean1.getDescription()%></td>
      <td><%=bean1.getSemester()%></td>
      <%
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
       String date = format.format(bean1.getExamDate());
      %>
      <td><%=date %></td>
      <td><%=bean1.getExamTime()%></td>
      <td><a href="TimetableCtl?id=<%=bean1.getId()%>">Edit</a></td>
      </tr>
      <%
      }
      %>
   </table>
   
   <table width="100%">
      <tr>
      <td align="left"><input type="submit" name="operation" value="<%=TimetableListCtl.OP_PREVIOUS%>" 
       <%=(pageNo > 1) ? "" : "disabled"%>></td>
      <td><input type="submit" name="operation" value="<%= TimetableListCtl.OP_DELETE%>"></td>
      <td><input type="submit" name="operation" value="<%= TimetableListCtl.OP_NEW%>"></td>
      <td align="right"><input type="submit" name="operation" value="<%=TimetableListCtl.OP_NEXT%>"
      <%=(nextPageSize != 0) ? "" : "disabled"%>></td>
   </tr>
   </table>
      
      <%
       }
      if(list1.size()==0){
      %>
      <input type="submit" name="operation" value="<%= TimetableListCtl.OP_BACK%>">
      <%} %>
      
      <input type="hidden" name="pageNo" value="<%= pageNo%>">
      <input type="hidden" name="pageSize" value="<%= pageSize%>">


        </form>
    </center>
</body>
<br><br><br>
<%@ include file="Footer.jsp" %>
</html>