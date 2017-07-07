package com.example.shashikant.penorbit;

import android.app.Fragment;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.shashikant.penorbit.data.MedicineContract.MedicineEntry;

import java.util.zip.Inflater;

import static android.R.id.empty;

/**
 * Created by Shashikant on 7/2/2017.
 */

public class FragmentListOfMedicine extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG =  FragmentListOfMedicine.class.getSimpleName();
    MedicineCursorAdapter mCursorAdapter;
    String []projection = {
            MedicineEntry._ID,
            MedicineEntry.MEDICINE_NAME,
            MedicineEntry.MEDICINE_FREQUENCY_TYPE,
            MedicineEntry.MEDICINE_QUANTITY_AT_A_TIME,
            MedicineEntry.MEDICINE_DOSE_PER_DAY,
            MedicineEntry.MEDICINE_REMINDERS,
            MedicineEntry.MEDICINE_NO_OF_PURCHASED
    };
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater Inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
         v = Inflater.inflate(R.layout.list_of_medicine,container,false);

        //getting reference to the list
        ListView medicineListView = (ListView)v.findViewById(R.id.list);

        View emptyView = v.findViewById(R.id.empty_view);
        medicineListView.setEmptyView(emptyView);

        //set up an adapter to create a list item for each row of medicine data in Cursor
        mCursorAdapter = new MedicineCursorAdapter(getActivity(),null);
        medicineListView.setAdapter(mCursorAdapter);
        //kick off the loader
        getLoaderManager().initLoader(0,null,FragmentListOfMedicine.this);

        //open editor activity when user clicks on the item of listview
        medicineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),MedicineEditorActivity.class);

                Uri currentMedicineUri = ContentUris.withAppendedId(MedicineEntry.CONTENT_URI,id);
                intent.setData(currentMedicineUri);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public android.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri baseUri;
        baseUri = MedicineEntry.CONTENT_URI;
        return new CursorLoader(getActivity(),baseUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}
