package com.iqn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iqn.bean.User;
import com.iqn.dao.UserDao;

public class UserLoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserLoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	private UserDao dao = new UserDao();
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接受前台的用户名和密码
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		//接收用户输入的验证码
		String kpg=request.getParameter("kaptcha");
		//获取图片的中的验证码信息
		String sykpg= (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY); 
		//准备参数对象
		User param = new User();
		param.setLoginName(loginName);
		param.setPassword(password);
		User queryUser = dao.login(param);
		//判断查询结果
		if(kpg.equalsIgnoreCase(sykpg)){
			if(queryUser==null){
				//获取打印out对象
				PrintWriter out = response.getWriter();
				out.print("<script>");
				out.print("alert('登录失败,请检查用户信息！');");
				out.print("location.href='/SMBMS'");
				out.print("</script>");
			}
			else{
				//保存到session
				request.getSession().setAttribute("user",queryUser);
				//跳转到主页面
				request.getRequestDispatcher("/frame.jsp").forward(request, response);
			}
		}else{
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('亲~验证码输入有误~~');");
			out.print("location.href='/SMBMS'");
			out.print("</script>");
		}
		
	}

/**
 * Initialization of the servlet. <br>
 *
 * @throws ServletException if an error occurs
 */
public void init() throws ServletException {

}

}
