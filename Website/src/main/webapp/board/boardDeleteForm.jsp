<%@page import="model.BoardBean"%>
<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 삭제</title>
</head>
<link rel="stylesheet" href="css/info.css">
<body>
	<%
		BoardDAO bdao = new BoardDAO();
		int num = Integer.parseInt(request.getParameter("num"));
		BoardBean bean = bdao.getOneUpdateBoard(num);
	%> 
		<form action= "board/boardDeleteProc.jsp" method="post">
		<section class="table-area">
			<table class="delete-table">
				<caption>게시글 삭제</caption>
				<tr>
					<td class="label">작성자</td>
					<td class="content"><%= bean.getWriter()  %></td>
					<td class="label">작성일</td>
					<td class="content"><%= bean.getRegDate() %></td>
				</tr>
				<tr>
					<td class="label">제목</td>
					<td colspan="3" class="content">
						<%= bean.getSubject() %>
					</td>
				</tr>
				<tr>
					<td class="label">비밀번호</td>
					<td colspan="3" class="content">
						<input type="password" name="password" class="item-text"/>
					</td>
				</tr>
			</table>
		</section>
		<div class="btn">
			<input type="hidden" name="num" value="<%= num %>"/>
			<input type="submit" value="글삭제"/>&nbsp;
			<button type="button" onclick="location.href='reserveMain.jsp?center=board/boardList.jsp'">목록보기</button>
		</div>
	</form>
</body>
</html>