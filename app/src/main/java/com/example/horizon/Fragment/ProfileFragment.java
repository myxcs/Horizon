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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.horizon.Activity.ChangeMail;
import com.example.horizon.Activity.ChangePass;
import com.example.horizon.Activity.ChangeProfile;
import com.example.horizon.Activity.LoginActivity;
import com.example.horizon.Activity.MainActivity;
import com.example.horizon.Activity.Recharge;
import com.example.horizon.Models.UserModel;
import com.example.horizon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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


  //  private ImageView back_button;

    private String money;
    private TextView player_money;
    private TextView player_name;
    private TextView player_email;
    private TextView player_password;
    CircleImageView profileImg;
    private UserModel userModel;

    LinearLayout container;

    private Button logout;
    private   FirebaseAuth auth;
    private FirebaseFirestore db;

  //  FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseStorage storage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference getNameDataReference;
    DatabaseReference getGetMoneyDataReference;
    DatabaseReference getProfileImgDataReference;
    DatabaseReference getEmailDataReference;
    DatabaseReference getBanStatusReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        //have no idea how it work
        try {
            getBanStatusReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/banStatus");
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "Tài khoản đã bị khóa", Toast.LENGTH_SHORT).show();
        }
        getBanStatusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Toast.makeText(getContext(), "Error to get ban status", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean banStatus = snapshot.getValue(boolean.class);
                    if (banStatus) {
                        Toast.makeText(getContext(), "Bạn đã bị cấm", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error to get ban status", Toast.LENGTH_SHORT).show();
            }
        });

        getNameDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/name");
        getGetMoneyDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/money");
        getProfileImgDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/profileImg");
        getEmailDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/email");


        back_button = view.findViewById(R.id.back_button);
        player_money = view.findViewById(R.id.player_money);
        player_name = view.findViewById(R.id.player_name);
        player_email = view.findViewById(R.id.player_email);
        player_password = view.findViewById(R.id.player_password);
        profileImg = view.findViewById(R.id.profile_img);
        logout = view.findViewById(R.id.logout_button);
       // container = view.findViewById(R.id.container);


        //make some change here, remember it
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();



        //make pic appear
//        database.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                       UserModel userModel = snapshot.getValue(UserModel.class);
//
//                        if (userModel != null) {
//                            // Do something with profileImg
//                            Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
//                        } else {
//                            // Handle the null case
//                            Toast.makeText(getContext(), "Result is null", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        getNameDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                player_name.setText(text);
                //player_money.setHint();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error to show profile name", Toast.LENGTH_SHORT).show();
            }
        });

        getGetMoneyDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    player_money.setText("Số dư: 0$");
                    return;
                }
                else
                {
                Long value = dataSnapshot.getValue(Long.class);
                int moneyRaw = value.intValue();
                 money = String.valueOf(moneyRaw);
                player_money.setText("Số dư: " + money + "$");
                //player_money.setHint();
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  Toast.makeText(getContext(), "Error to show profile money", Toast.LENGTH_SHORT).show();
            }
        });



        getProfileImgDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                try{
                    Glide.with(getContext()).load(text).into(profileImg);
                }
                catch (Exception e){

                    Toast.makeText(getContext(), "Error to show profile image", Toast.LENGTH_SHORT).show();
                }

                //player_money.setHint();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  Toast.makeText(getContext(), "Error to show profile image", Toast.LENGTH_SHORT).show();
            }
        });

        getEmailDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                player_email.setText("Email: " + text);
                //player_money.setHint();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  Toast.makeText(getContext(), "Error to show profile email", Toast.LENGTH_SHORT).show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getContext(), MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        player_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(getContext(), "money" + money, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), Recharge.class);
             //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        player_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeProfile.class);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        player_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeMail.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        player_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePass.class);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //không hiểu sao bị lỗi nhấn xuyên qua fragment, thế là mình tạo một cái linearlayout bao quát nhấn vào sẽ không có response gì, lmao thật
//        container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //nút logout này có lỗi nhưng ko biết fix =))
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "Error to logout", Toast.LENGTH_SHORT).show();
                }

                auth.signOut();
                FirebaseAuth.getInstance().signOut();
                FirebaseFirestore.getInstance().clearPersistence();

                    SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    try {
                        editor.clear();
                        editor.putString("remember", "false");
                        editor.apply();
                        editor.commit();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), "Error to logout", Toast.LENGTH_SHORT).show();
                    }



                    Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();

                //this shit is not working sometime and i dont know why
            }
        });

        //tính ra là định cho nút update nằm ở profile luôn
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              updateUserProfile();
//            }
//        });

        return view;
    }

//    private void updateUserProfile() {
//
//
//    }

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

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profileImg").setValue(uri.toString());
                            Toast.makeText(getContext(), "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Error to update profile picture", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //to upload the image in the database, i dont suppose to do this sh*t, make it quick i need to sleep
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error to update profile", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}