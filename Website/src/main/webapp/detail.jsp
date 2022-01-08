<%@page import="model.ItemListBean"%>
<%@page import="model.RentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>대여하기 : Light Stick</title>
<link rel="stylesheet" href="css/detail.css">
</head>
<body>
<%
	// no를 기준으로 데이터 베이스에 접근해서 값을 가져옴
	int no = Integer.parseInt(request.getParameter("no"));
	RentDAO rdao = new RentDAO();
	ItemListBean bean = rdao.getOneItem(no); // db에서 no를 기준으로 가져옴 
/* 	// 수량
	int qty = Integer.parseInt(request.getParameter("qty")); */
	
%>
	<form action="reserveMain.jsp?center=confirm.jsp" method="post">
		<h1><%=bean.getName()%> 공식 응원봉</h1>
		<section class="info">
			<article class="thumbnail">
				<div>
					<img alt="응원봉" src="img/<%=bean.getImg()%>"/>
				</div>
			</article>
			<article class="infoArea">
			<%-- 	<div>
					<h3><%=bean.getName() %></h3>
				</div> --%>
				<div class="item-info">
					<p>대여 수량</p>
					<select name="qty">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
					</select>
				</div>
				<div class="item-info">
					<p>대여 가격</p>
					<p><%=bean.getPrice() %>원</p>
				</div>
				<div class="item-info">
					<p>보증금 &nbsp; &nbsp;</p>
					<p><%=bean.getDeposit() %>원</p>
				</div>
				<div class="item-info">
					<p>상품 정보</p>
					<span><%=bean.getInfo()%></span>
				</div>
				<div class="item-info">
					<p>주의 사항</p>
					<span class="policy">상품 1건 당 대여 수량은 3개로 제한됩니다.</span>
				</div>
				<section class="btn">
					<input type="hidden" name="no" value="<%= no %>">
					<input type="hidden" name="name" value="<%= bean.getName() %>">
					<input type="hidden" name="img" value="<%= bean.getImg()%>">
				<!-- 	<input type="hidden" name="qty"> -->
					<input type="hidden" name="price" value="<%= bean.getPrice() %>">
					<input type="hidden" name="deposit" value="<%= bean.getDeposit() %>">
					<input class="rent-btn" type="submit" value="바로 대여하기">
				</section>
			</article>
		</section>
	</form>
</body>
</html>