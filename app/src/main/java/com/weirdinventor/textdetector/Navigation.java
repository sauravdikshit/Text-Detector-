package com.weirdinventor.textdetector;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

public class Navigation extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        navController = androidx.navigation.Navigation.findNavController(this,R.id.nav);
        NavigationUI.setupActionBarWithNavController(this,navController);


    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();

    }
}