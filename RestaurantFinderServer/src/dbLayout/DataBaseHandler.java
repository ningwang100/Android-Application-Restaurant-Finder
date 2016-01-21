/**
 * This class is used to handle all requests sent from UI and do corresponding 
 * operations
 */
package dbLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Comment;
import entities.User;

public class DataBaseHandler extends ConnectDB {
    /**
     * Initialize database and five tables
     */
	public void initializeDatabase() {
		CreateTable createTable = new CreateTable();
		createTable.createTable();
		Connection connect = getConnection();
		try {
			Statement stmt = connect.createStatement();
			initializeUser(stmt);
			initializeRestaurant(stmt);
			initializeComment(stmt);
			initializeFavRest(stmt);
			initializeRestComm(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
	}
    /**
     * Initialize Favorite_Restaurant table
     */
	private void initializeFavRest(Statement stmt) {
		try {
			String addData = "INSERT INTO Favorite_Restaurant VALUES('summerfuning@gmail.com',1)";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Favorite_Restaurant VALUES('summerfuning@gmail.com',2)";
			stmt.executeUpdate(addData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
     * Initialize Restaurant_Comment table
     */
	private void initializeRestComm(Statement stmt) {
		try {
			String addData = "INSERT INTO Restaurant_Comment VALUES(1,1)";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Restaurant_Comment VALUES(2,2)";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Restaurant_Comment VALUES(3,3)";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Restaurant_Comment VALUES(4,4)";
			stmt.executeUpdate(addData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
     * Initialize Restaurant table
     */
	private void initializeRestaurant(Statement stmt) {
		try {
			String addData = "INSERT INTO Restaurant VALUES(1,'Skibo Coffee House','5000 Forbes Avenue, Pittsburgh, PA 15213',"
					+ "'$$','Cafe','(412)268-1803','http://www.cmu.edu/dining/locations/skibo.html','-79.953723','40.4506896428')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Restaurant VALUES(2,'El Gallo de Oro','5001 Forbes Avenue, Pittsburgh, PA 15213',"
					+ "'$$$','Cafe','(412)268-1001','http://www.cmu.edu/dining/locations/cmc.html','-79.95375678','40.4506895786')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Restaurant VALUES(3,'Tartans Pavilion','5002 Forbes Avenue, Pittsburgh, PA 15213',"
					+ "'$$','Cafe','(412)204-1094','https://foursquare.com/v/tartans-pavilion/4adc955af964a5208b2d21e3','-79.7809884','40.6798654')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Restaurant VALUES(4,'Orient Express','4609 Forbes Avenue, Pittsburgh, PA 15213',"
					+ "'$','Chinese','(412)622-7232','http://www.allmenus.com/pa/pittsburgh/270890-orient-express/menu/','-79.78456543','40.6795678')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO Restaurant VALUES(5,'Yuva India Restaurant','412 South Craig Street, Pittsburgh, PA 15213',"
					+ "'$','India','(412)681-5700','https://plus.google.com/114114991469112529572/about?gl=us&hl=zh-CN','-79.576493','40.45068978')";
			stmt.executeUpdate(addData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
     * Initialize user table
     */
	public void initializeUser(Statement stmt) {
		try {
			Connection connect = getConnection();
			ResultSet rs = connect.getMetaData().getCatalogs();
			if(rs.next()){
			String addData = "INSERT INTO user VALUES('jxq48@case.edu','Jingwen','22','jingwen')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO user VALUES('summerfuning@gmail.com','Ningw','22','ningw')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO user VALUES('123','123','22','123')";
			stmt.executeUpdate(addData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
     * Initialize Comment table
     */
	public void initializeComment(Statement stmt) {
		try {
			String addData = "INSERT INTO comment VALUES(1,'Good','123')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO comment VALUES(2,'bad','123')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO comment VALUES(3,'delicious','summerfuning@gmail.com')";
			stmt.executeUpdate(addData);
			addData = "INSERT INTO comment VALUES(4,'environment good','jxq48@case.edu')";
			stmt.executeUpdate(addData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * Login check
     */
	public boolean LoginCheck(String username, String password) {
		Connection connect = getConnection();
		boolean checkResult = false;
		try {	
			ResultSet rs = connect.getMetaData().getCatalogs();
			if(rs.next()){
			Statement stmt = connect.createStatement();
			checkResult = UserDatabaseHandler.LoginCheck(stmt, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
		return checkResult;
	}
    /**
     * check whether this account has existed
     * @param username
     * @return
     */
	public boolean existUser(String username) {
		Connection connect = getConnection();
		boolean checkResult = false;
		try {
			Statement stmt = connect.createStatement();
			checkResult = UserDatabaseHandler.existUser(stmt, username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
		return checkResult;
	}
    /**
     * Insert a user in database
     * @param user
     * @return
     */
	public boolean addUser(User user) {
		Connection connect = getConnection();
		boolean addResult = false;
		try {
			Statement stmt = connect.createStatement();
			if (!UserDatabaseHandler.existUser(stmt, user.getUsername())) {
				UserDatabaseHandler.addUser(connect, user);
				addResult = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
		return addResult;
	}
    /**
     * Add restaurant to favorite list
     */
	public boolean addToFavoriteList(String username, int rest_Id) {
		Connection connect = getConnection();
		boolean addResult = false;
		try {
			Statement stmt = connect.createStatement();
			if (RestaurantDatabaseHandler.existRestaurant(stmt, rest_Id)) {
				System.out.println("restaurant exist");
				if (!RestaurantDatabaseHandler.existFavoList(stmt, username, rest_Id)) {
					System.out.println("not in fav list");
					addResult = RestaurantDatabaseHandler.addToFavoriteList(connect, stmt, username, rest_Id);
				}else{
					System.out.println("this restaurant already in favorite list");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
		return addResult;
	}
	/**
     * delete a restaurant from favorite list
     */
	public boolean deleteFromFavoriteList(String username,int rest_Id) {
		Connection connect = getConnection();
		boolean deleteResult = false;
		try {
			Statement stmt = connect.createStatement();
			if (RestaurantDatabaseHandler.existRestaurant(stmt, rest_Id)) {
				System.out.println("restaurant exist");
				if (RestaurantDatabaseHandler.existFavoList(stmt, username, rest_Id)) {
					System.out.println("in fav list");
					deleteResult = RestaurantDatabaseHandler.deleteFromFavoriteList(stmt, username, rest_Id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
		return deleteResult;
    }
	/**
     * get favorite list
     */
	public ArrayList<Restaurant> getFavoriteList(String username) {
		System.out.println("!!!!!!!!username is "+username);
		Connection connect = getConnection();
		ArrayList<Restaurant> list=new ArrayList<Restaurant>();
		list = RestaurantDatabaseHandler.getFavoriteList(connect,username);
    	disconnect(connect);
		return list;
    }
	/**
     * Add a comment to restaurant
     */
	public boolean addComment(int rest_Id,String text,String username){
		Connection connect = getConnection();
		boolean addResult = false;
		try {
			Statement stmt = connect.createStatement();
			if (RestaurantDatabaseHandler.existRestaurant(stmt, rest_Id)) {
				if(existUser(username)){
					addResult = CommentDatabaseHandler.addComment1(connect,stmt,rest_Id,text,username);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
		return addResult;
	}
	/**
     * get a restaurant's comment list
     */
	public ArrayList<Comment> getCommentList(int rest_id){
		Connection connect = getConnection();
		ArrayList<Comment> list=new ArrayList<Comment>();
		try {
			Statement stmt = connect.createStatement();
			if (RestaurantDatabaseHandler.existRestaurant(stmt, rest_id)) {
					System.out.println("RESTAURANT EXIST");
				   list=CommentDatabaseHandler.getCommentList(connect,rest_id);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	disconnect(connect);
		return list;
	} 
	/**
	 * get restaurants by name
	 */
	public ArrayList<Restaurant> getRestaurantsByName(String resName) {
		Connection connect = getConnection();
		ArrayList<Restaurant> list=new ArrayList<Restaurant>();
    	list = RestaurantDatabaseHandler.getRestaurantsByName(connect, resName);	
    	disconnect(connect);
    	return list;
    }
	/**
	 * get restaurants by address
	 */
	public ArrayList<Restaurant> getRestaurantsByAddress(String address) {
		Connection connect = getConnection();
		ArrayList<Restaurant> list=new ArrayList<Restaurant>();
    	list = RestaurantDatabaseHandler.getRestaurantsByAddress(connect, address);
    	disconnect(connect);
    	return list;
    }
	/**
	 * get restaurants by cuisine
	 */
	public ArrayList<Restaurant> getRestaurantsByCuisine(String Cuisine) {
		Connection connect = getConnection();
		ArrayList<Restaurant> list=new ArrayList<Restaurant>();
    	list = RestaurantDatabaseHandler.getRestaurantsByCuisine(connect, Cuisine);	
    	disconnect(connect);
    	return list;
    }
	// Disconnect the connection
	public void disconnect(Connection connect) {
		CreateTable createTable = new CreateTable();
		createTable.closeConnection(connect);
	}
	//get all restaurants
	public ArrayList<Restaurant> getAllRestaurant() {
		Connection connect = getConnection();
		ArrayList<Restaurant> list=new ArrayList<Restaurant>();
		list = RestaurantDatabaseHandler.getAllRestaurant(connect);
		disconnect(connect);
		return list;
	}
}
