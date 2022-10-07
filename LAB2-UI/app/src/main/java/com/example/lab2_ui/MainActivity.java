package com.example.lab2_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Map<String, String> colours = new HashMap<>();

    public void fillMapColours(){
        colours.put("Red", "#ff0000");
        colours.put("Green", "#00ff00");
        colours.put("Blue","#0000ff");
        colours.put("Black", "#000000");
        colours.put("White", "#ffffff");
        colours.put("Yellow", "#ffff00");
    }

//    String[] coloursArray = {"Red", "Green", "Blue", "Black", "White", "Yellow"};
//    String[] hexCodeColoursArray = {"#ff0000", "#00ff00", "#0000ff", "#000000", "#ffffff", "#00ffff"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillMapColours();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coloursArray);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, colours.keySet().toArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);
    }

        public void clicked(View view){
            EditText eName = (EditText) findViewById(R.id.eName);
            TextView tName = (TextView) findViewById(R.id.tName);

            switch (view.getId()){
                case R.id.bClick:
                    tName.setText("Hello, " + eName.getText());
                    Intent intent = new Intent(this, PopUpWindow.class);
                    intent.putExtra("popUpMessage", tName.getText());
                    startActivity(intent);
                    break;
                case R.id.positiveButton:
                    Toast.makeText(MainActivity.this, "Operation done successfully!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.negativeButton:
                    Toast.makeText(MainActivity.this, "Operation unsuccessful!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bShare:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, eName.getText().toString());
                    sendIntent.setType("text/plain");
                    Intent chooserIntent = Intent.createChooser(sendIntent, "Share this text with:");
                    if(sendIntent.resolveActivity(getPackageManager())!=null){
                        startActivity(chooserIntent);
                    }
                    break;
                case R.id.bSearch:
                    Intent searchIntent = new Intent();
                    searchIntent.setAction(Intent.ACTION_VIEW);
                    String url = "http://google.com/search?q="+eName.getText().toString();
                    searchIntent.setData(Uri.parse(url));
                    startActivity(searchIntent);
                    break;
            }
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Button bClick = (Button) findViewById(R.id.bClick);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        bClick.setTextColor(Integer.parseInt(hexCodeColoursArray[i]));
        bClick.setTextColor(Color.parseColor(colours.get(spinner.getSelectedItem())));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}