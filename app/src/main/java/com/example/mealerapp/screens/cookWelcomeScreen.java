package com.example.mealerapp.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cookWelcomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cookWelcomeScreen extends Fragment {

    private Database database;
    private String cookEmail;

    public cookWelcomeScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_cook_welcome_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static cookWelcomeScreen newInstance(String param1, String param2) {
        cookWelcomeScreen fragment = new cookWelcomeScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database();
        cookEmail = getArguments().getString("email");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_cook_welcome_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Add Meal Button
        Button addMealButton = getView().findViewById(R.id.addMeal);
        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddMealDialog().show();
            }
        });
    }

    private AlertDialog.Builder getAddMealDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Add Meal");

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView mealTitle = new TextView(getContext());
        mealTitle.setText("Meal Title");
        mealTitle.setPadding(40, 10, 0 , 10);
        layout.addView(mealTitle);

        EditText mealTitleInput = new EditText(getContext());
        mealTitleInput.setInputType(InputType.TYPE_CLASS_TEXT);
        mealTitleInput.setHint("Meal Title");
        mealTitleInput.setPadding(40,0,0,0);
        layout.addView(mealTitleInput);

        mealTitleInput.getText();

        TextView mealDescription = new TextView(getContext());
        mealDescription.setText("Meal Description");
        mealDescription.setPadding(40, 40, 0 , 10);
        layout.addView(mealDescription);

        EditText mealDescriptionInput = new EditText(getContext());
        mealDescriptionInput.setInputType(InputType.TYPE_CLASS_TEXT);
        mealDescriptionInput.setHint("Meal Description");
        mealDescriptionInput.setPadding(40,0,0,0);
        layout.addView(mealDescriptionInput);

        TextView mealPrice = new TextView(getContext());
        mealPrice.setText("Meal Price");
        mealPrice.setPadding(40, 40, 0 , 10);
        layout.addView(mealPrice);

        EditText mealPriceInput = new EditText(getContext());
        mealPriceInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        mealPriceInput.setHint("Meal Price");
        mealPriceInput.setPadding(40,0,0,0);
        layout.addView(mealPriceInput);

        dialog.setView(layout);

        dialog.setPositiveButton("Add Meal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mealTitleInput.getText().toString().length() < 1 || mealDescriptionInput.getText().toString().length() < 1 || mealPriceInput.getText().toString().length() < 1) {
                    Toast.makeText(getView().getContext(), "Error: Please fill all fields", Toast.LENGTH_LONG).show();
                } else {
                    database.getFirestore().collection("meals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            Map<String,Object> meal = new HashMap<>();
                            String id = Integer.toString(task.getResult().size());
                            meal.put("mealName", mealTitleInput.getText().toString());
                            meal.put("mealDescription", mealDescriptionInput.getText().toString());
                            meal.put("price", mealPriceInput.getText().toString());
                            meal.put("id", id);
                            meal.put("fromEmail", cookEmail);

                            database.getFirestore().collection("meals").document(id).set(meal);
                            Toast.makeText(getView().getContext(), "Successfully added the meal!", Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }
        });

        return dialog;
    }
}