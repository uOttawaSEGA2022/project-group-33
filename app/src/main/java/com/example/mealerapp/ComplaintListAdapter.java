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

import java.util.ArrayList;

public class ComplaintListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Complaint> complaints;
    LayoutInflater inlater;
    ListView lv;

    public ComplaintListAdapter(Context ctx, ArrayList<Complaint> complaints, ListView lv) {
        this.context = ctx;
        this.complaints = complaints;
        this.lv = lv;
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

        complaintFrom.setText(complaints.get(i).getFromEmail());
        complaintTo.setText(complaints.get(i).getToEmail());
        complaintMessage.setText(complaints.get(i).getMessage());
//        complaintFrom.setText(complaints.get(i).getFromEmail());

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

        return view;
    }
}
