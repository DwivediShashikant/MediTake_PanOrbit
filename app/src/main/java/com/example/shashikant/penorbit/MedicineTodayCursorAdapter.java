package com.example.shashikant.penorbit;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shashikant.penorbit.data.MedicineContract;
import com.example.shashikant.penorbit.data.MedicineContract.MedicineEntry;

/**
 * Created by Shashikant on 7/7/2017.
 */

public class MedicineTodayCursorAdapter extends CursorAdapter {

    MedicineTodayCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_medicine_today,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView medicineNameTextView  = (TextView)view.findViewById(R.id.today_medicine_name);
        TextView medicneDoseTypeTextView = (TextView)view.findViewById(R.id.today_medicine_dose_type);
        TextView medicineNoOfDoseTextView = (TextView)view.findViewById(R.id.today_no_of_doses);
        TextView medicineTimesTextView = (TextView)view.findViewById(R.id.today_times_per_dose);


        int nameColumnIndex = cursor.getColumnIndex(MedicineEntry.MEDICINE_NAME);
        int dosTypeIndex = cursor.getColumnIndex(MedicineEntry.MEDICINE_FREQUENCY_TYPE);
        int noOfDosesIndex = cursor.getColumnIndex(MedicineEntry.MEDICINE_DOSE_PER_DAY);
        int timesPerDoseIndex = cursor.getColumnIndex(MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME);

        String medName = cursor.getString(nameColumnIndex);
        String dosesPerDay =  cursor.getString(noOfDosesIndex);
        String quantity = cursor.getString(timesPerDoseIndex);

        String frequencyType;
        if(cursor.getInt(dosTypeIndex)==MedicineEntry.FREQUENCY_DAILY){
            frequencyType = "Daily";
        }
        else{
            frequencyType = "Weekly";
        }
        medicineNameTextView.setText(medName);
        medicneDoseTypeTextView.setText(frequencyType);
        medicineNoOfDoseTextView.setText(dosesPerDay+" doses each time");
        medicineTimesTextView.setText(quantity+" Times per Day");
    }
}
