<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="by.gsu.epamlab.ConstantsJSP" %>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF8">
<link rel="stylesheet" href="style.css">
<title>Start</title>

<script lang="javascript">
function disp(form) {
	if(form.id == "authorization"){
		document.getElementById('registration').style.display = "none";
	}else document.getElementById('authorization').style.display = "none";
    if (form.style.display == "none") {
        form.style.display = "block";
    } else {
        form.style.display = "none";
    }
}
    </script>
    
</head>

<body >
<div class="wrapper">
<div class="header">
 User:&nbsp;
<c:out value="${User.login}" default="Guest"></c:out>&nbsp;

          <a href="javascript:disp(document.getElementById('authorization'))">Login</a>&nbsp;&nbsp;
          <a href="javascript:disp(document.getElementById('registration'))">Registrate</a>
<hr>
</div>
<div class="content">


<c:if test="${not empty errorMessage}">
<h2><c:out value="${errorMessage}"/></h2>
<hr>
</c:if>

<form name="loginForm" id="authorization" method="POST" action="<c:url value='/authorization'/>" style="display: none;">
    <h2>Authorization form</h2>
	Login:<br>
	<input type="text" name=<%= ConstantsJSP.KEY_LOGIN %> value=""><br>
	Password:<br>
	<input type="password" name=<%= ConstantsJSP.KEY_PASSWORD %> value=""><br>
	<input type="submit" name="button" value="Enter">
</form>
<form name="RegForm" id="registration" method="POST" action="<c:url value='/authorization'/>" style="display: none;">
    <h2>Registration form</h2>
	Login:<br>
	<input type="text" name=<%= ConstantsJSP.KEY_LOGIN %> value=""><br>
	Password:<br>
	<input type="password" name=<%= ConstantsJSP.KEY_PASSWORD %> value=""><br>
	<input type="submit" name="button" value="Reg">
</form>
</div>

<div class="footer">
<hr>
Developed by
<b>Katsuba Pavel</b><br>
<a href="tel:+375259601265">+375 (25) 960-12-65</a><br>
<a href="mailto:kets911@gmail.com">kets911@gmail.com</a><br>
<a href="skype:kets902?chat">Skype</a>
</div>
</div>
</body>


</html>

