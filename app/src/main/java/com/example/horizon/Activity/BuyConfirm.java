package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    DatabaseReference getGetMoneyDataReference;
    DatabaseReference getBanStatusReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_confirm);

//tránh chết chương trình khi bị ban đột ngột
        try {
            getGetMoneyDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/money");
            getBanStatusReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/banStatus");
              //thực sự đã cố để khi bị ban thì sễ out ra màn login nhưng chế mãi không ra =))
//            if (getGetMoneyDataReference == null) {
//                getGetMoneyDataReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//                        Intent intent = new Intent(BuyConfirm.this, LoginActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        finish();
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(BuyConfirm.this, "T", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Tài khoản đã bị ban", Toast.LENGTH_SHORT).show();
        }
        getBanStatusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Toast.makeText(BuyConfirm.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean banStatus = snapshot.getValue(boolean.class);
                    if (banStatus) {
                        Toast.makeText(BuyConfirm.this, "Bạn đã bị cấm", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BuyConfirm.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BuyConfirm.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
            }
        });






        buyConfirm = findViewById(R.id.buy_button);
        recharge = findViewById(R.id.button_recharge);
        playerMoney = findViewById(R.id.player_money);

        //this is for back to main button, to lazy to change it name
        buyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyConfirm.this, MainActivity.class);
              //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });

        // this is for recharge activity
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyConfirm.this, Recharge.class);
              //  intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                startActivity(intent);
            }
        });

        //to show player money
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