package com.example.horizon.Fragment;

import android.net.Uri;
import android.os.Bundle;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.horizon.Activity.ChangeProfile;
import com.example.horizon.Activity.LoginActivity;
import com.example.horizon.Activity.MainActivity;
import com.example.horizon.Activity.Recharge;
import com.example.horizon.Models.UserModel;
import com.example.horizon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {


    private ImageView back_button;
    private TextView player_money;
    private EditText player_name;
    CircleImageView profileImg;
    private UserModel userModel;
    Button update;
    private Button logout;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference databaseReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/name");



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        back_button = view.findViewById(R.id.back_button);
        player_money = view.findViewById(R.id.player_money);
        player_name = view.findViewById(R.id.player_name);
        profileImg = view.findViewById(R.id.profile_img);
        update = view.findViewById(R.id.update);
        storage = FirebaseStorage.getInstance();

        //make pic appear
        database.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       UserModel userModel = snapshot.getValue(UserModel.class);



                        if (userModel != null) {

                            // Do something with profileImg
                            Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
                        } else {
                            // Handle the null case
                            Toast.makeText(getContext(), "Result is null", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                player_name.setText(text);
                //player_money.setHint();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
              //  Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        player_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Recharge.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        player_money.setText("000.000.000"+" VND");

        player_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeProfile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });



        FirebaseAuth auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        logout = view.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("rememberMe", false);
                editor.apply();
                editor.commit();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getActivity().finish();
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                //this shit is not working sometime and i dont know why
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              updateUserProfile();
            }
        });
        return view;
    }

    private void updateUserProfile() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);

            final StorageReference reference = storage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profileImg").setValue(uri.toString());
                            Toast.makeText(getContext(), "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //to upload the image in the database, i dont suppose to do this sh*t, make it quick i need to sleep
                }
            });

        }
    }
}