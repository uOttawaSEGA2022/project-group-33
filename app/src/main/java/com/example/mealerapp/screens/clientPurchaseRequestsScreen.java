package com.example.mealerapp.screens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.OrderRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link clientPurchaseRequestsScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class clientPurchaseRequestsScreen extends Fragment {

    ListView clientRequestsListView;
    ArrayList<OrderRequest> orders;
    clientRequestsAdapter requestsAdapter;
    Database database;
    String clientEmail;

    public clientPurchaseRequestsScreen() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static clientPurchaseRequestsScreen newInstance(String param1, String param2) {
        clientPurchaseRequestsScreen fragment = new clientPurchaseRequestsScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database();
        orders = new ArrayList<>();
        clientEmail = getArguments().getString("clientEmail");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client_purchase_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clientRequestsListView = getView().findViewById(R.id.clientPurchaseRequestList);

        database.getFirestore().collection("orders").whereEqualTo("clientEmail", clientEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Boolean deletedFromClient = Boolean.parseBoolean(document.get("deletedFromClient").toString());

                            if (!deletedFromClient) {
                                String clientEmail = document.get("clientEmail").toString();
                                String cookEmail = document.get("cookEmail").toString();
                                String id = document.get("id").toString();
                                String mealTitle = document.get("mealTitle").toString();
                                Float price = Float.parseFloat(document.get("price").toString());
                                String status = document.get("status").toString();
                                Boolean deletedFromCook = Boolean.parseBoolean(document.get("deletedFromCook").toString());
                                Boolean isReviewed = Boolean.parseBoolean(document.get("isReviewed").toString());
                                Boolean submittedComplaint = Boolean.parseBoolean(document.get("submittedComplaint").toString());

                                OrderRequest order = new OrderRequest(cookEmail, clientEmail, id, mealTitle, price, status, deletedFromClient, deletedFromCook, isReviewed, submittedComplaint);
                                orders.add(order);
                            }

                        }

                        requestsAdapter = new clientRequestsAdapter(getView().getContext(), orders, clientRequestsListView);
                        clientRequestsListView.setAdapter(requestsAdapter);
                    }
                }
            }
        });

    }
}