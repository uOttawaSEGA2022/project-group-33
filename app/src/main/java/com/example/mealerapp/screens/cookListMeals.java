package com.example.mealerapp.screens;

import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Complaint;
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.Meal;
import com.example.mealerapp.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cookListMeals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cookListMeals extends Fragment {

    private User.UserType userType;
    private Database database;
    LinearLayout myLayout;
    ListView mealList;

    public cookListMeals() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static cookListMeals newInstance(String param1, String param2) {
        cookListMeals fragment = new cookListMeals();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cook_list_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myLayout = getView().findViewById(R.id.container);
        mealList = getView().findViewById(R.id.mealsListView);
        ArrayList<Meal> meals = new ArrayList<>();

        database.getFirestore().collection("meals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String fromEmail = document.get("fromEmail").toString();
                            String id = document.get("id").toString();
                            String mealDescription = document.get("mealDescription").toString();
                            String mealName = document.get("mealName").toString();
                            boolean onOfferedList = (boolean) document.get("onOfferedList");
                            float price = Float.parseFloat(document.get("price").toString());

                            Meal c = new Meal(mealName, fromEmail, mealDescription, onOfferedList, price, id);
                            meals.add(c);

                        }

                        MealListAdapter mealListAdapter = new MealListAdapter(getView().getContext(), meals, mealList);
                        mealList.setAdapter(mealListAdapter);

                    }
                }
            }
        });

    }
}