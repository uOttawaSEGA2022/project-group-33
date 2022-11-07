package com.example.mealerapp;

import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link welcome_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class welcome_screen extends Fragment {

    private User.UserType userType;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    }

    @MainThread
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView tv = getView().findViewById(R.id.client_type);
        tv.setText(userType.toString());

        if (userType == User.UserType.ADMIN) {
            showComplaints(view);
        } else {
            return;
        }
    }

    public void showComplaints(@NonNull View view) {

    }
}