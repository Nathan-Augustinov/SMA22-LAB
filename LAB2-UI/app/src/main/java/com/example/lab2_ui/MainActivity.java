package com.example.lab2_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
        colours.put("Yellow", "#00ffff");
    }

    String[] coloursArray = {"Red", "Green", "Blue", "Black", "White", "Yellow"};
    String[] hexCodeColoursArray = {"#ff0000", "#00ff00", "#0000ff", "#000000", "#ffffff", "#00ffff"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, coloursArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

//        spinner.setAdapter(arrayAdapter);
    }

        public void clicked(View view){
            EditText eName = (EditText) findViewById(R.id.eName);
            TextView tName = (TextView) findViewById(R.id.tName);

            switch (view.getId()){
                case R.id.bClick:
                    tName.setText("Hello, " + eName.getText());
//                    Intent intent = new Intent(this, PopUpWindow.class);
//                    intent.putExtra("popUpMessage", tName.getText());
//                    startActivity(intent);
                    break;
                case R.id.positiveButton:
                    Toast.makeText(MainActivity.this, "Operation done successfully!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.negativeButton:
                    Toast.makeText(MainActivity.this, "Operation unsuccessful!",Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Button bClick = (Button) findViewById(R.id.bClick);
        bClick.setTextColor(Integer.parseInt(hexCodeColoursArray[i]));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}