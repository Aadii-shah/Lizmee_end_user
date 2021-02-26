package com.example.lizmee_end_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, NavigationView.OnNavigationItemSelectedListener {


    //Variables
    static final float END_SCALE = 0.7f;
    private static final String TAG = "MainActivity";
    String categoryName[];
    int categoryImages[] = {
            R.drawable.ic_fashion,
            R.drawable.ic_mobile,
            R.drawable.ic_electronics,
            R.drawable.ic_home,
            R.drawable.ic_groceries,
            R.drawable.ic_skincare,
            R.drawable.ic_fruniture,
            R.drawable.ic_books,
            R.drawable.ic_automobile};

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView drawer_icon;
    RelativeLayout main_layout;

    //RECYCLERVIEW
    RecyclerView sliderRecyclerView, categoryRecycler_View;
    ArrayList<TopSliderModel> topSliderModels;
    TopSliderAdapter topSliderAdapter;

    //PRODUCTS RECYCLERVIEW
    RecyclerView productList;
    ArrayList<ProductListModel> productListModels;
    ProductListAdapter productListAdapter;

    //LINEAR LAYOUT
    LinearLayout deliveryLocation;

    TextView recievedUserNAme;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recievedUserNAme = findViewById(R.id.recievedUserNAme);
        // create the get Intent object
        Intent intent = getIntent();

        // receive the value by getStringExtra() method
        // and key must be same which is send by first activity
        String str = intent.getStringExtra("message_key");

        // display the string into textView
        recievedUserNAme.setText(str);

        deliveryLocation = findViewById(R.id.deliveryLocation);
        deliveryLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(MainActivity.this, UserDeliveryLocationActivity.class);
                startActivity(intent);

            }
        });

         /* if (FirebaseAuth.getInstance().getCurrentUser()== null){
           startLoginActivity();
       } else {
           FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                   .addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                       @Override
                       public void onSuccess(GetTokenResult getTokenResult) {
                           Log.d(TAG, "onSuccess: " + getTokenResult.getToken());

                       }
                   });


       }*/

       /*  relativeCategory = findViewById(R.id.relativeCategory);
         relativeMerchandise = findViewById(R.id.relativeMerchandise);
         relativeSecondhand = findViewById(R.id.relativeSecondhand);*/

       /*  relativeCategory.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                   relativeCategory.setBackground(getResources().getDrawable(R.drawable.color_custom_bottom));
             }
         });

         relativeMerchandise.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 relativeMerchandise.setBackground(getResources().getDrawable(R.drawable.color_custom_bottom));
             }
         });

        relativeSecondhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeSecondhand.setBackground(getResources().getDrawable(R.drawable.color_custom_bottom));
            }
        });*/


        //ARRAYLIST
        categoryName = getResources().getStringArray(R.array.Category);


        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        drawer_icon = findViewById(R.id.drawer_icon);
        main_layout = findViewById(R.id.main_layout);

        navigationDrawer();

        //Top Slider Recycler Hooks

        sliderRecyclerView = findViewById(R.id.sliderRecyclerView);
        topSliderModels = new ArrayList<>();
        topSliderAdapter = new TopSliderAdapter(topSliderModels, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        sliderRecyclerView.setLayoutManager(mLayoutManager);
        sliderRecyclerView.setAdapter(topSliderAdapter);
        fetchTopSliderImages();


        //CATEGORY RECYCLERVIEW

        categoryRecycler_View = findViewById(R.id.categoryRecycler_View);

        int numberOfColumns = 3;


        CategoryRecyclerView categoryRecyclerView = new CategoryRecyclerView(categoryName, categoryImages, this);
        categoryRecycler_View.setAdapter(categoryRecyclerView);
        categoryRecycler_View.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        categoryRecycler_View.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        categoryRecycler_View.setNestedScrollingEnabled(false);
        categoryRecycler_View.setLayoutManager(new GridLayoutManager(this, numberOfColumns));



        //HOME PRODUCTS LISTS

        productList = findViewById(R.id.productList);
        productListModels = new ArrayList<>();
        productListAdapter = new ProductListAdapter(productListModels, MainActivity.this);
        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        // recyclerView.setLayoutManager(staggeredGridLayoutManager);
        int numberOfColumnsGrid = 2;
        productList.setLayoutManager(new GridLayoutManager(this, numberOfColumnsGrid));
        productList.setAdapter(productListAdapter);
        productList.setNestedScrollingEnabled(false);
        fetchProductList();


    }

    private void fetchProductList() {

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ProductList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue() != null) {

                        //Log.v("HoroscopeAries", "ok " + Objects.requireNonNull(dataSnapshot.getValue()).toString());
                        List<ProductListModel> feedList = new ArrayList<>();
                        feedList.clear();

                        for (DataSnapshot feedSnapshot : dataSnapshot.getChildren()) {
                            ProductListModel feedItem = feedSnapshot.getValue(ProductListModel.class);
                            feedList.add(feedItem);
                        }

                        productListModels.clear();
                        productListModels.addAll(feedList);

                        // refreshing recycler view
                        productListAdapter.notifyDataSetChanged();

                    }  //todo
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                //  Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void fetchTopSliderImages() {


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("TopSlider").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue() != null) {

                        //Log.v("HoroscopeAries", "ok " + Objects.requireNonNull(dataSnapshot.getValue()).toString());
                        List<TopSliderModel> feedList = new ArrayList<>();
                        feedList.clear();

                        for (DataSnapshot feedSnapshot : dataSnapshot.getChildren()) {
                            TopSliderModel feedItem = feedSnapshot.getValue(TopSliderModel.class);
                            feedList.add(feedItem);
                        }

                        topSliderModels.clear();
                        topSliderModels.addAll(feedList);

                        // refreshing recycler view
                        topSliderAdapter.notifyDataSetChanged();

                    }  //todo
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                //  Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    //Navigation Drawer Functions
    private void navigationDrawer() {

        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_electronicDevice);

        drawer_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        animateNavigationDrawer();


    }

    private void animateNavigationDrawer() {

        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        //drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                main_layout.setScaleX(offsetScale);
                main_layout.setScaleY(offsetScale);
                drawerLayout.setScrimColor(Color.TRANSPARENT);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = main_layout.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                main_layout.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
            return;
        }


        firebaseAuth.getCurrentUser().getIdToken(true)
                .addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                    @Override
                    public void onSuccess(GetTokenResult getTokenResult) {

                        Log.d(TAG, "onSuccess: " + getTokenResult.getToken());
                    }
                });
        Log.d(TAG, "onAuthStateChanged: " + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
}