package com.appproj.vd.shyambaba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebsiteActivity extends AppCompatActivity {

    WebView webViewWebsite;
    String url = "https://www.google.co.in/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(url);
        webViewWebsite = (WebView)findViewById(R.id.webViewWebsite);
        webViewWebsite.loadUrl(url);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
