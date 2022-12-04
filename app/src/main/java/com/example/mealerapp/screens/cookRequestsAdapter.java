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
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.OrderRequest;

import java.util.ArrayList;

public class cookRequestsAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderRequest> orders;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public cookRequestsAdapter(Context ctx, ArrayList<OrderRequest> orders, ListView lv) {
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
        view = inlater.inflate(R.layout.cook_request_list_item, null);
        OrderRequest thisOrder = orders.get(orderIndex);
        String orderId = orders.get(orderIndex).getId();

        TextView mealTitle = view.findViewById(R.id.cookOrderMealTitle);
        TextView mealPrice = view.findViewById(R.id.cookOrderMealPrice);
        TextView status = view.findViewById(R.id.cookRequestStatus);
        TextView from = view.findViewById(R.id.cookFromEmail);

        mealTitle.setText(thisOrder.getMealTitle());
        mealPrice.setText(String.valueOf(thisOrder.getPrice()));
        status.setText(thisOrder.getStatus());
        from.setText(thisOrder.getClientEmail());

        Button approveRequestButton = view.findViewById(R.id.cookApproveRequest);
        Button rejectRequestButton = view.findViewById(R.id.cookRejectRequest);
        Button deleteRequestButton = view.findViewById(R.id.cookDeleteRequest);

        approveRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getFirestore().collection("orders").document(orderId).update("status", "approved");
                thisOrder.setStatus("approved");
                approveRequestButton.setVisibility(View.GONE);
                rejectRequestButton.setVisibility(View.GONE);
                deleteRequestButton.setVisibility(View.VISIBLE);
                status.setText(thisOrder.getStatus());
            }
        });

        rejectRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getFirestore().collection("orders").document(orderId).update("status", "rejected");
                thisOrder.setStatus("rejected");
                approveRequestButton.setVisibility(View.GONE);
                rejectRequestButton.setVisibility(View.GONE);
                deleteRequestButton.setVisibility(View.VISIBLE);
                status.setText(thisOrder.getStatus());
            }
        });

        deleteRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getFirestore().collection("orders").document(orderId).update("deletedFromCook", true);
                thisOrder.setDeletedFromCook(true);
                orders.remove(orderIndex);
                lv.invalidateViews();
            }
        });

        if (thisOrder.getStatus().equals("pending")) {
            approveRequestButton.setVisibility(View.VISIBLE);
            rejectRequestButton.setVisibility(View.VISIBLE);
        } else {
            approveRequestButton.setVisibility(View.GONE);
            rejectRequestButton.setVisibility(View.GONE);
            deleteRequestButton.setVisibility(View.VISIBLE);
        }

        lv.invalidateViews();
        return view;
    }

}
