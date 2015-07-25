<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	success.jsp
	
	<br><br>
	time:${time }
	
	<br><br>
	names:${names }
	
	<br><br>
	request user:${requestScope.user }<br>
	request school:${requestScope.school }<br>
	session user:${sessionScope.user }<br>
	session school:${sessionScope.school }
	
	<br><br>
	ModelAttribute user:${requestScope.modelUser }
	
	<br><br>
	<fmt:message key="i18n.username"></fmt:message><br>
	<fmt:message key="i18n.password"></fmt:message>
</body>
</html>