package com.iqn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Suppliers;
import com.iqn.bean.Bill;
import com.iqn.bean.Provider;
import com.iqn.util.BaseDao;
import com.iqn.util.DBHelper;
import com.iqn.util.PageInfo;
//供应商dao
public class SupplierDao extends BaseDao{
    	//查询供应商名
	public List<String> queryAllname() throws SQLException{
		//创建连接
		Connection con = DBHelper.getConnection();
		//2:sql
		String sql="SELECT NAME FROM suppliers;";
		ResultSet rs=con.prepareStatement(sql).executeQuery();
		List<String>list= new ArrayList<String>();
		while(rs.next()){
			list.add(rs.getString("name"));
		}
		return list;
	}
	//分页查询 m=7
	public List<Provider> queryBypage(Integer start){
		String sql = "select * from suppliers limit ?,?";
		Object[] param=new Object[]{start,PageInfo.SUP_NUM};
		List<Provider> list=null;
		try {
			 list=this.queryAll(sql, param,Provider.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//分页查询,获取总数量
	public int getCount() throws SQLException{
		String sql = "select count(1)  from Suppliers ";
		Connection con = DBHelper.getConnection();
		ResultSet rs = con.prepareStatement(sql).executeQuery();
		rs.next();
		int i = rs.getInt(1);
		return i;
	}
	//条件分页查询
	public List<Provider> queryBysupname(Provider pro,Integer start){
		String sql="SELECT *FROM suppliers WHERE NAME like ? limit ?,?";
		Object[] param=new Object[]{"%"+pro.getName()+"%",start,PageInfo.SUP_NUM};
		List<Provider> list=null;
		try {
			 list=this.queryAll(sql, param,Provider.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//获取条件分页总数量
	public int getCount2(Provider pro) throws SQLException{
		String sql="select count(1) from suppliers where name like?";
		Connection con = DBHelper.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+pro.getName()+"%");
		ResultSet rs=ps.executeQuery();
		rs.next();
		int i=rs.getInt(1);
		return i;
	}
	//保存供应商信息
	public boolean save(Provider pro) throws Exception{
		String sql="INSERT INTO suppliers(NAME,linkman,phone,fax,address,describe)VALUES(?,?,?,?,?,?)";
		Object [] param=new Object[]{pro.getName(),pro.getLinkman(),pro.getPhone(),pro.getFax(),pro.getAddress(),pro.getDescribe()};
		boolean isOk = this.update(sql, param);
		return isOk;
	}
	//通过id查询数据
	public Provider queryByid(int id) throws Exception{
		String sql="select *from suppliers where id=?";
		Object[] param=new Object[]{id};
		List list = this.queryAll(sql,param,Provider.class);
		if(list.size()==0){
			return null;
		}else{
			return(Provider)list.get(0);//查询那一行的信息
		}
	}
	//通过id修改数据
	public boolean updateById(Provider pro) throws Exception{
		String sql="update suppliers set name=?,linkman=?,phone=?,address=?,fax=?,`describe`=?where id=?";
		Object []param=new Object[]{pro.getName(),pro.getLinkman(),pro.getPhone(),pro.getAddress(),pro.getFax(),pro.getDescribe(),pro.getId()};
		boolean isOk=this.update(sql, param);
		return isOk;
	}
	//通过id删除
	public boolean delectByid(int id) throws Exception{
		String sql="DELETE FROM suppliers WHERE id=?";
		Object[] param=new Object[]{id};
		boolean isOk=this.update(sql, param);
		return isOk;
	}
	
	public static void main(String[] args) throws Exception {
		SupplierDao dao=new SupplierDao();
		
		Provider pro=new Provider();
		dao.queryByid(1);
		System.out.println(dao.queryByid(1).getLinkman());
		
	/*	pro.setName("王飞飞");
		List<Provider> list=dao.queryBysupname(pro, 1);
		int i=dao.getCount();
		System.out.println(i);
		for (Provider string : list) {
			System.out.println(string.getLinkman());
		}*/
	}
	
}
