package fr.skyle.capitodico.ext

import android.view.View
import android.view.ViewGroup
import androidx.core.view.children

fun ViewGroup.lastChild(): View? =
    getChildAt(children.count() - 1)
