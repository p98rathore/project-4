<%@page import="in.co.sunrays.proj4.clt.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/Image/ors.png" sizes="16x16"/>
<title> Add-College</title>
<style>
body {
  font-family: Arial, Helvetica, sans-serif;
  background-color: black;
}

* {
  box-sizing: border-box;
}

/* Add padding to containers */
.container {
  padding: 16px;
  background-color: white;
}

/* Full-width input fields */
input[type=text], input[type=password] {
  width: 100%;
  padding: 15px;
  margin: 3px 0 15px 0;
  display: inline-block;
  border: none;
  background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
  background-color: #ddd;
  outline: none;
}

/* Overwrite default styles of hr */
hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 25px;
}

/* Set a style for the submit button */
.cancelbtn {
float: right;
width: 50%;
  padding: 14px 20px;
  background-color: #f44336;
}

/* Float cancel and signup buttons and add an equal width */
.savebtn{
  float: left;
  padding: 14px 20px;
  width: 50%;
}

.savebtn:hover {
  opacity: 1;
}

/* Add a blue text color to links */
a {
  color: dodgerblue;
}
</style>
</head>
<body>

<form action="/CollegeCtl" method="post" >
  <div class="container">
    <h1>Add College</h1>
    <p>Please fill in this form to add college.</p>
    <hr>
<div class="form-row">
    <label for="name"><b>NAME</b></label>
    <input type="text" placeholder="Enter Name" name="name" >
    <span id="12" style="color:red"></span>
</div>
<div class="form-row">
    <label for="address"><b>ADDRESS</b></label>
    <input type="text" placeholder="Enter Address" name="address" >
</div>
<div class="form-row">
    <label for="city"><b>CITY</b></label>
    <input type="text" placeholder="Enter City" name="city">
    </div>
    <div class="form-row">
    <label for="state"><b>STATE</b></label>
    <input type="text" placeholder="Enter State" name="state">
    </div>
    <div class="form-row">
    <label for="phoneno"><b>CONTACT NO</b></label>
    <input type="text" placeholder="Enter phone no" name="phoneno" >
    </div>
    <div class="clearfix">
      <button type="button" class="cancelbtn" >Cancel</button>
      <button type="submit" class="savebtn" name="save" >Sign Up</button>
    </div>
    </div>
  
 
</form>

</body>
</html>
