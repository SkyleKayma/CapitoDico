package fr.skyle.capitodico.utils

import android.content.Context
import fr.openium.kotlintools.ext.getDefaultSharedPreferences
import fr.openium.kotlintools.ext.getIntPref
import fr.openium.kotlintools.ext.setIntPref
import fr.skyle.capitodico.enum.SortType

class PreferencesUtils(private val context: Context) {

    // Sort & Filters

    var sortSelected: Int
        get() = context.getIntPref(PREF_SORT_SELECTED, SortType.ALPHABETICAL.ordinal)
        set(sort) = context.setIntPref(PREF_SORT_SELECTED, sort)

    fun resetSort() {
        context.getDefaultSharedPreferences().edit()
            .remove(PREF_SORT_SELECTED)
            .apply()
    }

    companion object {
        // Sort
        private const val PREF_SORT_SELECTED = "PREF_SORT_SELECTED"
    }
}