<%@page import="model.ItemReserveBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.ReserveViewBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.RentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CART</title>
</head>
<link rel="stylesheet" href="css/cart.css">
<body>
	<%
	String id = (String)session.getAttribute("id");
	if(id == null) {
%>
	<script>
		alert("로그인을 먼저 해주세요");
		location.href='reserveMain.jsp?center=loginForm.jsp';
	</script>		
<%
	}
	// 로그인 되어 있는 아이디를 읽어옴
	RentDAO rdao = new RentDAO();
	// 몇개 인지 모르므로 벡터로 가져옴
	ArrayList<ReserveViewBean> list = rdao.getAllReserve(id);

	
%>
	<section class="table-area">
		<table width="1000">	
		<caption>장바구니</caption>
		<tr height="40">
			<td width="150" align="center"></td>
			<td width="150" align="center">상품명</td>
			<td width="150" align="center">수량</td>
			<td width="100" align="center">주문금액</td>
			<td width="100" align="center"></td>
		</tr>
		<%
			for(int i=0; i < list.size(); i++) {
				ReserveViewBean bean = list.get(i);	
				int total = (bean.getPrice() + bean.getDeposit()) * bean.getQty();
				int reserveNo = bean.getReserveNo();
		%>
			<tr height="70">
				<td width="150" align="center">
				<img alt="" src="img/<%= bean.getImg() %>" width="70" height="70">
				</td>
				<td width="150" align="center"><%= bean.getName() %></td>
				<td width="60" align="center"><%=bean.getQty() %></td>
				<td width="100" align="center"><%= total %>원</td>
				<td width="90" align="center"><button onclick="location.href='reserveDel.jsp?id=<%= id %>&no=<%= bean.getNo() %>&reserveNo=<%= reserveNo %>'">삭제</button></td>
			</tr>
		<%
			}
		%>
		<%
			ReserveViewBean bean = new ReserveViewBean();	
			int total = 0;
		 
 			for(int i=0; i < list.size(); i++) {
				bean = list.get(i);	
				// 총 주문 금액
				total += bean.getPrice() + bean.getDeposit() * bean.getQty();
			}
			%>
			<tr>
				<td colspan="3">총 주문 금액</td>
				<td class="total"><%= total %>원</td>
				<td><button class="pay-btn" onclick="location.href='reserveMain.jsp?center=cart.jsp'">결제</button></td>
			</tr>
	</table>
</section>
<script>
	/* 결제하기 버튼 클릭 시 alert 띄우기 */
	const payBtn = document.querySelector(".pay-btn");
	function mouseClick() {
		alert("결제 서비스 개발 중입니다.");
	}
	payBtn.addEventListener("click", mouseClick);
</script>
</body>
</html>