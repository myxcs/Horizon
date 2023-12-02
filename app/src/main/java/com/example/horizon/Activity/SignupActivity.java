package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horizon.Models.UserModel;
import com.example.horizon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    //tạo account

     private FirebaseAuth auth;
     private FirebaseDatabase database;
     private DatabaseReference reference;

     private UserModel userModel;
    private EditText signupEmail, signupPassword, signupName;
    private Button signupBtn;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //database cho phần đăng kí
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
     //   reference = FirebaseDatabase.getInstance().getReference().child("Users");


        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupBtn = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.signup_text);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            //chuyển qua signin
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

    }

    private void createUser() {

        //tạo user

        String userMail = signupEmail.getText().toString();
        String userPass = signupPassword.getText().toString();
        String userName = signupName.getText().toString();


        //ktra nhập
        if (TextUtils.isEmpty(userName)) {
            signupName.setError("Tên người dùng không được để trống");
            return;
        }
        if(TextUtils.isEmpty(userMail)){
            signupEmail.setError("Email không được để trống");
            return;
        }
        if(TextUtils.isEmpty(userPass)){
            signupPassword.setError("Mật khẩu không được để trống");
            return;

        }
        if(userPass.length() < 8){
            signupPassword.setError("Mật khẩu ít nhất có 8 kí tự");
            return;
        }
        else {
            auth.createUserWithEmailAndPassword(userMail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                     //   set data user lên firebase
                        UserModel userModel = new UserModel(userName, userMail, userPass,"https://firebasestorage.googleapis.com/v0/b/horizon-7a734.appspot.com/o/profile_picture%2F8Oj4GCgAuFdc121pAr6FeQAfpaH2?alt=media&token=94faf616-ee94-4a06-80a3-687fca8a15d5",0);
                        String id = task.getResult().getUser().getUid();
                        database.getReference().child("Users").child(String.valueOf(id)).setValue(userModel);

//                        reference.child("user").child(String.valueOf(id)).setValue(userModel);
//                        reference.child("User").child(String.valueOf(userModel.getUserId())).setValue(userModel);
//                        database.getReference().child("Users").child(String.valueOf(id)).setValue(userModel);
                          //push dữ liệu
//                        reference.push().setValue(userModel);
                        Toast.makeText(SignupActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "Đăng kí thất bại: " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}