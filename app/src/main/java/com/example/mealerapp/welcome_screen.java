package com.example.mealerapp;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link welcome_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class welcome_screen extends Fragment {

    private User.UserType userType;
    private Database database;
    LinearLayout myLayout;
    ListView complaintList;

    public welcome_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment welcome_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static welcome_screen newInstance(String param1, String param2) {
        welcome_screen fragment = new welcome_screen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        userType = User.UserType.valueOf(getArguments().getString("user_type"));
        database = new Database();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    }

    @MainThread
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        myLayout = getView().findViewById(R.id.container);

        complaintList = getView().findViewById(R.id.complaintListView);
        showComplaints(view, myLayout);
    }

    public void showComplaints(@NonNull View view, LinearLayout layout) {

        ArrayList<Complaint> complaints = new ArrayList<>();

        database.firestore.collection("complaints").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String message = document.get("message").toString();
                            String date = document.get("date").toString();
                            String from = document.get("from").toString();
                            String to = document.get("to").toString();
                            String id = document.get("id").toString();

                            Complaint c = new Complaint(from, to, message, date, id);
                            complaints.add(c);
                        }

                        ComplaintListAdapter complaintListAdapter = new ComplaintListAdapter(getView().getContext(), complaints, complaintList);
                        complaintList.setAdapter(complaintListAdapter);

                    } else {
                        Log.d("Error", "Error getting documents");
                    }
                }
            }
        });

    }
}