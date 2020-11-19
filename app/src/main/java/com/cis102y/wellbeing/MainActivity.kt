package com.cis102y.wellbeing

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.TransitionManager
import com.cis102y.wellbeing.ui.auth.SignInActivity
import com.cis102y.wellbeing.utils.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null)
            setupBottomNavigationBar()

        checkCurrentUser()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_overflow_menu, menu)
        // Define the listener
        val expandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                TransitionManager.beginDelayedTransition(toolbar)
                return true // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                TransitionManager.beginDelayedTransition(toolbar)
                return true // Return true to expand action view
            }
        }

        // Get the MenuItem for the action item
        val actionMenuItem = menu?.findItem(R.id.action_search)

        // Assign the listener to that action item
        actionMenuItem?.setOnActionExpandListener(expandListener)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.navigate_settings)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = nav_view
        val navGraphIds = listOf(R.navigation.nav_graph_home,
            R.navigation.nav_graph_dashboard,
            R.navigation.nav_graph_notifications,
            R.navigation.nav_graph_profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )

        // When selected controller changes, set toolbar
        controller.observe(this, { navController: NavController ->
            setSupportActionBar(toolbar)
            toolbar.setupWithNavController(navController)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.navigate_settings || destination.id == R.id.navigate_edit_profile) {
                    nav_view.visibility = View.GONE
//                    val layoutParams = nav_host_fragment.layoutParams as ConstraintLayout.LayoutParams
//                    layoutParams.bottomMargin = 0
//                    nav_host_fragment.layoutParams = layoutParams
                }
                else {
                    nav_view.visibility = View.VISIBLE
//                    val layoutParams = nav_host_fragment.layoutParams as ConstraintLayout.LayoutParams
//                    layoutParams.bottomMargin = R.attr.actionBarSize
//                    nav_host_fragment.layoutParams = layoutParams
                }
            }
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            // User is signed in
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}
