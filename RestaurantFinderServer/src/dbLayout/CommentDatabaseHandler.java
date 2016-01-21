/**
 * This class is used to deal with cases about comment table.
 */
package dbLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Comment;

public class CommentDatabaseHandler {
	//Judge whether the comment list exist
	public static boolean existCommentList(Statement stmt, int cmment_ID) {
		String query = "SELECT * FROM Restaurant_Comment WHERE comment_id=" + cmment_ID;
		//System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				//System.out.println(rs.getString(1) + " " + rs.getString(2));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    //add comment to a restaurant
	public static boolean addComment1(Connection connect,Statement stmt,int rest_Id,String text,String username){
		 int value=0;int value1=0;
		 String query="INSERT INTO comment (text,username) VALUES('"+text+"','"+username+"')";
		 String query1="SELECT count(*) from comment";
		 String query2="INSERT INTO Restaurant_Comment (comment_id,res_id) VALUES(?,?)";
		 try {
			 ResultSet rs=stmt.executeQuery(query1);
			 if (rs.next()) {
			 value=rs.getShort(1);
			stmt.executeUpdate(query);
			 }
			rs=stmt.executeQuery(query1);
			if(rs.next()){
			 value1=rs.getShort(1);
			//System.out.println(value+" "+value1);
			}
			if(value1==value+1){
				PreparedStatement pstmt = connect.prepareStatement(query2);
	      		pstmt.setString(1, value1+"");
	      		pstmt.setString(2, rest_Id+"");
	      		pstmt.executeUpdate();
				pstmt.close();	
				return existCommentList(stmt,value1);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 return false;
		}
    // get a restaurant's comment list
	public static ArrayList<Comment> getCommentList(Connection connect,int rest_id){
		   String query = "SELECT comment_id FROM Restaurant_Comment WHERE res_id = " + rest_id;
			ArrayList<Comment> commentList = new ArrayList<Comment>();
			Comment comment=null;
			try {
				Statement stmt1=connect.createStatement();
				Statement stmt2=connect.createStatement();
				ResultSet rs = stmt2.executeQuery(query);
				if(rs.next()) {
					for (rs.first(); !rs.isAfterLast(); rs.next()) {
						//System.out.println("rst_id "+rs.getString(1));	
						String query1 = "SELECT * FROM comment WHERE comment_id ="+rs.getString(1);
						ResultSet rs1 = stmt1.executeQuery(query1);
						if(rs1.next()){
						comment=new Comment(rs1.getString(2),rs1.getString(3));
						//System.out.println("comment "+rs1.getString(1)+" "+rs1.getString(2));
						commentList.add(comment);
						}
					}
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return commentList;
	   }

}
