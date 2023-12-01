package com.example.horizon.Activity;

import androidx.annotation.NonNull;
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
import com.example.horizon.Models.PopularModel;
import com.example.horizon.Models.UserModel;
import com.example.horizon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Recharge extends AppCompatActivity {

    private ImageView back_button;
    private Button recharge;
    private EditText recharge_code;

    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    PopularModel popularModel =null;

    UserModel userModel;

  //  DatabaseReference getGetMoneyDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/money");

    FirebaseFirestore firestore;
  //  public TextView player_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        back_button = findViewById(R.id.back_button);
        recharge = findViewById(R.id.button_recharge);
        recharge_code = findViewById(R.id.recharge);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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

        //lấy money
//       getGetMoneyDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               if (dataSnapshot.getValue() == null) {
//                   // Handle the null case
//                   Toast.makeText(Recharge.this, "Số dụ: 0$", Toast.LENGTH_SHORT).show();
//               } else {
//                   Long value = dataSnapshot.getValue(Long.class);
//                   int moneyRaw = value.intValue();
//                   String userMoney = String.valueOf(moneyRaw);
//                 //  Toast.makeText(Recharge.this, "Số dụ: " + money + "$", Toast.LENGTH_SHORT).show();
//               }
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//               Toast.makeText(Recharge.this, "Error to show money", Toast.LENGTH_SHORT).show();
//           }
//       });
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Recharge.this, RechargeActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // startActivity(intent);
                if(recharge_code.getText().toString().equals("")){
                    Toast.makeText(Recharge.this, "Vui lòng nhập số tiền muốn nạp", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(recharge_code.getText().toString())>0) {

                    rechargeRequest();
                    Toast.makeText(Recharge.this, "Đã nhập code", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(Recharge.this, "Vui lòng nhập code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void rechargeRequest() {

        //lấy money
//        userModel = new UserModel();
//        int money = userModel.getMoney();
//        String moneyString = String.valueOf(money);

       // Toast.makeText(Recharge.this, " Số dụ: " + moneyString + "$", Toast.LENGTH_SHORT).show();

        //lấy thời gian
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("userMail", auth.getCurrentUser().getEmail());
        //cartMap.put("userMoney", moneyString);
        cartMap.put("moneyRecharge", recharge_code.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);

        firestore.collection("Recharge").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(Recharge.this, "Đã gửi yêu cầu nạp", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}