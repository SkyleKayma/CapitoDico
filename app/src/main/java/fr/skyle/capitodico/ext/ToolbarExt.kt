package fr.skyle.capitodico.ext

import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar

fun Toolbar.setNavigationIconColor(@ColorInt color: Int) =
    navigationIcon?.mutate()?.let {
        it.setTint(color)
        this.navigationIcon = it
    }

fun Toolbar.setCollapseIconColor(@ColorInt color: Int) =
    collapseIcon?.mutate()?.let {
        it.setTint(color)
        this.collapseIcon = it
    }