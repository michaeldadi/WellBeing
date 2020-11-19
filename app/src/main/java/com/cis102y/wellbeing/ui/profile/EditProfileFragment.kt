package com.cis102y.wellbeing.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cis102y.wellbeing.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = FirebaseAuth.getInstance().currentUser

        Glide.with(this).load(user?.photoUrl).fitCenter().placeholder(R.drawable.user_placeholder).into(edit_profile_image)
        btn_update_profile.setOnClickListener {
            user?.updateEmail(edit_profile_email.text.toString())
            user?.updateProfile(userProfileChangeRequest {
                displayName = edit_profile_name.text.toString()
            })
            findNavController().navigate(R.id.action_navigation_edit_profile_to_navigation_profile)
        }
        edit_profile_name.setText(user?.displayName)
        edit_profile_email.setText(user?.email)
    }
}
