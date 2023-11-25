package com.example.horizon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.horizon.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
     BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottom_nav);

        binding.bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intent = new Intent(MainActivity.this, HomeFragment.class);
                    startActivity(intent);
                    break;
                case R.id.nav_notification:
                    Intent intent1 = new Intent(MainActivity.this, NotificationFragment.class);
                    startActivity(intent1);
                    break;
                case R.id.nav_profile:
                    Intent intent2 = new Intent(MainActivity.this, ProfileFragment.class);
                    startActivity(intent2);
                    break;
            }
            return true;
        });




    }
}