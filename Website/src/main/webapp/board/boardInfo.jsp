<%@page import="model.BoardBean"%>
<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>
</head>
<link rel="stylesheet" href="css/info.css">
<body>
	<%
		// 공백 제거 후 정수형으로 변경
		int num = Integer.parseInt(request.getParameter("num").trim());
		// 데이터 베이스에 접근
		BoardDAO bdao = new BoardDAO();
		// boardbean 타입으로 하나의 게시글을 리턴
		BoardBean bean = bdao.getOneBoard(num); 
	%>
	<section class="table-area">
		<table>
		<caption>게시글 보기</caption>
			<tbody>
				<tr>
					<td class="label">글번호</td>
					<td class="content"><%= bean.getNum() %></td>
					<td class="label">조회수</td>
					<td class="content"><%= bean.getReadCount() %></td>
				</tr>
				<tr>
					<td class="label">작성자</td>
					<td class="content"><%= bean.getWriter() %></td>
					<td class="label">작성일</td>
					<td class="content"><%= bean.getRegDate() %></td>
				</tr>
				<tr>
					<td class="label">이메일</td>
					<td class="content" colspan="3"><%= bean.getEmail() %></td>
				</tr>
				<tr>
					<td class="label">제목</td>
					<td class="content" colspan="3"><%= bean.getSubject() %></td>
				</tr>
				<tr>
					<td class="label">내용</td>
					<td class="content" colspan="3"><%= bean.getContent() %></td>
				</tr>
			</tbody>
		</table>
	</section>
		<div class="btn">
			<button onclick="location.href='reserveMain.jsp?center=board/boardReWriteForm.jsp?num=<%= bean.getNum()%>&ref=<%= bean.getRef()%>&reStep=<%= bean.getReStep()%>&reLevel=<%= bean.getReStep()%>'">
			답글쓰기</button>
			<!-- num null 오류 num 안보내서 오류남 -->
			<button onclick="location.href='reserveMain.jsp?center=board/boardUpdateForm.jsp?num=<%= bean.getNum() %>'">수정하기</button>
			<button onclick="location.href='reserveMain.jsp?center=board/boardDeleteForm.jsp?num=<%= bean.getNum() %>'">삭제하기</button>
			<button onclick="location.href='reserveMain.jsp?center=board/boardList.jsp'">목록보기</button>
		</div>
</body>
</html>