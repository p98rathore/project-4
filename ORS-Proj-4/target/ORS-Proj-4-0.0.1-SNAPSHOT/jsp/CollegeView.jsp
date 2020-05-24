<%@page import="in.co.sunrays.proj4.clt.CollegeCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<title>COLLEGE</title>
</head>
<body>
<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CollegeBean"
            scope="request"></jsp:useBean>
 <%@ include file="Header.jsp"%>
 <form action="<%= ORSView.COLLEGE_CTL %>" method="post">
 <%
   System.out.print("jsp bean id2 "+bean.getId());
  %>
  
  <input type="hidden" name="id" value="<%=bean.getId()%>">
  <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
  <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
  <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
  <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
  
  <div align="center">

			<h1 align="center" style="margin-bottom: -5; color: navy">
			<%
            if (bean != null && bean.getId() > 0) 
            {%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Update College</font></th>
            </tr>
            <%}else{%>
            <tr  id="aa" style="background-color: #eaeae1;">
            <th align="left" style="padding: 7px"><font size="5px">Add College</font></th>
            </tr>
            <%}%>
			</h1>

			<div style="height: 10px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</font>
				</H3>
				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div> 

<table >


  <tr>
  <th align="left"><label>Name<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Name." name="name"  style="width: 173px;resize: none;" value="<%= DataUtility.getStringData(bean.getName())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%= ServletUtility.getErrorMessage("name", request) %></font>
  </td>
  </tr>
  
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  
  <tr>
  <th align="left"><label>Address<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter Address." name="address"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getAddress())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font>
  </td>
  </tr>
  
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  
  <%-- <tr>
  <th align="left"><label >State<span style="color: red">*</span></label></th>
  <td>
  <%
  LinkedHashMap<String ,String> map = new LinkedHashMap<String ,String>();
  map.put("Andaman and Nicobar Islands","Andaman and Nicobar Islands");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  map.put("Arunachal Pradesh", "Arunachal Pradesh");
  map.put("Assam", "Assam");
  map.put("Bihar", "Bihar");
  map.put("Chandigarh", "Chandigarh");
  map.put("Chhattisgarh", "Chhattisgarh");
  map.put("Dadra and Nagar Haveli", "Dadra and Nagar Haveli");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  map.put("Andhra Pradesh", "Andhra Pradesh");
  %>
  </td>
  </tr> --%>


  <tr>
  <th align="left"><label >State<span style="color: red">*</span></label></th>
    <td ><select name="state" style="height: 20px;width: 175px">
  <%
   if(bean.getId()>0){%>
   <option ><%=DataUtility.getString(bean.getState())%></option>
   <%}%>
   <%
   if(bean.getId()==0){%>
   <option >-------Select State--------</option>
   <%}%>


<option value="Andaman and Nicobar Islands">And. and Nico. Islands</option>
<option value="Andhra Pradesh">Andhra Pradesh</option>
<option value="Arunachal Pradesh">Arunachal Pradesh</option>
<option value="Assam">Assam</option>
<option value="Bihar">Bihar</option>
<option value="Chandigarh">Chandigarh</option>
<option value="Chhattisgarh">Chhattisgarh</option>
<option value="Dadra and Nagar Haveli">Dadra Nagar Haveli</option>
<option value="Daman and Diu">Daman Diu</option>
<option value="Delhi">Delhi</option>
<option value="Goa">Goa</option>
<option value="Gujarat">Gujarat</option>
<option value="Haryana">Haryana</option>
<option value="Himachal Pradesh">Himachal Pradesh</option>
<option value="Jammu and Kashmir">Jammu and Kashmir</option>
<option value="Jharkhand">Jharkhand</option>
<option value="Karnataka">Karnataka</option>
<option value="Kerala">Kerala</option>
<option value="Lakshadweep">Lakshadweep</option>
<option value="Madhya Pradesh">Madhya Pradesh</option>
<option value="Maharashtra">Maharashtra</option>
<option value="Manipur">Manipur</option>
<option value="Meghalaya">Meghalaya</option>
<option value="Mizoram">Mizoram</option>
<option value="Nagaland">Nagaland</option>
<option value="Orissa">Orissa</option>
<option value="Pondicherry">Pondicherry</option>
<option value="Punjab">Punjab</option>
<option value="Rajasthan">Rajasthan</option>
<option value="Sikkim">Sikkim</option>
<option value="Tamil Nadu">Tamil Nadu</option>
<option value="Tripura">Tripura</option>
<option value="Uttaranchal">Uttaranchal</option>
<option value="Uttar Pradesh">Uttar Pradesh</option>
<option value="West Bengal">West Bengal</option>
</select> 
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font>
 </td> 
  </tr>
  
  <tr>
  <th style="padding: 3px"></th>
  <td></td>
  </tr>
  
  <tr>
  <th align="left"><label >City<span style="color: red">*</span></label></th>
  <td ><input type="text" placeholder="Enter City." name="city"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getCity())%>">
  </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font>
  </td>
  </tr>
   
   <tr>
   <th style="padding: 3px"></th>
   <td></td>
   </tr>
       
   <tr>
   <th align="left"><label>Phone<span style="color: red">*</span></label></th>
   <td ><input type="text" placeholder="Enter Phone No." name="phone"  style="width: 173px;resize: none;" value="<%=DataUtility.getStringData(bean.getPhoneNo())%>">
   </td>
  <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("phone", request)%></font>
   </td>
   </tr>
   
   <tr>
   <th style="padding: 3px"></th>
   <td></td>
   </tr>
  
   
   <tr align="center">
 <th></th>
 <td colspan="2">
    <%
    if(bean.getId()>0 && bean!=null){
    %>
    <input type="submit" value="<%=CollegeCtl.OP_UPDATE %>" name="operation">
    <input type="submit" value="<%=CollegeCtl.OP_CANCEL %>" name="operation">
    <%}else {%>
   <input type="submit" value="<%= CollegeCtl.OP_SAVE %>" name="operation">
   <input type="submit" value="<%= CollegeCtl.OP_RESET %>" name="operation">
   <%}%>
     </td>
   </tr>
     
</table>
  </form>
  
<%@ include file="Footer.jsp" %>
</body>
</html>