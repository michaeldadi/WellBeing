package com.cis102y.wellbeing.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: HomeFirestoreRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        homeViewModel.text.observe(viewLifecycleOwner, {
//            text_home.text = it
//        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_container_home.setOnRefreshListener {
            swipe_container_home.isRefreshing = false
        }

        recycler_view_home.layoutManager = LinearLayoutManager(activity)

        //List posts by most recent
        val rootRef = FirebaseFirestore.getInstance()
        val query =
            rootRef.collection("posts").orderBy("createdTimestamp", Query.Direction.DESCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).setLifecycleOwner(this).build()

        adapter = HomeFirestoreRecyclerAdapter(options)
        recycler_view_home.adapter = adapter
    }

    private inner class HomeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var view: View
        private lateinit var img: ImageView

        fun HomeViewHolder(itemView: View) {
            super.itemView
            img = itemView.findViewById(R.id.post_likes)
        }

        fun setAuthorName(authorName: String) {

        }

        fun setCreatedTimestamp(createdTimestamp: Date?) {

        }

        fun setText(text: String) {

        }
        fun setLikeCount(likeCount: Int) {

        }

        fun setCommentCount(commentCount: Int) {

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
            holder.setText(model.text)
            holder.setCreatedTimestamp(model.createdTimestamp)
            Log.d("HomeFrag",model.createdTimestamp.toString())
            holder.setLikeCount(model.likeCount)
            holder.setCommentCount(model.commentCount)
        }
    }
}
