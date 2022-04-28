package com.jann_luellmann.thekenapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.jann_luellmann.thekenapp.adapter.EventSpinnerAdapter
import com.jann_luellmann.thekenapp.data.db.Database
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel
import com.jann_luellmann.thekenapp.data.view_model.ViewModelFactory
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel
import com.jann_luellmann.thekenapp.databinding.ActivityMainBinding
import com.jann_luellmann.thekenapp.dialog.WelcomeDialog
import com.jann_luellmann.thekenapp.util.Prefs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var eventWithDrinksAndCustomersViewModel: EventWithDrinksAndCustomersViewModel

    private lateinit var eventAndCustomerWithDrinksViewModel: EventAndCustomerWithDrinksViewModel

    private lateinit var adapter: DrinkingListFragmentPagerAdapter

    private val currentEventId: MutableLiveData<Long> = MutableLiveData(-1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Database.initialize(this.applicationContext)

        val prefEventId = Prefs.getCurrentEvent(applicationContext)
        if (prefEventId >= 0)
            currentEventId.value = prefEventId

        currentEventId.observe(this) {
            Prefs.putCurrentEvent(applicationContext, it)
        }

        setupViewModels()
        setupViewPager()
        welcomeMessage()
    }

    private fun welcomeMessage() {
        val showWelcomeMessage: Boolean = currentEventId.value == -1L
        if (showWelcomeMessage) {
            val dialog = WelcomeDialog()
            dialog.show(supportFragmentManager, "WelcomeDialog")
        }
    }

    private fun setupViewPager() {
        adapter = DrinkingListFragmentPagerAdapter(
            supportFragmentManager,
            lifecycle,
            currentEventId,
            eventWithDrinksAndCustomersViewModel,
            eventAndCustomerWithDrinksViewModel
        )

        binding.viewPager.adapter = adapter
        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text =
                if (position == 0) getString(R.string.list_heading) else getString(R.string.settings_heading)
        }.attach()

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
                    if (adapterView.size == 0) {
                        currentEventId.value = -1
                        return
                    }

                    val selectedItem: Any = adapterView.getItemAtPosition(i)
                    if (selectedItem is Event) {
                        currentEventId.value = selectedItem.eventId
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

            events?.let {
                spinner.adapter = EventSpinnerAdapter(applicationContext, events)
                for (i in 0 until spinner.count) {
                    val event = spinner.getItemAtPosition(i) as Event
                    if (event.eventId == currentEventId.value) {
                        spinner.setSelection(i)
                        break
                    }
                }
            }
        }
    }

    fun updateCurrentEvent(eventId: Long) {
        currentEventId.postValue(eventId)
    }

    private fun setupViewModels() {
        eventWithDrinksAndCustomersViewModel =
            ViewModelFactory().create(EventWithDrinksAndCustomersViewModel::class.java)

        eventAndCustomerWithDrinksViewModel =
            ViewModelFactory().create(EventAndCustomerWithDrinksViewModel::class.java)
    }
}