package com.jann_luellmann.thekenapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;
import com.jann_luellmann.thekenapp.data.db.Database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database.initialize(this.getApplicationContext());

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);

        ThekenappFragmentPagerAdapter adapter = new ThekenappFragmentPagerAdapter(this, getSupportFragmentManager());


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tab = tabLayout.newTab();
        Spinner spinner = new Spinner(MainActivity.this);
//        spinner.setAdapter(new SimpleAdapter());
        tab.setCustomView(spinner);
        tabLayout.addTab(tab);

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListInteraction() {
        Log.i("Fragment Interaction", "List");
    }

    @Override
    public void onSettingsInteraction() {
        Log.i("Fragment Interaction", "Settings");
    }
}
