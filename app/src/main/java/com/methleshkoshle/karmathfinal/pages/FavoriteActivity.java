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
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.adapter.ContentAdapter;
import com.methleshkoshle.karmathfinal.dao.ContentDao;
import com.methleshkoshle.karmathfinal.database.CommonDatabase;
import com.methleshkoshle.karmathfinal.model.Content;
import com.methleshkoshle.karmathfinal.model.ContentCard;
import com.methleshkoshle.karmathfinal.model.ContentViewModel;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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

        final Observer<Content.ContentTextList> contentTextListObserver = new Observer<Content.ContentTextList>() {
            @Override
            public void onChanged(@Nullable final Content.ContentTextList contentTextList) {
                mAdapter = new ContentAdapter(true, updateUI(contentTextList));

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                mAdapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                    @Override
                    public void onCopyClick(int position) {
                        text = contentTextList.contentList.get(position).content;
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
                        Constant.shareContent(getApplicationContext(), shareMessage);
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
            }
        };

        CommonDatabase.getFavorites("content");

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

    public void showEmptyMessage(){
        Context context = getApplicationContext();
        CharSequence text = "No Favorites Yet!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}