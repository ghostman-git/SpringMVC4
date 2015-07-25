<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Emp List</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.1.min.js"></script>
<!--  
	SpringMVC 处理静态资源:
	1. 为什么会有这样的问题:
	优雅的 REST 风格的资源URL 不希望带 .html 或 .do 等后缀
	若将 DispatcherServlet 请求映射配置为 /, 
	则 Spring MVC 将捕获 WEB 容器的所有请求, 包括静态资源的请求, SpringMVC 会将他们当成一个普通请求处理, 
	因找不到对应处理器将导致错误。
	2. 解决: 在 SpringMVC 的配置文件中配置 <mvc:default-servlet-handler/>
-->
<script type="text/javascript">
	$(function(){
		$(".delete").click(function(){
			var href = $(this).attr("href");
			$("form").attr("action", href).submit();
			return false;
		});
	})
</script>
</head>
<body>
	<form action="" method="POST">
		<input type="hidden" name="_method" value="DELETE"/>
	</form>

	<c:choose>
		<c:when test="${empty requestScope.empLst }">没有员工</c:when>
		<c:otherwise>
			<table border="1" cellpadding="10" cellspacing="0">
				<tr>
					<th>ID</th>
					<th>LastName</th>
					<th>Email</th>
					<th>Gender</th>
					<th>Department</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${requestScope.empLst }" var="emp">
					<tr>
						<td>${emp.id }</td>
						<td>${emp.lastName }</td>
						<td>${emp.email }</td>
						<td>${emp.gender == 0 ? 'Female' : 'Male' }</td>
						<td>${emp.department.departmentName }</td>
						<td><a href="${pageContext.request.contextPath }/emp/input/${emp.id}">Edit</a></td>
						<td><a class="delete" href="${pageContext.request.contextPath }/emp/edit/${emp.id}">Delete</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	
	<br><br>
	<a href="${pageContext.request.contextPath }/emp/input">Add Emp</a>
</body>
</html>