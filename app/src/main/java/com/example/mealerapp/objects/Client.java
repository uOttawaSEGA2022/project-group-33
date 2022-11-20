package com.example.mealerapp.objects;

import com.example.mealerapp.objects.User;

public class Client extends User {
    public Client(String email, String password, UserType type) {
        super(email, password, type);
    }

    public void orderFood() {
        return;
    }
}
