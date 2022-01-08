<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 삭제</title>
<link rel="stylesheet" href="css/join.css">	
</head>
<body>
	<div class="wrapper">
		<form action="join/memberDeleteProc.jsp" method="post">
			<div class="container">
				<h2>회원 탈퇴</h2>
				<div class="item">
					<label>아이디</label>
					<input type="text" name="id" value="<%=request.getParameter("id")%>">
					<%-- <label><%= request.getParameter("id") %></label> --%>
				</div>
				<div class="item">
				<label>비밀번호</label> 
				<input type="password" name="pw">
				</div>
				<div class="message">
					<p>회원 탈퇴 시 계정을 복구할 수 없습니다.</p>
				</div>
			</div>
			<div class="btn-style">
				<input type="hidden" name="id" value="<%=request.getParameter("id")%>"> 
				<input type="submit" value="회원 탈퇴" class="del-btn">
				<input type="reset" value="취소" class="del-btn">
			</div>
		</form>
	</div>
</body>
</html>