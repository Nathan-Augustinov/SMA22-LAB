package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upt.cti.smartwallet.model.MonthlyExpenses;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String dbLink = "https://smart-wallet-1e99c-default-rtdb.europe-west1.firebasedatabase.app/" ;
    private TextView tStatus;
    private EditText eSearch, eIncome, eExpenses;
    private Spinner mSpinner;
    private ArrayAdapter<String> sAdapter;
    private final List<MonthlyExpenses> monthlyExpenses = new ArrayList<>();
    private final List<String> monthNames = new ArrayList<>();
    private String currentMonth;

    private DatabaseReference databaseReference;
    private ValueEventListener databaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tStatus = findViewById(R.id.tStatus);
//        eSearch = findViewById(R.id.eSearch);
        eIncome = findViewById(R.id.eIncome);
        eExpenses = findViewById(R.id.eExpenses);
        mSpinner = findViewById(R.id.mSpinner);

        mSpinner.setOnItemSelectedListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance(dbLink);
        databaseReference = database.getReference();

        populateSpinner();
        sAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthNames);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(sAdapter);
    }

    private void createNewDBListener() {
        // remove previous databaseListener
        if (databaseReference != null && currentMonth != null && databaseListener != null)
            databaseReference.child("calendar").child(currentMonth).removeEventListener(databaseListener);
        databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                MonthlyExpenses monthlyExpense = dataSnapshot.getValue(MonthlyExpenses.class);
                // explicit mapping of month name from entry key
                if(monthlyExpense != null) {
                    monthlyExpense.month = dataSnapshot.getKey();
                    eIncome.setText(String.valueOf(monthlyExpense.getIncome()));
                    eExpenses.setText(String.valueOf(monthlyExpense.getExpenses()));
                    tStatus.setText("Found entry for " + currentMonth);
                }
                else{
                    Toast.makeText(MainActivity.this, "Wrong month", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        };
        // set new databaseListener
        databaseReference.child("calendar").child(currentMonth).addValueEventListener(databaseListener);

    }

    public void clicked(View view) {
        switch(view.getId()) {
//            case R.id.bSearch:
//                if(!eSearch.getText().toString().isEmpty()) {
//                    currentMonth = eSearch.getText().toString().toLowerCase();
//                    tStatus.setText("Searching...");
//                    createNewDBListener();
//                } else {
//                    Toast.makeText(this, "Search field may not be empty", Toast.LENGTH_LONG).show();
//                }
//                break;
            case R.id.bUpdate:
                if(databaseReference != null && currentMonth != null) {
                    if(!eIncome.getText().toString().isEmpty()) {
                        Double income = Double.parseDouble(eIncome.getText().toString());
                        databaseReference.child("calendar").child(currentMonth).child("income").setValue(income);
                    }
                    if(!eExpenses.getText().toString().isEmpty()) {
                        Double expenses = Double.parseDouble(eExpenses.getText().toString());
                        databaseReference.child("calendar").child(currentMonth).child("expenses").setValue(expenses);
                    }
                } else {
                    Toast.makeText(this, "You need to select a month", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void populateSpinner() {
        if(databaseReference != null) {
            databaseReference.child("calendar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot monthSnapshot : snapshot.getChildren()) {
                        MonthlyExpenses month = monthSnapshot.getValue(MonthlyExpenses.class);
                        if(month != null) {
                            month.month = monthSnapshot.getKey();
                            if(!monthNames.contains(month.month)){
                                monthNames.add(month.month);
                            }
//                            System.out.println("Incersc sa afisez luna " + month.month + " " + month.getIncome());
                        }
                    }
                    sAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        currentMonth = adapterView.getItemAtPosition(i).toString();
//        System.out.println("POS i " + i);
//        System.out.println("SELECTEZ LUNA CURENTA PLS " + adapterView.getItemAtPosition(i).toString());
        tStatus.setText("Searching...");
        createNewDBListener();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}