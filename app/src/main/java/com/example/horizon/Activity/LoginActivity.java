package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horizon.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    public static final String SHARED_PREFS = "sharedPrefs";
    private EditText loginEmail, loginPassword;

    private CheckBox rememberMe;
    private Button loginBtn;
    private TextView signupRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        rememberMe = findViewById(R.id.checkbox);
        loginBtn = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signup_re_button);

        SharedPreferences sharedPreferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = sharedPreferences.getString("remember","");
        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(checkbox.equals("false")){
            Toast.makeText(this, "vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if(!pass.isEmpty()){
                        auth.signInWithEmailAndPassword(email,pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại, vui lòng kiểm tra lại email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                    else {
                        loginPassword.setError("Vui lòng nhập lại mật khẩu");
                    }
                } else if (email.isEmpty()){
                        loginEmail.setError("Vui lòng nhập email");
                    }
                    else {
                        loginEmail.setError("Vui lòng điền đầy đủ email");
                    }
                }

        });



       rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if(compoundButton.isChecked()){
                   SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString("remember", "true");
                   editor.apply();
                   Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
               } else if (!compoundButton.isChecked()) {
                   SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putString("remember", "false");
                   editor.apply();
                   Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
               }
           }
       });


        signupRedirectText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                    }
                });
    }
}