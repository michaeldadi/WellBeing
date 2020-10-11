package com.cis102y.wellbeing.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.cis102y.wellbeing.R
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        profileViewModel.text.observe(viewLifecycleOwner, {
//            textView.text = it
//        })
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val file = Uri.fromFile(File(""))
        val storageRef = FirebaseStorage.getInstance().reference
        val profileRef = storageRef.child("images/users/${file.lastPathSegment}")
        var uploadTask = profileRef.putFile(file)

        //Glide.with(this).load(profileRef).into()
    }
}
