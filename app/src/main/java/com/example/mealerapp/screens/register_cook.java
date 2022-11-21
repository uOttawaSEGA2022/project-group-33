package com.example.mealerapp.screens;

import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mealerapp.Helper;
import com.example.mealerapp.R;
import com.example.mealerapp.databinding.FragmentRegisterCookBinding;
import com.example.mealerapp.objects.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link register_cook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class register_cook extends Fragment {


    // TODO: Rename and change types of parameters
    private FragmentRegisterCookBinding binding;
    private Database database;
    private FirebaseFirestore fb;
    private EditText emailEditText;
    private EditText passwordEditText;

    public register_cook() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment register_cook.
     */
    // TODO: Rename and change types and number of parameters
    public static register_cook newInstance(String param1, String param2) {
        register_cook fragment = new register_cook();
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
        // Inflate the layout for this fragment

        database = new Database();
        fb = database.getFirestore();

        binding = FragmentRegisterCookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @MainThread
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.addCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEditText = (EditText)getView().findViewById(R.id.input_email4);
                String email = emailEditText.getText().toString();

                passwordEditText = (EditText)getView().findViewById(R.id.input_password4);
                String password = passwordEditText.getText().toString();

                if (!Helper.isValidEmail(email)) {
                    getView().findViewById(R.id.email_error3).setVisibility(View.VISIBLE);
                } else {
                    getView().findViewById(R.id.email_error3).setVisibility(View.GONE);
                }

                if (!Helper.isPasswordValid(password)) {
                    getView().findViewById(R.id.password_error3).setVisibility(View.VISIBLE);
                } else {
                    getView().findViewById(R.id.password_error3).setVisibility(View.GONE);
                }

                if (Helper.isValidEmail(email) && Helper.isPasswordValid(password)) {

                    database.getFirestore().collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            Map<String,Object> users = new HashMap<>();
                            String id = Integer.toString(task.getResult().size());
                            users.put("email", email);
                            users.put("password", password);
                            users.put("type", "COOK");
                            users.put("id", id);
                            users.put("permanentSuspension", false);
                            users.put("tempSuspension", "null");

                            Bundle bundle = new Bundle();
                            bundle.putString("type", "COOK");
                            bundle.putString("email", email);
                            bundle.putString("password", password);
                            bundle.putString("id", id);

                            database.getFirestore().collection("users").document(id).set(users);


                            NavHostFragment.findNavController(register_cook.this)
                                    .navigate(R.id.registerCook_to_cookWelcomeScreen, bundle);

                        }
                    });
                }
            }
        });
    }
}