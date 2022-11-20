package com.example.mealerapp.objects;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import android.widget.Button;

import com.example.mealerapp.R;
import com.example.mealerapp.screens.register_cook;

public class Cook extends User {
    private register_cook binding;
    private Database database;
    public Cook(String email, String password, UserType type) {
        super(email, password, type);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
       setContentView(R.layout.cook_welcome_page);
       Button addMeal = (Button) findViewById(R.id.addMeal);
       Button addMealToOffered = (Button) findViewById(R.id.addMealOffered);
       Button delMeal = (Button) findViewById(R.id.delMeal);

       delMeal.setOnClickListener(new View.OnClickListener(){
        public void onClick(View view){

            }
        });



//        public void cookFood () {
//            return;
//        }
    }
}

