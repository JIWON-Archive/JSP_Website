<%@page import="model.RentDAO"%>
<%@page import="model.ItemListBean"%>
<%@page import="model.ItemReserveBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약확인</title>
<link rel="stylesheet" href="css/detail.css">
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!-- ItemReserveBean 사용 -->
<jsp:useBean id="rbean" class="model.ItemReserveBean">
	<jsp:setProperty name="rbean" property="*"/>
</jsp:useBean>

<%
	String id = (String)session.getAttribute("id");
	// 세션에서 넘어온 아이디라 null 임
	if(id == null) {
%>
		<script>
			alert("로그인후 예약이 가능합니다.");
			location.href='reserveMain.jsp?center=loginForm.jsp';
		</script>
<% 
	}
		// 결과적으로 아무런 문제가 없다면 데이터 저장 후 결과 페이지 보여주기
		// 아이디 값이 빈 클래스에 없기에 가져와야한다.
		String id1 = (String)session.getAttribute("id");
		rbean.setId(id1);
		// 데이터 베이스에 빈클래스를 저장
		RentDAO rdao = new RentDAO();
		rdao.setReserveItem(rbean);
		// 응원봉 정보 얻어오기
		ItemListBean bean = rdao.getOneItem(rbean.getNo());
		// 총 금액
		int total = bean.getPrice() + bean.getDeposit();
		// detail.jsp의 select로 받은 qty
	 	int qty = Integer.parseInt(request.getParameter("qty"));
		rbean.setQty(qty);
%>

	<form action="reserveMain.jsp?center=cart.jsp" method="post">
		<h1>대여하기</h1>
 		<section class="info">
			<article class="thumbnail">
				<div>
					<img alt="응원봉" src="img/<%=bean.getImg()%>"/>
				</div>
			</article>
			<article class="infoArea">
				<div>
					<h3><%=bean.getName() %></h3>
				</div>
				<div class="item-info">
					<p>대여 수량</p>
					<p><%= qty %></p>
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
					<p>주의 사항</p>
					<span class="policy">상품 1건 당 대여 수량은 3개로 제한됩니다.</span>
				</div>
				<div class="item-info">
					<p>최종 금액</p>
					<p><%= total * qty %></p>
				</div>
				<section class="btn">
					<input type="hidden" name="name" value="<%= bean.getName() %>">
					<input type="hidden" name="img" value="<%= bean.getImg()%>">
					<input type="hidden" name="qty" value="<%= qty %>">
					<input type="hidden" name="reserveNo" value="<%= rbean.getReserveNo() %>">
					<input class="rent-btn" type="submit" value="결제하기">
				</section>
			</article>
		</section>
	</form>
	
<script>
	/* 결제하기 버튼 클릭 시 alert 띄우기 */
	const rentBtn = document.querySelector(".rent-btn");
	function mouseClick() {

		alert("결제 서비스 개발 중입니다.");
		history.go(-1);

	}
	rentBtn.addEventListener("click", mouseClick);
</script>

</body>
</html>