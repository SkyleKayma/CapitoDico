package fr.skyle.capitodico.ui.cardDetail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.flexbox.FlexboxLayout
import fr.openium.kotlintools.ext.appCompatActivity
import fr.openium.kotlintools.ext.getColorCompat
import fr.skyle.capitodico.R
import fr.skyle.capitodico.base.fragment.AbstractFragment
import fr.skyle.capitodico.ext.lastChild
import fr.skyle.capitodico.ext.setNavigationIconColor
import fr.skyle.capitodico.ext.trimTrailingZero
import fr.skyle.capitodico.utils.JsonUtils
import kotlinx.android.synthetic.main.fragment_card_detail.*

class FragmentCardDetail : AbstractFragment() {

    private val args: FragmentCardDetailArgs by navArgs()

    private var card: JsonUtils.Card? = null

    // --- Life cycle
    // ---------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_card_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card = JsonUtils.cards.firstOrNull { it.name == args.cardName }

        card?.let {
            setupToolbar()
            setCardInfo()
        } ?: findNavController().popBackStack()
    }

    // --- Methods
    // ---------------------------------------------------

    private fun setupToolbar() {
        val toolbar = toolbarCardDetail as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
        toolbar.setNavigationIconColor(requireContext().getColorCompat(R.color.ca_text_light_primary))
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setHomeButtonEnabled(true)
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

            val textViewEvent =
                (LayoutInflater.from(context)
                    .inflate(R.layout.list_item_card_detail_event, flexBoxLayoutCardDetail) as FlexboxLayout).lastChild() as? TextView

            textViewEvent?.apply {
                text = event?.readable_name ?: ""
                backgroundTintList = ColorStateList.valueOf(
                    event?.color?.let { Color.parseColor(it) } ?: requireContext().getColorCompat(android.R.color.transparent)
                )
            }
        }

        // Set effect
        textViewCardDetailEffect.text = card?.effect?.ifEmpty { "-" }
    }
}