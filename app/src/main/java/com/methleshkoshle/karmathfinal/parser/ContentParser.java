package com.methleshkoshle.karmathfinal.parser;

import com.methleshkoshle.karmathfinal.model.Content;
import com.methleshkoshle.karmathfinal.model.Content.ContentTextList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContentParser {
    public static ContentTextList getContentTextList(List<Map> contentTextMapList){
        ContentTextList contentTextList = new ContentTextList();
        contentTextList.contentList = new ArrayList<>();
        for(Map contentTextMap : contentTextMapList){
            ArrayList<Object> sequence = (ArrayList<Object>) contentTextMap.get("content");
            String text = getContentFromIntegerList(sequence);
            Content content = new Content();
            double doubleId = (double) contentTextMap.get("id");
            content.id = (int) doubleId;
            content.favorite = (boolean) contentTextMap.get("favorite");
            content.content = text;
            contentTextList.contentList.add(content);
        }
        return contentTextList;
    }

    public static String getContentFromIntegerList(List<Object> content) {
        String text = "";
        StringBuilder stringBuilder = new StringBuilder();
        for(Object integer : content){
            double db = (double) integer;
            int integerValue = (int) db;
            stringBuilder.append(Character.toChars(integerValue));
            if(integerValue == 2404 || integerValue == 44){
                stringBuilder.append("\n");
            }
        }
        text = stringBuilder.toString();
        return text;
    }
}
