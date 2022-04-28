package com.jann_luellmann.thekenapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel

class DrinkingListFragmentPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    eventId: LiveData<Long>,
    eventWithDrinksAndCustomersViewModel: EventWithDrinksAndCustomersViewModel,
    eventAndCustomerWithDrinksViewModel: EventAndCustomerWithDrinksViewModel
) : FragmentStateAdapter(fm, lifecycle) {

    private val listFragment =
        ListFragment(
            eventId,
            eventWithDrinksAndCustomersViewModel,
            eventAndCustomerWithDrinksViewModel
        )
    private val settingsFragment = SettingsFragment(eventId, eventWithDrinksAndCustomersViewModel)

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> listFragment
            1 -> settingsFragment
            else -> listFragment
        }
    }
}