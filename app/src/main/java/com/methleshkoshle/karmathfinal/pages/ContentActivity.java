package com.methleshkoshle.karmathfinal.pages;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.methleshkoshle.karmathfinal.HomeActivity;
import com.methleshkoshle.karmathfinal.api.ContentApi;
import com.methleshkoshle.karmathfinal.constant.Conditionals;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.dao.ContentDao;
import com.methleshkoshle.karmathfinal.database.CommonDatabase;
import com.methleshkoshle.karmathfinal.model.ContentCard;
import com.methleshkoshle.karmathfinal.adapter.ContentAdapter;
import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.model.Content;
import com.methleshkoshle.karmathfinal.model.Content.ContentTextList;
import com.methleshkoshle.karmathfinal.model.ContentViewModel;

import java.util.ArrayList;

public class ContentActivity extends AppCompatActivity {

    public static String name;
    public static String fileName;
    public static String tempFileName;
    public static int imageResource;
    @MenuRes
    public static int menuResource;
    @StringRes
    public static int labelResID;

    public static LottieAnimationView lottieAnimationView;
    public static ImageView categoryImageView;

    public static RecyclerView mRecyclerView;
    public static ContentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    public static ContentViewModel contentViewModel;

    private ArrayList<ContentCard> mContentCardList;

    private ContentDao contentDao;


    @Override
    protected void onStart() {
        ContentApi.contentTexts.clear();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menuResource, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(labelResID);
        setContentView(R.layout.activity_content);

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

        final Observer<ContentTextList> contentTextListObserver = new Observer<ContentTextList>() {
            @Override
            public void onChanged(@Nullable final ContentTextList contentTextList) {
                mAdapter = new ContentAdapter(false, updateUI(contentTextList));

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                mAdapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                    @Override
                    public void onCopyClick(int position) {
                        String text = contentTextList.contentList.get(position).content;
                        myClip = ClipData.newPlainText("text", text);
                        myClipboard.setPrimaryClip(myClip);

                        Context context = getApplicationContext();
                        text = "Content Copied!";
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
                        Content content = contentTextList.contentList.get(position);
                        content.favorite = true;
                        contentTextList.contentList.set(position, content);
                        contentDao.insertAll(content);
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

        contentViewModel.getCurrentContent().observe(this, contentTextListObserver);

        lottieAnimationView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);

        if(Conditionals.isInternetWorking(getApplicationContext())) {
            ContentApi.getContent(getApplicationContext(), name, "content");
        }
        else{
            CommonDatabase.getContent(name, "content");
            Toast.makeText(ContentActivity.this, Constant.NO_INTERNET_MESSAGE, Toast.LENGTH_SHORT).show();
        }

        mAdapter = new ContentAdapter(false, mContentCardList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    private ArrayList<ContentCard> updateUI(ContentTextList contentTextList){
        ArrayList<ContentCard> mContentCardList = new ArrayList<>();
        for(Content content : contentTextList.contentList){
            mContentCardList.add(
                new ContentCard(
                    imageResource, content.content, content.favorite
                )
            );
        }
        return mContentCardList;
    }
}