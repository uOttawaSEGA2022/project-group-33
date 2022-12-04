package com.example.mealerapp.objects;

public class Cook extends User {
    private Database database;
    private boolean permanentSuspension;
    private String tempSuspension;
    private String chefName;
    private int numberOfReviews;
    private float rating;
    private int sumOfScores;

    public Cook(String email, String password) {
        super(email, password, UserType.COOK);
    }

}

