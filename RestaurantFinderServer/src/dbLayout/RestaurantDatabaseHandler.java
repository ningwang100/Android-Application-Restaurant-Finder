/**
 * This class is used to handle all cases about restaurant model
 */
package dbLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RestaurantDatabaseHandler {
	// Judge whether the restaurant exist in database
	public static boolean existRestaurant(Statement stmt, int rest_ID) {
		String restId = String.valueOf(rest_ID);
		String query = "SELECT * FROM restaurant WHERE res_id =" + restId;

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
    //Judge whether favorite list exists or not
	public static boolean existFavoList(Statement stmt, String username, int rest_ID) {
		String query = "SELECT * FROM Favorite_Restaurant WHERE username = '" + username + "' AND res_id=" + rest_ID;
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
   // add restaurant to favorite list
	public static boolean addToFavoriteList(Connection connect, Statement stmt, String username, int rest_Id) {
		
		String query = "INSERT INTO Favorite_Restaurant (username,res_id) VALUES(?,?)";
		try {
			PreparedStatement pstmt = connect.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, rest_Id + "");
			pstmt.executeUpdate();
			pstmt.close();
			if (existFavoList(stmt, username, rest_Id)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    //delete an restaurant from favorite list
	public static boolean deleteFromFavoriteList(Statement stmt, String username, int rest_Id) {
		String query = "DELETE FROM Favorite_Restaurant WHERE username = '" + username + "' AND res_id=" + rest_Id;
		try {
			stmt.executeUpdate(query);
			if (!existFavoList(stmt, username, rest_Id)) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    //get user favorite list
	public static ArrayList<Restaurant> getFavoriteList(Connection connect, String username) {
		String query = "SELECT res_id FROM Favorite_Restaurant WHERE username = '" + username + "'";
		ArrayList<Restaurant> favoriteList = new ArrayList<Restaurant>();
		Restaurant restaurant = null;

		try {
			Statement stmt1 = connect.createStatement();
			Statement stmt2 = connect.createStatement();
			ResultSet rs = stmt2.executeQuery(query);
		
			if (rs != null && rs.next()) {
				for (rs.first(); !rs.isAfterLast(); rs.next()) {
					
					String query1 = "SELECT * FROM restaurant WHERE res_id =" + rs.getString(1);
					ResultSet rs1 = stmt1.executeQuery(query1);
					if (rs1.next()) {
						restaurant = new Restaurant(Integer.parseInt(rs1.getString(1)), rs1.getString(2),
								rs1.getString(3), rs1.getString(4),
								rs1.getString(5), rs1.getString(6), rs1.getString(7),
								Double.parseDouble(rs1.getString(8)), Double.parseDouble(rs1.getString(9)));
						favoriteList.add(restaurant);
					}
				}
				rs.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return favoriteList;
	}
   //search restaurant by name and return an arraylist
	public static ArrayList<Restaurant> getRestaurantsByName(Connection connect, String resName) {
		String query = "SELECT * FROM restaurant";
		String st="";
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		Restaurant restaurant = null;
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			if (rs1.next()) {
				for (rs1.first(); !rs1.isAfterLast(); rs1.next()) {
					String [] save=	rs1.getString(2).toLowerCase().split("\\s+");
					for(int i=0;i<save.length;i++){
						st=st+save[i];
					}
					System.out.println(st);
					int index = st.indexOf(resName);
					if(index!=-1){
					restaurant = new Restaurant(Integer.parseInt(rs1.getString(1)), rs1.getString(2),
							rs1.getString(3), rs1.getString(4),
							rs1.getString(5), rs1.getString(6), rs1.getString(7),
							Double.parseDouble(rs1.getString(8)), Double.parseDouble(rs1.getString(9)));

					restList.add(restaurant);
					}
					st="";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restList;
	}
	//search restaurant by address and return an arraylist
	public static ArrayList<Restaurant> getRestaurantsByAddress(Connection connect, String address) {
		String query = "SELECT * FROM restaurant";
	    System.out.println(address);
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		Restaurant restaurant = null;
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			if (rs1.next()) {
				for (rs1.first(); !rs1.isAfterLast(); rs1.next()) {
					String [] save=	rs1.getString(3).toLowerCase().split("\\s+");
					System.out.println(rs1.getString(3).toLowerCase());
					String st=save[0]+save[1];
					 int index = st.indexOf(address);
					 if(index!=-1){
					restaurant = new Restaurant(Integer.parseInt(rs1.getString(1)), rs1.getString(2),
							rs1.getString(3), rs1.getString(4),
							rs1.getString(5), rs1.getString(6), rs1.getString(7),
							Double.parseDouble(rs1.getString(8)), Double.parseDouble(rs1.getString(9)));
					System.out.println(
							"restaurant " + rs1.getString(1) + " " + rs1.getString(2) + " " + rs1.getString(3));
					restList.add(restaurant);
					 }
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restList;
	}
	//search restaurant by cuisine and return an arraylist
	public static ArrayList<Restaurant> getRestaurantsByCuisine(Connection connect, String cuisine) {
		String query = "SELECT * FROM restaurant WHERE cuisine = '" + cuisine + "'";
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		Restaurant restaurant = null;
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			if (rs1.next()) {
				for (rs1.first(); !rs1.isAfterLast(); rs1.next()) {
					restaurant = new Restaurant(Integer.parseInt(rs1.getString(1)), rs1.getString(2),
							rs1.getString(3), rs1.getString(4),
							rs1.getString(5), rs1.getString(6), rs1.getString(7),
							Double.parseDouble(rs1.getString(8)), Double.parseDouble(rs1.getString(9)));
					restList.add(restaurant);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restList;
	}
	//get all rstaurants 
	public static ArrayList<Restaurant> getAllRestaurant(Connection connect) {
		String query = "SELECT * FROM restaurant";
		ArrayList<Restaurant> restList = new ArrayList<Restaurant>();
		Restaurant restaurant = null;
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			if (rs1.next()) {
				for (rs1.first(); !rs1.isAfterLast(); rs1.next()) {
					restaurant = new Restaurant(Integer.parseInt(rs1.getString(1)), rs1.getString(2),
							rs1.getString(3), rs1.getString(4),
							rs1.getString(5), rs1.getString(6), rs1.getString(7),
							Double.parseDouble(rs1.getString(8)), Double.parseDouble(rs1.getString(9)));
					System.out.println(
							"restaurant " + rs1.getString(1) + " " + rs1.getString(2) + " " + rs1.getString(7));
					restList.add(restaurant);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return restList;

	}
}
