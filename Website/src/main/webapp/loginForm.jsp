<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>빛나는너 : 로그인</title>
</head>
<link rel="stylesheet" href="css/join.css">
<body>
	<div class="wrapper">
		<form action="loginProc.jsp" method="post">
		<section>
			<div class="container">
				<h2>로그인</h2>
				<div class="item">
					<label>아이디</label> 
					<input type="text" name="id">
				</div>
				<div class="item">
					<label>비밀번호</label>
					<input type="password" name="pw">
				</div>
			</div>
			<div class="btn-style">
				<input type="submit" value="로그인" class="up-btn">
				<button type="button" class="up-btn" onclick="location.href='MemberList.jsp'">취소</button>
			</div>
		</section>
		</form>
	</div>
</body>
</html>