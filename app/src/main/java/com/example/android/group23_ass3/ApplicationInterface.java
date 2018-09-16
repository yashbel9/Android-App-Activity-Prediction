package com.example.android.group23_ass3;

import android.content.Context;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;

public class ApplicationInterface {
    Context mContext;
    ApplicationInterface(Context c) {
        mContext = c;
    }
    @JavascriptInterface
    public String getData() {
        ArrayList data = DatabaseHelper.getDataToVisualize(mContext);
        return data.toString();
    }
}
