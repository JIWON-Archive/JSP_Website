<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<%
		session.invalidate();
		response.sendRedirect("reserveMain.jsp");
	%>
	<%-- <jsp:forward page="reserveMain.jsp"/>	 --%>
</body>
</html>