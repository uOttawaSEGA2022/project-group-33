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
import com.example.mealerapp.databinding.FragmentRegisterClientBinding;
import com.example.mealerapp.objects.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link register_client#newInstance} factory method to
 * create an instance of this fragment.
 */
public class register_client extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentRegisterClientBinding binding;
    private Database database;
    private FirebaseFirestore fb;
    private EditText emailEditText;
    private EditText passwordEditText;

    public register_client() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment register_client.
     */
    // TODO: Rename and change types and number of parameters
    public static register_client newInstance(String param1, String param2) {
        register_client fragment = new register_client();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        database = new Database();
        fb = database.getFirestore();
        binding = FragmentRegisterClientBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @MainThread
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEditText = (EditText)getView().findViewById(R.id.input_email3);
                String email = emailEditText.getText().toString();

                passwordEditText = (EditText)getView().findViewById(R.id.input_password3);
                String password = passwordEditText.getText().toString();

                if (!Helper.isValidEmail(email)) {
                    getView().findViewById(R.id.email_error_client).setVisibility(View.VISIBLE);
                } else {
                    getView().findViewById(R.id.email_error_client).setVisibility(View.GONE);
                }

                if (!Helper.isPasswordValid(password)) {
                    getView().findViewById(R.id.password_error_client).setVisibility(View.VISIBLE);
                } else {
                    getView().findViewById(R.id.password_error_client).setVisibility(View.GONE);
                }

                if (Helper.isValidEmail(email) && Helper.isPasswordValid(password)) {

                    database.getFirestore().collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            Map<String,Object> users = new HashMap<>();
                            String id = Integer.toString(task.getResult().size());
                            users.put("email", email);
                            users.put("password", password);
                            users.put("type", "CLIENT");

                            Bundle bundle = new Bundle();
                            bundle.putString("type", "CLIENT");
                            bundle.putString("email", email);
                            bundle.putString("password", password);
                            bundle.putString("id", id);

                            database.getFirestore().collection("users").document(id).set(users);

                            NavHostFragment.findNavController(register_client.this)
                                    .navigate(R.id.action_register_client2_to_welcome_screen, bundle);
                        }
                    });

                }
            }
        });
    }
}