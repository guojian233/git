package com.iqn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iqn.bean.Bill;
import com.iqn.bean.Provider;
import com.iqn.bean.User;
import com.iqn.util.BaseDao;
import com.iqn.util.DBHelper;
import com.iqn.util.PageInfo;

/**
 * 用户dao
 */
public class UserDao extends BaseDao{

	//查询
	public User login(User user){
		String sql = "select * from smbms_user where loginname = ? and loginpwd = ?";
		Object[] param = new Object[]{user.getLoginName(),user.getPassword()};
		List<User> list = null;
		try {
			list =this.queryAll(sql, param, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.size()==0?null:list.get(0);
	}
	// 查询所有用户数据
	public List<User> queryBypage(Integer start){
		String sql="SELECT * FROM smbms_user limit ? ,?"	;
		Object[] param=new Object[]{start,PageInfo.User_NUM};
		List<User> list=null;
		try {
			list=this.queryAll(sql, param, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//查询用户数量
	public int getCount() throws SQLException{
		String sql = "select count(1) from smbms_user";
		Connection con = DBHelper.getConnection();
		ResultSet rs = con.prepareStatement(sql).executeQuery();
		rs.next();
		int i = rs.getInt(1);
		return i;
	} 
	//添加用户
	public boolean save(User user) throws Exception{
		String sql="INSERT INTO  smbms_user(NAME,loginname,loginpwd,gender,birthdate,phone,addree,user_type) values(?,?,?,?,?,?,?,?)";
		Object [] param= new Object[]{user.getName(),user.getLoginName(),user.getPassword(),user.getGender(),user.getBirthDate(),user.getPhone(),user.getAddress(),user.getUserType()};
		boolean isOk=this.update(sql, param);
		return isOk;
	}
	//通过id查询登录的密码
	public User querypwdByid(int id) throws Exception{
		String sql="SELECT loginpwd FROM smbms_user WHERE user_id=?";
		Object[] param=new Object[]{id};
		List<User> list=this.queryAll(sql, param,User.class);
		return list.size()==0?null:list.get(0);
	}
	//修改密码
	public boolean updatepwd(User user) throws Exception{
		String sql="update smbms_user set loginPwd=?";
		Object[] param=new Object[]{user.getPassword()};
		boolean isOk=this.update(sql, param);
		return isOk;
	}
	//条件查询
	public List<User> queryByName(User user,Integer start) {
		String sql="select *from smbms_user where name like? limit ?,?";
		Object[]param=new Object[]{"%"+user.getName()+"%",start,PageInfo.User_NUM};
		List list=null;
		try {
			list = this.queryAll(sql, param, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//条件查询获取总数量
	public int getCount2(User user) throws SQLException{
		String sql="select count(1) from smbms_user where name like ? ";
		Connection con = DBHelper.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%"+user.getName()+"%");
		ResultSet rs=ps.executeQuery();
		rs.next();
		int i=rs.getInt(1);
		return i;
	}
	//修改用户数据
	public boolean UpdateById(User user) throws Exception{
		String sql="update smbms_user set name=?,gender=?,birthDate=?,phone=?,addree=?,user_type=? where user_id=?";
		Object [] param=new Object[]{user.getName(),user.getGender(),user.getBirthDate(),user.getPhone(),user.getAddress(),user.getUserType(),user.getUserId()};
		boolean isOk=this.update(sql, param);
		return isOk;
	}
	//通过id查询数据
	public User queryById(int id) throws Exception{
		String sql="select *from smbms_user where user_id=?";
		Object[] param=new Object[]{id};
		List list = this.queryAll(sql,param,User.class);
		if(list.size()==0){
			return null;
		}else{
			return(User)list.get(0);//查询那一行的信息
		}
	}
	//t通过id删除所有用户信息
	public boolean delectByid(int id) throws Exception{
		String sql="DELETE FROM smbms_user WHERE user_id=?";
		Object[] param=new Object[]{id};
		boolean isOk=this.update(sql, param);
		return isOk;
	}

	public static void main(String[] args) {
		UserDao dao=new UserDao();
		//User user=new User();
		try {
			System.out.println(dao.querypwdByid(0).getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
