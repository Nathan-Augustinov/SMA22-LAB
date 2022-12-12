package com.upt.cti.smartwallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPaymentActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String dbLink = "https://smart-wallet-1e99c-default-rtdb.europe-west1.firebasedatabase.app/" ;
    private DatabaseReference databaseReference;
    private String paymentType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        EditText name = (EditText) findViewById(R.id.name);
        EditText cost = (EditText) findViewById(R.id.cost);
        Button save = (Button) findViewById(R.id.buttonSave);

        Spinner spinnerpayments = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpayments.setAdapter(adapter);

        spinnerpayments.setOnItemSelectedListener(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance(dbLink).getReference().child("wallet");

                Payment payment = new Payment(Double.parseDouble(cost.getText().toString()), name.getText().toString(), paymentType);

                databaseReference.child(getCurrentTime()).setValue(payment);
            }
        });


    }



    public static String getCurrentTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd:HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        paymentType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
