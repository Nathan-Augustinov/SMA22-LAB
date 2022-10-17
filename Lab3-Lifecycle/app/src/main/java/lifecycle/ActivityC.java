package lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lab3_lifecycle.R;

public class ActivityC extends Activity {

    private static final String TAG = "Activity C";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        setTitle("C");
        Log.d(TAG, "On create C");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "On start C");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "On resume C");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "On pause C");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "On restart C");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "On stop C");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On destroy C");
    }

    public void clicked(View view){
        switch (view.getId()){
            case R.id.buttonA:
                startActivity(new Intent(this, ActivityA.class));
                break;
            case R.id.buttonB:
                startActivity(new Intent(this, ActivityB.class));
                break;
            case R.id.buttonC:
                startActivity(new Intent(this, ActivityC.class));
                break;
        }
    }
}
