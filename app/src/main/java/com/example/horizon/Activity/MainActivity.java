package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.horizon.Fragment.HomeFragment;
import com.example.horizon.Fragment.NotificationFragment;
import com.example.horizon.Fragment.ProfileFragment;
import com.example.horizon.R;
import com.example.horizon.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

//    BottomNavigationView bottomNavigationView;
//    HomeFragment homeFragment = new HomeFragment();
//    NotificationFragment notificationFragment = new NotificationFragment();
//    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//    bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
//    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected( MenuItem item) {
//            int itemId = item.getItemId();
//            if (itemId == R.id.nav_home) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
//                return true;
//            } else if (itemId == R.id.nav_notification) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
//                return true;
//            } else if (itemId == R.id.nav_profile) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
//                return true;
//            }
//            return false;
//        }
//    });



}}
