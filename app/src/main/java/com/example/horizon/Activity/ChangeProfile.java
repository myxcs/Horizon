package com.example.horizon.Activity;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.horizon.Fragment.ProfileFragment;
import com.example.horizon.R;

public class ChangeProfile extends AppCompatActivity {
   private ImageView back_button;
    private Button changeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        back_button = findViewById(R.id.back_button);
        changeName = findViewById(R.id.button_change_name);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment profileFragment = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.change_profile_layout, profileFragment).commit();
                finish();
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangeProfile.this,"Đổi tên thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}