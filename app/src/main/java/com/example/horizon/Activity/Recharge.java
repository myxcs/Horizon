package com.example.horizon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horizon.Fragment.ProfileFragment;
import com.example.horizon.R;

public class Recharge extends AppCompatActivity {

    private ImageView back_button;
    private Button recharge;
    private EditText recharge_code;
  //  public TextView player_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        back_button = findViewById(R.id.back_button);
        recharge = findViewById(R.id.button_recharge);
        recharge_code = findViewById(R.id.recharge);
        //player_money = findViewById(R.id.player_money);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment profileFragment = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, profileFragment).commit();
                finish();
            }
        });
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Recharge.this, RechargeActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // startActivity(intent);
                if(recharge_code.getText().toString().equals("")){
                    Toast.makeText(Recharge.this, "Vui lòng nhập code", Toast.LENGTH_SHORT).show();
                }
                else if (recharge_code.getText().toString().equals("123456")){
                Toast.makeText(Recharge.this, "Nạp thành công", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Recharge.this, "Sai code", Toast.LENGTH_SHORT).show();
            }}
        });
    }
}