package com.cis102y.wellbeing

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.cis102y.wellbeing.ui.auth.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        findPreference<Preference>("sign_out")?.setOnPreferenceClickListener {
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
            startActivity(Intent(context, SignInActivity::class.java))
            return@setOnPreferenceClickListener true
        }
        return super.onPreferenceTreeClick(preference)
    }
}
