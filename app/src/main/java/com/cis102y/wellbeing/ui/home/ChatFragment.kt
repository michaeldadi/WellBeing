package com.cis102y.wellbeing.ui.home

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.ChatMessage
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_chat.*
import java.util.*

class ChatFragment: Fragment() {
    val MSG_TYPE_SENDER = 0
    val MSG_TYPE_RECEIVER = 1
    val user = FirebaseAuth.getInstance().currentUser
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        send.setOnClickListener {
            FirebaseFirestore.getInstance().collection("chat").add(ChatMessage(user!!.uid, textsend.text.toString(), user.displayName!!, Date()))
            textsend.text.clear()
        }

        chat_recycler_view.layoutManager = LinearLayoutManager(activity)
        val rootRef = FirebaseFirestore.getInstance()
        val query =
            rootRef.collection("chat").orderBy("messageTimestamp", Query.Direction.ASCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<ChatMessage>().setQuery(query, ChatMessage::class.java)
                .setLifecycleOwner(this).build()
        val adapter = ChatFirestoreRecyclerAdapter(options)
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                chat_recycler_view.scrollToPosition(ChatFirestoreRecyclerAdapter(options).itemCount - 1)
            }
        })
        chat_recycler_view.adapter = adapter
    }

    private inner class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val author: TextView
        val message: TextView
        val timestamp: TextView
        init {
            super.itemView

            author = itemView.findViewById(R.id.sendername)
            message = itemView.findViewById(R.id.textmessage)
            timestamp = itemView.findViewById(R.id.timestamp)
        }
    }

    private inner class ChatFirestoreRecyclerAdapter(options: FirestoreRecyclerOptions<ChatMessage>): FirestoreRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
            val view = LayoutInflater.from(parent.context)
            if(viewType == MSG_TYPE_SENDER)
                return ChatViewHolder(view.inflate(R.layout.chat_sender_message, parent, false))
            return ChatViewHolder(view.inflate(R.layout.chat_receiver_message, parent, false))
        }

        override fun onBindViewHolder(holder: ChatViewHolder, position: Int, model: ChatMessage) {
            holder.message.text = model.messageText
            holder.author.text = model.messageAuthor
            holder.timestamp.text = DateUtils.getRelativeTimeSpanString(model.messageTimestamp!!.time)
        }

        override fun getItemViewType(position: Int): Int {
            if(getItem(position).messageUid == user?.uid)
                return MSG_TYPE_SENDER
            return MSG_TYPE_RECEIVER
        }
    }
}
