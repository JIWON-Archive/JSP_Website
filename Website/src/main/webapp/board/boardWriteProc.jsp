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
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty name="boardbean" property="*"/>
</jsp:useBean>
<%
	//데이터 베이스 쪽으로 빈클래스를 넘겨줌
	BoardDAO bdao = new BoardDAO();
	// 데이터 저장 메소드 호출
	bdao.insertBoard(boardbean);
	// 게시글 저장 후 전체 게시글 보기
	response.sendRedirect("../reserveMain.jsp?center=board/boardList.jsp");
%>

</body>
</html>