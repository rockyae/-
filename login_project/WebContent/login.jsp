<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body style="text-align: center;">
	<h1>用户登录</h1>
	<form action="login" method="post">
		账户：<input type="text" id="uname" name="uname" value="${uname }"/><br/><br/>
	 	密码：<input type="password" id="upwd" name="upwd" value="${upwd }"/><br/><br/>
	 	<input type="submit" value="登录"  onclick="return checkForm()"/><br/><br/>
	 	<!-- 回显提示信息 -->
	 	<span id="msg" style="color:red"></span>
	</form>
</body>
	<script type="text/javascript" src="statics/js/jquery-3.5.1.min.js"></script>
	<script type="text/javascript">
		function checkForm(){
			// 获取参数
			var uname = $("#uname").val();
			var upwd = $("#upwd").val();
			// 参数非空判断
			if(uname == null || uname == ""){
				$("#msg").html("姓名不能为空！")
				return false;
			}
			if(upwd == null || upwd == ""){
				$("#msg").html("密码不能为空！");
				return false;
			}
			return true;
		}
	</script>
</html>