package com.example.horizon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.horizon.R;

public class BuyConfirm extends AppCompatActivity {
    private ImageView back;
    private Button buyConfirm;

    private TextView recharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_confirm);

        back = findViewById(R.id.back_button);
        buyConfirm = findViewById(R.id.buy_button);
        recharge = findViewById(R.id.button_recharge);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyConfirm.this, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);

            }
        });
        buyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyConfirm.this, BuySuccess.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyConfirm.this, Recharge.class);
                intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                startActivity(intent);
            }
        });
    }
}