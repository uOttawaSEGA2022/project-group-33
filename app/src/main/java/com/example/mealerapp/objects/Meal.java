package com.example.mealerapp.objects;

public class Meal {

    private String mealName;
    private String from;
    private String mealDescription;
    private boolean isOnOfferedMealList;
    private float price;
    private String id;
    Database database;

    public String getId() {
        return id;
    }

    public Meal(String mealName, String from, String mealDescription, boolean isOnOfferedMealList, float price, String id) {
        this.mealName = mealName;
        this.from = from;
        this.mealDescription = mealDescription;
        this.isOnOfferedMealList = isOnOfferedMealList;
        this.price = price;
        this.id = id;
        database = new Database();

    }

    public boolean isOnOfferedMealList() {
        return isOnOfferedMealList;
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

    public void deleteFromDatabase() {
        database.getFirestore().collection("meals").document(id).delete();
    }

    public void setOnOfferedMealList(boolean onOfferedMealList) {
        isOnOfferedMealList = onOfferedMealList;
    }

}
