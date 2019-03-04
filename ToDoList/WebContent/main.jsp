<%@ page import="by.gsu.epamlab.ConstantsJSP" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/finalWeb/style.css">
<title>Main</title>
<script language="javascript">
      function process(type) {
          document.logout.processType.value=type;
          document.logout.submit();
      }
      function proc(type) {
          document.menu.processType.value=type;
          document.menu.submit();
      }
      function disp(form) {
    		if(form.id == "addition"){
    			form.style.display = "block";
    		}else form.style.display = "none";
      }
      function setCheck(){
    	  var allRows = document.forms['tasks'].elements['flag'];
    	  for(var i = 0; i<allRows.length; i++){
    		  if(allRows[i].checked == false){
    			  allRows[i].click();
    		  }
    	  }
      }
      function download(id){
    	  document.downloadForm.downloadInp.value=id;
          document.downloadForm.submit();
      }
    </script>
</head>

<body >
<div class="wrapper">
<div class="header">
 <form name="logout" method="POST" ACTION="<c:url value='/authorization'/>">
 User:&nbsp;${User.login}
	<input type="hidden" name="processType" value="">
          <a href="javascript:process('Logout')">Logout</a>
	</form>
<hr>
</div>

<div class="nav">
<form name="menu" method="POST" ACTION="<c:url value='/filter/menu'/>">
<input type="hidden" name="processType" value="">
	<a href="javascript:proc('Today')">Today</a>
	<a href="javascript:proc('Tomorrow')">Tomorrow</a>
	<a href="javascript:proc('Someday')">Someday</a>
	<a href="javascript:proc('Fixed')">Fixed</a>
	<a href="javascript:proc('Recycle_Bin')">Recycle Bin</a>
	</form>
</div>

<div class="content">

<CENTER>
<H2>${menuOption}</H2>

<c:if test="${not empty errorMessage}">
<h2><c:out value="${errorMessage}"/></h2>
<hr>
</c:if>

<c:if test="${menuOption eq 'Tomorrow' or menuOption eq 'Today' or empty menuOption}">
	<FORM NAME="tasks" method="POST" ACTION='<c:url value="/filter/operation"/>'>
		<TABLE>
				<thead style="background: #53afbf">
				<tr>
					<th>Check</th>
					<th width="500px">Event</th>
					<th>File</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach var="task" items="${tasks}"  varStatus="status">
					<c:set var="background" value="#fff"/>
					<c:if test="${status.count % 2 == 0}">
			        	<c:set var="background" value="#0ff"/>
					</c:if>
		       			<TR style="background: ${background}">
		      				<TD align="center"><input name="flag" id = "check" type="checkbox" value="${task.id}"></TD>
					        <TD><font>${task.name}</font></TD>
					        <TD>
					        <c:if test="${not empty task.fileName}">
					        <input TYPE="button" NAME="<%=ConstantsJSP.KEY_BUTTON%>" onclick="download(${task.id})" VALUE="Download">
					        </c:if>
					        </TD>
		       			</TR>
					</c:forEach>
				</tbody>
			</TABLE>
			<INPUT TYPE="button" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="New" onclick="disp(document.getElementById('addition'))">
			<INPUT TYPE="SUBMIT" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="Done">
			<INPUT TYPE="SUBMIT" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="Delet">
	</FORM>
	
	<form name="TaskForm" id="addition" method="POST"  enctype="multipart/form-data" action="<c:url value='/filter/Upload'/>" style="display: none;">
    	<h2>Addition form</h2>
		<label>Description:</label><br>
		<input type="text" name="Descripption" value=""><br>
		<label>Select file:</label><br>
		<p><input type="file" name="fileName"></p>
		<input type="submit" name="<%=ConstantsJSP.KEY_BUTTON%>" value="Add">
	</form>
</c:if>
			
<c:if test="${menuOption eq 'Someday' or menuOption eq 'Fixed' or menuOption eq 'Recycle_Bin'}">

	<FORM NAME="tasks" method="POST" ACTION='<c:url value="/filter/operation"/>'>
		<TABLE>
				<thead style="background: #53afbf">
				<tr>
					<th>Check</th>
					<th width="500px">Event</th>
					<th>Time</th>
					<th>File</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach var="task" items="${tasks}"  varStatus="status">
					<c:set var="background" value="#fff"/>
					<c:if test="${status.count % 2 == 0}">
			        	<c:set var="background" value="#0ff"/>
					</c:if>
		       			<TR style="background: ${background}">
		      				<TD align="center"><input name="flag" id = "check" type="checkbox" value="${task.id}"></TD>
					        <TD><font>${task.name}</font></TD>
					        <TD><font>${task.date}</font></TD>
					        <TD>
					        <c:if test="${not empty task.fileName}">
					        <input TYPE="button" NAME="<%=ConstantsJSP.KEY_BUTTON%>" onclick="download(${task.id})" VALUE="Download">
					        </c:if>
					        </TD>
		       			</TR>
					</c:forEach>
				</tbody>
			</TABLE>
			<c:if test="${menuOption eq 'Someday'}">
				<INPUT TYPE="button" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="New" onclick="disp(document.getElementById('addition'))">
				<INPUT TYPE="SUBMIT" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="Done">
			</c:if>
			<c:if test="${menuOption eq 'Recycle_Bin'}">
				<INPUT TYPE="SUBMIT" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="Return task">
				<INPUT TYPE="SUBMIT" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="Delet all" onclick="setCheck()">
			</c:if>
			<INPUT TYPE="SUBMIT" NAME="<%=ConstantsJSP.KEY_BUTTON%>" VALUE="Delet">
	</FORM>
	<form name="TaskForm" id="addition" method="POST"  enctype="multipart/form-data" action="<c:url value='/filter/Upload'/>" style="display: none;">
    	<h2>Addition form</h2>
		<label>Description:</label><br>
		<input type="text" name="Descripption" value=""><br>
		<label>Date in <b style="color: #f00;">yyyy-mm-dd</b> format:</label><br>
		<input type="date" name="Date" value=""><br>
		<label>Select file:</label>
		<p><input type="file" name="fileName"></p>
		<input type="submit" name="<%=ConstantsJSP.KEY_BUTTON%>" value="Add">
	</form>
</c:if>
<form name="downloadForm" method="post" action="<c:url value='/filter/Download'/>">
<input type="hidden" name="downloadInp" value="">
</form>
			
</CENTER> 
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