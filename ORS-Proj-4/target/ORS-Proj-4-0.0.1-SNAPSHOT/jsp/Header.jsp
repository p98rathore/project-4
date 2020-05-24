
<%@page import="in.co.sunrays.proj4.bean.RoleBean"%>
<%@page import="in.co.sunrays.proj4.clt.LoginCtl"%>
<%@page import="in.co.sunrays.proj4.clt.ORSView"%>
<%@page import="in.co.sunrays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<%
UserBean userbean = (UserBean)session.getAttribute("user");
String welcomeMsg="Hi  ";

boolean ube=userbean!=null;
if(ube){
	String role =(String)session.getAttribute("role");
	welcomeMsg+=userbean.getFirstName()+"("+role+")";
}
else{
	welcomeMsg +=  "Guest";
}
%>

<table>
<tr>
  <td width="90%" ><a href="<%=ORSView.WELCOME_CTL%>" ><font>Welcome</font></a>
  |
     <%
            if (ube) {
        %> <a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><font>Logout</font></a>
        
        <%}else{%>
        	<a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> <%
        }
    %></td>
   <td  rowspan="2">
            <h1 align="Right">
             <img src="<%=ORSView.APP_CONTEXT %>/Image/customLogo.jpg" width="250" height="50">
            </h1>
        </td>
        
    </tr>
    <tr>
     <td >
            <h3>
                <%=welcomeMsg%></h3>
        </td>
    </tr>
     <%
        if (ube) {
    %>

    <tr>
        <td colspan="2" >
        
        <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |
            <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet Merit
                List</b>
        </a> | <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> | <a
            href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a>| <%
           if (userbean.getRoleId()== RoleBean.ADMIN) {
         %> <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> | 
         <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | 
         <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
         <a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> |
         <a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> |
         <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | 
        
         <a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> |
         <a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> | 
          <%}
          
 		if (userbean.getRoleId() == RoleBean.COLLEGE || userbean.getRoleId() == RoleBean.ADMIN) {%>
            <a href="<%=ORSView.STUDENT_CTL%>">Add Student</a> | 
            <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</a> | 
            <a href="<%=ORSView.COURSE_CTL%>">Add Course</a> | 
            <a href="<%=ORSView.COURSE_LIST_CTL%>">Course List</a> | 
            <a href="<%=ORSView.SUBJECT_CTL%>">Add Subject</a> | 
            <a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</a> | 
            <a href="<%=ORSView.FACULTY_CTL%>">Add Faculty</a> | 
            <a href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</a> <%
 	}	
 		if (userbean.getRoleId() == RoleBean.COLLEGE || userbean.getRoleId() == RoleBean.FACULTY
 				|| userbean.getRoleId() == RoleBean.ADMIN) {%>|  
 				<a href="<%=ORSView.TIMETABLE_CTL%>">Add Timetable</a> | 
                <a href="<%=ORSView.TIMETABLE_LIST_CTL%>">Timetable List</a> <%
 	}

 		if (userbean.getRoleId() == RoleBean.ADMIN) {
 %> | <a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</a> <%
 	}
 	} else {
 %> <a href="<%=ORSView.LOGIN_CTL%>"></a> <%
 	}
 %></td>
    
    </tr>
    
     </table>
     
           </body>
           <hr>
           </html>

