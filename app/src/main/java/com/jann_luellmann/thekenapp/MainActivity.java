package com.jann_luellmann.thekenapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;
import com.jann_luellmann.thekenapp.adapter.EventSpinnerAdapter;
import com.jann_luellmann.thekenapp.data.db.Database;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel;
import com.jann_luellmann.thekenapp.util.Prefs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private boolean isSpinnerInitialized = false;

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
        viewPager.setAdapter(adapter);

        EventViewModel eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.findAll().observe(this, events -> {
            Spinner spinner = findViewById(R.id.eventSpinner);
            spinner.setAdapter(new EventSpinnerAdapter(getApplicationContext(), events));

            long currentEventId = Prefs.getLong(getApplicationContext(), Prefs.CURRENT_EVENT, 1L);
            for (int i = 0; i < spinner.getCount(); i++) {
                Event event = (Event) spinner.getItemAtPosition(i);
                if(event.getEventId() == currentEventId) {
                    spinner.setSelection(i);
                    break;
                }
            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!isSpinnerInitialized) {
                        isSpinnerInitialized = true;
                        return;
                    }

                    Object selectedItem = adapterView.getItemAtPosition(i);
                    if(selectedItem instanceof Event) {
                        Event selectedEvent = (Event) selectedItem;
                        Prefs.putLong(getApplicationContext(), Prefs.CURRENT_EVENT, selectedEvent.getEventId());

                        adapter.getListFragment().onEventUpdated(selectedEvent.getEventId());
                        adapter.getSettingsFragment().onEventUpdated(selectedEvent.getEventId());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        });
    }
}
