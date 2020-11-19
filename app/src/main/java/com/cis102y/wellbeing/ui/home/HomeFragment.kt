package com.cis102y.wellbeing.ui.home

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.recyclerview_home_item.view.*

class HomeFragment : Fragment() {

    private lateinit var adapter: HomeFirestoreRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        swipe_container_home.setOnRefreshListener {
            swipe_container_home.isRefreshing = false
        }

        recycler_view_home.layoutManager = LinearLayoutManager(activity)

        //List posts by most recent
        val rootRef = FirebaseFirestore.getInstance()
        val query =
            rootRef.collection("posts").orderBy("createdTimestamp", Query.Direction.DESCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java)
                .setLifecycleOwner(this).build()

        adapter = HomeFirestoreRecyclerAdapter(options)
        recycler_view_home.adapter = adapter

        Glide.with(this).load(user?.photoUrl).fitCenter().placeholder(R.drawable.user_placeholder).into(post_update_profile_picture)

        post_update_profile_picture.setOnClickListener {
//            findNavController().graph = nav_host_fragment.findNavController().navInflater.inflate(R.navigation.nav_graph_profile)
            findNavController().navigate(R.id.action_navigate_home_to_navigation_profile)
        }

        goto_post_update.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_post_update)
        }
    }

    private inner class HomeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val authorName: TextView
        val content: TextView
        val likeCount: TextView
        var btnLike: ImageButton
        val commentCount: TextView
        val postedTime: TextView

        init {
            super.itemView

            image = itemView.post_profile_picture
            authorName = itemView.post_author
            content = itemView.post_text
            likeCount = itemView.post_likes
            btnLike = itemView.action_like
            commentCount = itemView.post_comments
            postedTime = itemView.post_timestamp
        }
    }

    private inner class HomeFirestoreRecyclerAdapter(options: FirestoreRecyclerOptions<Post>) :
        FirestoreRecyclerAdapter<Post, HomeViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_home_item, parent, false)
            return HomeViewHolder(view)
        }

        override fun onBindViewHolder(holder: HomeViewHolder, position: Int, model: Post) {
            Glide.with(requireContext()).load(model.imgUri).fitCenter().placeholder(R.drawable.user_placeholder).into(holder.image)

            holder.authorName.text = model.authorName
            holder.content.text = model.text
            holder.likeCount.text = model.likeCount.toString()
            holder.commentCount.text = model.commentCount.toString()
            holder.postedTime.text = DateUtils.getRelativeTimeSpanString(model.createdTimestamp!!.time)
//            holder.itemView.action_like.setOnClickListener {
//                if (holder.btnLike.drawable == ResourcesCompat.getDrawable(resources, R.drawable.ic_thumb_up_black_24dp, null))
//                    holder.btnLike.setImageResource(R.drawable.ic_thumb_up_blue_24dp)
//                else
//                    holder.btnLike.setImageResource(R.drawable.ic_thumb_up_black_24dp)
//            }
        }
    }
}
