<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test SpringMVC</title>
<link rel="shortcut icon" type="image/x-icon" href="http://spring.io/img/favicon.png"/>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#testJson").click(function(e){
		e.preventDefault();
		var url = this.href;
		var args = {};
		$.post(url, args, function(data){
			for(var i = 0; i < data.length; i++){
				var id = data[i].id;
				var lastName = data[i].lastName;
				
				alert(id + ": " + lastName);
			}
		});
		return false;
	});
})
</script>
</head>
<body>
	<br><br>
	<a href="${pageContext.request.contextPath }/springmvc/responsestatus?i=10">test ResponseStatusExceptionResolver</a>
	
	<br><br>
	<a href="${pageContext.request.contextPath }/springmvc/exception?i=10">test ExceptionHandlerExceptionResolver</a>
	
	<br><br>
	<form action="${pageContext.request.contextPath }/springmvc/fileupload" method="post" enctype="multipart/form-data">
		文件：<input type="file" name="file" />
		<input type="submit" value="test FileUpload" />
	</form>
	
	<br><br>
	<a href="${pageContext.request.contextPath }/springmvc/responseentity">test ResponseEntity </a>

	<br><br>
	<a href="${pageContext.request.contextPath }/springmvc/byties">test Byties</a>

	<br><br>
	<form action="${pageContext.request.contextPath }/springmvc/converter" method="post" enctype="multipart/form-data">
		文件：<input type="file" name="file" />
		描述：<input type="text" name="desc" />
		<input type="submit" value="test HttpMessageConverter" />
	</form>

	<br/><br/>
	<a id="testJson" href="${pageContext.request.contextPath }/springmvc/json">test Json</a>

	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/redirect">test Redirect</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/view">test View</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/viewandviewresolver">test ViewAndViewResolver</a>
	
	<br/><br/>
	<!--  
		模拟修改操作
		1. 原始数据为: 1, Tom, 123456,zpb@163.com,12
		2. 密码不能被修改.
		3. 表单回显, 模拟操作直接在表单填写对应的属性值
	-->
	<form action="${pageContext.request.contextPath }/springmvc/modelattribute" method="post">
		<input type="hidden" name="id" value="1" />
		<input type="text" name="username" value="Tom" /><br/>
		<input type="text" name="email" value="zpb@163.com" /><br/>
		<input type="text" name="age" value="12" /><br/>
		<input type="submit" value="Test ModelAttribute" />
	</form>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/sessionattributes">test SessionAttributes</a>

	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/map">test Map</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/modelandview">test ModelAndView</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/servletapi">test ServletAPI</a>

	<br/><br/>
	<form action="${pageContext.request.contextPath }/springmvc/pojo" method="post">
		<input type="text" name="username" placeholder="username" /><br/>
		<input type="password" name="username" placeholder="password" /><br/>
		<input type="text" name="email" placeholder="email" /><br/>
		<input type="text" name="age" placeholder="age" /><br/>
		<input type="text" name="adress.province" placeholder="province" /><br/>
		<input type="text" name="adress.city" placeholder="city" /><br/>
		<input type="submit" value="test Pojo" />
	</form>

	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/cookievalue">test CookieValue</a>

	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/requestheader">test requestHeader</a>

	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/requestparam?username=xiaozhou&age=112">test requestParam</a>

	<br/><br/>
	<form action="${pageContext.request.contextPath }/springmvc/rest" method="post">
		<input type="hidden" name="_method" value="delete" />
		<input type="submit" value="test rest delete" />
	</form>
	<form action="${pageContext.request.contextPath }/springmvc/rest" method="post">
		<input type="hidden" name="_method" value="put" />
		<input type="submit" value="test rest put" />
	</form>
	<form action="${pageContext.request.contextPath }/springmvc/rest" method="post">
		<input type="submit" value="test rest post" />
	</form>
	<form action="${pageContext.request.contextPath }/springmvc/rest/110" method="get">
		<input type="submit" value="test rest get" />
	</form>

	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/pathvariable/123">test PathVariable</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/antpath/mvc/abc">test AntPath</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/paramsandheaders?name=xiaozhou&age=10">test ParamsAndHeaders</a>
	
	<br/><br/>
	<form action="${pageContext.request.contextPath }/springmvc/method" method="post">
		<input type="submit" value="test requestMethod" />
	</form>
	<a href="${pageContext.request.contextPath }/springmvc/method">test requestMethod</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/springmvc/mapping">test requestMapping</a>
	
	<br/><br/>
	<a href="${pageContext.request.contextPath }/hello">hello world</a>
	
</body>
</html>