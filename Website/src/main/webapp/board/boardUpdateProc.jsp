<%@page import="model.BoardBean"%>
<%@page import="model.BoardDAO"%>
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

<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty name="boardbean" property="*"/>
</jsp:useBean>

<%
	// 데이터 베이스 연결		
	BoardDAO bdao = new BoardDAO(); 
	// 해당 게시글의 패스워드 값을 얻어옴
	// num을 기준으로
	String pass = bdao.getPass(boardbean.getNum());
	
	// 기존 패스워드 값-> boardbean.getPassword()과 Update 시 작성했던 password 값이 같은 지 비교
	if(pass.equals(boardbean.getPassword())) {
		// 데이터 수정하는 메서드 호출
		bdao.updateBoard(boardbean); 
		// 수정이 완료되면 전체 게시글 보기
		response.sendRedirect("../reserveMain.jsp?center=board/boardList.jsp");
	} else {
		// 비밀번호가 틀리다면
%>
		<script type="text/javascript">
			alert("비밀번호가 일치하지 않습니다. 다시 확인 후 수정해주세요.");
			history.go(-1);	
		</script>		
<%
	}
	
%>
</body>
</html>