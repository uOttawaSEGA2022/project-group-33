package com.example.mealerapp.screens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealerapp.R;
import com.example.mealerapp.objects.Database;
import com.example.mealerapp.objects.Meal;
import java.util.ArrayList;

public class MealListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Meal> meals;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public MealListAdapter(Context ctx, ArrayList<Meal> meals, ListView lv) {
        this.context = ctx;
        this.meals = meals;
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
        view = inlater.inflate(R.layout.meal_list_item, null);

        Meal thisMeal = meals.get(mealIndex);

        TextView mealTitle = view.findViewById(R.id.mealTitle);
        TextView mealPrice = view.findViewById(R.id.mealPrice);
        TextView mealDescription = view.findViewById(R.id.mealDescription);

        mealTitle.setText(thisMeal.getMealName());
        mealPrice.setText(String.valueOf(thisMeal.getPrice()));
        mealDescription.setText(thisMeal.getMealDescription());

        Button offeredMealListButton = view.findViewById(R.id.addRemoveOffered);
        Button deleteMealButton = view.findViewById(R.id.deleteMeal);

        if (thisMeal.isOnOfferedMealList()) {
            offeredMealListButton.setText("Remove From Offered Meal List");
            deleteMealButton.setVisibility(View.GONE);
        } else {
            offeredMealListButton.setText("Add to Offered Meal List");
            deleteMealButton.setVisibility(View.VISIBLE);
        }

        deleteMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisMeal.deleteFromDatabase();
                meals.remove(mealIndex);
                lv.invalidateViews();
                Toast.makeText(view.getContext(), "Meal Deleted!", Toast.LENGTH_LONG).show();
                return;
            }
        });

        offeredMealListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisMeal.isOnOfferedMealList()) {
                    offeredMealListButton.setText("Add to Offered Meal List");
                    database.getFirestore().collection("meals").document(thisMeal.getId()).update("onOfferedList", false);
                    thisMeal.setOnOfferedMealList(false);
                    deleteMealButton.setVisibility(View.VISIBLE);
                } else {
                    offeredMealListButton.setText("Remove From Offered Meal List");
                    database.getFirestore().collection("meals").document(thisMeal.getId()).update("onOfferedList", true);
                    thisMeal.setOnOfferedMealList(true);
                    deleteMealButton.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }
}
