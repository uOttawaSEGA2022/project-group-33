package com.example.mealerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mealerapp.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private EditText emailEditText;
    private EditText passwordEditText;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_register_client2);
            }
        });

        binding.registerCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_register_cook2);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEditText = (EditText)getView().findViewById(R.id.input_email);
                String email = emailEditText.getText().toString();

                passwordEditText = (EditText)getView().findViewById(R.id.input_password);
                String password = passwordEditText.getText().toString();

                Log.v("email", "YOOO " + email);
                if (!Helper.isValidEmail(email)) {
                    getView().findViewById(R.id.email_error).setVisibility(View.VISIBLE);
                } else {
                    getView().findViewById(R.id.email_error).setVisibility(View.GONE);
                }

                if (!Helper.isPasswordValid(password)) {
                    getView().findViewById(R.id.password_error).setVisibility(View.VISIBLE);
                } else {
                    getView().findViewById(R.id.password_error).setVisibility(View.GONE);
                }

                if (Helper.isValidEmail(email) && Helper.isPasswordValid(password)) {
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_welcome_screen);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}