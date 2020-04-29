package dev.ahmdaeyz.pinster.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dev.ahmdaeyz.pinster.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLandingScreen()
    }

    private fun setLandingScreen() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val hasChoseCategories = sharedPref.getBoolean(getString(R.string.has_chose_categories), false)
        val navHostFragment = nav_host_fragment as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_graph)
        val navController = navHostFragment.navController
        val landingDestination =
                if (hasChoseCategories) R.id.homeFragment
                else R.id.signInFragment
        navGraph.startDestination = landingDestination
        navController.graph = navGraph
    }
}