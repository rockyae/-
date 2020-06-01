<%@page import="com.xxx.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
</head>
	<body style="text-align: center;">
	<h1>用户信息</h1>
	<img src="${user.head }" width="200px" height="200px">
	<h2>用户名：${user.uname }</h2>
	<h2>密码：${user.upwd }</h2>
	<h2>昵称：${user.nick }</h2>
	<h2>
	<%
		User user = (User)session.getAttribute("user");
		if(user != null && user.getSex() == 1){
		out.print("性别：男");
		}else if(user != null && user.getSex() != 1){
		out.print("性别：女");
		}
	%>
	</h2>
	<h2>年龄：${user.age }</h2>
	<h2>地址：${user.address }</h2>
</body>
</html>