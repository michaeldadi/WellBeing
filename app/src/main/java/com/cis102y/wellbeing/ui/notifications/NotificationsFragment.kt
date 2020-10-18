package com.cis102y.wellbeing.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.Notification
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.util.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var adapter: NotificationFirestoreRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
//        notificationsViewModel.text.observe(viewLifecycleOwner, {
//            text_notifications.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_container_notifications.setOnRefreshListener {
            swipe_container_notifications.isRefreshing = false
        }

        recycler_view_notifications.layoutManager = LinearLayoutManager(activity)
        //Add horizontal borders between each notification
        recycler_view_notifications.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        //List notifications by most recent
        val rootRef = FirebaseFirestore.getInstance()
        val query =
            rootRef.collection("notifications")
                .orderBy("notifiedTimestamp", Query.Direction.DESCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<Notification>()
                .setQuery(query, Notification::class.java).setLifecycleOwner(this).build()

        adapter = NotificationFirestoreRecyclerAdapter(options)
        recycler_view_notifications.adapter = adapter
    }

    private inner class NotificationViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var view: View

        fun setNotifiedTimestamp(notifiedTimestamp: Date?) {

        }

        fun setPhotoUrl(photoUrl: String) {

        }

        fun setText(text: String) {

        }
    }

    private inner class NotificationFirestoreRecyclerAdapter(options: FirestoreRecyclerOptions<Notification>) :
        FirestoreRecyclerAdapter<Notification, NotificationViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_notification_item, parent, false)
            return NotificationViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: NotificationViewHolder,
            position: Int,
            model: Notification
        ) {

        }

    }
}
