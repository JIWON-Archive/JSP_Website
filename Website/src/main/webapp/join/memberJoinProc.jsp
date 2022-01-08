<%@page import="model.MemberBean"%>
<%@page import="model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");	
		
	%>
	<!-- MemberBean 객체를 mbean이라는 이름으로 생성 -->
	<jsp:useBean id="mbean" class="model.MemberBean">
		<!-- * mbean 객체의 모든 값. 각 프로퍼티의 값을 같은 이름을 갖는 파라미터의 값으로 설정한다. -->		
		<jsp:setProperty name="mbean" property="*" />
	</jsp:useBean>

<%
	String id = request.getParameter("id");
//	mbean.setHobby(textHobby);
	MemberDAO mdao = new MemberDAO();
	mdao.insertMember(mbean);
	// 가입 회원 정보 가져오기 [memberList.jsp -> 모든 회원 보기]
	session.setAttribute("id", id);
//	response.sendRedirect("../reserveMain.jsp?center=join/memberDetail.jsp?id="+id);
	response.sendRedirect("../reserveMain.jsp");
%>

</body>
</html>