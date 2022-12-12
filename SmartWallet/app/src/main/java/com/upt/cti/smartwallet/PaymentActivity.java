package com.upt.cti.smartwallet;

import static com.upt.cti.smartwallet.AddPaymentActivity.getCurrentTime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upt.cti.smartwallet.model.Month;
import com.upt.cti.smartwallet.ui.PaymentAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private static final String dbLink = "https://smart-wallet-1e99c-default-rtdb.europe-west1.firebasedatabase.app/" ;
    private int currentMonth, timestampMonth;
    private List<Payment> payments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_payments);

        TextView tStatus = (TextView) findViewById(R.id.tStatus);
        Button bPrevious = (Button) findViewById(R.id.bPrevious);
        Button bNext = (Button) findViewById(R.id.bNext);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        ListView listPayments = (ListView) findViewById(R.id.listPayments);
        final PaymentAdapter adapter = new PaymentAdapter(this, payments, R.layout.item_payment);
        listPayments.setAdapter(adapter);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        timestampMonth = Month.monthFromTimestamp(timestamp.toString());

        tStatus.setText("Loading payments for " + Month.intToMonthName(timestampMonth) + " ...");

        final FirebaseDatabase database = FirebaseDatabase.getInstance(dbLink);
        databaseReference = database.getReference();

        SharedPreferences pref = getSharedPreferences("MyPref", 0); // 0 - for private mode
        currentMonth = pref.getInt("month", -1);

        if(currentMonth == -1)
        {
            currentMonth = Month.monthFromTimestamp(getCurrentTime());
        }



        bPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMonth == 0){
                    currentMonth = 12;
                }
                currentMonth = currentMonth - 1;
                tStatus.setText("Found " + payments.size() + " payments for " + Month.intToMonthName(currentMonth) + ".");
                pref.edit().putInt("month", currentMonth).apply();
                recreate();
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMonth == 11){
                    currentMonth = -1;
                }
                currentMonth = currentMonth + 1;
                tStatus.setText("Found " + payments.size() + " payments for " + Month.intToMonthName(currentMonth) + ".");
                pref.edit().putInt("month", currentMonth).apply();
                recreate();
            }
        });


        databaseReference.child("wallet").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    Payment payment = snapshot.getValue(Payment.class);
                    payment.timestamp = snapshot.getKey();
                    timestampMonth = Month.monthFromTimestamp(payment.timestamp);

                    if (payment.timestamp != null && currentMonth == timestampMonth) {
                        payments.add(payment);
                        adapter.notifyDataSetChanged();
                    }

                    tStatus.setText("Found " + payments.size() + " payments for " + Month.intToMonthName(currentMonth) + ".");
                } catch (Exception e) {
                    System.out.println("Exception in child added method");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                try {
                    Payment payment = snapshot.getValue(Payment.class);
                    payment.timestamp = snapshot.getKey();
                    timestampMonth = Month.monthFromTimestamp(payment.timestamp);
                    if (payments.contains(payment))
                        payments.set(payments.indexOf(payment), payment);
                    else
                        payments.add(payment);
                    adapter.notifyDataSetChanged();

                    tStatus.setText("Found " + payments.size() + " payments for " + Month.intToMonthName(currentMonth) + ".");
                } catch (Exception e) {
                    System.out.println("Exception in child changed method");
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                try {
                    Payment payment = snapshot.getValue(Payment.class);
                    payment.timestamp = snapshot.getKey();
                    timestampMonth = Month.monthFromTimestamp(payment.timestamp);
                    payments.remove(payment);
                    adapter.notifyDataSetChanged();

                    tStatus.setText("Found " + payments.size() + " payments for " + Month.intToMonthName(currentMonth) + ".");
                } catch (Exception e) {
                    System.out.println("Exception in child removed method");
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void clicked(View view) {
        switch (view.getId()) {
            case R.id.bPrevious:
                break;
            case R.id.bNext:
                break;
            case R.id.fabAdd:
                startActivity(new Intent(PaymentActivity.this, AddPaymentActivity.class));
                break;
        }
    }
}
