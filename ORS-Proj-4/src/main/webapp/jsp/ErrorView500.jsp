<%@page import="in.co.sunrays.proj4.clt.ORSView"%>
<%@page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Error</title>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>

<link rel="stylesheet" type="text/css" href="/ORS-Proj-4/css/Error.css">

</head>
<body>

	<br>
	<br>
	<br>
	<br>
	<div align="center">
		<h1 align="center">Oops! Something went wrong.</h1>
		<font style="color: red; font-size: 25px"><b>500</b> : Internal server
			error.</font>

	</div>
	<h4 align="center">
		<font size="5px" color="black"> <a
			href="<%=ORSView.WELCOME_CTL%>" style="color: deepskyblue;">*Please
				click here to Go Back*</a></font>
	</h4>

</body>
</html>