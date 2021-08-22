package com.methleshkoshle.karmathfinal.api;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.methleshkoshle.karmathfinal.constant.Constant;
import com.methleshkoshle.karmathfinal.model.ContentText.ContentTextList;
import com.methleshkoshle.karmathfinal.pages.ContentActivity;
import com.methleshkoshle.karmathfinal.parser.ContentParser;
import com.methleshkoshle.karmathfinal.response.ContentResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContentApi{
    public static List<Map> contentTexts = new ArrayList<>();

    public static void getContent(final Activity activity, Context context, final String categoryName, final String type) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("Title", "Android Volley Demo");
            jsonBody.put("Author", "BNK");

            JsonObjectRequest jsonObjectRequest = new
                    JsonObjectRequest(Request.Method.GET, Constant.contentUrl, jsonBody, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(JSONObject response) {
                    String text = response.toString();
                    ContentResponse contentResponse = new Gson().fromJson(response.toString(), ContentResponse.class);
                    String categoryJson = contentResponse.categories.get(categoryName).toString();

                    Map category = new Gson().fromJson(categoryJson, Map.class);
                    if(type.equals("song"))
                        contentTexts = (List<Map>) category.get("song");
                    else
                        contentTexts =  (List<Map>) category.get("content");

                    ContentTextList contentTextList = ContentParser.getContentTextList(contentTexts);

                    Log.i("VOLLEY", text);
                    ContentActivity.contentViewModel.getCurrentContent().setValue(contentTextList);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
