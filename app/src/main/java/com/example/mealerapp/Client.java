package com.example.mealerapp;

public class Client extends User {
    public Client(String email, String password, UserType type) {
        super(email, password, type);
    }

    public void orderFood() {
        return;
    }
}
