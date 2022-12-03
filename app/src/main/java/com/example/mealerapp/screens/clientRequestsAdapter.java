package com.example.mealerapp.screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.OrderRequest;
import com.example.mealerapp.objects.Database;

import java.util.ArrayList;

public class clientRequestsAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderRequest> orders;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public clientRequestsAdapter(Context ctx, ArrayList<OrderRequest> orders, ListView lv) {
        this.context = ctx;
        this.orders = orders;
        this.lv = lv;
        database = new Database();
        inlater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return orders.size();
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
    public View getView(int orderIndex, View view, ViewGroup viewGroup) {
        view = inlater.inflate(R.layout.client_request_list_item, null);
        OrderRequest thisOrder = orders.get(orderIndex);

        TextView mealTitle = view.findViewById(R.id.clientOrderMealTitle);
        TextView mealPrice = view.findViewById(R.id.clientOrderMealPrice);
        TextView status = view.findViewById(R.id.clientRequestStatus);

        mealTitle.setText(thisOrder.getMealTitle());
        mealPrice.setText(String.valueOf(thisOrder.getPrice()));
        status.setText(thisOrder.getStatus());

        Button reviewChef = view.findViewById(R.id.clientReviewChef);

        reviewChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (thisOrder.getStatus().equals("approved")) {
            reviewChef.setVisibility(View.VISIBLE);
        } else {
            reviewChef.setVisibility(View.GONE);
        }

        return view;
    }

}
