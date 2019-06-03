package com.zinzin.autochessguide.utils;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;

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
}
