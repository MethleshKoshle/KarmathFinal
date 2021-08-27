package com.methleshkoshle.karmathfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.methleshkoshle.karmathfinal.adapter.ItemAdapter;
import com.methleshkoshle.karmathfinal.api.ContentApi;
import com.methleshkoshle.karmathfinal.constant.Conditionals;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.feedback.AboutmeActivity;
import com.methleshkoshle.karmathfinal.feedback.WriteActivity;
import com.methleshkoshle.karmathfinal.model.ItemRow;
import com.methleshkoshle.karmathfinal.pages.ContentActivity;
import com.methleshkoshle.karmathfinal.pages.FavoriteActivity;
import com.methleshkoshle.karmathfinal.pages.SongActivity;
import com.methleshkoshle.karmathfinal.pages.ThoughtActivity;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static Context context;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item ) {
        switch (item.getItemId()){
            case R.id.nav_song:
                if(!Conditionals.isInternetWorking(context)){
                    Toast.makeText(HomeActivity.this, Constant.NO_INTERNET_MESSAGE, Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(HomeActivity.this, SongActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_favorites:
                Intent intent2 = new Intent(HomeActivity.this, FavoriteActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_share:
                Constant.shareApp(getApplicationContext());
                break;
            case R.id.nav_abt_me:
                Intent intent5 = new Intent(HomeActivity.this, AboutmeActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_write_a_review:
                Intent intent6 = new Intent(HomeActivity.this, WriteActivity.class);
                startActivity(intent6);
                break;
            case R.id.nav_thought:
                Intent intent7 = new Intent(HomeActivity.this, ThoughtActivity.class);
                startActivity(intent7);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivity.context = getApplicationContext();
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final ArrayList<ItemRow> mExampleList = new ArrayList<>();
        for(int i=0; i<Constant.CATEGORY_COUNT; i++){
            mExampleList.add(
                new ItemRow(
                    Constant.imageResource[i], Constant.hindiName[i], Constant.contentDescription[i]
                )
            );
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            Intent intent = new Intent(HomeActivity.this, ContentActivity.class);
            @Override
            public void onItemClick(int position) {
                ContentActivity.name = Constant.name[position];
                ContentActivity.fileName = Constant.fileName[position];
                ContentActivity.tempFileName = Constant.tempFileName[position];
                ContentActivity.imageResource = Constant.imageResource[position];
                ContentActivity.menuResource = Constant.menuResource[position];
                ContentActivity.labelResID = Constant.labelResID[position];
                if(!Conditionals.isInternetWorking(context)) {
                    Toast.makeText(HomeActivity.this, Constant.NO_INTERNET_MESSAGE, Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}