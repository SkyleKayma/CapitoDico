package fr.skyle.capitodico.base.fragment

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Created by Openium on 19/02/2019.
 */

abstract class AbstractFragment : Fragment() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
            true
        } else super.onOptionsItemSelected(item)
}