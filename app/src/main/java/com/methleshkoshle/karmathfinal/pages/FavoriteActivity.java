package com.methleshkoshle.karmathfinal.pages;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.methleshkoshle.karmathfinal.HomeActivity;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.adapter.ContentAdapter;
import com.methleshkoshle.karmathfinal.dao.ContentDao;
import com.methleshkoshle.karmathfinal.database.CommonDatabase;
import com.methleshkoshle.karmathfinal.model.Content;
import com.methleshkoshle.karmathfinal.model.ContentCard;
import com.methleshkoshle.karmathfinal.model.ContentViewModel;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ContentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static LottieAnimationView lottieAnimationView;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    private String text;
    private ContentDao contentDao;

    public static ContentViewModel contentViewModel;
    private ArrayList<ContentCard> mContentCardList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.liked_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContentCardList = new ArrayList<>();

        contentDao = CommonDatabase.db.contentDao();

        lottieAnimationView = findViewById(R.id.animation);
        lottieAnimationView.playAnimation();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        final Observer<Content.ContentTextList> contentTextListObserver = contentTextList -> {
            mAdapter = new ContentAdapter(true, updateUI(contentTextList));

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            if(contentTextList.contentList.size()==0){
                String type = HomeActivity.songModeOn?Constant.SONG:Constant.CONTENT;
                Toast.makeText(FavoriteActivity.this, "No liked "+type+"s :(", Toast.LENGTH_SHORT).show();
            }

            myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

            mAdapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                }
                @Override
                public void onCopyClick(int position) {
                    text = contentTextList.contentList.get(position).content+"\n\n";
                    text += "Shared via © *Karmath App*\n";
                    text += Constant.playstoreUrl;
                    myClip = ClipData.newPlainText("text", text);
                    myClipboard.setPrimaryClip(myClip);

                    Context context = getApplicationContext();
                    CharSequence text = "Content Copied!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                @Override
                public void onShareClick(int position) {
                    String shareMessage = contentTextList.contentList.get(position).content +"\n\n";
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Karmath");
                    shareMessage += "Shared via © *Karmath App*\n";
                    shareMessage += Constant.playstoreUrl;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one to share."));
                }

                @Override
                public void onAddFavoriteClick(int position) {
                    Context context = getApplicationContext();
                    CharSequence text = "Already present!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                @Override
                public void onRemoveFavoriteClick(int position) {
                    Content content = contentTextList.contentList.get(position);
                    content.favorite = false;
                    contentTextList.contentList.set(position, content);
                    contentDao.insertAll(content);
                }
            });
            lottieAnimationView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        };

        if(HomeActivity.songModeOn)
            CommonDatabase.getFavorites(Constant.SONG);
        else
            CommonDatabase.getFavorites(Constant.CONTENT);

        contentViewModel.getCurrentContent().observe(this, contentTextListObserver);

        lottieAnimationView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);

        mAdapter = new ContentAdapter(true, mContentCardList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    private ArrayList<ContentCard> updateUI(Content.ContentTextList contentTextList){
        ArrayList<ContentCard> mContentCardList = new ArrayList<>();
        for(Content content : contentTextList.contentList){
            int contentIndex = Constant.contentIndex.get(content.category);
            mContentCardList.add(
                    new ContentCard(
                            Constant.imageResource[contentIndex], content.content, content.favorite
                    )
            );
        }
        return mContentCardList;
    }
}