<%@page import="model.BoardBean"%>
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
	<!-- 데이터를 한번에 받아오는 빈 클래스를 사용하도록 -->
	<jsp:useBean id="boardbean" class="model.BoardBean">
		<jsp:setProperty name="boardbean" property="*"/>
	</jsp:useBean>
	<%
		// 데이터 베이스 객체 생성
		BoardDAO bdao = new BoardDAO();
		bdao.reWriteBoard(boardbean); 
		
		// 답변 데이터를 모두 저장한 후 전체 게시글 보기를 설정
		response.sendRedirect("../reserveMain.jsp?center=board/boardList.jsp");
	%>
</body>
</html>