package fr.openium.ddiv2.base.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.skyle.capitodico.R

/**
 * [BottomSheetDialogFragment] that uses a custom
 * theme which sets a rounded background to the dialog
 * and doesn't dim the navigation bar
 */
abstract class AbstractBottomSheetDialogFragment : BottomSheetDialogFragment() {

    open val preventCollapseState: Boolean = false

    // This is here only to prevent collapsed state to be shown
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        if (preventCollapseState) {
            val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

            bottomSheetDialog.setOnShowListener {
                val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)

                bottomSheet?.let {
                    val behavior = BottomSheetBehavior.from(it)
                    behavior.skipCollapsed = true
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            bottomSheetDialog
        } else {
            BottomSheetDialog(requireContext(), theme)
        }

    override fun getTheme(): Int =
        R.style.BottomSheetDialogTheme
}