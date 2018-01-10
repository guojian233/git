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
import com.iqn.bean.Bill;
import com.iqn.bean.BillCAS;
import com.iqn.dao.BillDao;
import com.iqn.dao.SupplierDao;
import com.iqn.util.PageInfo;

@SuppressWarnings("serial")
public class BillServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	/**
	 * 所有访问bill的请求都会发送一个m的 
	 * 参数 1: 代表分页查询。 并且会传第二个参数page,代表了第几页的数据.
	 * 	如果page为null代表了第一页 
	 * 参数 2: 参数1的基础上添加组合查询 
	 * 参数 3: 添加账单信息
	 * 参数 4: 按照用户传入的id查询该条数据,数据保存到request,然后将页面跳转到billUpdate.jsp
	 * 参数 5: 修改用户传入的信息按照id
	 * m=6：根据供应商查出商品供给并封装成json
	 * m=7:删除用户
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取分页条件m
		Integer m = Integer.valueOf(request.getParameter("m"));
		switch (m) {
		case 1:
			// 分页：
			try {
				m1(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			// 条件分页
			try {
				m2(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:
			// 保存数据
			try {
				m3(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			// 保存数据
			try {
				m4(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 5:
			// 保存数据
			try {
				m5(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 6:
			// 根据供应商查出商品供给并封装成json
			try {
				m6(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 7:
			try {
				m7(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private BillDao dao = new BillDao();

	// m1方法
	private void m1(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		// 1：获取page参数（页号）
		// 获取页号
		String page = request.getParameter("page");
		int page2 = (page == null ? 1 : Integer.valueOf(page));
		/*
		 * Integer page=Integer.valueOf(request.getParameter("page")); int
		 * page2=(page==null?1:page.intValue());
		 */// 问题2
		// 获取返回值
		// List<Bill> list = dao.queryByPage((page2-1)*PageInfo.BILL_NUM);
		// 2：调用dao获取（1：总页数，2：查询结果）
		// 总页数 清楚一点：总页数等于总数量/每页的显示数量
		int total = dao.getCount();// 总数量
		int totalpage = total / PageInfo.BILL_NUM;
		if (totalpage % PageInfo.BILL_NUM != 0||total<10) {
			totalpage += 1;
		}// 获得总页数
		// 查询结果获取返回值
		List<Bill> list = dao.queryByPage((page2 - 1) * PageInfo.BILL_NUM);
		// 3：保存数据
		request.setAttribute("list", list);// 保存list数据
		request.setAttribute("page", page2);// 保存页号
		request.setAttribute("total", totalpage);// 保存总页数
		// 4；跳转页面
		request.getRequestDispatcher("/bill/billList.jsp").forward(request,
				response);
	}

	private Bill bill = new Bill();

	// 条件分页 m2:
	private void m2(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		// 1:接收前台传来的三个参数
		String tradeName = request.getParameter("productName");// 商品名
		String supplier = request.getParameter("proName");// 供应商名
		int payment = Integer.valueOf(request.getParameter("payed"));// 是否付款
		// 2:为dao准备参数
		bill.setTradeName(tradeName);
		bill.setSupplier(supplier);
		bill.setPayment(payment);
		// 获取页号
		String page = request.getParameter("page");
		int page2 = (page == null ? 1 : Integer.valueOf(page));
		// 总页数 清楚一点：总页数等于总数量/每页的显示数量
		int total = dao.getCount2(bill);// 总数量
		int totalpage = total / PageInfo.BILL_NUM;
		if (totalpage % PageInfo.BILL_NUM != 0||total<10) {
			totalpage += 1;
		}// 获得总页数
		// 返回list的查询结果
		List<Bill> list = dao.queryByPage2(bill, (page2 - 1)
				* PageInfo.BILL_NUM);
		// 3：保存数据
		request.setAttribute("list", list);// 保存list数据
		request.setAttribute("page", page2);// 保存页号
		request.setAttribute("total", totalpage);// 保存总页数
		request.setAttribute("bill", bill);// 保存用户搜索的条件
		// 4；跳转页面
		request.getRequestDispatcher("/bill/billList.jsp").forward(request,
				response);
	}

	private static SupplierDao dao2=new SupplierDao();

	// 添加数据
	private void m3(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 1:接收前台的数据
		Bill bill = new Bill();
		bill.setTradeName(request.getParameter("tradeName"));
		bill.setUnit(request.getParameter("unit"));
		bill.setNum(request.getParameter("num"));
		bill.setAmount(request.getParameter("amount"));
		bill.setSupplier(request.getParameter("supplier"));
		bill.setPayment(Integer.valueOf(request.getParameter("payment")));
		PrintWriter out = response.getWriter();
		boolean isOk = dao.save(bill);
		if(isOk){
			out.print("<script>");
			out.print("alert('添加成功');");
			out.print("location.href='/SMBMS/bill?m=1';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('添加失败');");
			out.print("location.href='/SMBMS/sup?m=0';");
			out.print("</script>");
		}
	}
	/**
	 * 
	 * java代码中/表示webroot目录
	 * jsp页面/表示在webapps目录
	 */
	private void m4(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//接收id
		Integer id=Integer.valueOf(request.getParameter("id"));
		//调用dao方法查询信息
		Bill bill=dao.queryByid(id);
		//保存到request里面
		request.setAttribute("bill", bill);
		//保存下拉列表的信息
		request.setAttribute("suplist",dao2.queryAllname());
		//转发
		request.getRequestDispatcher("/bill/billUpdate.jsp").forward(request, response);
	}
	private void m5(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//修改用户传入的信息按照id
		Bill bill = new Bill();
		bill.setId(Integer.valueOf(request.getParameter("id")));
		bill.setTradeName(request.getParameter("tradeName"));
		bill.setUnit( request.getParameter("unit"));
		bill.setNum(request.getParameter("num"));
		bill.setAmount(request.getParameter("amount"));
		bill.setSupplier( request.getParameter("supplier"));
		bill.setPayment(Integer.valueOf(request.getParameter("payment")));
		PrintWriter out = response.getWriter();
		boolean isOk = dao.updateByid(bill);
		if(isOk){
			out.print("<script>");
			out.print("alert('修改成功');");
			out.print("location.href='/SMBMS/bill?m=1';");//修改成功返回查询所有数据的地址m=1
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('修改失败');");
			out.print("location.href='/SMBMS/bill?m=4';");//修改失败返回m=4修改的页面
			out.print("</script>");
		}
	}
	private void m6(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//加载所有供应商名称
		List<BillCAS> list = dao.queryCAS();
		PrintWriter out = response.getWriter();
		//创建Gson对象
		Gson gs = new Gson();
		//传入任意对象到toJson方法.返回该对象的json字符串
		String json = gs.toJson(list);
		out.print(json);
	}
	//删除账单信息
	private  void m7(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取id
		Integer id=Integer.valueOf(request.getParameter("id"));
		boolean isOk=dao.delectByid(id);
		PrintWriter out=response.getWriter();
		if(isOk){
			out.print("<script>");
			out.print("alert('删除成功');");
			out.print("location.href='/SMBMS/bill?m=1';");
			out.print("</script>");
		}else{
			out.print("<script>");
			out.print("alert('删除失败');");
			out.print("location.href='/SMBMS/bill?m=4';");
			out.print("</script>");
		}	
	}
}