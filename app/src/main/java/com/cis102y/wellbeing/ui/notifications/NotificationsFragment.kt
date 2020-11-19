package com.cis102y.wellbeing.ui.notifications

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.Notification
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.recyclerview_notification_item.view.*

class NotificationsFragment : Fragment() {

    private lateinit var adapter: NotificationFirestoreRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
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
        val image: ImageView
        val content: TextView
        val notifiedTime: TextView
        init {
            super.itemView

            image = itemView.notification_profile_picture
            content = itemView.notification_text
            notifiedTime = itemView.notification_date
        }
    }

    private inner class NotificationFirestoreRecyclerAdapter(options: FirestoreRecyclerOptions<Notification>) :
        FirestoreRecyclerAdapter<Notification, NotificationViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_notification_item, parent, false)
            return NotificationViewHolder(view)
        }

        override fun onBindViewHolder(holder: NotificationViewHolder, position: Int, model: Notification) {
            Glide.with(requireContext()).load(model.imgUri).fitCenter().placeholder(R.drawable.user_placeholder).into(holder.image)

            holder.content.text = model.text
            holder.notifiedTime.text = DateUtils.getRelativeTimeSpanString(model.notifiedTimestamp!!.time)
        }
    }
}
