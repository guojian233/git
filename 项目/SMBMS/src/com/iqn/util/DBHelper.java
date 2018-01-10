package com.iqn.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBHelper {
	private static String url;
	private static String user;
	private static String pwd;
	static {
		try {
			Properties pt = new Properties();
			pt.load(new FileInputStream("F://jdbc.properties"));
			url = pt.getProperty("jdbc.url");
			user = pt.getProperty("jdbc.user");
			pwd = pt.getProperty("jdbc.pwd");
			Class.forName(pt.getProperty("jdbc.driver"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("�����������");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("�Ҳ���ָ�������ļ�·��");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("��ȡ�����ļ�����");
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, pwd);
	}

	public static void close(Connection con, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Result�ر�ʧ��");
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Statment");
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Connection");
			}
		}
	}
}
