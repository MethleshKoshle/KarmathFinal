package com.methleshkoshle.karmathfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.methleshkoshle.karmathfinal.pages.ContentActivity;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static Context context;

    public static String TempFile=null;
    public static String nowUrl=null;
    public static String nowActivity=null;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item ) {
        switch (item.getItemId()){
            case R.id.nav_song:
                if(Conditionals.isInternetWorking(context)) {
                    startAsyncTask(Constant.sourceSongURL, Constant.tempSongFile, "गीत");
                }
                else{
                    Toast.makeText(MainActivity2.this, "Connect to Internet to load new content!", Toast.LENGTH_SHORT).show();
                }
                Intent intent1 = new Intent(MainActivity2.this, SongActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_favorites:
                Intent intent2 = new Intent(MainActivity2.this, FavoriteActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Karmath");
                    String shareMessage= "\nLet me recommend you this beautiful qoute App *Karmath*:\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.methleshkoshle.myapplication" + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
            case R.id.nav_abt_me:
                Intent intent5 = new Intent(MainActivity2.this, AboutmeActivity.class);
                startActivity(intent5);
                break;
            case R.id.nav_write_a_review:
                Intent intent6 = new Intent(MainActivity2.this, WriteActivity.class);
                startActivity(intent6);
                break;
            case R.id.nav_thought:
                Intent intent7 = new Intent(MainActivity2.this, ThoughtActivity.class);
                startActivity(intent7);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity2.context = getApplicationContext();
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        final ArrayList<ExampleItem> mExampleList = new ArrayList<>();
        for(int i=0; i<9; i++){
            mExampleList.add(
                new ExampleItem(
                    Constant.imageResource[i], Constant.hindiName[i], Constant.contentDescription[i]
                )
            );
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            Intent intent = new Intent(MainActivity2.this, ContentActivity.class);
            @Override
            public void onItemClick(int position) {
                ContentActivity.name = Constant.name[position];
                ContentActivity.fileName = Constant.fileName[position];
                ContentActivity.tempFileName = Constant.tempFileName[position];
                ContentActivity.imageResource = Constant.imageResource[position];
                ContentActivity.labelResID = Constant.labelResID[position];
                if(Conditionals.isInternetWorking(context)) {
                    startAsyncTask(
                            Constant.sourceURL[position], Constant.tempFileName[position], Constant.hindiName[position]);
                }
                else{
                    Toast.makeText(MainActivity2.this, "Connect to Internet to load new content!", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
    public void startAsyncTask(String urlToExecute, String currentTempFile, String currentActivity){
        TempFile=currentTempFile;
        nowActivity=currentActivity;
        nowUrl=urlToExecute;
        ExampleAsyncTask task = new ExampleAsyncTask(MainActivity2.this);
        task.execute(urlToExecute);
    }
    @SuppressLint("StaticFieldLeak")
    class ExampleAsyncTask extends AsyncTask<String, String, String> {
        private WeakReference<MainActivity2> activityWeakReference;
        ExampleAsyncTask(MainActivity2 activity) {
            activityWeakReference = new WeakReference<MainActivity2>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity2 activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
        }
        @Override
        /* access modifiers changed from: protected */
        public String doInBackground(String... Strings) {
            FileOutputStream fileOS = null;
            try {
                BufferedInputStream inputStream = new BufferedInputStream(new URL(Strings[0]).openStream());
                fileOS = MainActivity2.this.openFileOutput(TempFile, 0);
                byte[] data = new byte[1024];
                while (true) {
                    int read = inputStream.read(data, 0, 1024);
                    int byteContent = read;
                    if (read != -1) {
                        fileOS.write(data, 0, byteContent);
                    } else {
                        break;
                    }
                }
            } catch (IOException e2) {
            } finally {
                try {
                    fileOS.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return "Finished!";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            MainActivity2 activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity2 activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            Toast.makeText(MainActivity2.this, nowActivity, Toast.LENGTH_SHORT).show();
        }
    }
}