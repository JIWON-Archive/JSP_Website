<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<link rel="stylesheet" href="css/hf-style.css">
<body>
<%
	request.setCharacterEncoding("UTF-8");
/* 
	// 사용자로 부터 데이터를 읽어 들임
	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	
	// 아이디와 패스워드를 저장
	session.setAttribute("id", id);
	session.setAttribute("pass", pass); */
	
	String id = (String)session.getAttribute("id");
	// 세션의 유지시간 설정 60초 * 10 = 10분
	session.setMaxInactiveInterval(60*3);
		
%>
	<header>
		<a class="logo" href="reserveMain.jsp">빛 나 는 너</a>
		<!-- <a href="#"><img class="logo" src="img/logo.png"></a> -->
		<nav>
			<ul>
			<%
				if(id == null) {
			%>
					<li><a href="reserveMain.jsp?center=loginForm.jsp">LOGIN</a></li>
					<li><a href="reserveMain.jsp?center=join/join.jsp">JOIN</a></li>
			<%
				} else if(id !=null) {
			%>
					<li><a href="reserveMain.jsp?center=join/memberDetail.jsp?id=<%= id %>"><%= id %> 님</a></li>
					<li><a href="logout.jsp">LOGOUT</a></li>
					
			<%
				}
			%>	
				<li><a href="reserveMain.jsp?center=itemList.jsp">RENT</a></li>
				<li><a href="reserveMain.jsp?center=cart.jsp">CART</a></li>
				<li><a href="reserveMain.jsp?center=board/boardList.jsp">Q&A</a></li>
			</ul>
			<div class="clear"></div>
		</nav>
	</header>
</body>
</html>