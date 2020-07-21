package fr.skyle.capitodico.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import fr.skyle.capitodico.R
import fr.skyle.capitodico.base.activity.AbstractActivity
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AbstractActivity() {

    // --- Life cycle
    // ---------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListeners()
    }

    // --- Methods
    // ---------------------------------------------------

    private fun setupListeners() {
        bottomNavigationMain.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }
}