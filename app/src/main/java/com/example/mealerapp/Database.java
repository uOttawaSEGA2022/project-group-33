package com.example.mealerapp;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.events.Event;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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

//    public void readData(MyCallback myCallback, String email, String password) {
//        firestore.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    if (!task.getResult().isEmpty()) {
//                        List<Event> eventList = new ArrayList<>()
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            User.UserType type = User.UserType.valueOf(document.get("type").toString());
//                            Log.v("found type", "yo");
//                            Event e = User.UserType.valueOf(String.valueOf(document.get("type")));
//                            eventList.add()
//                            Log.v("found type", type[0].toString());
//                        }
//                    } else {
//                    }
//                }
//            }
//        });
//    }

//    public User findUser (String email, String password) {
//        final User.UserType[] type = new User.UserType[1];
//        final String[] doneQuery = new String[1];
//
//        while (doneQuery[0] == null) {
//            if (type[0] == User.UserType.CLIENT) {
//                Log.v("generate_client", "yo");
//                return new Client(email, password, type[0]);
//            }
//            if (type[0] == User.UserType.COOK) {
//                Log.v("generate_cook", "hello");
//                return new Cook(email, password, type[0]);
//            }
//            if (type[0] == User.UserType.ADMIN) {
//                return new Admin(email, password, type[0]);
//            }
//        }
//
//        return null;
//
//    }

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
