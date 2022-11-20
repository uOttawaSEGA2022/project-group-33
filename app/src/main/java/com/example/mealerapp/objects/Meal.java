package com.example.mealerapp.objects;

public class Meal {

    private String mealName;
    private String from;
    private String mealDescription;
    private float price;

    public Meal(String mealName, String mealDescription, float price) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.price = price;
    }

    public String getMealName() {
        return mealName;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public float getPrice() {
        return price;
    }
}
