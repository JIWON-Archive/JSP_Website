<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<% 
	String pass = request.getParameter("password");
	int num = Integer.parseInt(request.getParameter("num"));
	
	// 데이터 베이스 연결
	BoardDAO bdao = new BoardDAO();
	String password = bdao.getPass(num);
	
	// 기존 비밀번호와 delete Form에서 작성한 비밀번호를 비교
	if(pass.equals(password)) {
		// 비밀번호와 같다면
		bdao.deleteBoard(num);
		response.sendRedirect("../reserveMain.jsp?center=board/boardList.jsp");
	} else {
%>
	<script type="text/javascript"">
		alert("비밀번호를 확인해주세요.");
		history.go(-1);
	</script>
<%
	}
%>
</body>
</html>