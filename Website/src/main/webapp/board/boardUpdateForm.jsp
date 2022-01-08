<%@page import="model.BoardBean"%>
<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>
<!-- <link rel="stylesheet" href="css/info.css"> -->
<link rel="stylesheet" href="css/up-del.css">
<body>
<%
	// 해당 게시글 번호를 통하여 게시글을 수정
	int num = Integer.parseInt(request.getParameter("num").trim());
	// 하나의 게시글에 대한 정보를 리턴
	BoardDAO bdao = new BoardDAO();
	BoardBean bean = bdao.getOneUpdateBoard(num); 
%>
	<section class="table-area">
	<form action="board/boardUpdateProc.jsp" method="post">
		<table>
			<caption>게시글 수정</caption>
			<tr>
				<td>작성자</td>
				<td><%= bean.getWriter()  %></td>
				<td>작성일</td>
				<td><%= bean.getRegDate() %></td>
			</tr>
			<tr>
				<td>제목</td>
				<td colspan="3">
					<input type="text" name="subject" value="<%= bean.getSubject() %>" class="item-text""/>
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td colspan="3">
					<input type="password" name="password" class="item-text"/>
				</td>
			</tr>
			<tr>
				<td>글 내용</td>
				<td colspan="3">
					<textarea rows="10" cols="60" name="content"><%= bean.getContent() %></textarea>
				</td>
			</tr>
		</table>
		<div class="btn">
			<input type="hidden" name="num" value="<%= bean.getNum()%>"/>
			<input type="submit" value="글수정"/>&nbsp;&nbsp;
			<button type="button" onclick="location.href='reserveMain.jsp?center=board/boardList.jsp'">목록보기</button>
		</div>
		</form>
		</section>
</body>
</html>