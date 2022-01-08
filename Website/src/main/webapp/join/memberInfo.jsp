<%@page import="model.MemberBean"%>
<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
<link rel="stylesheet" href="css/join.css">
<body>
	<%
		/*
			0. memberList에서 넘긴 id를 받아줌 
			1. 데이터 베이스에서 한 회원의 정보를 가져옴
			2. table 태그를 이용하여 화면에 회원의 정보를 출력
		*/
		
		// MemberList의 id를 받아줌
		String id = request.getParameter("id");
		MemberDAO mdao = new MemberDAO();
		MemberBean mbean = mdao.oneSelectMember(id); 
	%>
	<section class="member-info">
		<div class="container">
			<h2>회원 정보</h2>
			<div class="item">
				<label>아이디</label><p><%=mbean.getId() %></p>
			</div>
			<div class="item">
				<label>비밀번호</label><p><%=mbean.getPw() %></p>
			</div>
		</div>
		<div class="btn">
			<div>
				<button
					onclick="location.href='memberUpdateForm.jsp?id=<%=mbean.getId()%>'">회원수정</button>
				<button
					onclick="location.href='memberDeleteForm.jsp?id=<%=mbean.getId()%>'">회원삭제</button>
				<button onclick="location.href='memberList.jsp'">목록보기</button>
				<button onclick="location.href='memberJoin.jsp'">회원가입</button>
			</div>
		</div>
	</section>
</body>
</html>