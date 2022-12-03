package com.example.mealerapp.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.Meal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clientFindMealsScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clientFindMealsScreen extends Fragment {

    ListView mealsListView;
    ArrayList<Meal> meals;
    clientMealListAdapter mealListAdapter;
    Database database;

    public clientFindMealsScreen() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static clientFindMealsScreen newInstance(String param1, String param2) {
        clientFindMealsScreen fragment = new clientFindMealsScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database();
        meals = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_find_meals, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealsListView = getView().findViewById(R.id.findMealsListView);

        // Fill in the mealList from cooks that are not suspended
        database.getFirestore().collection("meals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> MealTask) {
                if (MealTask.isSuccessful()) {
                    if (!MealTask.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot MealDocument : MealTask.getResult()) {
                            String fromEmail = MealDocument.get("fromEmail").toString();

                            //do another query to see if the cook is banned or not
                            database.getFirestore().collection("users").whereEqualTo("email", fromEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> UserTask) {
                                    if (UserTask.isSuccessful()) {
                                        if (!UserTask.getResult().isEmpty()) {
                                            for (QueryDocumentSnapshot UserDocument : UserTask.getResult()) {
                                                boolean permanentSuspension = Boolean.parseBoolean(UserDocument.get("permanentSuspension").toString());

                                                if (permanentSuspension == true) {
                                                    return;
                                                }

                                                String endDateTempSuspension = UserDocument.get("tempSuspension").toString();
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
                                                Date tempSuspensionDate;
                                                Date todaysDate = new Date();
                                                try {
                                                    tempSuspensionDate = sdf.parse(endDateTempSuspension);
                                                } catch (ParseException e) {
                                                    tempSuspensionDate = null;
                                                    e.printStackTrace();
                                                }

                                                int todaysDateComparedToSuspensionDate = todaysDate.compareTo(tempSuspensionDate);
                                                if (todaysDateComparedToSuspensionDate < 0 || todaysDateComparedToSuspensionDate == 0) {
                                                    // user is suspended
                                                } else {
                                                    // user is not suspended
                                                    String id = MealDocument.get("id").toString();
                                                    String mealDescription = MealDocument.get("mealDescription").toString();
                                                    String mealName = MealDocument.get("mealName").toString();
                                                    boolean onOfferedList = (boolean) MealDocument.get("onOfferedList");
                                                    float price = Float.parseFloat(MealDocument.get("price").toString());
                                                    Meal c = new Meal(mealName, fromEmail, mealDescription, onOfferedList, price, id);
                                                    meals.add(c);

                                                }
                                            }

                                            mealListAdapter = new clientMealListAdapter(getView().getContext(), meals, mealsListView);
                                            mealsListView.setAdapter(mealListAdapter);
                                        }
                                    }
                                }
                            });

                        }



                    }
                }
            }
        });



        // Inflate the layout for this fragment
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu);
                SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mealListAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

    }
}