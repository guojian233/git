package com.iqn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.internal.bind.TimeTypeAdapter;
import com.iqn.bean.User;
import com.iqn.dao.UserDao;
import com.iqn.util.PageInfo;

public class UserServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UserServlet() {
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
	/**
	 * m=1就是分页查询
	 * m=2添加用户
	 * m=3 条件分页
	 * 按照用户传入的id查询该条数据,数据保存到request,然后将页面跳转到userUpdate.jsp
	 * M=5根据id修改数据
	 * m=6删除数据
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取分页条件m
		Integer m = Integer.valueOf(request.getParameter("m"));
		switch (m) {
		case 1:
			try {
				m1(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:

			try {
				m2(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 3:
			try {
				m3(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case 4:

			try {
				m4(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			try {
				m5(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 6:
			try {
				m6(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
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
	private static UserDao dao=new UserDao();
	private  void m1(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		// 1：获取page参数（页号）
		// 获取页号
		String page = request.getParameter("page");
		int page2 = (page == null ? 1 : Integer.valueOf(page));
		//获取总页数
		int total=dao.getCount();
		int totalpage =total/PageInfo.User_NUM;
		if(totalpage%PageInfo.User_NUM !=0||total<5){
			totalpage+=1;
		}
		//获取返回值
		List<User>list=dao.queryBypage((page2 - 1) * PageInfo.User_NUM);
		// 3：保存数据
		request.setAttribute("list", list);// 保存list数据
		request.setAttribute("page", page2);// 保存页号
		request.setAttribute("total", totalpage);// 保存总页数
		//转发
		request.getRequestDispatcher("/user/userList.jsp").forward(request, response);
	}
	//添加用户
	private void m2(HttpServletRequest request,HttpServletResponse response) throws Exception{
		User user=new User();
		user.setName(request.getParameter("name"));
		user.setLoginName(request.getParameter("loginName"));
		user.setPassword(request.getParameter("password"));
		user.setGender(Integer.valueOf(request.getParameter("gender")));
		SimpleDateFormat formate=new  SimpleDateFormat("yyyy-mm-dd");
		Date date=formate.parse(request.getParameter("birthDate"));
		//user.setBirthDate(request.getParameter("brithDate").replace("-", ""));
		//user.setBirthDate(Timestamp.valueOf(date.toString()));
		/*user.setBirthDate(Timestamp.valueOf(request.getParameter("birthDate")));*/
		//user.setBirthDate(request.getParameter("birthDate"));
		user.setPhone(request.getParameter("phone"));
		user.setAddress(request.getParameter("address"));
		user.setUserType(Integer.valueOf(request.getParameter("userType")));
		PrintWriter out = response.getWriter();
		boolean isOk = dao.save(user);
		if(isOk){
			out.print("<script>");
			out.print("alert('添加成功');");
			out.print("location.href='/SMBMS/user?m=1';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('添加失败');");
			out.print("location.href='/SMBMS/user/userAdd.jsp';");
			out.print("</script>");
		}
	}
	//条件分页
	private void m3(HttpServletRequest request,HttpServletResponse response) throws SQLException, ServletException, IOException{
		//获取page参数
		//1:获取前台的name
		String name=request.getParameter("name");
		// 获取页号
		String page = request.getParameter("page");
		int page2 = (page == null ?1 :Integer.valueOf(page));
		User user=new User();
		user.setName(name);
		//获取总页数
		int total=dao.getCount2(user);
		int totalpage =total/PageInfo.User_NUM;
		if(totalpage%PageInfo.User_NUM!=0||total<5){
			totalpage++;
		}
		//获取返回值
		List<User>list=dao.queryByName(user,(page2 - 1) * PageInfo.User_NUM);
		// 3：保存数据
		request.setAttribute("list", list);// 保存list数据
		request.setAttribute("page", page2);// 保存页号
		request.setAttribute("total", totalpage);// 保存总页数
		request.setAttribute("user", user);//保存用户的搜索条件
		//转发
		request.getRequestDispatcher("/user/userList.jsp").forward(request,response);
	}
	//按照用户传入的id查询该条数据,数据保存到request,然后将页面跳转到userUpdate.jsp
	private void m4(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Integer id=Integer.valueOf(request.getParameter("userId"));
		//保存user
		User user=dao.queryById(id);
		//保存到reqeuest
		request.setAttribute("user", user);
		//跳转
		request.getRequestDispatcher("/user/userUpdate.jsp").forward(request,response);
	}
	//5：修改数据
	private void m5(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//通过用户传入的id修改
		User user=new User();
		user.setUserId(Integer.valueOf(request.getParameter("userId")));
		user.setName(request.getParameter("name"));
		user.setGender(Integer.valueOf(request.getParameter("gender")));
		//获取日期类型
		SimpleDateFormat formate=new  SimpleDateFormat("yyyy-MM-dd");
		Date date=formate.parse(request.getParameter("birthDate"));
		user.setBirthDate(date);
		//user.setBirthDate(request.getParameter("brithDate").replace("-", ""));
		//user.setBirthDate(Timestamp.valueOf(request.getParameter("birthDate")));
		//user.setBirthDate(request.getParameter("birthDate"));
		user.setPhone(request.getParameter("phone"));
		user.setAddress(request.getParameter("address"));
		user.setUserType(Integer.valueOf(request.getParameter("userType")));
		PrintWriter out = response.getWriter();
		boolean isOk = dao.UpdateById(user);
		if(isOk){
			out.print("<script>");
			out.print("alert('更新成功');");
			out.print("location.href='/SMBMS/user?m=1';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('添加失败');");
			out.print("location.href='/SMBMS/user?m=4';");
			out.print("</script>");
		}
	}
	//6:删除用户
	private void m6(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//通过用户传入的id修改
		Integer id=Integer.valueOf(request.getParameter("userId"));
		//不能将当前用户删除（1：获取当前用户）
		User user=(User)request.getSession().getAttribute("user");
		Integer cruId=Integer.valueOf(user.getUserId());//当前用户id
		PrintWriter out = response.getWriter();
		//当前用户和删除的id不同则可以删除
		if(!(cruId.equals(id))){
			boolean isOk = dao.delectByid(id);
			if(isOk){
				out.print("<script>");
				out.print("alert('删除成功');");
				out.print("location.href='/SMBMS/user?m=1';");
				out.print("</script>");
			}else{
				out.print("<script>");
				out.print("alert('删除失败');");
				out.print("location.href='/SMBMS/user?m=4';");
				out.print("</script>");
			}	
		}else{
			out.print("<script>");
			out.print("alert('亲~不能删除当前用户');");
			out.print("location.href='/SMBMS/user/userUpdate.jsp';");
			out.print("</script>");
		}
	}
}
