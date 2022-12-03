package com.example.mealerapp.screens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealerapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientFragmentWelcomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientFragmentWelcomeScreen extends Fragment {
    public ClientFragmentWelcomeScreen() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ClientFragmentWelcomeScreen newInstance(String param1, String param2) {
        ClientFragmentWelcomeScreen fragment = new ClientFragmentWelcomeScreen();
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
}