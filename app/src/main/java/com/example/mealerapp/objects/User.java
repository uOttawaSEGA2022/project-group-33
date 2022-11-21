package com.example.mealerapp.objects;
import androidx.appcompat.app.AppCompatActivity;

public abstract class User extends AppCompatActivity {

    private String firstName;
    private String lastName;
    private String id;

    private String email;
    private String password;
    private UserType type;

    public enum UserType {
        CLIENT,
        COOK,
        ADMIN
    }

    public User(String email, String password, UserType type) {
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }


}
