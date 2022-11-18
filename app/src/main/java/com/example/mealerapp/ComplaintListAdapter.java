package com.example.mealerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ComplaintListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> listTitle;
    LayoutInflater inlater;

    public ComplaintListAdapter(Context ctx, ArrayList<String> titles) {
        this.context = ctx;
        this.listTitle = titles;
        inlater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listTitle.size();
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
        TextView complaintFrom = (TextView) view.findViewById(R.id.complaintFrom);
        TextView complaintTo = (TextView) view.findViewById(R.id.complaintTo);
        TextView complaintMessage = (TextView) view.findViewById(R.id.complaintMessage);
        complaintFrom.setText(listTitle.get(i));
        return view;
    }
}
