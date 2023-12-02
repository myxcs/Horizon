package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.horizon.Adapters.GamesListAdapter;
import com.example.horizon.Adapters.NewGamesAdapter;
import com.example.horizon.Adapters.PopularAdapters;
import com.example.horizon.Adapters.SliderAdapters;
import com.example.horizon.Domian.ListGame;
import com.example.horizon.Domian.SliderItems;
import com.example.horizon.Fragment.HomeFragment;
import com.example.horizon.Fragment.NotificationFragment;
import com.example.horizon.Fragment.ProfileFragment;
import com.example.horizon.Models.NewGamesModel;
import com.example.horizon.Models.PopularModel;
import com.example.horizon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView popularGames;
    RecyclerView newGames;
    List<PopularModel> popularModelsList;
    List<NewGamesModel> newGamesModelsList;



    FirebaseAuth auth;
    FirebaseFirestore db;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private RecyclerView.Adapter adapterPopularGames;

    private RecyclerView.Adapter adapterNewGames;
    private RecyclerView recyclerViewPopularGames;
    private RecyclerView recyclerViewNewGames;
    private PopularAdapters popularAdapters;
    private NewGamesAdapter newGamesAdapter;

   // private RecyclerView.Adapter adapterBestGames, AdapterUpcomingGames, adapterCategory;
    //private RecyclerView recyclerViewBestGames, recyclerViewUpcomingGames, recyclerViewCategory;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2, mStringRequest3;
    private ProgressBar loading1, loading2, loading3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    auth = FirebaseAuth.getInstance();
    db = FirebaseFirestore.getInstance();

        //popular games recycler
        popularGames = findViewById(R.id.view1);
        popularGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        popularModelsList = new ArrayList<>();
        popularAdapters = new PopularAdapters(this, popularModelsList);
        popularGames.setAdapter(popularAdapters);

        //new games recycler
        newGames = findViewById(R.id.view2);
        newGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        newGamesModelsList = new ArrayList<>();
        newGamesAdapter = new NewGamesAdapter(this, newGamesModelsList);
        newGames.setAdapter(newGamesAdapter);



        //read firestore
        //should not like this bro, but these no faking data, forgive me

        //popular games
        db.collection("PopularGames")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               PopularModel popularModel = document.toObject(PopularModel.class);
                               popularModelsList.add(popularModel);
                               popularAdapters.notifyDataSetChanged();
                               loading1.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        //new games

        db.collection("NewGames")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                NewGamesModel newGamesModel = document.toObject(NewGamesModel.class);
                                newGamesModelsList.add(newGamesModel);
                                newGamesAdapter.notifyDataSetChanged();
                                loading2.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//navigation
    bottomNavigationView = findViewById(R.id.bottom_navigation);

    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected( MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;
            } else if (itemId == R.id.nav_notification) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
                return true;
            } else if (itemId == R.id.nav_profile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;
            }
            return false;
        }
    });

initView();
banner();
//sendRequest();

}

//    private void sendRequest() {
//        mRequestQueue = Volley.newRequestQueue(this);
//        loading1.setVisibility(View.VISIBLE);
//        mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
//            Gson gson = new Gson();
//            loading1.setVisibility(View.GONE);
//            ListGame items = gson.fromJson(response, ListGame.class);
//            adapterBestGames = new GamesListAdapter(items);
//            recyclerViewBestGames.setAdapter(adapterBestGames);
//
//        }, error -> {
//            loading1.setVisibility(View.GONE);
//            Log.i("myXcs", "onErrorResponse: " + error.toString());
//        });
//        mRequestQueue.add(mStringRequest);
//    }

    //popular games recycler



    private void banner() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.img_slider1));
        sliderItems.add(new SliderItems(R.drawable.img_slider2));
        sliderItems.add(new SliderItems(R.drawable.img_slider3));
        sliderItems.add(new SliderItems(R.drawable.img_slider4));
        sliderItems.add(new SliderItems(R.drawable.img_slider5));

        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
         viewPager2.setPageTransformer(compositePageTransformer);
         viewPager2.setCurrentItem(1);
         viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        sliderHandler.removeCallbacks(sliderRunnable);
//        sliderHandler.postDelayed(sliderRunnable, 3000);
    }
});
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    } ;

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }

    private void initView() {

        viewPager2 = findViewById(R.id.viewpages);
//        recyclerViewBestGames = findViewById(R.id.view1);
//        recyclerViewBestGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewUpcomingGames = findViewById(R.id.view2);
//        recyclerViewUpcomingGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerViewCategory = findViewById(R.id.view3);
//        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loading1 = findViewById(R.id.progressBar1);
        loading2 = findViewById(R.id.progressBar2);
//        loading3 = findViewById(R.id.progressBar3);
    }
}
