package com.example.shashikant.penorbit;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shashikant.penorbit.data.MedicineContract;
import com.example.shashikant.penorbit.data.MedicineContract.MedicineEntry;

import static android.R.attr.data;

/**
 * Created by Shashikant on 7/2/2017.
 */

public class FragmentMedicineToday extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    MedicineTodayCursorAdapter medicineTodayCursorAdapter;
    public static final String LOG_TAG = FragmentMedicineToday.class.getSimpleName();
    String []projection = {
            MedicineEntry._ID,
            MedicineEntry.MEDICINE_NAME,
            MedicineEntry.MEDICINE_FREQUENCY_TYPE,
            MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME,
            MedicineEntry.MEDICINE_DOSE_PER_DAY,
            MedicineEntry.MEDICINE_REMINDERS,
            MedicineEntry.MEDICINE_NO_OF_PURCHASED
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View v = Inflater.inflate(R.layout.medicine_today,container,false);
        getActivity().setTitle("Medicines For Today");
        ListView medicineTodayListView = (ListView)v.findViewById(R.id.listView_med_today);

        //set up empty view
        View emptyView = v.findViewById(R.id.empty_view);
        medicineTodayListView.setEmptyView(emptyView);

        //set adapter to the list view
        medicineTodayCursorAdapter = new MedicineTodayCursorAdapter(getActivity(),null);
        medicineTodayListView.setAdapter(medicineTodayCursorAdapter);
        // kick off loader manager
        getLoaderManager().initLoader(0,null,FragmentMedicineToday.this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = MedicineEntry.MEDICINE_TODAY+"=?";
        String[] selectionArgs = new String[]{"1"};
        return new CursorLoader(getActivity(),MedicineEntry.CONTENT_URI,projection,selection,selectionArgs,null);
        //return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null){
            Toast.makeText(getActivity(),"Set Medicine For The Day First",Toast.LENGTH_LONG).show();
        }
        medicineTodayCursorAdapter.swapCursor(data);
       // Log.v(LOG_TAG,data.getString(data.getColumnIndex(MedicineEntry.MEDICINE_NAME))+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        medicineTodayCursorAdapter.swapCursor(null);
    }

}
