package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class DBConnect {
	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/mdatabase";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "123456";

	// 定义数据库的连接
	private Connection connection;
	// 定义sql语句的执行对象
	private Statement stat;
	// 定义查询返回的结果集合
	private ResultSet resultset;

	public DBConnect() {
		try {
			Class.forName(JDBC_DRIVER);// 注册驱动
			connection = DriverManager.getConnection(DB_URL, USER, PASS);// 定义连接
		} catch (Exception e) {
			System.out.println("数据库连接失败！");
			e.printStackTrace();
		}
	}

	/**
	 * java中jdbc查询出来的数据转为json数组
	 * 
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static JSONArray resultSetToJsonArry(ResultSet rs) throws Exception {
		// json数组
		JSONArray array = new JSONArray();

		// 获取列数
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();
		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();

			// 遍历每一列
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				if (value == null) {
					jsonObj.put(columnName, JSONObject.NULL);
				} else {
					jsonObj.put(columnName, value);
				}
			}
			array.put(jsonObj);
		}

		return array;
	}

	/**
	 * 通过sql查询，返回查询结果的json格式
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String query(String sql) throws Exception {
		stat = connection.createStatement();
		resultset = stat.executeQuery(sql);
		JSONArray tmp = resultSetToJsonArry(resultset);
		JSONObject result = new JSONObject();
		result.put("messages", tmp);
		return result.toString();
	}

	/**
	 * 注意在finally里面执行以下方法，关闭连接
	 */
	public void closeConnect() {
		if (resultset != null) {
			try {
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
