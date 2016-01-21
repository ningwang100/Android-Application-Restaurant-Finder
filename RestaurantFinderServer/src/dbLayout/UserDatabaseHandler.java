/**
 * This class is used to handle all cases about User table.
 */
package dbLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.User;

public class UserDatabaseHandler {
	//Judge whether username and password is valid or not
	public static boolean LoginCheck(Statement stmt, String username, String password) {
		boolean checkResult = false;
		String query = "SELECT * FROM user WHERE username = '" + username + "' AND password=" + "'" + password + "'";
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				checkResult = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkResult;
	}
    //Judge whether user already exist
	public static boolean existUser(Statement stmt, String username) {
		String query = "SELECT * FROM user WHERE username = '" + username + "'";
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    // insert user to User table
	public static boolean addUser(Connection connect, User user) {
		try {
			String addData = "INSERT INTO user (username,name,age,password) VALUES(?,?,?,?)";
			PreparedStatement stmt = connect.prepareStatement(addData);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getAge());
			stmt.setString(4, user.getPassword());
			stmt.executeUpdate();
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
