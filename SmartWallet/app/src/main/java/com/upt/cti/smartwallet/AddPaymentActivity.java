package com.upt.cti.smartwallet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPaymentActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String dbLink = "https://smart-wallet-1e99c-default-rtdb.europe-west1.firebasedatabase.app/" ;
    private DatabaseReference databaseReference;
    private String paymentType, paymentDatabaseKey;
    private boolean paymentFound = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        EditText name = (EditText) findViewById(R.id.name);
        EditText cost = (EditText) findViewById(R.id.cost);
        Button save = (Button) findViewById(R.id.buttonSave);
        Button delete = (Button) findViewById(R.id.buttonDelete);

        Spinner spinnerpayments = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerpayments.setAdapter(adapter);

        spinnerpayments.setOnItemSelectedListener(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || cost.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"All fields must be filled in!", Toast.LENGTH_SHORT).show();
                }

                databaseReference = FirebaseDatabase.getInstance(dbLink).getReference().child("wallet");

                Payment payment = new Payment(Double.parseDouble(cost.getText().toString()), name.getText().toString(), paymentType);

                databaseReference.child(getCurrentTime()).setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Payment successfully added!", Toast.LENGTH_SHORT);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || cost.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"All fields must be filled in!", Toast.LENGTH_SHORT).show();
                }

                databaseReference = FirebaseDatabase.getInstance(dbLink).getReference().child("wallet");

                Payment payment = new Payment(Double.parseDouble(cost.getText().toString()), name.getText().toString(), paymentType);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            Payment databasePayment = dataSnapshot.getValue(Payment.class);
                            if(payment.getType().equals(databasePayment.getType()) &&
                                    payment.getName().equals(databasePayment.getName()) &&
                                    payment.getCost() == databasePayment.getCost()){
                                paymentFound=true;
                                paymentDatabaseKey = dataSnapshot.getKey();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Payment not found!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        if(paymentFound){
                            databaseReference.child(paymentDatabaseKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Payment deleted successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        paymentType = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static String getCurrentTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd:HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
