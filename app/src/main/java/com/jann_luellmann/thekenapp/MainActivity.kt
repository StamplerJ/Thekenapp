package com.jann_luellmann.thekenapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jann_luellmann.thekenapp.adapter.EventSpinnerAdapter
import com.jann_luellmann.thekenapp.data.db.Database
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel
import com.jann_luellmann.thekenapp.util.Prefs

class MainActivity : AppCompatActivity() {
    private var isSpinnerInitialized = false
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Database.initialize(this.getApplicationContext())
        setupViewPager()
    }

    private fun setupViewPager() {
        val viewPager: ViewPager = findViewById<ViewPager>(R.id.viewPager)
        val adapter = ThekenappFragmentPagerAdapter(this, getSupportFragmentManager())
        val tabLayout: TabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = adapter
        val eventViewModel: EventViewModel =
            ViewModelProviders.of(this).get(EventViewModel::class.java)
        eventViewModel.findAll()?.observe(this) { events ->
            val spinner: Spinner = findViewById<Spinner>(R.id.eventSpinner)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>,
                    view: View,
                    i: Int,
                    l: Long
                ) {
                    if (!isSpinnerInitialized) {
                        isSpinnerInitialized = true
                        return
                    }
                    val selectedItem: Any = adapterView.getItemAtPosition(i)
                    if (selectedItem is Event) {
                        Prefs.putLong(
                            applicationContext,
                            Prefs.CURRENT_EVENT,
                            selectedItem.eventId
                        )
                        adapter.listFragment?.onEventUpdated(selectedItem.eventId)
                        adapter.settingsFragment?.onEventUpdated(selectedItem.eventId)
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

            events?.let {
                spinner.adapter = EventSpinnerAdapter(applicationContext, events)
                val currentEventId: Long =
                    Prefs.getLong(applicationContext, Prefs.CURRENT_EVENT, 1L)
                for (i in 0 until spinner.count) {
                    val event = spinner.getItemAtPosition(i) as Event
                    if (event.eventId == currentEventId) {
                        spinner.setSelection(i)
                        break
                    }
                }
            }
        }
    }
}