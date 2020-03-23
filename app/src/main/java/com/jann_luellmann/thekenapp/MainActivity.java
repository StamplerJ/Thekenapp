package com.jann_luellmann.thekenapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);

        ThekenappFragmentPagerAdapter adapter = new ThekenappFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
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
