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
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        profileViewModel =
//            ViewModelProvider(this).get(ProfileViewModel::class.java)
        //        profileViewModel.text.observe(viewLifecycleOwner, {
//            text_profile.text = it
//        })
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gotoEditProfile.setOnClickListener {
            findNavController().navigate(R.id.navigate_edit_profile)
        }
        val user = FirebaseAuth.getInstance().currentUser
        val docRef = FirebaseFirestore.getInstance().collection("users").document(user!!.uid)

        profile_name.text = user.displayName
        profile_email.text = user.email

        docRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val document = it.result
                Glide.with(this).load(user.photoUrl).fitCenter().placeholder(R.drawable.user_placeholder).into(profile_image)
                profile_user_name.text = document?.getString("userName")
                profile_phone.text = document?.getString("phoneNumber")
                profile_sex.text = document?.getString("sex")
                profile_dob.text = document?.getString("dateOfBirth")
            }
        }
    }
}
