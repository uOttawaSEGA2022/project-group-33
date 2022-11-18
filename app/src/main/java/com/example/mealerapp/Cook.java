package com.example.mealerapp;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.widget.Button;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mealerapp.databinding.FragmentFirstBinding;

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



        public void cookFood () {
            return;
        }
    }
}

