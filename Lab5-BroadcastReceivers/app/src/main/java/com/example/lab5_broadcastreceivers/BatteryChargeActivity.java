package com.example.lab5_broadcastreceivers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;


public class BatteryChargeActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "CHANNEL_ID";

    private String chargingStatus = null;
    private int notificationId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingTheChargingStatus();
        settingTheTypeOfCharging();

        Intent newIntent = new Intent(this, BatteryChargeActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        newIntent.putExtra("chargingStatus", chargingStatus);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,newIntent,0);

        createNotificationChannel();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Charging status changed")
                .setContentText(chargingStatus)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notificationId, notificationBuilder.build());
        notificationId++;

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void settingTheChargingStatus(){

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, intentFilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

        TextView chargingStatusTextView = (TextView) findViewById(R.id.chargingStatusTextView);


        if(isCharging){
            this.chargingStatus = "Your phone is charging";
            chargingStatusTextView.setText(chargingStatus);
        }
        else{
            this.chargingStatus = "Your phone is not charging";
            chargingStatusTextView.setText(chargingStatus);
        }
    }

    public void settingTheTypeOfCharging(){

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, intentFilter);

        TextView typeOfChargingTextView = (TextView) findViewById(R.id.typeOfChargingTextView);

        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

        if(usbCharge){
            typeOfChargingTextView.setText("Your phone is charging using usb");
        }
        else{
            if(acCharge){
                typeOfChargingTextView.setText("Your phone is charged using ac");
            }
            else{
                typeOfChargingTextView.setText("No type of charging");
            }
        }
    }
}