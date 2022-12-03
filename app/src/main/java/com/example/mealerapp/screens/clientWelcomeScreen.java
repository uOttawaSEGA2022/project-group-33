package com.example.mealerapp.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mealerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clientWelcomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clientWelcomeScreen extends Fragment {
    public clientWelcomeScreen() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static clientWelcomeScreen newInstance(String param1, String param2) {
        clientWelcomeScreen fragment = new clientWelcomeScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_welcome_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button findMealsButton = view.findViewById(R.id.findMeal);
        Button purchaseRequestButton = view.findViewById(R.id.purchaseRequest);

        findMealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "fill this in");
                findMealsScreen findMeals = new findMealsScreen();
                findMeals.setArguments(bundle);
                NavHostFragment.findNavController(clientWelcomeScreen.this).navigate(R.id.clientWelcome_to_findMeal);
            }
        });

        purchaseRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "fill this in");
                clientPurchaseRequestsScreen requestsScreen = new clientPurchaseRequestsScreen();
                requestsScreen.setArguments(bundle);
                NavHostFragment.findNavController(clientWelcomeScreen.this).navigate(R.id.clientWelcome_to_purchaseRequest);
            }
        });
    }
}