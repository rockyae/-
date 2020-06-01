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
* �û���¼
*
*/
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
protected void service(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
// �����̨���ղ��������������⣬��ʹ��request����֮ǰ����
request.setCharacterEncoding("UTF-8");
// ���ղ���
String uname = request.getParameter("uname");
String upwd = request.getParameter("upwd");
// ҵ���߼�
// �ǿ��ж�
// �û���Ϊ��
if("".equals(uname.trim()) || uname == null){
// ��request�������д����ݣ���������
request.setAttribute("uname", uname);
request.setAttribute("upwd", upwd);
// ����ת����login.jsp�������û��������룬����ʾ�û�
request.setAttribute("msg", "�û�������Ϊ�գ�");
request.getRequestDispatcher("login.jsp").forward(request,
response);
return;
}
// ����Ϊ��
if("".equals(upwd.trim()) || upwd == null){
// ��request�������д����ݣ���������
request.setAttribute("uname", uname);
request.setAttribute("upwd", upwd);
// ����ת����login.jsp�������û��������룬����ʾ�û�
request.setAttribute("msg", "���벻��Ϊ�գ�");
request.getRequestDispatcher("login.jsp").forward(request,
response);
return;
}
// �����û�����ѯ���ݿ��е��û���Ϣ
User user = null;
Connection conn = null;
PreparedStatement sta = null;
ResultSet res = null;
try {
// ��������
Class.forName("com.mysql.jdbc.Driver");
//��ȡ����
conn =
DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root",null);
//��дsql
String sql = "select * from user1 where uname = ?";
//Ԥ����
sta = conn.prepareStatement(sql);
//���ò���
sta.setString(1, uname);
//ִ�в�ѯ,�õ������
res = sta.executeQuery();
//���������
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
//�ж��û��Ƿ����
if(user == null){
//�û�������
//��request�������д����ݣ���������
request.setAttribute("uname", uname);
request.setAttribute("upwd", upwd);
//����ת����login.jsp�������û��������룬����ʾ�û�
request.setAttribute("msg", "�û��������ڣ�");
request.getRequestDispatcher("login.jsp").forward(request,
response);
return;
}
//�ж������Ƿ���ȷ
if(user.getUpwd().equals(upwd)){
//��¼�ɹ�
	// ��Cookie��һ���û���������ֱ�Ӵ浽һ��Cookie���ȡ��ʱ���ַ����ָ������
	Cookie cookie = new Cookie("user",uname+"-"+upwd);
	// ����3�����ʱ�� ��λ ��
	cookie.setMaxAge(3*24*60*60);
	response.addCookie(cookie);
	// ��Session
	// ��ȡsession
	HttpSession session = request.getSession();
	// ��session�д�user
	session.setAttribute("user", user);
	// �ض�����ת����ҳ
	response.sendRedirect("index.jsp");
	return;
	}
	// ��¼ʧ��
	// ��request�������д����ݣ���������
	request.setAttribute("uname", uname);
	request.setAttribute("upwd", upwd);
	// ����ת����login.jsp�������û��������룬����ʾ�û�
	request.setAttribute("msg", "�û������������");
	request.getRequestDispatcher("login.jsp").forward(request, response);
	}
}