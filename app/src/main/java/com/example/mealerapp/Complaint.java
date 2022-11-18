package com.example.mealerapp;

import com.google.firebase.firestore.CollectionReference;

public class Complaint {

    private String fromEmail;
    private String toEmail;
    private String message;
    private String date;
    private String id;

    private static Database database;

    public Complaint(String fromEmail, String toEmail, String message, String date, String id) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.message = message;
        this.date = date;
        this.id = id;

        database = new Database();
    }

    public static int getNextId() {
        database = new Database();
        CollectionReference collection = database.firestore.collection("complaints");
        AggregateQuery countQuery = collection.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AggregateQuerySnapshot snapshot = task.getResult();
            } else {
            }
        });
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void deleteFromDatabase() {
//        database.firestore.collection("complaints").whereEqualTo("id", id).get().
    }
}
