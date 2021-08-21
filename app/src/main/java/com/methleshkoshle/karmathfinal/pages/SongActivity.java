package com.methleshkoshle.karmathfinal.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;


import com.methleshkoshle.karmathfinal.constant.Conditionals;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.adapter.SongAdapter;
import com.methleshkoshle.karmathfinal.model.ContentCard;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SongAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView mContent;

    private ClipboardManager myClipboard;
    private ClipData myClip;

    public static String text = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayList<Integer> oldSongID = new ArrayList<>();
        final ArrayList<String> loadedFromStorage = new ArrayList<>();
        final ArrayList<String> categoryLocal = new ArrayList<>();
        final ArrayList<Boolean> switchStates = new ArrayList<>();

        FileInputStream fis = null;
        String text;
        try {
            fis = openFileInput(Constant.songFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            text=sb.toString();
            StringBuilder line;
            int n= text.length(), i = 0;
            while(i<n){
                char c=text.charAt(i);
                if(Conditionals.isDigit(c)){
                    line = new StringBuilder();
                    int j=i;
                    while (Conditionals.isDigit(text.charAt(j))) {
                        line.append(text.charAt(j));
                        j++;
                    }
                    while (!Conditionals.isDigit(text.charAt(j))){
                        line.append(text.charAt(j));
                        if(Conditionals.lineBreak(text.charAt(j)))
                            line.append("\n");
                        j++;
                        if(j==n)break;
                    }
                    String tmp=line.toString();
                    String [] arr = tmp.split("_", 4);
                    oldSongID.add(Integer.parseInt(arr[0]));
                    loadedFromStorage.add(arr[3]);
                    categoryLocal.add(arr[2]);

                    if(arr[1].equals("false")){
                        switchStates.add(false);
                    }
                    else{
                        switchStates.add(true);
                    }
                    i=j;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Fetch New File Here
        fis = null;
        try {
            fis = openFileInput(Constant.tempSongFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }
            text=sb.toString();
            StringBuilder line;
            int n= text.length(), i=0;
            while(i<n){
                char c=text.charAt(i);
                if(Conditionals.isDigit(c)){
                    line = new StringBuilder();
                    int j=i;
                    while (Conditionals.isDigit(text.charAt(j))) {
                        line.append(text.charAt(j));
                        j++;
                    }
                    while (!Conditionals.isDigit(text.charAt(j))){
                        line.append(text.charAt(j));
                        if(Conditionals.lineBreak(text.charAt(j)))
                            line.append("\n");
                        j++;
                        if(j==n)break;
                    }
                    String tmp=line.toString();
                    String [] arr = tmp.split("_", 4);
                    int num=Integer.parseInt(arr[0]);
                    if(!oldSongID.contains(num)) {
                        oldSongID.add(num);
                        loadedFromStorage.add(arr[3]);
                        categoryLocal.add(arr[2]);
                        switchStates.add(false);
                    }
                    i=j;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        final ArrayList<ContentCard> mSongList = new ArrayList<>();
        int n=oldSongID.size();
        for(int i=0; i<n; i++) {
            int index = Constant.contentIndex.get(categoryLocal.get(i));
            mSongList.add(new ContentCard(Constant.imageResource[index], loadedFromStorage.get(i), switchStates.get(i)));
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mAdapter = new SongAdapter(mSongList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mContent = findViewById(R.id.SongView);

        mAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onCopyClick(int position) {
                String text = mSongList.get(position).getContent();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Context context = getApplicationContext();
                CharSequence msgText = "Content Copied!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, msgText, duration);
                toast.show();
            }

            @Override
            public void onShareClick(int position) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Karmath");
                    String shareMessage;
                    shareMessage = mSongList.get(position).getContent() +"\n\n";
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
                if (!switchStates.get(position)){
                    Context context = getApplicationContext();
                    CharSequence textEnabled = "Added to Favorites!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, textEnabled, duration);
                    toast.show();
                    switchStates.set(position, true);
                }
                load();
            }

            @Override
            public void onRemoveFavoriteClick(int position) {
                Context context = getApplicationContext();
                CharSequence textDisabled = "Removed from Favorites!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, textDisabled, duration);
                toast.show();
                switchStates.set(position, false);
                load();
            }
            // Write the current state in the Boolean Text File
            void load() {
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(Constant.songFileName, MODE_PRIVATE);
                    String text;

                    int n = loadedFromStorage.size();
                    for (int i = 0; i < n; i++) {
                        if (switchStates.get(i))
                            text = oldSongID.get(i) + "_true_" + categoryLocal.get(i) + "_" + loadedFromStorage.get(i);
                        else
                            text = oldSongID.get(i) + "_false_" + categoryLocal.get(i) + "_" + loadedFromStorage.get(i);
                        fos.write(text.getBytes());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}