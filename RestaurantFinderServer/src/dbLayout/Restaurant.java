package dbLayout;
import java.io.Serializable;
import java.util.ArrayList;

import entities.Comment;
public class Restaurant implements Serializable {
	private static final long serialVersionUID = -2963666700461986338L;
	 private int restID;
	private String name;
    private String address;
    private String price;
    private String cuisine;
    private String phone;
    private String website;
    private double longitude;
    private double latitude;
    private ArrayList<Comment> comments = new ArrayList<>();
    
    public Restaurant(int restID, String name, String address, String price, String cuisine, String phone, String website, double longitude, double latitude) {
        this.restID = restID;
        this.name = name;
        this.address = address;
        this.price = price;
        this.cuisine = cuisine;
        this.phone = phone;
        this.website = website;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public int getRestID() {return restID;}

    public ArrayList<Comment> getComments()
    {
        return comments;
    }

    public void addComment(Comment comm) {
        comments.add(comm);
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
