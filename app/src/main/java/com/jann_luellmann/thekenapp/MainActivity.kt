package com.jann_luellmann.thekenapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jann_luellmann.thekenapp.adapter.EventSpinnerAdapter
import com.jann_luellmann.thekenapp.data.db.Database
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel
import com.jann_luellmann.thekenapp.dialog.WelcomeDialog
import com.jann_luellmann.thekenapp.util.Prefs

class MainActivity : AppCompatActivity() {

    val adapter = ThekenappFragmentPagerAdapter(this, supportFragmentManager)
    private var isSpinnerInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Database.initialize(this.applicationContext)
        welcomeMessage()
        setupViewPager()
    }

    private fun welcomeMessage() {
        val showWelcomeMessage: Boolean = Prefs.getCurrentEvent(this) == -1L
        if (showWelcomeMessage) {
            val dialog = WelcomeDialog()
            dialog.show(supportFragmentManager, "WelcomeDialog")
        }
    }

    private fun setupViewPager() {
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = adapter
        val eventViewModel: EventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventViewModel.findAll().observe(this) { events ->
            val spinner: Spinner = findViewById(R.id.eventSpinner)
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

                    if (adapterView.size == 0) {
                        Prefs.putCurrentEvent(this@MainActivity, -1L)
                        return
                    }

                    val selectedItem: Any = adapterView.getItemAtPosition(i)
                    if (selectedItem is Event) {
                        updateCurrentEvent(selectedItem.eventId)
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

            events?.let {
                spinner.adapter = EventSpinnerAdapter(applicationContext, events)
                val currentEventId: Long = Prefs.getCurrentEvent(applicationContext)
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

    fun updateCurrentEvent(eventId: Long) {
        Prefs.putCurrentEvent(applicationContext, eventId)

        runOnUiThread {
            adapter.notifyDataSetChanged()
            adapter.listFragment?.onEventUpdated(eventId)
            adapter.settingsFragment?.onEventUpdated(eventId)
        }
    }
}