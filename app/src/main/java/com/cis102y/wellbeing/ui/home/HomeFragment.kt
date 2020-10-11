package com.cis102y.wellbeing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, {
//            textView.text = it
//        })
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view_home)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        //List posts by most recent
        val rootRef = FirebaseFirestore.getInstance()
        val query = rootRef.collection("posts").orderBy("timestamp", Query.Direction.DESCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = HomeFirestoreRecyclerAdapter(options)
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

    private inner class HomeViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private lateinit var view: View
        fun setText(text: String) {

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

        }

    }
}
