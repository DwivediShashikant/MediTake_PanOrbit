package com.example.shashikant.penorbit;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.shashikant.penorbit.data.MedicineContract;

/**
 * Created by Shashikant on 7/6/2017.
 */

public class MedicineCursorAdapter extends CursorAdapter{
    public static final String LOG_TAG = MedicineCursorAdapter.class.getSimpleName();

    MedicineCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView  = (TextView)view.findViewById(R.id.list_medicine_name);
        TextView doseTypeTextView = (TextView)view.findViewById(R.id.list_dose_type);
        TextView quantityPurhcasedTextView= (TextView)view.findViewById(R.id.list_medicine_purchased);
        if(quantityPurhcasedTextView!=null){
            Log.v(LOG_TAG,"quantity PurhcasedTextView is not emplty");
        }
        int nameColumeIndex = cursor.getColumnIndex(MedicineContract.MedicineEntry.MEDICINE_NAME);
        int doseTypeColumnIndex = cursor.getColumnIndex(MedicineContract.MedicineEntry.MEDICINE_FREQUENCY_TYPE);
        int quantityColumnIndex = cursor.getColumnIndex(MedicineContract.MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME);

        Log.v(LOG_TAG,"");
        String medName = cursor.getString(nameColumeIndex);
        int medDoseType = cursor.getInt(doseTypeColumnIndex);
        int mediQuantity = cursor.getInt(quantityColumnIndex);
        Log.v(LOG_TAG,"quantity at a time->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+mediQuantity);
        String frequnecyType;
        if(medDoseType == MedicineContract.MedicineEntry.FREQUENCY_DAILY){
            frequnecyType = "Daily";
        }
        else{
            frequnecyType = "Weekly";
        }
        nameTextView.setText(medName);
        doseTypeTextView.setText(frequnecyType);
        //quantityPurhcasedTextView.setText(mediQuantity);
    }
}
