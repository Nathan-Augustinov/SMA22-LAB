package lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lab3_lifecycle.R;

public class ActivityA extends Activity {

    private static final String TAG = "Activity A";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        setTitle("A");
        Log.d(TAG, "On create A");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "On start A");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "On resume A");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "On pause A");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "On restart A");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "On stop A");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On destroy A");
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
