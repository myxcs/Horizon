package com.example.horizon.Activity;

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

public class DetailActivity extends AppCompatActivity {

    private ImageView back;
    private Button buy;

    ImageView detailImg;
    TextView detailName;
    PopularModel popularModel =null;
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
        buy = findViewById(R.id.buy_button);


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
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, BuyConfirm.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            } });
        }
}