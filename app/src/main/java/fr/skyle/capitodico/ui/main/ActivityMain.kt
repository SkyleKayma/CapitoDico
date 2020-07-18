package fr.skyle.capitodico.ui.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import fr.skyle.capitodico.R
import fr.skyle.capitodico.base.activity.AbstractActivity
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListeners()
    }

    private fun setupListeners() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottomNavigationMain.setupWithNavController(navController)
        bottomNavigationMain.setOnNavigationItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }
}