package com.example.mealerapp;

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

import com.example.mealerapp.databinding.FragmentRegisterCookBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link register_cook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class register_cook extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        fb = database.firestore;
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
                    Map<String,Object> users = new HashMap<>();
                    users.put("email", email);
                    users.put("password", password);
                    users.put("type", "COOK");

                    Bundle bundle = new Bundle();
                    bundle.putString("user_type", "COOK");

                    fb.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            NavHostFragment.findNavController(register_cook.this)
                                    .navigate(R.id.action_register_cook2_to_welcome_screen, bundle);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}