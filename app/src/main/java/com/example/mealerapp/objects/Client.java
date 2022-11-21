package com.example.mealerapp.objects;

import com.example.mealerapp.objects.User;

public class Client extends User {
    public Client(String email, String password) {
        super(email, password, UserType.CLIENT);
    }

    public void orderFood() {
        return;
    }
}
