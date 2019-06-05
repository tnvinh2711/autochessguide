package com.zinzin.autochessguide.utils;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Utils {

    public static String parseFile(Activity activity,String filename){
        String text;
        try {
            InputStream is = activity.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            text = new String(buffer);

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        }
        return text;
    }

    public static String convertObjToJson(Object obj){
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return json;
    }

    public static String linkStringFromArray(List<String> array){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            stringBuilder.append(array.get(i)).append(" / ");
        }
        String s = stringBuilder.toString();
        return s.substring(0, s.length()-2);
    }
}
