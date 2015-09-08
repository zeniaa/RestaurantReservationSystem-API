package solutions.egen.rrs.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {

	private final static String DB_URL = "jdbc:mysql://localhost:3307/RestaurantReservation_db";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "mysql";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("MySQL JDBC Driver has loded.");
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Error in loading MySQL JDBC Driver");
		}
	}

	public static Connection connect() {
		Connection conn = null;
		try {

			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("Connection Sucuesful");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection Not Succesful");
		}

		return conn;
	}

	public static void closeResouce(PreparedStatement ps, ResultSet rs, Connection conn) {
		try {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DBUtils.connect();
	}

}
