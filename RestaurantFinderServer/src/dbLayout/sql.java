/**
 * This class is used to create tables.
 */
package dbLayout;

public class sql {
	//create table user
	public static final String Create_User = "CREATE TABLE user("
+"username VARCHAR(255) NOT NULL PRIMARY KEY,"
+"name VARCHAR(255),"
+"age INTEGER, "
+"password VARCHAR(255));";
	//create table comment
	public static final String Create_Comment = "CREATE TABLE comment("
			+"comment_id INTEGER NOT NULL AUTO_INCREMENT,"
			+"text VARCHAR(255),"
			+"username VARCHAR(255),"
			+"PRIMARY KEY(comment_id));";
	//create table restaurant
	public static final String Create_Restaurant = "CREATE TABLE restaurant("
+"res_id INTEGER NOT NULL,"
+"resName VARCHAR(255),"
+"address VARCHAR(255),"
+"price VARCHAR(255),"
+"cuisine VARCHAR(255),"
+"phone VARCHAR(255),"
+"website VARCHAR(255),"
+"longitude REAL,"
+"latitude REAL,"
+"PRIMARY KEY (res_id));";
	//create table Restaurant_Comment
	public static final String Create_Restaurant_Comment = "CREATE TABLE Restaurant_Comment("
+"comment_id INTEGER NOT NULL,"
+"res_id INTEGER NOT NULL,"
+"FOREIGN KEY(comment_id) REFERENCES comment(comment_id) ON DELETE CASCADE,"
+"FOREIGN KEY(res_id) REFERENCES restaurant(res_id) ON DELETE CASCADE,"
+"PRIMARY KEY(comment_id));";
	//create table Favorite_Restaurant
	public static final String Create_Favorite_Restaurant = "CREATE TABLE Favorite_Restaurant("
+"username VARCHAR(255) NOT NULL,"
+"res_id INTEGER NOT NULL,"
+"PRIMARY KEY(username,res_id),"
+"FOREIGN KEY(username) REFERENCES user(username) ON DELETE CASCADE,"
+"FOREIGN KEY(res_id) REFERENCES restaurant(res_id) ON DELETE CASCADE"
+")ENGINE=InnoDB DEFAULT CHARSET=latin1;";
}
