package com.cis102y.wellbeing.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cis102y.wellbeing.R
import com.cis102y.wellbeing.models.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_post.*
import java.util.*

class PostFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = FirebaseAuth.getInstance().currentUser
        btn_publish_post.setOnClickListener {
            FirebaseFirestore.getInstance().collection("posts").add(Post(user?.photoUrl.toString(), user?.displayName!!, edit_post_text.text.toString(), Date(),0,0))
            findNavController().navigate(R.id.action_navigate_post_update_to_navigate_home3)
        }
    }
}
