package com.example.mealerapp.objects;

public class OrderRequest {

    private String cookEmail;
    private String clientEmail;
    private String id;
    private String mealTitle;
    private float price;
    private String status;
    Database database;

    public String getId() {
        return id;
    }

    public OrderRequest(String cookEmail, String clientEmail, String id, String mealTitle, float price, String status) {
        this.cookEmail = cookEmail;
        this.clientEmail = clientEmail;
        this.id = id;
        this.mealTitle = mealTitle;
        this.price = price;
        this.status = status;
        database = new Database();
    }

    public String getCookEmail() {
        return cookEmail;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public String getMealTitle() {
        return mealTitle;
    }

    public float getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
