<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>빛나는너</title>
</head>
<body>
<!-- 시작 페이지  -->
<% 
	String center = request.getParameter("center");
	if(center == null) {
		center = "main.jsp";
	}
%>

	<section>
		<jsp:include page="header.jsp"/>
	</section>
		<section>
		<jsp:include page="<%= center %>"/>
	</section>
		<section>
		<jsp:include page="footer.jsp"/>
	</section>
</body>
</html>