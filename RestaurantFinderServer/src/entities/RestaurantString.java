package entities;
import com.google.gson.Gson;

public class RestaurantString {
	 private String name;
	    private String address;
	    private String price;
	    private String cuisine;

	    private String phone;
	    private String website;

	    private String longitude;
	    private String latitude;

	    private String restID;

	    public RestaurantString(int restID, String name, String address, String price, String cuisine, String phone, String website, double longitude, double latitude) {
	        this.restID = String.valueOf(restID);
	        this.name = name;
	        this.address = address;
	        this.price = price;
	        this.cuisine = cuisine;
	        this.phone = phone;
	        this.website = website;
	        this.longitude = String.valueOf(longitude);
	        this.latitude = String.valueOf(latitude);
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

	    public String getRestID() {return restID;}


	    public String getLongitude() {
	        return longitude;
	    }

	    public String getLatitude() {
	        return latitude;
	    }
		
	    //Use Gson to serielize data into JSON format
	    @Override
		public String toString() {
			return new Gson().toJson(this);
		}
}
