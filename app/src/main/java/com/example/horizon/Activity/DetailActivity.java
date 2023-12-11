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
import com.example.horizon.Models.GamesModel;
import com.example.horizon.Models.NewGamesModel;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
     TextView detailPrice;
     PopularModel popularModel =null;
     NewGamesModel newGamesModel = null;

     GamesModel gamesModel = null;

     UserModel userModel=null;



     FirebaseFirestore firestore;
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference getGetMoneyDataReference;
     DatabaseReference getBanStatusReference;
     FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            getGetMoneyDataReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/money");
            getBanStatusReference = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/banStatus");
        }
        catch (Exception e) {
            Toast.makeText(this, "Tài khoản đã bị khóa", Toast.LENGTH_SHORT).show();
        }


         // tạo một oject lưu v truyền dữ liệu
        final Object object = getIntent().getSerializableExtra("object");
        if (object instanceof PopularModel) {
            popularModel = (PopularModel) object;
        }
        final Object object2 = getIntent().getSerializableExtra("object2");
        if (object2 instanceof NewGamesModel) {
            newGamesModel = (NewGamesModel) object2;
        }
        final Object object3 = getIntent().getSerializableExtra("object3");
        if (object3 instanceof GamesModel) {
            gamesModel = (GamesModel) object3;
        }

        // lấy số tiền hiện tại của người dùng
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
               Toast.makeText(DetailActivity.this, "Error to get money " + money, Toast.LENGTH_SHORT).show();
           }
       });

        //ban status
        getBanStatusReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    Toast.makeText(DetailActivity.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean banStatus = snapshot.getValue(boolean.class);
                    if (banStatus) {
                        Toast.makeText(DetailActivity.this, "Bạn đã bị cấm", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetailActivity.this, "Error to get ban status", Toast.LENGTH_SHORT).show();
            }
        });



        //activity to activity
        detailImg = findViewById(R.id.game_pics);
        detailName = findViewById(R.id.game_name);
        detailDescription = findViewById(R.id.description);
        detailStorage = findViewById(R.id.storage);
        detailPrice = findViewById(R.id.price);
        addToCart = findViewById(R.id.buy_button);
        back = findViewById(R.id.back_button);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        //xử lí phím mua
        addToCart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(popularModel!=null){

                    if(moneyRaw>= popularModel.getPrice()){

                        moneyRaw = moneyRaw - popularModel.getPrice();
                        getGetMoneyDataReference.setValue(moneyRaw);
                        addedToCart();
                        Intent intent = new Intent(DetailActivity.this, BuySuccess.class);
                      //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(DetailActivity.this, BuyConfirm.class);
                       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        Toast.makeText(DetailActivity.this, "Bạn không đủ tiền", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (newGamesModel!=null){
                     if (moneyRaw>= newGamesModel.getPrice()){
                         moneyRaw = moneyRaw - newGamesModel.getPrice();
                         getGetMoneyDataReference.setValue(moneyRaw);
                        addedToCart();
                        Intent intent = new Intent(DetailActivity.this, BuySuccess.class);
                      //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(DetailActivity.this, BuyConfirm.class);
                       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        Toast.makeText(DetailActivity.this, "Bạn không đủ tiền", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (gamesModel!=null){
                    if (moneyRaw>= gamesModel.getPrice()){
                        moneyRaw = moneyRaw - gamesModel.getPrice();
                        getGetMoneyDataReference.setValue(moneyRaw);
                        addedToCart();
                        Intent intent = new Intent(DetailActivity.this, BuySuccess.class);
                      //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(DetailActivity.this, BuyConfirm.class);
                       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                        Toast.makeText(DetailActivity.this, "Bạn không đủ tiền", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(DetailActivity.this, "Error to buy", Toast.LENGTH_SHORT).show();
                }
          //đoạn code này không hiểu sao lại hoạt động khá tốt, vẫn còn nhiều ngoại lệ và lỗi cần xử lí =)))
            }
        });

       // truyền dữ liệu từ model để hiển thị trong detail
        if (popularModel != null) {
            Glide.with(getApplicationContext()).load(popularModel.getImg_url()).into(detailImg);
            detailName.setText(popularModel.getName());
            detailDescription.setText(popularModel.getDescription());
            detailStorage.setText(popularModel.getStorage());
            detailPrice.setText(popularModel.getPrice() + "$");
        }
        else if (newGamesModel != null) {
            Glide.with(getApplicationContext()).load(newGamesModel.getImg_url()).into(detailImg);
            detailName.setText(newGamesModel.getName());
            detailDescription.setText(newGamesModel.getDescription());
            detailStorage.setText(newGamesModel.getStorage());
            detailPrice.setText(newGamesModel.getPrice() + "$");
        }
        else if (gamesModel != null) {
            Glide.with(getApplicationContext()).load(gamesModel.getImg_url()).into(detailImg);
            detailName.setText(gamesModel.getName());
            detailDescription.setText(gamesModel.getDescription());
            detailStorage.setText(gamesModel.getStorage());
            detailPrice.setText(gamesModel.getPrice() + "$");
        }
        else {
            Toast.makeText(this, "Error to show game", Toast.LENGTH_SHORT).show();
        }


        //phím back, thi thoảng lỗi
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });
    }

        //đưa thông tin mua hàng lên phần added to cart firestore
    private void addedToCart() {

        //lấy thời gian
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        //them vao cart
        //hell nah sau cả buổi tối cũng fix đc cái user money, bây h là 5 giờ sáng, tôi hối hận

        if(popularModel!=null){
            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", popularModel.getName());
            cartMap.put("userMail", auth.getCurrentUser().getEmail());
            cartMap.put("userMoney", money);
            cartMap.put("productPrice", popularModel.getPrice());
            cartMap.put("Downloaded", popularModel.getDownloaded()+1);
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);

            firestore.collection("addToCart").document(auth.getCurrentUser().getUid())
                    .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(DetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                    });
            addPopularGamesDownloadedData();
        }
        else if(newGamesModel!=null){

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", newGamesModel.getName());
            cartMap.put("userMail", auth.getCurrentUser().getEmail());
            cartMap.put("userMoney", money);
            cartMap.put("productPrice", newGamesModel.getPrice());
            cartMap.put("Downloaded", newGamesModel.getDownloaded()+1);
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);

            firestore.collection("addToCart").document(auth.getCurrentUser().getUid())
                    .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(DetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                    });
            addNewGamesDownloadedData();
        }
        else if(gamesModel!=null){

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", gamesModel.getName());
            cartMap.put("userMail", auth.getCurrentUser().getEmail());
            cartMap.put("userMoney", money);
            cartMap.put("productPrice", gamesModel.getPrice());
            cartMap.put("Downloaded", gamesModel.getDownloaded()+1);
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);

            firestore.collection("addToCart").document(auth.getCurrentUser().getUid())
                    .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(DetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                    });
            addGamesDownloadedData();
        }
        else {
            Toast.makeText(this, "Error popular or new is null", Toast.LENGTH_SHORT).show();
        }
        // phần downloaded bị sai nhưng vẫn chưa nghĩ ra cách sửa
    }



    //thêm dữ dữ liệu downloaded để thông kê cho phần popular games
    private void addPopularGamesDownloadedData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference popularGameRef = db.collection("PopularGames");

        popularGameRef.whereEqualTo("name", popularModel.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModel.setDownloaded(popularModel.getDownloaded() + 1);
                                popularGameRef.document(document.getId()).set(popularModel);
                            }
                        } else {
                            Toast.makeText(DetailActivity.this, "Error to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    //phần new games
    private void addNewGamesDownloadedData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference newGameRef = db.collection("NewGames");

        newGameRef.whereEqualTo("name", newGamesModel.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NewGamesModel newGamesModel = document.toObject(NewGamesModel.class);
                                newGamesModel.setDownloaded(newGamesModel.getDownloaded() + 1);
                                newGameRef.document(document.getId()).set(newGamesModel);
                            }
                        } else {
                            Toast.makeText(DetailActivity.this, "Error to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void addGamesDownloadedData() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference gameRef = db.collection("games");

        gameRef.whereEqualTo("name", gamesModel.getName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                GamesModel gamesModel = document.toObject(GamesModel.class);
                                gamesModel.setDownloaded(gamesModel.getDownloaded() + 1);
                                gameRef.document(document.getId()).set(gamesModel);
                            }
                        } else {
                            Toast.makeText(DetailActivity.this, "Error to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
