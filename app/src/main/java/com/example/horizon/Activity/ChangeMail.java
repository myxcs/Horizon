package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.horizon.Fragment.ProfileFragment;
import com.example.horizon.Models.UserModel;
import com.example.horizon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ChangeMail extends AppCompatActivity {
    private ImageView back_button;
    private Button button_change_email;

    private FirebaseAuth auth;

    private DatabaseReference reference;
    private FirebaseFirestore db;
    private UserModel userModel;
    EditText change_email;



    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference getEmailDataReference;
    DatabaseReference getBanStatusReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mail);

        try {
            getEmailDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/email");
            getBanStatusReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/banStatus");
        }
        catch (Exception e) {
            Toast.makeText(this, "Tài khoản đã bị khóa", Toast.LENGTH_SHORT).show();
        }
        getBanStatusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Toast.makeText(ChangeMail.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean banStatus = snapshot.getValue(boolean.class);
                    if (banStatus) {
                        Toast.makeText(ChangeMail.this, "Bạn đã bị cấm", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangeMail.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChangeMail.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
            }
        });





        back_button = findViewById(R.id.back_button);
        button_change_email = findViewById(R.id.button_change_email);
        change_email = findViewById(R.id.change_email);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment profileFragment = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.change_profile_layout, profileFragment).commit();
                finish();
            }
        });

        button_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = change_email.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ChangeMail.this,"Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child(FirebaseAuth.getInstance().getUid()).child("email").setValue(email);
                    Toast.makeText(ChangeMail.this,"Đổi email thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    }

