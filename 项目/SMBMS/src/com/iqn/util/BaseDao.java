package com.iqn.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
	/**
	 * 执行增删改
	 * 
	 * @param sql
	 *            :执行的语句
	 * @param param
	 *            :处理问号占位符
	 * @return false:执行失败 true:执行成功
	 */
	public boolean update(String sql, Object[] param) throws Exception {
		Connection con = DBHelper.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		// 处理?号占位符
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
		}
		int i = ps.executeUpdate();
		DBHelper.close(con, ps, null);
		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param sql
	 *            执行的语句
	 * @param param
	 *            处理?号占位符
	 * @param classz
	 *            要封装到那一个bean
	 * @return 返回List集合(保存的是查询表的数据)
	 * 		       如果查到了 则size()>0 反之=0
	 * @throws Exception
	 */
	public List queryAll(String sql, Object[] param, Class<?> classz)
			throws Exception {
		Connection con = DBHelper.getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
					ps.setObject(i + 1, param[i]);
			}
		}
		ResultSet rs = ps.executeQuery();
		List list = new ArrayList();
		while (rs.next()) {
			list.add(getObject(rs, classz));
		}
		DBHelper.close(con, ps, rs);
		return list;
	}

	public <T> T getObject(ResultSet rs, Class<T> c) throws Exception {
		T proxyObj = (T) c.newInstance();
		Field[] fArray = c.getDeclaredFields();
		for (int i = 0; i < fArray.length; i++) {
			String fName = fArray[i].getName();
			fName = fName.substring(0, 1).toUpperCase()
					+ fName.substring(1, fName.length());
			String mName = "set" + fName;
			Method method = c.getMethod(mName, fArray[i].getType());
			method.invoke(proxyObj, rs.getObject(i + 1));
		}
		return proxyObj;
	}
}
