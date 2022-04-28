package com.jann_luellmann.thekenapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jann_luellmann.thekenapp.adapter.TextAdapter
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel
import com.jann_luellmann.thekenapp.databinding.FragmentSettingsBinding
import com.jann_luellmann.thekenapp.dialog.CreateEntryDialogFragment

class SettingsFragment(
    private val eventId: LiveData<Long>,
    private val eventWithDrinksAndCustomersViewModel: EventWithDrinksAndCustomersViewModel
) : Fragment(), EventChangedListener {

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var drinksAdapter: TextAdapter<Drink>
    private lateinit var customersAdapter: TextAdapter<Customer>
    private lateinit var eventsAdapter: TextAdapter<Event>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drinksAdapter = TextAdapter(parentFragmentManager)
        customersAdapter = TextAdapter(parentFragmentManager)
        eventsAdapter = TextAdapter(parentFragmentManager)

        setupRecyclerView(binding.drinksList, drinksAdapter, true)
        setupRecyclerView(binding.customerList, customersAdapter, true)

        binding.addEvent.setOnClickListener {
            CreateEntryDialogFragment(Event()).show(
                parentFragmentManager,
                getString(R.string.event_tag)
            )
        }

        eventId.observe(viewLifecycleOwner) {
            binding.addDrink.setOnClickListener { _ ->
                CreateEntryDialogFragment(Drink(), it).show(
                    parentFragmentManager,
                    getString(R.string.drink_tag)
                )
            }
            binding.addCustomer.setOnClickListener { _ ->
                CreateEntryDialogFragment(Customer(), it).show(
                    parentFragmentManager,
                    getString(R.string.customer_tag)
                )
            }

            onEventUpdated(eventId)
        }

        // Event setup
        setupRecyclerView(binding.eventList, eventsAdapter, true)
        val eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventViewModel.findAll().observe(viewLifecycleOwner) { events ->
            eventsAdapter.setData(events.sortedBy { it.name })
        }
    }

    private fun <type> setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: TextAdapter<type>,
        editable: Boolean
    ) {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter.showEditButton = editable
        recyclerView.adapter = adapter
    }

    override fun onEventUpdated(updatedId: LiveData<Long>) {
        val eventWithDrinksAndCustomers = Transformations.switchMap(updatedId) {
            eventWithDrinksAndCustomersViewModel.findById(it)
        }

        eventWithDrinksAndCustomers.observe(viewLifecycleOwner) { event: EventWithDrinksAndCustomers? ->

            binding.addDrink.isEnabled = event != null
            binding.addCustomer.isEnabled = event != null

            if (event == null)
                return@observe

            event.customers.sort()
            drinksAdapter.setData(event.drinks.sortedBy { it.name })
            customersAdapter.setData(event.customers.sortedBy { it.name })
        }
    }
}