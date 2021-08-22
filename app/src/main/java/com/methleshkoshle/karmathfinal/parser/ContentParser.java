package com.methleshkoshle.karmathfinal.parser;

import com.methleshkoshle.karmathfinal.model.ContentText;
import com.methleshkoshle.karmathfinal.model.ContentText.ContentTextList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContentParser {
    public static ContentTextList getContentTextList(List<Map> contentTextMapList){
        ContentTextList contentTextList = new ContentTextList();
        contentTextList.contentTextList = new ArrayList<>();
        for(Map contentTextMap : contentTextMapList){
            String text = "";
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<Integer> sequence = (ArrayList<Integer>) contentTextMap.get("content");
            for(Object integer : sequence){
                double db = (double) integer;
                stringBuilder.append(Character.toChars((int)db));
            }
            text = stringBuilder.toString();
            ContentText contentText = new ContentText();
            double doubleId = (double) contentTextMap.get("id");
            contentText.id = (int) doubleId;
            contentText.favorite = (boolean) contentTextMap.get("favorite");
            contentText.content = text;
            contentTextList.contentTextList.add(contentText);
        }
        return contentTextList;
    }
}
