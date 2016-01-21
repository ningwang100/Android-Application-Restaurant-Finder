package dbLayout;

/*
 * Name:ning wang
 * This class is used to create a database and three tables of 
 * the database.First we register for JDBC driver, connect with
 * the mysql server, then read sql language from sql to 
 * create a database with five tables.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class CreateTable extends ConnectDB{
	/*
	 * This method is used to connect with mysql server and create
	 * a database carConfigure with three tables
	 */
	public void createTable(){
		try {
			//Register JDBC Driver
			Class.forName(SQL_DRIVER);
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();
		}
		try {
			//connect database and create tables
			Connection connect=getConnectionForCreate("jdbc:mysql:"
					+ "//localhost:3306/");  
		    Statement stmt = connect.createStatement();  
		    stmt.executeUpdate("DROP DATABASE IF EXISTS test1;");
		    stmt.executeUpdate("CREATE DATABASE test1;");
		    stmt.executeUpdate("USE test1;");
		    String Create_Comment = sql.Create_Comment;
		    String Create_Restaurant = sql.Create_Restaurant;
		    String Create_User = sql.Create_User;
		    String Create_Rest_Comm = sql.Create_Restaurant_Comment;
		    String Create_User_Rest = sql.Create_Favorite_Restaurant;
		    stmt.executeUpdate(Create_Comment);
		    stmt.executeUpdate(Create_Restaurant);
		    stmt.executeUpdate(Create_User);
		    stmt.executeUpdate(Create_Rest_Comm);
		    stmt.executeUpdate(Create_User_Rest);
		    System.out.println("Finish creating tables!");
		} catch (SQLException e) {
			System.out.print("get data error!"); 
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}	
	}
}

