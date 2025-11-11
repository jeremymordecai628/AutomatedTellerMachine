package AutomatedTellerMachine; //import package 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {

	private static final String DB_URL = "jdbc:sqlite:bank.db";

	// Load SQLite driver once when the class is loaded
	static {
		try {
			Class.forName("org.sqlite.JDBC");
			System.out.println("✅ SQLite JDBC driver loaded successfully");
		} catch (ClassNotFoundException e) {
			System.out.println("❌ SQLite JDBC driver not found: " + e.getMessage());
		}
	}

	/**
	 * Get a database connection
	 *
	 * @return Connection object or null if connection failed
	*/

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(DB_URL);
		} catch (SQLException e) {
			System.out.println("❌ Database connection error: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Authenticate user by account number and password
	 * */
	public boolean authenticate(String accountNo, String password) {
		String sql = "SELECT * FROM user WHERE account_no = ? AND password = ?";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (conn == null) {
				System.out.println("Connection failed.");
				return false;
			}
			pstmt.setString(1, accountNo);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			System.out.println(" Database error: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Get account balance
	 * */

	public double getBalance(String accountNo) {
		String sql = "SELECT balance FROM account WHERE account_no = ?";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (conn == null) return -1;
			pstmt.setString(1, accountNo);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getDouble("balance");
			}
		} catch (SQLException e) {
			System.out.println(" Database error: " + e.getMessage());
		}

		return -1; // account not found
			   
	}
	/**
	 * Update account balance
	 * */

	public boolean updateBalance(String accountNo, double newBalance) {
		String sql = "UPDATE account SET balance = ? WHERE account_no = ?";
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (conn == null) return false;
			pstmt.setDouble(1, newBalance);
			pstmt.setString(2, accountNo);
			
			int rows = pstmt.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			System.out.println(" Database error: " + e.getMessage());
			return false;
		}
	}
}
