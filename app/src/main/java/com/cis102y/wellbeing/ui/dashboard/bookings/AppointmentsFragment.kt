package com.cis102y.wellbeing.ui.dashboard.bookings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.Appointment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_appointments.*
import java.util.*

class AppointmentsFragment : Fragment() {

    private lateinit var adapter: AppointmentFirestoreRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_appointments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        swipe_container_appointments.setOnRefreshListener {
            swipe_container_appointments.isRefreshing = false
        }

//        recycler_view_appointments.layoutManager = LinearLayoutManager(activity)
        //Add horizontal borders between each notification
        //List notifications by most recent
        val rootRef = FirebaseFirestore.getInstance()
        val query =
            rootRef.collection("notifications")
                .orderBy("notifiedTimestamp", Query.Direction.DESCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query, Appointment::class.java).setLifecycleOwner(this).build()

        adapter = AppointmentFirestoreRecyclerAdapter(options)
//        recycler_view_appointments.adapter = adapter
    }

    private inner class AppointmentViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var view: View

        fun setNotifiedTimestamp(notifiedTimestamp: Date?) {

        }

        fun setPhotoUrl(photoUrl: String) {

        }

        fun setText(text: String) {

        }
    }

    private inner class AppointmentFirestoreRecyclerAdapter(options: FirestoreRecyclerOptions<Appointment>) :
        FirestoreRecyclerAdapter<Appointment, AppointmentViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_appointment_item, parent, false)
            return AppointmentViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: AppointmentViewHolder,
            position: Int,
            model: Appointment
        ) {

        }

    }
}
