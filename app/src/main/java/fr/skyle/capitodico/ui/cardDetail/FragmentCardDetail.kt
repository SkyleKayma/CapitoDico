package fr.skyle.capitodico.ui.cardDetail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.FlexboxLayout
import fr.openium.kotlintools.ext.appCompatActivity
import fr.openium.kotlintools.ext.getColorCompat
import fr.skyle.capitodico.R
import fr.skyle.capitodico.base.fragment.AbstractFragment
import fr.skyle.capitodico.ext.setNavigationIconColor
import fr.skyle.capitodico.ext.trimTrailingZero
import fr.skyle.capitodico.utils.JsonUtils
import kotlinx.android.synthetic.main.fragment_card_detail.*

class FragmentCardDetail : AbstractFragment() {

    private val args: FragmentCardDetailArgs by navArgs()

    private var card: JsonUtils.Card? = null

    // --- Life cycle
    // ---------------------------------------------------

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_card_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card = JsonUtils.cards.firstOrNull { it.name == args.cardName }

        card?.let {
            setToolbarStyle()
            setCardInfo()
        } ?: findNavController().popBackStack()
    }

    // --- Methods
    // ---------------------------------------------------

    private fun setToolbarStyle() {
        toolbarCardDetail.setNavigationIconColor(requireContext().getColorCompat(R.color.ca_text_light_primary))
        appCompatActivity.setSupportActionBar(toolbarCardDetail)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.title = card?.name?.ifEmpty { getString(R.string.card_detail_title_empty) }
    }

    private fun setCardInfo() {
        // Set name
        textViewCardDetailName.text = card?.name?.ifEmpty { "-" }

        // Set value
        textViewCardDetailValue.text = card?.value?.trimTrailingZero()?.ifEmpty { "-" }

        // Set priority
        textViewCardDetailPriority.text = card?.priority?.trimTrailingZero()?.ifEmpty { "-" }

        // Set description
        textViewCardDetailDescription.text = card?.description?.ifEmpty { "-" }

        // Set events
        card?.events?.forEach {
            val event = JsonUtils.events[it]

            val flexBoxEvent =
                LayoutInflater.from(context).inflate(R.layout.item_card_detail_event, flexBoxLayoutCardDetail) as FlexboxLayout
            val child = flexBoxEvent.getChildAt(flexBoxEvent.children.count() - 1) as TextView
            child.text = event?.readable_name ?: ""
            child.backgroundTintList = event?.color?.let {
                ColorStateList.valueOf(Color.parseColor(it))
            } ?: ColorStateList.valueOf(requireContext().getColorCompat(android.R.color.transparent))

//            flexBoxLayoutCardDetail.addView(textViewEvent)
        }

        // Set effect

        textViewCardDetailEffect.text = card?.effect?.ifEmpty { "-" }
    }
}