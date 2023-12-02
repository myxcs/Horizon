package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horizon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyConfirm extends AppCompatActivity {
    private Button buyConfirm;

    private TextView recharge;

    private TextView playerMoney;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference getGetMoneyDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/money");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_confirm);

        buyConfirm = findViewById(R.id.buy_button);
        recharge = findViewById(R.id.button_recharge);
        playerMoney = findViewById(R.id.player_money);

        buyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyConfirm.this, MainActivity.class);
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
        getGetMoneyDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                playerMoney.setText("Số tiền hiện tại: " + snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(BuyConfirm.this, "Error to show player money ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}