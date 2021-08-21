package com.methleshkoshle.karmathfinal.pages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.methleshkoshle.karmathfinal.HomeActivity;
import com.methleshkoshle.karmathfinal.api.ContentApi;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.model.ContentCard;
import com.methleshkoshle.karmathfinal.adapter.ContentAdapter;
import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.helper.FileIoHelper;
import com.methleshkoshle.karmathfinal.model.ContentText;
import com.methleshkoshle.karmathfinal.response.ContentResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContentActivity extends AppCompatActivity {

    public static String name;
    public static String fileName;
    public static String tempFileName;
    public static int imageResource;
    @StringRes
    public static int labelResID;

    public static LottieAnimationView lottieAnimationView;

    public static RecyclerView mRecyclerView;
    public static ContentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mContent;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onStart() {
        ContentApi.contentTexts.clear();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(labelResID);
        setContentView(R.layout.activity_content);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();

        final FileIoHelper currentFileIoHelper = new FileIoHelper();

        currentFileIoHelper.init(context, name, fileName, tempFileName);

        currentFileIoHelper.loadLocalContent();
        currentFileIoHelper.fetchNewFile();

        ArrayList<String> loadedFromStorage = currentFileIoHelper.getLoadedFromStorage();

        ArrayList<Boolean> switchStates = currentFileIoHelper.getSwitchStates();

        final ArrayList<ContentCard> mContentCardList = new ArrayList<>();

        // do not remove
//        int n=loadedFromStorage.size();
//
//        for(int i=0; i<n; i++){
//            mContentCardList.add(new ContentCard(imageResource, loadedFromStorage.get(i), switchStates.get(i)));
//        }

        lottieAnimationView = findViewById(R.id.animation);
        lottieAnimationView.playAnimation();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        if(!ContentApi.contentFetched) {
            lottieAnimationView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
            ContentApi.getContent(ContentActivity.this, getApplicationContext(), name, "content");
        }
        else{
            lottieAnimationView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
//            mAdapter.notifyDataSetChanged();
        }

        for(Map contentText : ContentApi.contentTexts){
            String text = "";
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Integer> sequence = (ArrayList<Integer>) contentText.get("content");
            for(Object integer : sequence){//.content){
                double db = (double) integer;
                stringBuilder.append(Character.toChars((int)db));
            }
            text = stringBuilder.toString();
            mContentCardList.add(new ContentCard(imageResource, text, (Boolean) contentText.get("favorite")));//.favorite));

            lottieAnimationView.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }

        mAdapter = new ContentAdapter(mContentCardList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mContent = findViewById(R.id.textView11);

        mAdapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onCopyClick(int position) {
                String text = mContentCardList.get(position).getContent();
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
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Karmath");
                    String shareMessage;
                    shareMessage = mContentCardList.get(position).getContent() +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                Context context = getApplicationContext();
                CharSequence text = "Share Content!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onAddFavoriteClick(int position) {
                currentFileIoHelper.handleAddClickEvents(position);
            }

            @Override
            public void onRemoveFavoriteClick(int position) {
                currentFileIoHelper.handleRemoveClickEvents(position);
            }
        });
    }
}