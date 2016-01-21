package entities;

import com.google.gson.Gson;

public class Comment {
    private String text;
    private String username;

    public Comment(String text, String username) {
    	this.text = text;
    	this.username = username;
    }
    
    public String getText() {
        return text;
    }
    
    public String getUsername() {
    	return username;
    }
    
    //Use Gson to serielize data into JSON format
    @Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
