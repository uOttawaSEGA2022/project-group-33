package com.example.mealerapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.protobuf.Any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComplaintListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Complaint> complaints;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public ComplaintListAdapter(Context ctx, ArrayList<Complaint> complaints, ListView lv) {
        this.context = ctx;
        this.complaints = complaints;
        this.lv = lv;
        database = new Database();
        inlater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return complaints.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inlater.inflate(R.layout.content_complaint_list_view, null);

        TextView complaintFrom = view.findViewById(R.id.complaintFrom);
        TextView complaintTo = view.findViewById(R.id.complaintTo);
        TextView complaintMessage = view.findViewById(R.id.complaintMessage);

        String toEmail = complaints.get(i).getToEmail();

        complaintFrom.setText(complaints.get(i).getFromEmail());
        complaintTo.setText(toEmail);
        complaintMessage.setText(complaints.get(i).getMessage());

        Button dismiss = view.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("yo", "yo");
                complaints.get(i).deleteFromDatabase();
                complaints.remove(i);
                lv.invalidateViews();
                return;
            }
        });

        Button permanentSuspension = view.findViewById(R.id.permSuspension);
        permanentSuspension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.firestore.collection("users").whereEqualTo("email", toEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("yoyo", "deleting");
                                document.getReference().update("permanentSuspension", true);
                                complaints.get(i).deleteFromDatabase();
                                complaints.remove(i);
                                lv.invalidateViews();
                            }
                        }
                    }
                });
            }
        });

        return view;
    }
}
