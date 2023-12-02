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

import com.bumptech.glide.Glide;
import com.example.horizon.Models.NewGamesModel;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private ImageView back;

     private String money;
     private int moneyRaw;
     Button addToCart;
     ImageView detailImg;
     TextView detailName;
     TextView detailDescription;
     TextView detailStorage;
     PopularModel popularModel =null;
     NewGamesModel newGamesModel = null;

     UserModel userModel=null;



    FirebaseFirestore firestore;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference getGetMoneyDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/money");

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        pass ảnh game
        final Object object = getIntent().getSerializableExtra("object");
        if (object instanceof PopularModel) {
            popularModel = (PopularModel) object;
        }
        final Object object2 = getIntent().getSerializableExtra("object2");
        if (object2 instanceof NewGamesModel) {
            newGamesModel = (NewGamesModel) object2;
        }

       getGetMoneyDataReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.getValue() == null) {
                   Toast.makeText(DetailActivity.this, "Error to get money", Toast.LENGTH_SHORT).show();
                   return;
               }
               else
               {
                   Long value = dataSnapshot.getValue(Long.class);
                    moneyRaw = value.intValue();
                   money = String.valueOf(moneyRaw);
                   //player_money.setHint();
               }
           }
           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(DetailActivity.this, "Error to get money", Toast.LENGTH_SHORT).show();

           }
       });






        //activity to activity
        detailImg = findViewById(R.id.game_pics);
        detailName = findViewById(R.id.game_name);
        detailDescription = findViewById(R.id.description);
        detailStorage = findViewById(R.id.storage);
        addToCart = findViewById(R.id.buy_button);




        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        //mua
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moneyRaw>= popularModel.getPrice()){
                    addedToCart();
                    Intent intent = new Intent(DetailActivity.this, BuySuccess.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(DetailActivity.this, "Bạn không đủ tiền", Toast.LENGTH_SHORT).show();
                }

            }
        });

       // String img_url = popularModel.getImg_url();
        if (popularModel != null) {
            Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(detailImg);
            detailName.setText(popularModel.getName());
            detailDescription.setText(popularModel.getDescription());
            detailStorage.setText(popularModel.getStorage());
        }
        else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

        //String img_url = newGamesModel.getImg_url();
        if (newGamesModel != null) {
            Glide.with(getApplicationContext()).load(newGamesModel.getImg_url()).into(detailImg);
            detailName.setText(newGamesModel.getName());
            detailDescription.setText(newGamesModel.getDescription());
            detailStorage.setText(newGamesModel.getStorage());
        }

//phím back, thi thoảng lỗi
        back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);

            }
        });
        }
        //đưa thông tin mua hàng lên firestore
    private void addedToCart() {

//lấy thời gian
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());



        //them vao cart
        //hell nah sau cả buôi tôi cũng fix đc cái user money, bây h là 5 giờ sáng, tôi hối hận
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", popularModel.getName());
        cartMap.put("userMail", auth.getCurrentUser().getEmail());
        cartMap.put("userMoney", money);
        cartMap.put("productPrice", popularModel.getPrice());
        cartMap.put("Downloaded", popularModel.getDownloaded()+1);
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);

//        cartMap.put("productImage", newGamesModel.getImg_url());
//        cartMap.put("productPrice", newGamesModel.getPrice());
//        cartMap.put("Downloaded", newGamesModel.getDownloaded()+1);

        firestore.collection("addToCart").document(auth.getCurrentUser().getUid())
        .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}