<%@page import="model.RentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<%	
		String id = request.getParameter("id");
		int no = Integer.parseInt(request.getParameter("no"));
		int reserveNo = Integer.parseInt(request.getParameter("reserveNo"));
		
		RentDAO rdao = new RentDAO();
		
		rdao.itemReserveDelete(id, no, reserveNo); 
		
		response.sendRedirect("reserveMain.jsp?center=cart.jsp");
	%>
</body>
</html>