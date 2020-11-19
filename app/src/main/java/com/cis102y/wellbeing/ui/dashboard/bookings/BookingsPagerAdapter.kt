package com.cis102y.wellbeing.ui.dashboard.bookings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cis102y.wellbeing.ui.dashboard.MapsFragment


class BookingsPagerAdapter(activity: FragmentActivity, private val itemsCount: Int) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AppointmentsFragment()
            1 -> MapsFragment()
            else -> Fragment()
        }
    }
    override fun getItemCount(): Int {
        return itemsCount
    }
}
