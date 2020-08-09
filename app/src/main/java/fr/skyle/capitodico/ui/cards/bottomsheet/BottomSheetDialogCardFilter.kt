package fr.skyle.capitodico.ui.cards.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import fr.openium.ddiv2.base.bottomsheet.AbstractBottomSheetDialogFragment
import fr.skyle.capitodico.R
import fr.skyle.capitodico.event.eventFilterChanged
import fr.skyle.capitodico.utils.PreferencesUtils
import kotlinx.android.synthetic.main.bottom_sheet_card_filter.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class BottomSheetDialogCardFilter : AbstractBottomSheetDialogFragment() {

    override val preventCollapseState: Boolean = true

    private val prefUtils by inject<PreferencesUtils>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        View.inflate(context, R.layout.bottom_sheet_card_filter, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinnerSort()
        setListeners()
    }

    private fun setSpinnerSort() {
        val adapterSort = object : ArrayAdapter<String>(
            requireContext(), R.layout.spinner_item_sort, resources.getStringArray(R.array.cards_filter_sortby)
        ) {}

        adapterSort.setDropDownViewResource(R.layout.spinner_item_dropdown_sort)

        spinnerBottomSheetDialogCardFilterSort.adapter = adapterSort
        spinnerBottomSheetDialogCardFilterSort.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    prefUtils.sortSelected = position

                    lifecycleScope.launch {
                        eventFilterChanged.send(Unit)
                    }
                }
            }

        spinnerBottomSheetDialogCardFilterSort.setSelection(prefUtils.sortSelected)
    }

    private fun setListeners() {
        textViewBottomSheetCardFilterClose.setOnClickListener {
            dismiss()
        }

        imageButtonBottomSheetCardFilterReset.setOnClickListener {
            // Reset sort
            prefUtils.resetSort()
            spinnerBottomSheetDialogCardFilterSort.setSelection(prefUtils.sortSelected)

            // Reset Activation filter
            itemFilterActivatorSelector.reset()

            lifecycleScope.launch {
                eventFilterChanged.send(Unit)
            }
        }
    }
}