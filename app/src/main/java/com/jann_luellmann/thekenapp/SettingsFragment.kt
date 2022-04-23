package com.jann_luellmann.thekenapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jann_luellmann.thekenapp.adapter.TextAdapter
import com.jann_luellmann.thekenapp.data.db.Database
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers
import com.jann_luellmann.thekenapp.data.repository.EventWithDrinksAndCustomersRepository
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel
import com.jann_luellmann.thekenapp.data.view_model.ViewModelFactory
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel
import com.jann_luellmann.thekenapp.databinding.FragmentSettingsBinding
import com.jann_luellmann.thekenapp.dialog.CreateEntryDialogFragment
import com.jann_luellmann.thekenapp.util.Prefs
import java.util.*

class SettingsFragment : Fragment(), EventChangedListener {

    private lateinit var binding: FragmentSettingsBinding
    private var eventViewModel: EventViewModel? = null
    private val eventWithDrinksAndCustomersViewModel: EventWithDrinksAndCustomersViewModel by viewModels {
        ViewModelFactory(EventWithDrinksAndCustomersRepository(Database.getInstance().eventWithDrinksAndCustomersDAO()))
    }
    private var observableData: LiveData<EventWithDrinksAndCustomers>? = null

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

        binding.addDrink.setOnClickListener {
            CreateEntryDialogFragment(Drink()).show(
                parentFragmentManager,
                getString(R.string.drink_tag)
            )
        }
        binding.addCustomer.setOnClickListener { v: View? ->
            CreateEntryDialogFragment(
                Customer()
            ).show(parentFragmentManager, getString(R.string.customer_tag))
        }
        binding.addEvent.setOnClickListener { v: View? ->
            CreateEntryDialogFragment(Event()).show(
                parentFragmentManager,
                getString(R.string.event_tag)
            )
        }
        val eventId: Long = Prefs.getLong(context, Prefs.CURRENT_EVENT, 1L)
        onEventUpdated(eventId)

        // Event setup
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventViewModel?.findAll()?.observe(viewLifecycleOwner) { events ->
            setupRecyclerView(
                binding.eventList, eventsAdapter, true
            )
            eventsAdapter.setData(events)
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

    override fun onEventUpdated(eventId: Long) {
        observableData?.let {
            if (it.hasObservers())
                it.removeObservers(this)
        }

        eventWithDrinksAndCustomersViewModel.findById(eventId).observe(viewLifecycleOwner)
        { event: EventWithDrinksAndCustomers? ->
            if (event == null) return@observe
            event.customers.sort()
            drinksAdapter.setData(event.drinks)
            customersAdapter.setData(event.customers)
        }
    }
}