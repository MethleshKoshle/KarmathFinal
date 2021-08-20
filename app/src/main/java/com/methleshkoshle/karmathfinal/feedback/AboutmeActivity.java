package com.methleshkoshle.karmathfinal.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.methleshkoshle.karmathfinal.R;

public class AboutmeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);

    }

    public void showLinkedin(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/methlesh-koshle-208a64145/"));
        startActivity(browserIntent);
    }

    public void showGithub(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/MethleshKoshle"));
        startActivity(browserIntent);
    }

    public void showInstagram(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/methlesh_koshle/"));
        startActivity(browserIntent);
    }

    public void showFacebook(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/methlesh.koshle"));
        startActivity(browserIntent);
    }
}