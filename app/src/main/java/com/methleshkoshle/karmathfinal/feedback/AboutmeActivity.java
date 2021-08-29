package com.methleshkoshle.karmathfinal.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.constant.Constant;

public class AboutmeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);

    }

    public void showLinkedin(MenuItem menuItem) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.linkedInUrl));
        startActivity(browserIntent);
    }

    public void showGithub(MenuItem menuItem) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.gitHubUrl));
        startActivity(browserIntent);
    }

    public void showInstagram(MenuItem menuItem) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.instagramUrl));
        startActivity(browserIntent);
    }

    public void showFacebook(MenuItem menuItem) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.facebookUrl));
        startActivity(browserIntent);
    }
}