package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	private static DataBaseConnection instance;
	private Connection connection;

	private final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyQuanCaPhe;encrypt=false;trustServerCertificate=true;";
	private final String USER = "sa";
	private final String PASSWORD = "123456";

	private DataBaseConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Kết nối cơ sở dữ liệu thành công!");
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Lỗi kết nối cơ sở dữ liệu! " + e.getMessage());
		}
	}

	public static synchronized DataBaseConnection getInstance() {
		if (instance == null) {
			instance = new DataBaseConnection();
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Đã đóng kết nối Database an toàn!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}