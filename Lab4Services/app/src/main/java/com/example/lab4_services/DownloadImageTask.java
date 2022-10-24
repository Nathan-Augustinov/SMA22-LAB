package com.example.lab4_services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private Context context;
    public DownloadImageTask(Context context){
        this.context = context;
        Toast.makeText(context, "Please wait, it may take a few seconds.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap bitmap = null;
        try{
            String longURL = URLTools.getLongUrl(strings[0]);
            try {
                InputStream in = new URL(longURL).openStream();
                bitmap = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            Thread.sleep(5000);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ((MyApplication) context.getApplicationContext()).setBitmap(bitmap);

        Intent stopIntent = new Intent(context, ForegroundImageService.class);
        stopIntent.setAction(ForegroundImageService.STOPFOREGROUND_ACTION);
        context.startService(stopIntent);
    }
}
