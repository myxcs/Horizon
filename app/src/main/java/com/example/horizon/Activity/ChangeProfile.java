package com.example.horizon.Activity;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horizon.Fragment.ProfileFragment;
import com.example.horizon.Models.UserModel;
import com.example.horizon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeProfile extends AppCompatActivity {
   private ImageView back_button;
    private Button button_change_name;

    private FirebaseAuth auth;

    private DatabaseReference reference;
    private FirebaseFirestore db;
    private UserModel userModel;
    EditText change_name;




    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference getNameDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/name");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        back_button = findViewById(R.id.back_button);
        button_change_name = findViewById(R.id.button_change_name);
        change_name = findViewById(R.id.change_name);

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

        button_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = change_name.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ChangeProfile.this,"Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                }
                else {
                    reference.child(FirebaseAuth.getInstance().getUid()).child("name").setValue(name);
                    Toast.makeText(ChangeProfile.this,"Đổi tên thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}