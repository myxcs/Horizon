package com.example.horizon.Fragment;

import androidx.fragment.app.Fragment;

//import com.example.horizon.Adapters.GamesListAdapter;

//đáng ra số code này được chạy
public class HomeFragment extends Fragment {

//    private ViewPager2 viewPager2;
//    private Handler sliderHandler = new Handler();
//    private RecyclerView.Adapter adapterBestGames, AdapterUpcomingGames, adapterCategory;
//    private RecyclerView recyclerViewBestGames, recyclerViewUpcomingGames, recyclerViewCategory;
//    private RequestQueue mRequestQueue;
//    private StringRequest mStringRequest, mStringRequest2, mStringRequest3;
//    private ProgressBar loading1, loading2, loading3;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.fragment_home, container, false);
//        initView();
//        banner();
//        sendRequest();
//
//
//        private void sendRequest () {
//            mRequestQueue = Volley.newRequestQueue(this);
//            loading1.setVisibility(View.VISIBLE);
//            mStringRequest = new StringRequest(Request.Method.GET, "https://moviesapi.ir/api/v1/movies?page=1", response -> {
//                Gson gson = new Gson();
//                loading1.setVisibility(View.GONE);
//                ListGame items = gson.fromJson(response, ListGame.class);
//                adapterBestGames = new GamesListAdapter(items);
//                recyclerViewBestGames.setAdapter(adapterBestGames);
//
//            }, error -> {
//                loading1.setVisibility(View.GONE);
//                Log.i("myXcs", "onErrorResponse: " + error.toString());
//            });
//            mRequestQueue.add(mStringRequest);
//        }
//
//        private void banner () {
//            List<SliderItems> sliderItems = new ArrayList<>();
//            sliderItems.add(new SliderItems(R.drawable.img_slider1));
//            sliderItems.add(new SliderItems(R.drawable.img_slider2));
//            sliderItems.add(new SliderItems(R.drawable.img_slider3));
//            sliderItems.add(new SliderItems(R.drawable.img_slider4));
//            sliderItems.add(new SliderItems(R.drawable.img_slider5));
//
//            viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
//            viewPager2.setClipToPadding(false);
//            viewPager2.setClipChildren(false);
//            viewPager2.setOffscreenPageLimit(5);
//            viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
//
//            CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//            compositePageTransformer.addTransformer(new MarginPageTransformer(40));
//            compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//                @Override
//                public void transformPage(@NonNull View page, float position) {
//                    float r = 1 - Math.abs(position);
//                    page.setScaleY(0.85f + r * 0.15f);
//                }
//            });
//            viewPager2.setPageTransformer(compositePageTransformer);
//            viewPager2.setCurrentItem(1);
//            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//                @Override
//                public void onPageSelected(int position) {
//                    super.onPageSelected(position);
//                    sliderHandler.removeCallbacks(sliderRunnable);
////        sliderHandler.postDelayed(sliderRunnable, 3000);
//                }
//            });
//        }
//
//        private Runnable sliderRunnable = new Runnable() {
//            @Override
//            public void run() {
//                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
//            }
//        };
//
//        @Override
//        protected void onPause () {
//            super.onPause();
//            sliderHandler.removeCallbacks(sliderRunnable);
//        }
//
//        @Override
//        protected void onResume () {
//            super.onResume();
//            sliderHandler.postDelayed(sliderRunnable, 2000);
//        }
//
//        private void initView () {
//
//            viewPager2 = viewPager2.findViewById();
//            recyclerViewBestGames = findViewById(R.id.view1);
//            recyclerViewBestGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//            recyclerViewUpcomingGames = findViewById(R.id.view2);
//            recyclerViewUpcomingGames.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//            recyclerViewCategory = findViewById(R.id.view3);
//            recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//            loading1 = findViewById(R.id.progressBar1);
//            loading2 = findViewById(R.id.progressBar2);
//            loading3 = findViewById(R.id.progressBar3);
//        }
//    }
}
