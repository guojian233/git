package com.iqn.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.iqn.bean.Provider;
import com.iqn.dao.SupplierDao;
import com.iqn.util.PageInfo;
import com.mysql.jdbc.LoadBalancingConnectionProxy;

public class SupplierServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SupplierServlet() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 获取m, 
	 * m=0.加载所有的供应商名字.直接跳转到账单添加页面
	 * m=-1.加载所有的供应商名字AJAX,以out打印
	 * m=1时添加供应商数据
	 * m=2时通过id查询数据并保存在request中，然后转发到update页面
	 * m=3时数据的更新或者修改
	 * m=7时分页查询
	 * m=8条件分页
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer m = Integer.valueOf(request.getParameter("m"));
		switch (m) {
		case -1:
			try {
				loadSelected1(request,response);//加载下拉菜单
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 0:
			try {
				loadSelected2(request,response);//加载下拉菜单
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				m1(request,response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 2:
			try {
				m2(request,response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 3:
			try {
				m3(request,response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 4:
			try {
				m4(request,response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;

		case 7:
			try {
				m7(request,response);//供应商分页
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 8:
			try {
				m8(request,response);//条件查询
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
	private SupplierDao dao = new SupplierDao();
	private void loadSelected1(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		//加载所有供应商名称
		List<String> list = dao.queryAllname();
		PrintWriter out = response.getWriter();
		//创建Gson对象
		Gson gs = new Gson();
		//传入任意对象到toJson方法.返回该对象的json字符串
		String json = gs.toJson(list);
		out.print(json);
	}
	private void loadSelected2(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		//加载所有供应商名称
		List<String> list = dao.queryAllname();
		//保存到request
		request.setAttribute("suplist", list);
		//跳转到指定的页面(账单添加页面)
		request.getRequestDispatcher("/bill/billAdd.jsp").forward(request, response);
	}
	//分页查询m=7
	private void m7(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		//获取page参数
		String page=request.getParameter("page");
		Integer page2=page==null?1:Integer.valueOf(page);
		//获取总数量
		int total=dao.getCount();
		//获取页数
		int totalpage=total/PageInfo.SUP_NUM;
		if(totalpage%PageInfo.SUP_NUM!=0){
			totalpage+=1;
		}
		//接收返回值
		List<Provider> list=dao.queryBypage((page2-1)*PageInfo.SUP_NUM);
		//保存数据到页面
		request.setAttribute("list", list);
		request.setAttribute("page", page2);
		request.setAttribute("total", totalpage);
		//转发
		request.getRequestDispatcher("/provider/providerList.jsp").forward(request, response);
	}
	//条件分页查询
	private void m8(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		//获取前台传入的参数
		String proName=request.getParameter("proName");
		//为dao准备参数
		Provider pro=new Provider();
		pro.setName(proName);
		//获取page参数
		String page=request.getParameter("page");
		Integer page2=page==null?1:Integer.valueOf(page);
		//获取总数量
		int total=dao.getCount2(pro);
		//获取页数
		int totalpage=total/PageInfo.SUP_NUM;
		if(totalpage%PageInfo.SUP_NUM!=0){
			totalpage+=1;
		}
		//调用dao方法
		List<Provider>list=dao.queryBysupname(pro, (page2-1)*PageInfo.SUP_NUM);
		//保存数据
		request.setAttribute("list", list);
		request.setAttribute("page", page2);
		request.setAttribute("total", totalpage);
		request.setAttribute("pro", pro);
		//转发
		request.getRequestDispatcher("/provider/providerList.jsp").forward(request, response);

	}
	//添加数据（供应商）
	private void m1(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Provider pro=new Provider();
		pro.setName(request.getParameter("proName"));//供应商
		pro.setLinkman(request.getParameter("linkman"));//联系人
		pro.setPhone(request.getParameter("phone"));//联系电话
		pro.setAddress(request.getParameter("address"));//联系地址
		pro.setFax(request.getParameter("fax"));//传真
		pro.setDescribe(request.getParameter("desc"));//描述
		boolean isOk=dao.save(pro);
		PrintWriter out=response.getWriter();
		if(isOk){
			out.print("<script>");
			out.print("alert('添加成功');");
			out.print("location.href='/SMBMS/sup?m=7';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('添加失败');");
			out.print("location.href='/SMBMS/sup?m=1';");
			out.print("</script>");
		}
	}
	//m=2时通过id查询数据并保存在request中，然后转发到update页面
	private void m2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取id
		Integer id=Integer.valueOf(request.getParameter("id"));
		System.out.println(id.toString());
		//调用dao方法查询信息
		Provider provider=dao.queryByid(id);
		//保存到request里面
		request.setAttribute("provider", provider);
		//转发到
		request.getRequestDispatcher("/provider/providerUpdate.jsp").forward(request, response);
	}
	//m=3修改数据
	//private static Provider pro=new Provider();
	private void m3(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取id
		Provider pro=new Provider();
		pro.setId(Integer.valueOf(request.getParameter("id")));
		pro.setName(request.getParameter("name"));
		pro.setLinkman(request.getParameter("linkman"));
		pro.setPhone(request.getParameter("phone"));
		pro.setAddress(request.getParameter("proAddress"));
		pro.setFax(request.getParameter("fax"));
		pro.setDescribe(request.getParameter("describe"));
		boolean isOk=dao.updateById(pro);
		PrintWriter out = response.getWriter();
		if(isOk){
			out.print("<script>");
			out.print("alert('修改成功');");
			out.print("location.href='/SMBMS/sup?m=7';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('修改失败');");
			out.print("location.href='/SMBMS/sup?m=3';");
			out.print("</script>");
		}

	}
	//m=4删除
	private void m4(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取id
		Integer id=Integer.valueOf(request.getParameter("id"));
		boolean isOk=dao.delectByid(id);
		PrintWriter out=response.getWriter();
		if(isOk){
			out.print("<script>");
			out.print("alert('删除成功');");
			out.print("location.href='/SMBMS/sup?m=7';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('删除失败');");
			out.print("location.href='/SMBMS/provider/providerUpdate.jsp';");
			out.print("</script>");
		}	
	}
	public void init() throws ServletException {
		// Put your code here
	}

}
