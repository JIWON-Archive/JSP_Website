<%@page import="model.RentDAO"%>
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
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		// 회원 아이디와 패스워드가 일치하는지 비교
		RentDAO rdao = new RentDAO();
		// 해당 회원이 있는지 여부를 숫자로 표현
		int result = rdao.getMember(id, pw);
		System.out.println(id);
		System.out.println(pw);
		System.out.println(result);
		
		if(result == 0) {
	%>
			<script>
				alert("회원 아이디 또는 패스워드가 틀립니다.");
				location.href='reserveMain.jsp?center=loginForm.jsp';
			</script>
	<%		
		} else {
			// 로그인 처리가 되었다면
			session.setAttribute("id", id);
			response.sendRedirect("reserveMain.jsp");
		}
	%>
</body>
</html>