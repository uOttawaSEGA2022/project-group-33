package com.example.mealerapp.objects;

public class Complaint {

    private String fromEmail;
    private String toEmail;
    private String message;
    private String id;

    private Database database;

    public Complaint(String fromEmail, String toEmail, String message, String id) {
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.message = message;
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


    public String getId() {
        return id;
    }

    public void deleteFromDatabase() {
        database.getFirestore().collection("complaints").document(id).delete();
    }
}
