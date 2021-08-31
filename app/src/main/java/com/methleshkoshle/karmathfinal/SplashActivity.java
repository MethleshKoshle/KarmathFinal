package com.methleshkoshle.karmathfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.methleshkoshle.karmathfinal.database.CommonDatabase;
import com.methleshkoshle.karmathfinal.database.ContentDatabase;
import com.methleshkoshle.karmathfinal.model.Content;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        HomeActivity.songModeOn = false;
        CommonDatabase.db = Room.databaseBuilder(getApplicationContext(),
                ContentDatabase.class, "database-content").allowMainThreadQueries().build();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}