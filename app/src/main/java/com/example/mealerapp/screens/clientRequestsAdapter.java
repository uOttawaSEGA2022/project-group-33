package com.example.mealerapp.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.OrderRequest;
import com.example.mealerapp.objects.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class clientRequestsAdapter extends BaseAdapter {

    Context context;
    ArrayList<OrderRequest> orders;
    LayoutInflater inlater;
    ListView lv;
    Database database;
    int checkedScore;

    public clientRequestsAdapter(Context ctx, ArrayList<OrderRequest> orders, ListView lv) {
        this.context = ctx;
        this.orders = orders;
        this.lv = lv;
        database = new Database();
        inlater = LayoutInflater.from(ctx);
    }

    public void setCheckedScore(int i) {
        this.checkedScore = i;
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
        Button deleteRequest = view.findViewById(R.id.clientDeleteRequest);
        Button submitComplaintButton = view.findViewById(R.id.clientSubmitComplaint);

        submitComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setMessage("Submit Complaint");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                TextView complaintTitle = new TextView(context);
                complaintTitle.setText("Complaint Message");
                complaintTitle.setPadding(40, 10, 0 , 10);
                layout.addView(complaintTitle);

                EditText complaintInput = new EditText(context);
                complaintInput.setInputType(InputType.TYPE_CLASS_TEXT);
                complaintInput.setHint("Enter complaint...");
                complaintInput.setPadding(40,0,0,0);
                layout.addView(complaintInput);

                dialog.setView(layout);

                dialog.setPositiveButton("Submit Complaint", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Map<String,Object> complaint = new HashMap<>();
                        UUID uuid = UUID.randomUUID();
                        String id = uuid.toString();

                        complaint.put("from", thisOrder.getClientEmail());
                        complaint.put("to", thisOrder.getCookEmail());
                        complaint.put("message", complaintInput.getText().toString());
                        complaint.put("id", id);

                        database.getFirestore().collection("complaints").document(id).set(complaint);
                        thisOrder.setSubmittedComplaint(true);
                        submitComplaintButton.setVisibility(View.GONE);
                        Toast.makeText(view.getContext(), "Successfully submitted Complaint!", Toast.LENGTH_LONG).show();

                    }
                });

                dialog.show();
            }
        });

        reviewChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Enter a score for the chef");
                String[] items = {"1 (bad)","2","3","4","5 (amazing)"};
                alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                setCheckedScore(1);
                                break;
                            case 1:
                                setCheckedScore(2);
                                break;
                            case 2:
                                setCheckedScore(3);
                                break;
                            case 3:
                                setCheckedScore(4);
                                break;
                            case 4:
                                setCheckedScore(5);
                                break;
                        }
                    }
                });

                alertDialog.setPositiveButton("Submit Review", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database.getFirestore().collection("users").whereEqualTo("email", thisOrder.getCookEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        int numberOfRatings = Integer.parseInt(document.get("numberOfRatings").toString());
                                        numberOfRatings++;
                                        database.getFirestore().collection("users").document(document.getId()).update("numberOfRatings", numberOfRatings);

                                        int sumOfScore = Integer.parseInt(document.get( "sumOfScore").toString());
                                        sumOfScore = sumOfScore + checkedScore;

                                        float newScore = sumOfScore / numberOfRatings;
                                        database.getFirestore().collection("users").document(document.getId()).update("sumOfScore", sumOfScore);
                                        database.getFirestore().collection("users").document(document.getId()).update("rating", newScore);
                                        thisOrder.setReviewed(true);
                                        database.getFirestore().collection("orders").document(thisOrder.getId()).update("isReviewed", true);
                                        reviewChef.setVisibility(View.GONE);
                                        Toast.makeText(view.getContext(), "Review Sent!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        deleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getFirestore().collection("orders").document(thisOrder.getId()).update("deletedFromClient", true);
                thisOrder.setDeletedFromClient(true);
                orders.remove(orderIndex);
                lv.invalidateViews();
            }
        });

        if (thisOrder.getSubmittedComplaint() == true) {
            submitComplaintButton.setVisibility(View.GONE);
        } else {
            submitComplaintButton.setVisibility(View.VISIBLE);
        }

        if (thisOrder.getReviewed() == true) {
            reviewChef.setVisibility(View.GONE);
        } else {
            reviewChef.setVisibility(View.VISIBLE);
        }

        if (thisOrder.getStatus().equals("approved")) {
            deleteRequest.setVisibility(View.VISIBLE);
        } else if (thisOrder.getStatus().equals("rejected")) {
            deleteRequest.setVisibility(View.VISIBLE);
            reviewChef.setVisibility(View.GONE);
            submitComplaintButton.setVisibility(View.GONE);
        } else {
            reviewChef.setVisibility(View.GONE);
            submitComplaintButton.setVisibility(View.GONE);
            deleteRequest.setVisibility(View.GONE);
        }

        return view;
    }

}
