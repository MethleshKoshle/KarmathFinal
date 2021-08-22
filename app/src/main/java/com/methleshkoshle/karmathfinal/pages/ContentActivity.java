package com.methleshkoshle.karmathfinal.pages;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.methleshkoshle.karmathfinal.api.ContentApi;
import com.methleshkoshle.karmathfinal.model.ContentCard;
import com.methleshkoshle.karmathfinal.adapter.ContentAdapter;
import com.methleshkoshle.karmathfinal.R;
import com.methleshkoshle.karmathfinal.model.ContentText;
import com.methleshkoshle.karmathfinal.model.ContentText.ContentTextList;
import com.methleshkoshle.karmathfinal.model.ContentViewModel;

import java.util.ArrayList;

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

    public static ContentViewModel contentViewModel;


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


        // Get the ViewModel.
        contentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);

        // Create the observer which updates the UI.
        final Observer<ContentTextList> contentTextListObserver = new Observer<ContentTextList>() {
            @Override
            public void onChanged(@Nullable final ContentTextList contentTextList) {
                // Update the UI, in this case, a TextView.
//                nameTextView.setText(newName);

                mAdapter = new ContentAdapter(updateUI(contentTextList));

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
                lottieAnimationView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        contentViewModel.getCurrentContent().observe(this, contentTextListObserver);


        lottieAnimationView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        ContentApi.getContent(ContentActivity.this, getApplicationContext(), name, "content");

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

    private ArrayList<ContentCard> updateUI(ContentTextList contentTextList){
        ArrayList<ContentCard> mContentCardList = new ArrayList<>();
        for(ContentText contentText : contentTextList.contentTextList){
            mContentCardList.add(
                new ContentCard(
                    imageResource, contentText.content, contentText.favorite
                )
            );
        }
        return mContentCardList;
    }
}