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

public class clientMealListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Meal> meals;
    LayoutInflater inlater;
    ListView lv;
    Database database;

    public clientMealListAdapter(Context ctx, ArrayList<Meal> meals, ListView lv) {
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
        view = inlater.inflate(R.layout.client_meal_list_item, null);

        Meal thisMeal = meals.get(mealIndex);

        return view;
    }
}
