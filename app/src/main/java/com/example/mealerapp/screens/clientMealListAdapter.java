package com.example.mealerapp.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.Meal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class clientMealListAdapter extends ArrayAdapter {

    Context context;
    String clientEmail;
    ArrayList<Meal> originalMeals;
    ArrayList<Meal> meals;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public clientMealListAdapter(Context ctx, ArrayList<Meal> meals, ListView lv, String clientEmail) {
        super(ctx, R.layout.client_meal_list_item, meals);
        this.context = ctx;
        this.meals = meals;
        this.originalMeals = meals;
        this.lv = lv;
        database = new Database();
        inlater = LayoutInflater.from(ctx);
        this.clientEmail = clientEmail;
    }

    @Override
    public int getCount() {
        return meals.size();
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
    public View getView(int mealIndex, View view, ViewGroup viewGroup) {
        view = inlater.inflate(R.layout.client_meal_list_item, null);
        Meal thisMeal = meals.get(mealIndex);

        TextView mealTitle = view.findViewById(R.id.clientMealTitle);
        TextView mealPrice = view.findViewById(R.id.clientMealPrice);
        TextView mealDescription = view.findViewById(R.id.clientMealDescription);

        mealTitle.setText(thisMeal.getMealName());
        mealPrice.setText(String.valueOf(thisMeal.getPrice()));
        mealDescription.setText(thisMeal.getMealDescription());

        Button placeOrderButton = view.findViewById(R.id.placeOrder);
        Button viewChefProfileButton = view.findViewById(R.id.clientViewProfile);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID uuid= UUID.randomUUID();

                Map<String, Object> placeOrder = new HashMap<>();
                placeOrder.put("id", uuid.toString());
                placeOrder.put("cookEmail", meals.get(mealIndex).getFromEmail());
                placeOrder.put("clientEmail", clientEmail);
                placeOrder.put("mealTitle", thisMeal.getMealName());
                placeOrder.put("price", thisMeal.getPrice());
                placeOrder.put("status", "pending");
                placeOrder.put("deletedFromCook", false);
                placeOrder.put("deletedFromClient", false);
                placeOrder.put("isReviewed", false);

                database.getFirestore().collection("orders").document(uuid.toString()).set(placeOrder).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(view.getContext(), "Placed Order!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        viewChefProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cookEmail = meals.get(mealIndex).getFromEmail();

                database.getFirestore().collection("users").whereEqualTo("email", cookEmail).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String chefNameText = document.get("chefName").toString();
                                String numberOfRatingsText = document.get("numberOfRatings").toString();
                                String ratingText = document.get("rating").toString();

                                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                                dialog.setMessage("View Chef Profile");

                                LinearLayout layout = new LinearLayout(getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);

                                TextView chefName = new TextView(getContext());
                                chefName.setText("Chef Name: " + chefNameText);
                                chefName.setPadding(40, 10, 0 , 10);
                                layout.addView(chefName);

                                TextView rating = new TextView(getContext());
                                rating.setText("Rating: " + ratingText);
                                rating.setPadding(40, 40, 0 , 10);
                                layout.addView(rating);

                                TextView numberOfRatings = new TextView(getContext());
                                numberOfRatings.setText("Number of ratings: " + numberOfRatingsText);
                                numberOfRatings.setPadding(40, 40, 0 , 40);
                                layout.addView(numberOfRatings);

                                dialog.setView(layout);
                                dialog.show();
                            }
                        }
                    }
                });
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                meals = (ArrayList<Meal>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                List<Meal> FilteredArrList = new ArrayList<Meal>();

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = originalMeals.size();
                    results.values = originalMeals;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < meals.size(); i++) {
                        String data = meals.get(i).getMealName();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(meals.get(i));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}
