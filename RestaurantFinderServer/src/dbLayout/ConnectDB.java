/**
 * This class is used to connect to mysql
 */
package dbLayout;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class ConnectDB {
	public String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private String URL = "jdbc:mysql://localhost:3306/test1";
	private String userName="root";
	private String password="";
	/*
	 * This method is used for first connect.Database is not created now.
	 */
	protected Connection getConnectionForCreate(String DB_URL){
		Connection connect=null;
		try {
			connect=DriverManager.getConnection(DB_URL, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
	/*
	 * This method is used to connect with a specifice database in mysql
	 * server.
	 */
	protected Connection getConnection(){
		Connection connect=null;
		try {
			return DriverManager.getConnection(URL,userName,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connect;
	}
	/*
	 * This method is used to close connection
	 */
	protected void closeConnection(Connection connect){
        if (connect == null) {
            return;
        }
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
