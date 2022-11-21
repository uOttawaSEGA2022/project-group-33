package com.example.mealerapp.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mealerapp.Helper;
import com.example.mealerapp.R;
import com.example.mealerapp.databinding.FragmentLoginBinding;
import com.example.mealerapp.objects.Admin;
import com.example.mealerapp.objects.Client;
import com.example.mealerapp.objects.Cook;
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class LogInFragment extends Fragment {

    private FragmentLoginBinding binding;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Database database;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        database = new Database();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LogInFragment.this)
                        .navigate(R.id.action_FirstFragment_to_register_client2);
            }
        });

        binding.registerCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LogInFragment.this)
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

                if (!Helper.isValidEmail(email) || !Helper.isPasswordValid(password)) {
                    return;
                }

                final User[] user = new User[1];

                database.getFirestore().collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User.UserType type = User.UserType.valueOf(document.get("type").toString());

                                    if (type == null) {
                                        return;
                                    }
                                    getView().findViewById(R.id.invalid_login).setVisibility(View.GONE);

                                    if (type == User.UserType.CLIENT) {
                                        user[0] = new Client(email, password, type);
                                    }
                                    if (type == User.UserType.COOK) {
                                        user[0] = new Cook(email, password, type);
                                    }
                                    if (type == User.UserType.ADMIN) {
                                        user[0] = new Admin(email, password, type);
                                    }

                                    if (Helper.isValidEmail(email) && Helper.isPasswordValid(password) && user[0] != null) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("type", user[0].getType().toString());
                                        bundle.putString("email", user[0].getEmail());
                                        bundle.putString("id", (String) document.get("id"));
                                        if (type == User.UserType.ADMIN) {
                                            adminWelcomeScreen ws = new adminWelcomeScreen();
                                            ws.setArguments(bundle);
                                            NavHostFragment.findNavController(LogInFragment.this)
                                                    .navigate(R.id.action_FirstFragment_to_welcome_screen, bundle);
                                        } else if (type == User.UserType.COOK) {
                                            cookWelcomeScreen cookScreen = new cookWelcomeScreen();
                                            cookScreen.setArguments(bundle);
                                            NavHostFragment.findNavController(LogInFragment.this)
                                                    .navigate(R.id.login_to_cookWelcomeScreen, bundle);
                                        }

                                    }
                                }
                            } else {
                                getView().findViewById(R.id.invalid_login).setVisibility(View.VISIBLE);
                                Log.d("Error", "Error getting documents");
                            }
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}