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
import com.example.horizon.Models.PopularModel;
import com.example.horizon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private ImageView back;
     Button addToCart;

    ImageView detailImg;
    TextView detailName;
    PopularModel popularModel =null;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Object object = getIntent().getSerializableExtra("object");
        if (object instanceof PopularModel) {
            popularModel = (PopularModel) object;
        }


        //activity to activity
        detailImg = findViewById(R.id.game_pics);
        detailName = findViewById(R.id.game_name);
        addToCart = findViewById(R.id.buy_button);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        //mua
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           addedToCart();
                Intent intent = new Intent(DetailActivity.this, BuySuccess.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
               startActivity(intent);
            }
        });


       // String img_url = popularModel.getImg_url();
        if (popularModel != null) {
            Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(detailImg);
            detailName.setText(popularModel.getName());
        }

//        Glide.with(getApplicationContext()).load(img_url).into(detailImg);



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
//        buy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DetailActivity.this, BuyConfirm.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                finish();
//                startActivity(intent);
//            } });
        }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());


        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", popularModel.getName());
      //  cartMap.put("productPrice", popularModel.getPrice());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);

        firestore.collection("addToCart").document(auth.getCurrentUser().getUid())
        .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });


    }
}