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
	<jsp:useBean id="mbean" class="model.MemberBean">
		<jsp:setProperty name="mbean" property="*"/>
	</jsp:useBean>
<%
	MemberDAO mdao = new MemberDAO();
	String pass = mdao.getPassword(mbean.getId());
	
	if(mbean.getPw().equals(pass)) { 
		mdao.deleteMember(mbean.getId());
		// session 종료
		session.invalidate();
		response.sendRedirect("../reserveMain.jsp");
	} else { 
%>	
		<script type="text/javascript">
			alert("패스워드가 맞지 않습니다. 다시 확인해주세요.");
			history.go(-1);
		</script>
<%
	}
%>

</body>
</html>