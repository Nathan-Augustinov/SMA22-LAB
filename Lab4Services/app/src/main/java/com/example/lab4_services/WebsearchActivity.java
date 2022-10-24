package com.example.lab4_services;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WebsearchActivity extends Activity {

    public static String EXTRA_URL = "Web search url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websearch);

        WebView webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new MyCustomWebViewClient());
        webview.loadUrl("https://www.google.com/search?q=cat&tbm=isch&source=lnms&sa=X");

    }

    public void loadImage(View view){

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        ClipData.Item item = clipData.getItemAt(0);
        String url = item.getText().toString();

        if(!url.contains("https://images.app.goo.gl/")){
            Toast.makeText(this, "URL not valid. Try another.", Toast.LENGTH_SHORT).show();
        }
        else{
            if(view.getId() == R.id.bLoadBackground){
                Intent intent = new Intent(this, ImageIntentService.class);
                intent.putExtra(EXTRA_URL, url);
                startService(intent);
            }
            else{
                if(view.getId() == R.id.bLoadForeground){
                    Intent startIntent = new Intent(this, ForegroundImageService.class);
                    startIntent.setAction(ForegroundImageService.STARTFOREGROUND_ACTION);
                    startIntent.putExtra(EXTRA_URL, url);
                    startService(startIntent);
                }
            }
        }
    }
}
