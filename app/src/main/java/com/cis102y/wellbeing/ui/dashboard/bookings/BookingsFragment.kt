package com.cis102y.wellbeing.ui.dashboard.bookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.cis102y.wellbeing.R
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_bookings.*

class BookingsFragment : Fragment() {

    private var BookingsPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            Toast.makeText(context,
                "Selected position: $position",
                Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        private const val ARG_POSITION = "position"

        fun getInstance(position: Int): Fragment {
            val bookingsFragment = BookingsFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            bookingsFragment.arguments = bundle
            return bookingsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = FirebaseAuth.getInstance().currentUser
        Glide.with(this).load(user?.photoUrl).fitCenter().placeholder(R.drawable.user_placeholder).into(bookings_header_user_image)
        btn_bookings_schedule_appointment.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_appointments_to_navigate_search_appointment)
        }

        bookings_pager.adapter = BookingsPagerAdapter(requireActivity(),2)
        TabLayoutMediator(bookings_tab_layout, bookings_pager) { tab, position ->
            tab.text = resources.getStringArray(R.array.appointment_tabs)[position].substringBefore(' ')
//            bookings_pager.setCurrentItem(tab.position, true)
        }.attach()
    }
}
