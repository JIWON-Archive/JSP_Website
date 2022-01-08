<%@page import="model.MemberBean"%>
<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 수정</title>
</head>
<link rel="stylesheet" href="css/join.css">
<body>
<%		
	String id = request.getParameter("id");
	MemberDAO mdao = new MemberDAO();
	MemberBean mbean = mdao.oneSelectMember(id);
%>
<div class="wrapper">
	<form action="join/memberUpdateProc.jsp" method="post">
		<div class="container">
				<h2>회원 정보 수정</h2>
				<div class="item">
					<p>아이디</p>
					&nbsp;&nbsp; <input type="text" name="id"
						value="<%=mbean.getId()%>" readonly="readonly">
				</div>
				<%-- <div class="item">
				<label>이메일</label>
				<input type="email" name="email" value="<%= mbean.getEmail()%>">
			</div>
			<div class="item">
				<label>전화</label>
				<input type="tel" name="tel" value="<%= mbean.getTel() %>">
			</div> --%>
			<div class="item">
				<p>비밀번호</p>
				<input type="text" name="pw" value="<%= mbean.getPw() %>">
			</div>
				<div class="item">
				<p>전화번호</p>
				<input type="tel" name="tel" value="전화번호를 입력하세요">
			</div>
			<!-- </div>
				<div class="item">
				<p>이메일 </p>
				<input type="email" name="email" value="이메일">
			</div> -->
		</div>
		<div class="btn-style">
			<input type="hidden" name="id" value="<%= mbean.getId()%>">
			<input type="submit" class="up-btn" value="수정 완료">&nbsp;
			<input type="reset" class="up-btn" value="취소">&nbsp;
		</div>
	</form>
</div>	
</body>
</html>