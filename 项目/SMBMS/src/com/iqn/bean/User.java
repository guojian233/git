package com.iqn.bean;

import java.util.Date;

/**
 * 用户
 * @author bdqn
 *
 */
public class User {
	//用户编号
	private int userId;
	//用户名称
	private String name;
	//登陆账号
	private String loginName;
	private String password;
	//1 男 2 女
	private int gender;
	//出生日期
	private Date birthDate;
	//电话
	private String phone;
	//地址
	private String address;
	//用户类别  1 管理员  2 普通员工 3 经理
	private int userType;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}	

}
