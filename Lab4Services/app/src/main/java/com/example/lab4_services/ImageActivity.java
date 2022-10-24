package com.example.lab4_services;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ImageActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        MyApplication myApplication = (MyApplication) getApplicationContext();
        if(myApplication.getBitmap() == null){
            Toast.makeText(this, "Error transmitting URL.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            ImageView imageView = (ImageView) findViewById(R.id.imageview);
            imageView.setImageBitmap(myApplication.getBitmap());
        }
    }
}
