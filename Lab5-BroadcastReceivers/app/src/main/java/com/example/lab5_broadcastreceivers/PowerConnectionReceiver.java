package com.example.lab5_broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null){
            String state = extras.getString(BatteryManager.EXTRA_STATUS);
            Log.d("MY_DEBUG_TAG", state);
            if (state.equals(BatteryManager.EXTRA_PLUGGED)) {
                String statusBattery = extras.getString(BatteryManager.EXTRA_LEVEL);
                Log.d("MY_DEBUG_TAG", statusBattery);
            }
        }
    }
}