package fr.skyle.capitodico.ui.cards.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.openium.ddiv2.base.bottomsheet.AbstractBottomSheetDialogFragment
import fr.skyle.capitodico.R


class BottomSheetDialogCardFilter : AbstractBottomSheetDialogFragment() {

    override val preventCollapseState: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        View.inflate(context, R.layout.bottom_sheet_dialog_card_filter, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}