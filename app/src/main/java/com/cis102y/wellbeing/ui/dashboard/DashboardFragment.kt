package com.cis102y.wellbeing.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.DashboardCard
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.recyclerview_dashboard_item.view.*

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btn_map.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_maps)
        }
        btn_bookings.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_appointments)
        }
        btn_symptoms.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_dashboard_to_navigate_chat)
        }
        btn_prescriptions.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_orders)
        }
        btn_nutrition.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_nutrition)
        }

        val rootRef = FirebaseFirestore.getInstance()
        val query =
            rootRef.collection("dashboard").orderBy("title", Query.Direction.DESCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<DashboardCard>().setQuery(query, DashboardCard::class.java)
                .setLifecycleOwner(this).build()
        dashboard_pager.adapter = DashboardFirestoreRecyclerAdapter(options)
    }

    private inner class DashboardViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView
        val title: TextView
        val desc: TextView
        init {
            super.itemView

            image = itemView.image
            title = itemView.title
            desc = itemView.desc
        }
    }

    private inner class DashboardFirestoreRecyclerAdapter(options: FirestoreRecyclerOptions<DashboardCard>) :
        FirestoreRecyclerAdapter<DashboardCard, DashboardViewHolder>(options) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_dashboard_item, parent, false)
            return DashboardViewHolder(view)
        }

        override fun onBindViewHolder(holder: DashboardViewHolder, position: Int, model: DashboardCard) {
            Glide.with(requireContext()).load(model.imageUrl).fitCenter().into(holder.image)
            holder.title.text = model.title
            holder.desc.text = model.desc
        }
    }
}
