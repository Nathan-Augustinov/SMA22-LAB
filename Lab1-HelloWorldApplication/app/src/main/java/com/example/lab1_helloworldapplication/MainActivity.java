package com.example.lab1_helloworldapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText eName = (EditText) findViewById(R.id.eName);
        Button bClick = (Button) findViewById(R.id.bClick);
        TextView tName = (TextView) findViewById(R.id.tName);

        bClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tName.setText("Hello, " + eName.getText());
                Intent intent = new Intent(MainActivity.this, PopUpWindow.class);
                intent.putExtra("popUpMessage", tName.getText());
                startActivity(intent);
            }
        });

        Button positiveButton = (Button) findViewById(R.id.positiveButton);
        Button negativeButton = (Button) findViewById(R.id.negativeButton);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Successful",Toast.LENGTH_SHORT).show();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Unsuccessful",Toast.LENGTH_SHORT).show();
            }
        });

    }

//    public void clicked(View view){
//        EditText eName = (EditText) findViewById(R.id.eName);
//        Button bClick = (Button) findViewById(R.id.bClick);
//        TextView tName = (TextView) findViewById(R.id.tName);
//
//        switch (view.getId()){
//            case R.id.bClick:
//                tName.setText("Hello, " + eName.getText());
//                break;
//        }
//    }
}