package com.example.mealerapp;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Database {
    FirebaseFirestore firestore;

    private Cook testCook = new Cook("cook@gmail.com", "cook", User.UserType.COOK);
    private Client testClient = new Client("client@gmail", "client", User.UserType.CLIENT);

    public Database() {
        firestore = FirebaseFirestore.getInstance();
    }

    public User findUser (String email, String password) {
        firestore.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String firstName = String.valueOf(document.get("email"));
                            System.out.println("YOOOOO");
                            System.out.println(firstName);
                        }
                    }
                }
            }
        });

      return null;
    }

    public boolean addUser (User user) {

        final boolean[] result = new boolean[1];
        Map<String,Object> users = new HashMap<>();
        users.put("email", user.getEmail());
        users.put("password", user.getPassword());
        users.put("type", user.getType());

        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                result[0] = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                result[0] = false;
            }
        });

        return result[0];
    }
}
