package com.iqn.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iqn.bean.User;
import com.iqn.dao.UserDao;

public class UserpwdServler extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserpwdServler() {
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
		this.doPost(request, response);
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
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			Integer m = Integer.valueOf(request.getParameter("m"));
			switch (m) {
			case 1:
				//修改密码
				try {
					m1(request,response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
		private static UserDao dao=new UserDao();
		//修改密码
		private void m1(HttpServletRequest request, HttpServletResponse response) throws Exception{
			//获取前台传入的密码
			String oldpwd=request.getParameter("oldPassword");
			String newpwd=request.getParameter("newPassword");
			//判断旧密码的正确
			User user=(User)request.getSession().getAttribute("user");
			String pwd=user.getPassword();
			//获得session中保存的旧密码
			PrintWriter out=response.getWriter();
			if(pwd.equals(oldpwd)){
				//密码正确则更新
				user.setPassword(newpwd);
				boolean isOk=dao.updatepwd(user);
				if(isOk){
					request.getSession().invalidate();
					out.print("<script>");
					out.print("alert('密码更新成功');");
					out.print("top.location='/SMBMS/login.jsp';");
					out.print("");
					out.print("</script>");
				}else{
					out.print("<script>");
					out.print("alert('密码更新失败');");
					out.print("location.href='/SMBMS/user/updatePwd.jsp';");
					out.print("</script>");

				}
			}else{
				out.print("<script>");
				out.print("alert('旧密码错误');");
				out.print("location.href='/SMBMS/user/updatePwd.jsp';");
				out.print("</script>");
				
			}

		}
		/**
		 * Initialization of the servlet. <br>
		 *
		 * @throws ServletException if an error occurs
		 */
		public void init() throws ServletException {
			// Put your code here
		}

	}
