<%@page import="model.ItemListBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.RentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>대여하기</title>
<!-- 	<link rel="stylesheet" href="css/reset.css"> -->
<link rel="stylesheet" href="css/item-list.css">
</head>
 <body>
<!-- 
<header>
	<a class="logo" href="main.jsp">빛 나 는 너</a>
	<a href="#"><img class="logo" src="img/logo.png"></a>
	<nav>
		<ul>
			<li class="active"><a href="#">대여하기</a></li>
			<li><a href="#">Login</a></li>
			<li><a href="MemberJoin.jsp">Join</a></li>
			<li><a href="#">Q&A</a></li>
		</ul>
		<div class="clear"></div>
	</nav>
</header>
 -->
	<!-- main page -->
	<section class="section-main">
		<h1>Light Stick</h1>
		<%	
	// 데이터 베이스에 가서 no에 맞는 아이템 리스트를 가져온다.
	RentDAO rdao = new RentDAO();
	ArrayList<ItemListBean> list = rdao.getAllList();
	
	int j = 0;
	for(int i = 0; i< list.size()-4; i++) {
		ItemListBean bean = list.get(i);
		if(j%4 == 0) {
%>
			<section class="section-list">
			<%
		}
		
%>
				<article class="img-wrap">
					<a class="item-link" href="reserveMain.jsp?center=detail.jsp?no=<%= bean.getNo()%>&name=<%= bean.getName() %>">
						<div class="item-box">
							<img src="img/<%=bean.getImg()%>" alt="" class="light-stick">
							<h2><%=bean.getName()%></h2>
						</div>
					</a>
				</article>
			<%
		j = j + 1;
	}
%>
			<!-- 						
			<section class="section-list">
				<article class="img-wrap">
					<a class="item-link" href="#">
						<div class="item-box">
							<img src="img/투바투.png" alt="" class="light-stick">
							<h2>투모로우바이투게더</h2>
						</div>
					</a>
				</article>
			</section> -->
			</section>
		</section>
<!-- <footer>
	<div>이용약관 이메일 Contact Us 사업장 소개 도움말 COPYRIGHTⓒ 빛나는너 All right
		reserved.</div>
</footer> -->
</body>
</html>