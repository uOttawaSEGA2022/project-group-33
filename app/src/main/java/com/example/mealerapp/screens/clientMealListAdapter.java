package com.example.mealerapp.screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.Meal;

import java.util.ArrayList;
import java.util.List;

public class clientMealListAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Meal> originalMeals;
    ArrayList<Meal> meals;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public clientMealListAdapter(Context ctx, ArrayList<Meal> meals, ListView lv) {
        super(ctx, R.layout.client_meal_list_item, meals);
        this.context = ctx;
        this.meals = meals;
        this.originalMeals = meals;
        this.lv = lv;
        database = new Database();
        inlater = LayoutInflater.from(ctx);
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

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
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
