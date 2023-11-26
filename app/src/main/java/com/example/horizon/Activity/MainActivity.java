package com.example.horizon.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.horizon.Adapters.SliderAdapters;
import com.example.horizon.Domian.SliderItems;
import com.example.horizon.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    BottomNavigationView bottomNavigationView;
//    HomeFragment homeFragment = new HomeFragment();
//    NotificationFragment notificationFragment = new NotificationFragment();
//    ProfileFragment profileFragment = new ProfileFragment();

    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//    bottomNavigationView = findViewById(R.id.bottom_navigation);
//
//    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
//    bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected( MenuItem item) {
//            int itemId = item.getItemId();
//            if (itemId == R.id.nav_home) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
//                return true;
//            } else if (itemId == R.id.nav_notification) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationFragment).commit();
//                return true;
//            } else if (itemId == R.id.nav_profile) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
//                return true;
//            }
//            return false;
//        }
//    });

initView();
banner();

}

    private void banner() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.img_slider1));
        sliderItems.add(new SliderItems(R.drawable.img_slider2));
        sliderItems.add(new SliderItems(R.drawable.img_slider3));

        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_ALWAYS);

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
    }
}
