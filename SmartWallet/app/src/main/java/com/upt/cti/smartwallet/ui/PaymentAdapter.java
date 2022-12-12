package com.upt.cti.smartwallet.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.upt.cti.smartwallet.Payment;
import com.upt.cti.smartwallet.R;
import com.upt.cti.smartwallet.model.PaymentType;

import java.util.List;

public class PaymentAdapter extends ArrayAdapter<Payment> {

    private Context context;
    private List<Payment> payments;
    private int layoutResID;

    public PaymentAdapter(Context context, List<Payment> payments, int layoutResID) {
        super(context, layoutResID, payments);
        this.context = context;
        this.payments = payments;
        this.layoutResID = layoutResID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemHolder itemHolder;
        View view = convertView;

        if(view == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            itemHolder = new ItemHolder();

            view = layoutInflater.inflate(layoutResID, parent, false);
            itemHolder.tIndex = (TextView) view.findViewById(R.id.tIndex);
            itemHolder.tName = (TextView) view.findViewById(R.id.tName);
            itemHolder.lHeader = (RelativeLayout) view.findViewById(R.id.lHeader);
            itemHolder.tDate = (TextView) view.findViewById(R.id.tDate);
            itemHolder.tTime = (TextView) view.findViewById(R.id.tTime);
            itemHolder.tCost = (TextView) view.findViewById(R.id.tCost);
            itemHolder.tType = (TextView) view.findViewById(R.id.tType);

            view.setTag(itemHolder);
        }
        else{
            itemHolder = (ItemHolder) view.getTag();
        }

        final Payment pItem = payments.get(position);
        itemHolder.tIndex.setText(String.valueOf(position + 1));
        itemHolder.tName.setText(pItem.getName());
        itemHolder.lHeader.setBackgroundColor(PaymentType.getColorFromPaymentType(pItem.getType()));
        itemHolder.tCost.setText(pItem.getCost() + " LEI");
        itemHolder.tType.setText(pItem.getType());
        itemHolder.tDate.setText("Date: " + pItem.timestamp.substring(0, 10));
        itemHolder.tTime.setText("Time: " + pItem.timestamp.substring(11));

        return view;


    }

    private static class ItemHolder{
        TextView tIndex;
        TextView tName;
        RelativeLayout lHeader;
        TextView tDate, tTime;
        TextView tCost, tType;
    }
}


