package com.example.mealerapp.objects;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.events.Event;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    FirebaseFirestore firestore;

    private Cook testCook = new Cook("cook@gmail.com", "cook", User.UserType.COOK);
    private Client testClient = new Client("client@gmail", "client", User.UserType.CLIENT);

    public Database() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface MyCallback {
        void onCallback(List<Event> eventList);
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
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
