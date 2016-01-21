package entities;


public class User {
    private String username;
    private String password;
    private String name;
    private String age;

    public User(String username, String password, String name, String age) {
     this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

}
