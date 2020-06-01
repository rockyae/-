package com.xxx.servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xxx.po.User;

/**
* 用户登录
*
*/
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void service(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
// 解决后台接收参数中文乱码问题，在使用request对象之前设置
request.setCharacterEncoding("UTF-8");
// 接收参数
String uname = request.getParameter("uname");
String upwd = request.getParameter("upwd");
// 业务逻辑
// 非空判断
// 用户名为空
if("".equals(uname.trim()) || uname == null){
// 往request作用域中存数据，用来回显
request.setAttribute("uname", uname);
request.setAttribute("upwd", upwd);
// 请求转发回login.jsp，回显用户名和密码，并提示用户
request.setAttribute("msg", "用户名不能为空！");
request.getRequestDispatcher("login.jsp").forward(request,
response);
return;
}
// 密码为空
if("".equals(upwd.trim()) || upwd == null){
// 往request作用域中存数据，用来回显
request.setAttribute("uname", uname);
request.setAttribute("upwd", upwd);
// 请求转发回login.jsp，回显用户名和密码，并提示用户
request.setAttribute("msg", "密码不能为空！");
request.getRequestDispatcher("login.jsp").forward(request,
response);
return;
}
// 根据用户名查询数据库中的用户信息
User user = null;
Connection conn = null;
PreparedStatement sta = null;
ResultSet res = null;
try {
// 加载驱动
Class.forName("com.mysql.jdbc.Driver");
//获取连接
conn =
DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root",null);
//编写sql
String sql = "select * from user1 where uname = ?";
//预编译
sta = conn.prepareStatement(sql);
//设置参数
sta.setString(1, uname);
//执行查询,得到结果集
res = sta.executeQuery();
//分析结果集
while(res.next()){
user = new User();
user.setId(res.getInt("id"));
user.setUname(uname);
user.setUpwd(res.getString("upwd"));
user.setNick(res.getString("nick"));
user.setHead(res.getString("head"));
user.setSex(res.getInt("sex"));
user.setAge(res.getInt("age"));
user.setAddress(res.getString("address"));
}
} catch (Exception e) {
e.printStackTrace();
} finally {
try {
if(conn != null){
conn.close();
}
if(sta != null){
sta.close();
}
if(res != null){
res.close();
}
} catch (Exception e) {
e.printStackTrace();
}
}
//判断用户是否存在
if(user == null){
//用户不存在
//往request作用域中存数据，用来回显
request.setAttribute("uname", uname);
request.setAttribute("upwd", upwd);
//请求转发回login.jsp，回显用户名和密码，并提示用户
request.setAttribute("msg", "用户名不存在！");
request.getRequestDispatcher("login.jsp").forward(request,
response);
return;
}
//判断密码是否正确
if(user.getUpwd().equals(upwd)){
//登录成功
	// 存Cookie，一般用户名和密码直接存到一个Cookie里，获取的时候字符串分割就行了
	Cookie cookie = new Cookie("user",uname+"-"+upwd);
	// 设置3天过期时间 单位 秒
	cookie.setMaxAge(3*24*60*60);
	response.addCookie(cookie);
	// 存Session
	// 获取session
	HttpSession session = request.getSession();
	// 往session中存user
	session.setAttribute("user", user);
	// 重定向跳转到首页
	response.sendRedirect("index.jsp");
	return;
	}
	// 登录失败
	// 往request作用域中存数据，用来回显
	request.setAttribute("uname", uname);
	request.setAttribute("upwd", upwd);
	// 请求转发回login.jsp，回显用户名和密码，并提示用户
	request.setAttribute("msg", "用户名或密码错误！");
	request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}