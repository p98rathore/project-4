<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj4.clt.UserRegistrationCtl"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>
<title>User Registration</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="http://code.jquery.com/jquery-1.12.4.js"></script>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script >
$( function() {
   
	  $( "#datepicker" ).datepicker({
		  changeMonth :true,
		  changeYear :true,
		  yearRange :'1980:2030',
		  dateFormat:'dd/mm/yy'
		  
	  });
  } );
</script>
</head>
<body>
<%@ include file="Header.jsp"%>
 <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
            scope="request"></jsp:useBean>
            
    <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">  
            
                  
    <form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

 <div align="center"> 
<h1 align="center" style="margin-bottom: -15; color: navy">User Registration</h1>   
<div style="height : 10px; margn-bottom: 8px">
<h3 align="center">
<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
</h3>
<h3 align="center">
    
               <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
       
</h3>

</div>
  <table align="center">


<tr>
 <H2>
            
          
            </H2>
         <th align="left">First Name<span style="color: red">*</span></th>
                    <td align="center"><input type="text" name="firstName" style="width: 173px;resize: none;" placeholder="Enter first name"  value="<%=DataUtility.getStringData(bean.getFirstName())%>" ><font color="red"> </font></td>
                    <td style="position: fixed">
                      <center><font color="red"><%= ServletUtility.getErrorMessage("firstName",request) %></font></center>
                      
                     </td>
                     </tr>
                                                           <tr>
<th style="padding: 3px"></th>
<td></td>
</tr>
                      <tr>
                    <th align="left">Last Name<span style="color: red">*</span></th>
                    <td align="center"><input type="text" name="lastName" style="width: 173px;resize: none;"  placeholder="Enter last name" value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
                    <td style="position: fixed">
                      <center><font color="red"><%= ServletUtility.getErrorMessage("lastName",request) %></font></center>
                      
                     </td>
                </tr>
                                                                         <tr>
<th style="padding: 3px"></th>
<td></td>
</tr>
                <tr>
                    <th align="left">LoginId<span style="color: red">*</span></th>
                    <td align="center"><input type="text" name="login" style="width: 173px;resize: none;"  placeholder="Must be Email ID"  value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
                      <td style="position: fixed">
                      <center><font color="red"><%=ServletUtility.getErrorMessage("login",request) %></font></center>
                      </td>
                    
                </tr>
                                                                         <tr>
<th style="padding: 3px"></th>
<td></td>
</tr>
                <tr>
                    <th align="left">Password<span style="color: red">*</span></th>
                    <td align="center"><input type="password" name="password" style="width: 173px;resize: none;"  placeholder="Enter password"   value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
                    <td style="position: fixed">
                      <center><font color="red"><%= ServletUtility.getErrorMessage("password",request) %></font></center>
                      </td>
                    
                </tr>
                                                                         <tr>
<th style="padding: 3px"></th>
<td></td>
</tr>
                <tr>
                    <th align="left">Confirm Password<span style="color: red">*</span></th>
                    <td align="center"><input type="password" name="confirmPassword" style="width: 173px;resize: none;"  placeholder="Enter confirm password"  value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
                    <td style="position: fixed">
                      <center><font color="red"><%= ServletUtility.getErrorMessage("confirmPassword",request) %></font></center>
                      </td>
                </tr>
                                                                         <tr>
<th style="padding: 3px"></th>
<td></td>
</tr>
                <tr>
                    <th align="left">Gender<span style="color: red">*</span></th>
                    <td>
                    <%
                    HashMap<String,String> map= new HashMap<String,String>();
                    
                    map.put("Female","Female");
                    map.put("Male","Male");
                    
                    String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
                    %>
                    <%=htmlList %>
                     </td>
                     <td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gender",request) %></font>
                     </td>
                </tr>
                
                   
   <tr>
   <th style="padding: 3px"></th>
   <td></td>
   </tr>
   
    <tr>
   <th align="left"><label for="Confirm">Mobile Number<span style="color: red">*</span></label></th>
   <td ><input type="text" placeholder="Enter Mobile Number" maxlength=10  name="mobile" style="width: 173px;resize: none;"  value="<%=ServletUtility.getParameter("mobile", request)%>">
   </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobile", request)%></font>
   </td>
   </tr>
                
                                                                         <tr>
<th style="padding: 3px"></th>
<td></td>
</tr>

                <tr>
                    <th align="left">Date Of Birth<span style="color: red">*</span> </th>
                    <td align="center"><input type="text"  readonly="readonly" id="datepicker" style="width: 173px;resize: none;"  placeholder="Enter Date of birth" name="dob" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
                     <td style="position: fixed">
                      <center><font color="red"><%= ServletUtility.getErrorMessage("dob",request) %></font></center>
                      </td>                         
                </tr>
                                                                         <tr>
<th style="padding: 3px"></th>
<td></td>
</tr>
               <tr align="center">
   <th></th>
   <td colspan="2">
   <input type="submit" value="SignUp" name="operation">
   <input type="submit" value="Reset" name="operation">
   </td>
   </tr>
       </table></form>
       <table align="center">
       
     
  </div>
  </table>
</body>
 <%@ include file="Footer.jsp" %> 
</html>