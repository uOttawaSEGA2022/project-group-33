package com.example.mealerapp.objects;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import com.example.mealerapp.screens.register_cook;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.List;

public class Cook extends User {
    private Database database;
    private boolean permanentSuspension;
    private String tempSuspension;
    public Cook(String email, String password) {
        super(email, password, UserType.COOK);
    }

}

