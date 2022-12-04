package com.example.mealerapp.objects;

public class OrderRequest {

    private String cookEmail;
    private String clientEmail;
    private String id;
    private String mealTitle;
    private float price;
    private String status;
    private Boolean deletedFromClient;
    private Boolean deletedFromCook;
    private Boolean isReviewed;
    Database database;

    public String getId() {
        return id;
    }

    public OrderRequest(String cookEmail, String clientEmail, String id, String mealTitle, float price, String status, Boolean deletedFromClient, Boolean deletedFromCook, Boolean isReviewed) {
        this.cookEmail = cookEmail;
        this.clientEmail = clientEmail;
        this.id = id;
        this.mealTitle = mealTitle;
        this.price = price;
        this.status = status;
        this.deletedFromClient = deletedFromClient;
        this.deletedFromCook = deletedFromCook;
        this.isReviewed = isReviewed;
        database = new Database();
    }

    public Boolean getReviewed() {
        return isReviewed;
    }

    public void setReviewed(Boolean reviewed) {
        isReviewed = reviewed;
    }

    public Boolean getDeletedFromClient() {
        return deletedFromClient;
    }

    public Boolean getDeletedFromCook() {
        return deletedFromCook;
    }

    public void setDeletedFromClient(Boolean deletedFromClient) {
        this.deletedFromClient = deletedFromClient;
    }

    public void setDeletedFromCook(Boolean deletedFromCook) {
        this.deletedFromCook = deletedFromCook;
    }

    public void setStatus(String status) {
        this.status = status;
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
