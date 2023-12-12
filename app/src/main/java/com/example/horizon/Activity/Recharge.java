package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Map;

public class Recharge extends AppCompatActivity {

    private ImageView back_button;
    private Button recharge;
    private EditText recharge_code;
    private String money;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    PopularModel popularModel =null;
    UserModel userModel;

    DatabaseReference getGetMoneyDataReference;
    DatabaseReference getBanStatusReference;


  //  public TextView player_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        try {
            getBanStatusReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/banStatus");
        }
        catch (Exception e) {
            Toast.makeText(this, "Tài khoản đã bị khóa", Toast.LENGTH_SHORT).show();
        }
        getBanStatusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Toast.makeText(Recharge.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean banStatus = snapshot.getValue(boolean.class);
                    if (banStatus) {
                        Toast.makeText(Recharge.this, "Bạn đã bị cấm", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Recharge.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Recharge.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
            }
        });

        getGetMoneyDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/money");


        back_button = findViewById(R.id.back_button);
        recharge = findViewById(R.id.button_recharge);
        recharge_code = findViewById(R.id.recharge);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //player_money = findViewById(R.id.player_money);

        //phím back, toàn thi thoảng bị lỗi
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment profileFragment = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, profileFragment).commit();
                finish();
            }
        });

        //lấy tiền của thằng user, phải tạo một biến toàn cục rồi lấy dữ liệu, công mò mãi mới ra
        getGetMoneyDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(Recharge.this, "Error to get money", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Long value = dataSnapshot.getValue(Long.class);
                    int moneyRaw = value.intValue();
                    money = String.valueOf(moneyRaw);
                    //player_money.setHint();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Recharge.this, "Error to get money", Toast.LENGTH_SHORT).show();

            }
        });

        //xử lí nút nạp tiền
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recharge_code.getText().toString().equals("")){
                    Toast.makeText(Recharge.this, "Vui lòng nhập số tiền muốn nạp", Toast.LENGTH_SHORT).show();
                }
                else if (Long.parseLong(recharge_code.getText().toString())>2000000000) {
                    Toast.makeText(Recharge.this, "Số tiền nạp tối đa là 2.000.000.000", Toast.LENGTH_SHORT).show();
                }
                else if (Long.parseLong(recharge_code.getText().toString())>0) {

                    rechargeRequest();
                    Toast.makeText(Recharge.this, "Đã nhập số tiền", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Recharge.this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //gửi yêu cầu nạp
    private void rechargeRequest() {


        //lấy thời gian
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> rechargeMap = new HashMap<>();
        rechargeMap.put("userMail", auth.getCurrentUser().getEmail());
        rechargeMap.put("userMoney", money);
        rechargeMap.put("moneyRecharge", recharge_code.getText().toString());
        rechargeMap.put("currentDate", saveCurrentDate);
        rechargeMap.put("currentTime", saveCurrentTime);
        rechargeMap.put("isRecharge", false);

        //gửi yêu cầu nạp
        firestore.collection("Recharge").add(rechargeMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(Recharge.this, "Đã gửi yêu cầu nạp", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}