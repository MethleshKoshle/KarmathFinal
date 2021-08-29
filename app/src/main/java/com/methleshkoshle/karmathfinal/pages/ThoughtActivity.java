package com.methleshkoshle.karmathfinal.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.api.ContentApi;
import com.methleshkoshle.karmathfinal.constant.Conditionals;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.model.ContentViewModel;

public class ThoughtActivity extends AppCompatActivity {
    public static ImageView thoughtOfTheDay;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    public static CardView thoughtCardView;
    public static ImageView copyThought;
    public static ImageView shareThought;
    public static ContentViewModel contentViewModel;
    public static String thoughtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought);
        if(Conditionals.isInternetWorking(getApplicationContext())) {
            ContentApi.getThoughtOfTheDayJson(getApplicationContext());
        }
        else{
            Toast.makeText(ThoughtActivity.this, "Please connect to Internet and try again!", Toast.LENGTH_SHORT).show();
        }
        thoughtOfTheDay = findViewById(R.id.imageView7);
        copyThought = findViewById(R.id.copyContent);
        copyThought.setVisibility(View.INVISIBLE);
        shareThought = findViewById(R.id.shareContent);
        shareThought.setVisibility(View.INVISIBLE);
        thoughtCardView = findViewById(R.id.cardName);
        thoughtCardView.setCardBackgroundColor(Color.parseColor("#483D8B"));
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        copyThought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = ThoughtActivity.thoughtContent+"\n\n";
                content += "Shared via © *Karmath App*\n";
                content += Constant.playstoreUrl;
                myClip = ClipData.newPlainText("text", content);
                myClipboard.setPrimaryClip(myClip);

                Context context = getApplicationContext();
                CharSequence msgText = "Content Copied!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, msgText, duration);
                toast.show();
            }
        });
        shareThought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Karmath");
                    String shareMessage;
                    shareMessage = ThoughtActivity.thoughtContent+"\n\n";
                    shareMessage += "Shared via © *Karmath App*\n";
                    shareMessage += Constant.playstoreUrl;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
    }
}