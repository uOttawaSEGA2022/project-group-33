package com.example.mealerapp;

import java.util.ArrayList;

public class Database {
    private ArrayList<User> users = new ArrayList<User>();

    private Cook testCook = new Cook("cook@gmail.com", "cook", User.UserType.COOK);
    private Client testClient = new Client("client@gmail", "client", User.UserType.CLIENT);

    public Database() {
        // Add test users
        users.add(testCook);
        users.add(testClient);
    }

    public User findUser (String email, String password) {
      for (User user: users) {
          if (user.getEmail() == email && user.getPassword() == password) {
              return user;
          }
      }
      return null;
    }

    public void addUser (User user) {
        users.add(user);
    }
}
