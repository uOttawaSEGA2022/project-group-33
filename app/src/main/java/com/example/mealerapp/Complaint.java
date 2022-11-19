package com.example.mealerapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

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
        database.firestore.collection("complaints").document(id).delete();
    }
}
