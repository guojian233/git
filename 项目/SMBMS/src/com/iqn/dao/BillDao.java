package com.iqn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.iqn.bean.Bill;
import com.iqn.bean.BillCAS;
import com.iqn.util.BaseDao;
import com.iqn.util.DBHelper;
import com.iqn.util.PageInfo;

public class BillDao extends BaseDao {
	// m=1:分页查询
	@SuppressWarnings("unchecked")
	public List<Bill> queryByPage(Integer start) {
		String sql = "select * from bill limit ?,?";
		Object[] param = new Object[] { start, PageInfo.BILL_NUM };
		List<Bill> list = null;
		try {
			list = this.queryAll(sql, param, Bill.class);//第一个问题
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//m=1:查询总数量
	public int getCount() throws SQLException{
		String sql = "select count(1)  from bill ";
		Connection con = DBHelper.getConnection();
		ResultSet rs = con.prepareStatement(sql).executeQuery();
		rs.next();
		int i = rs.getInt(1);
		return i;
	} 
	//m=2条件查询
	public List<Bill> queryByPage2(Bill bill,Integer start) {
		String sql = "select * from bill where tradeName like ? and supplier like ? and payment = ? limit ?,? ";
		Object[] param = new Object[] {"%"+bill.getTradeName()+"%","%"+bill.getSupplier()+"%",bill.getPayment(), start, PageInfo.BILL_NUM };
		List<Bill> list = null;
		try {
			list = this.queryAll(sql, param, Bill.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//保存账单信息m=3
	public boolean save(Bill bill) throws Exception{
		String sql = "INSERT INTO bill (tradeName,unit,num,amount,supplier,payment,createDate)VALUES(?,?,?,?,?,?,?)";
		Object[] param = new Object[]{bill.getTradeName(),bill.getUnit(),bill.getNum(),bill.getAmount(),bill.getSupplier(),bill.getPayment(),new Date()};
		boolean isOk = this.update(sql, param);
		return isOk;
	}
	//通过id查询商品bill详细信息m=4
	public Bill queryByid(int id) throws Exception{
		String sql = "select * from bill where id = ?";
		Object[] param=new Object[]{id};
		List list = this.queryAll(sql,param,Bill.class);
		if(list.size()==0){
			return null;
		}else{
			return(Bill)list.get(0);//查询那一行的信息
		}
	}
	//更新的操作m=5
	public boolean updateByid(Bill bill) throws Exception{
		String sql = "update bill set tradeName=?,unit=?,num=?,amount=?,supplier=?,payment=? where id=?";
		Object[] param=new Object[]{bill.getTradeName(),bill.getUnit(),bill.getNum(),bill.getAmount(),bill.getSupplier(),bill.getPayment(),bill.getId()};
		boolean isOk = this.update(sql, param);
		return isOk;
	}
	//根据供应商查出商品数
	
	public List<BillCAS> queryCAS() throws Exception{
		String sql="SELECT COUNT(1),supplier AS 供应商 FROM bill GROUP BY supplier;";
		List<BillCAS> list=this.queryAll(sql, null, BillCAS.class);
		return list;
		
		
	}
	//条件查询的获取总数量的方法
	public int getCount2(Bill bill) throws SQLException {
		String sql = "select count(1)  from bill where tradeName like ? and supplier like ? and payment = ? ";
		Connection con = DBHelper.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,"%"+bill.getTradeName()+"%");
		ps.setString(2,"%"+bill.getSupplier()+"%");
		ps.setInt(3, bill.getPayment());
		ResultSet rs=ps.executeQuery();
		rs.next();
		int i=rs.getInt(1);
		return i;
	} 
   //根据id删除
	public boolean delectByid(int id) throws Exception{
		String sql="DELETE FROM bill WHERE id=?";
		Object[] param=new Object[]{id};
		boolean isOk=this.update(sql, param);
		return isOk;
	}
	public static void main(String[] args) {
		BillDao dao=new BillDao();
		List<Bill> list=dao.queryByPage(0);
		for (Bill bill : list) {
			System.out.println(bill.getId());
		}
	}


}
