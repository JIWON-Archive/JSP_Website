<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.BoardBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.BoardDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
</head>
<link rel="stylesheet" href="css/list.css">
<body>
<!-- 	<h2>전체 게시글 보기</h2> -->
	<!-- 게시글 보기에 카운터링을 설정하기 위한 변수들을 선언 -->
<%

	// 화면에 보여질 게시글의 개수를 지정
	int pageSize = 10;
	// 현재 카운터를 클릭한 번호 값을 읽어옴
	String pageNum = request.getParameter("pageNum");
	// 만약 처음 boardList.jsp를 클릭하거나 수정 삭제 등 다른 게시글에서 이 페이지로 넘어오면
	// pageNum 값이 없기에 null 값 처리
	if(pageNum == null) {
		pageNum = "1";
	}
	
	int count = 0; // 전체 글의 개수를 저장하는 변수
	int number = 0; // 페이지 넘버링 변수
	// 현재 보고자하는 페이지 숫자를 지정
	int currentPage = Integer.parseInt(pageNum);
	
	// 전체 게시글의 내용을 jsp 쪽으로 가져와야함
	BoardDAO bdao = new BoardDAO(); 
	
	// 전체 게시글의 개수를 읽어 들인 메소드 호출
	count = bdao.getAllCount(); 
	
	// 현재 페이지를 보여줄 시작 번호를 설정 = 데이터 베이스에서 불러올 시작 번호
	int startRow = (currentPage-1) * pageSize + 1;
	int endRow = currentPage * pageSize;
	
	// 최신글 10개를 기준으로 게시글을 리턴
	ArrayList<BoardBean> list = bdao.getAllBoard(startRow, endRow);
	
	// 데이터를 표시할 번호 지정
	number = count - (currentPage-1) * pageSize;
%>
<section class="table-area">
	<table>
		<caption>Q & A</caption>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>
		<%
			for(int i=0; i< list.size(); i++) {
				BoardBean bean = list.get(i);
				// ArrayList에 저장되어 있는 빈 클래스를 하나씩 추출
		%>
		<tr>
			<td><%= number-- %></td>
			<td>
				<a id="subject" href="reserveMain.jsp?center=board/boardInfo.jsp?num=<%= bean.getNum() %>">
				 <%-- <%
					// 답변 2칸 들여쓰기 없애기
					if(bean.getReStep() > 1) {
						// 공백을 for문 만큼
						for(int j=0; j < (bean.getReStep()-1)*2 ; j++) {
				%>
							&nbsp;				
				<%
						} 
					} 
				%> --%>
				<%= bean.getSubject() %></a>
			</td>
			<td><%= bean.getWriter() %></td>
			<td><%= bean.getRegDate() %></td>
			<td><%= bean.getReadCount() %></td>
		</tr>
		<% } %>
	</table>
	<div class="write-btn">
		<button onclick="location.href='reserveMain.jsp?center=board/boardWriteForm.jsp'">글쓰기</button>
	</div>
	<p>
	<!-- 페이지 카운터링 소스 -->
	<%
		if(count > 0) {
			// 카운터링 숫자를 얼마까지 보여줄건지 결정
			int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
			// 시작 페이지 숫자를 설정
			int startPage = 1;
			if(currentPage % 10 != 0) {
				startPage = (int)(currentPage/10) * 10 + 1;
			} else {
				startPage = ((int)(currentPage/10) -1) * 10 + 1;
			}
			// 카운터링 처리 숫자
			int pageBlock = 10;
			// 화면에 보여질 페이지의 마지막 숫자
			int endPage = startPage + pageBlock-1;
			
			if(endPage > pageCount) {
				endPage = pageCount;
			}
			// 이전이라는 링크를 만들건지 파악
			if(startPage > 10) {
	%>
				<a class="count" href="boardList.jsp?pageNum=<%= startPage-10 %>">[이전]</a>
	<%	
			}
			// 페이징 처리
			for(int i=startPage; i<=endPage; i++) {
	%>
				<a class="count" href="boardList.jsp?pageNum=<%= i %>">[<%= i %>]</a>
	<%
			}
			// 다음이라는 링크를 만들건지 파악
			if(endPage < pageCount) {
	%>	
				<a class="count" href="boardList.jsp?pageNum<%= startPage + 10 %>">[다음]</a>				
	
	<%
			}
		}
	%>
	</p>
</section>	
</body>
</html>