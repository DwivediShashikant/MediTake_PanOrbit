package com.example.shashikant.penorbit;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnMenuTabClickListener;

import static android.R.attr.fragment;
import static android.os.Build.VERSION_CODES.M;
import static android.support.v7.widget.AppCompatDrawableManager.get;

public class MainActivity extends AppCompatActivity {

    BottomBar mbottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View rootView;
        mbottomBar = BottomBar.attach(this,savedInstanceState);
        mbottomBar.setItemsFromMenu(R.menu.menu_main, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if(menuItemId == R.id.my_medicine){
                    FragmentListOfMedicine f = new FragmentListOfMedicine();
                    View v = findViewById(R.id.search_bar);
                    v.setVisibility(View.VISIBLE);
                    getFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                }
                else if(menuItemId == R.id.medicine_today){
                    FragmentMedicineToday f = new FragmentMedicineToday();
                    View v = findViewById(R.id.search_bar);
                    v.setVisibility(View.INVISIBLE);
                    getFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                }
                else if(menuItemId == R.id.my_profile){
                    FragmentMyProfile f = new FragmentMyProfile();
                    View v = findViewById(R.id.search_bar);
                    v.setVisibility(View.INVISIBLE);
                    getFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
                }
            }
            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        mbottomBar.mapColorForTab(0,"#009688");
        mbottomBar.mapColorForTab(1,"#0097a7");
        mbottomBar.mapColorForTab(2,"#ffab00");

        BottomBarBadge unread;
        unread = mbottomBar.makeBadgeForTabAt(1,"#f50057",7);
        unread.show();
    }
}
