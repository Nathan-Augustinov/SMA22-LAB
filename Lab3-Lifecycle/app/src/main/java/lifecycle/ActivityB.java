package lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lab3_lifecycle.R;

public class ActivityB extends Activity {

    private static final String TAG = "Activity B";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        setTitle("B");
        Log.d(TAG, "On create B");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "On start B");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "On resume B");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "On pause B");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "On restart B");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "On stop B");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On destroy B");
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
