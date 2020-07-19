package fr.skyle.capitodico.ext

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import fr.skyle.capitodico.R

fun Fragment.navigate(navDirections: NavDirections, navOptions: NavOptions? = null) {
    val finalNavOptions = navOptions ?: NavOptions.Builder().setEnterAnim(R.anim.nav_default_enter_anim)
        .setExitAnim(R.anim.nav_default_exit_anim)
        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(R.anim.nav_default_pop_exit_anim).build()

    findNavController().navigate(navDirections, finalNavOptions)
}