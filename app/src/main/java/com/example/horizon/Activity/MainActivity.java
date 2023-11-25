package com.example.horizon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.horizon.Fragment.HomeFragment;
import com.example.horizon.Fragment.NotificationFragment;
import com.example.horizon.Fragment.ProfileFragment;
import com.example.horizon.R;
import com.example.horizon.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    bottomNavigationView = findViewById(R.id.bottom_navigation);


    }

}