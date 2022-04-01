package com.jann_luellmann.thekenapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jann_luellmann.thekenapp.SettingsFragment
import com.jann_luellmann.thekenapp.R

class ThekenappFragmentPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var listFragment: ListFragment? = null
    var settingsFragment: SettingsFragment? = null
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> listFragment!!
            1 -> settingsFragment!!
            else -> ListFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.list_heading)
            1 -> context.getString(R.string.settings_heading)
            else -> "Default"
        }
    }

    init {
        if (listFragment == null) listFragment = ListFragment()
        if (settingsFragment == null) settingsFragment = SettingsFragment()
    }
}