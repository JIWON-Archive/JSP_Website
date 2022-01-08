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
		/*	memberInfo.jsp 와 동일
			0. memberJoin에서 넘긴 id를 받아줌 
			1. 데이터 베이스에서 한 회원의 정보를 가져옴
			2. table 태그를 이용하여 화면에 회원의 정보를 출력
		*/
		
		// memberJoin의 id를 받아줌
		String id = request.getParameter("id");
		MemberDAO mdao = new MemberDAO();
		MemberBean mbean = mdao.oneSelectMember(id); 
	%>
	<div class="wrapper">
		<section class="info-form">
			<div class="container">
				<h2>회원 정보</h2>
				<div class="item">
					<p>아이디</p>&nbsp; &nbsp;
					<input type="text" name="pw" value="<%= mbean.getId() %>" readonly="readonly">
				</div>
				<div class="item">
					<p>비밀번호</p>
					<input type="text" name="pw" value="<%= mbean.getPw() %>">
				</div>
			</div>
			<div class="btn-style">
				<div>
			<%-- 		<button class="up-btn"
						onclick="location.href='../reserveMain.jsp?center=join/memberUpdateForm.jsp?id=<%=mbean.getId()%>'">정보수정</button> --%>
					<button class="up-btn"
						onclick="location.href='../reserveMain.jsp?center=join/memberDeleteForm.jsp?id=<%=mbean.getId()%>'">회원탈퇴</button>
					<button class="up-btn"
						onclick="location.href='../reserveMain.jsp?center=join/memberDetail.jsp?id=<%=mbean.getId()%>'">취소</button>
				</div>
			</div>
		</section>
	</div>
</body>
</html>