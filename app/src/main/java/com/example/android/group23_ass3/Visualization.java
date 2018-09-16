package com.example.android.group23_ass3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebSettings;


/**
 * Created by yashbelorkar on 19/04/18.
 */

public class Visualization extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visual);
        initVisualization();
    }

    private void initVisualization() {
        WebView viewWeb = findViewById(R.id.webview);
        viewWeb.addJavascriptInterface(new ApplicationInterface(this), "Android");
        WebSettings setting = viewWeb.getSettings();
        setting.setJavaScriptEnabled(true);
        viewWeb.loadUrl("file:///android_asset/index.html");
    }
}
