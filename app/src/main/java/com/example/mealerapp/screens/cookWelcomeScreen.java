package com.example.mealerapp.screens;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mealerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link cookWelcomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class cookWelcomeScreen extends Fragment {

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
        if (getArguments() != null) {
        }
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Add Meal");
            }
        });
    }
}