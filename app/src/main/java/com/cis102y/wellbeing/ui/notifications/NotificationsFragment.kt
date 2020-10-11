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
//        val textView: TextView = root.findViewById(R.id.text_notifications)
//        notificationsViewModel.text.observe(viewLifecycleOwner, {
//            textView.text = it
//        })
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_notifications)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        //Add horizontal borders between each notification
        recyclerView?.addItemDecoration(
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
                .setQuery(query, Notification::class.java).build()

        adapter = NotificationFirestoreRecyclerAdapter(options)
        recyclerView?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private inner class NotificationViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var view: View

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
