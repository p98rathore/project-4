<%@page import="in.co.sunrays.proj4.clt.ForgetPasswordCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>
<title> Forget Passwords</title>
</head>
<body>
 <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.UserBean"
            scope="request"></jsp:useBean>
            <form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
 <div align="center">
<h1>Forgot your password?</h1>


<div style="margin-top: 15px">
<lable>Submit your email address and we'll send you password.</lable><br><br>
</div>
<div align="center">
<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
</div>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
<table>
                <th><label>Email Id :</label></th>&emsp;
                <td><input type="text" name="login" placeholder="Enter ID Here"
                    value="<%=ServletUtility.getParameter("login", request)%>"></td>&emsp;
                <td><input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>"><br></td>
                <td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
</table>
</div>
    </form>
    
<%@ include file="Footer.jsp" %>    
</body>
</html>
