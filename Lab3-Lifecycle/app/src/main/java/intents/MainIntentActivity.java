package intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lab3_lifecycle.R;

public class MainIntentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent);
    }

    public void clicked(View view){
        switch (view.getId()){
            case R.id.firstButton:
                Intent firstIntent = new Intent();
                firstIntent.setAction("android.intent.action.VIEW");
                firstIntent.setData(Uri.parse("http://www.google.com"));
                startActivity(firstIntent);
                break;
            case R.id.secondButton:
                Intent secondIntent = new Intent();
                secondIntent.setAction("android.intent.action.VIEW");
                secondIntent.setData(Uri.parse("tel:00401213456"));
                startActivity(secondIntent);
                break;
            case R.id.thirdButton:
                Intent thirdIntent = new Intent();
                thirdIntent.setAction("MSA.LAUNCH");
                thirdIntent.setData(Uri.parse("http://www.google.com"));
                startActivity(thirdIntent);
                break;
            case R.id.fourthButton:
                Intent fourthIntent = new Intent();
                fourthIntent.setAction("MSA.LAUNCH");
                fourthIntent.setData(Uri.parse("tel:00401213456"));
                startActivity(fourthIntent);
                break;
        }
    }
}
