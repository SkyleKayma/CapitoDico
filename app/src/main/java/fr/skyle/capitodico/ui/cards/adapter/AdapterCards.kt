package fr.skyle.capitodico.ui.cards.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import fr.openium.kotlintools.ext.getColorCompat
import fr.openium.kotlintools.ext.getDrawableCompat
import fr.skyle.capitodico.R
import fr.skyle.capitodico.ext.trimTrailingZero
import fr.skyle.capitodico.utils.JsonUtils
import kotlinx.android.synthetic.main.item_card.view.*

class AdapterCards(
    private var data: List<Data>,
    private var onCardClicked: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_VIEW_TYPE = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE ->
                ItemViewHolder(inflater.inflate(R.layout.item_card, parent, false))
            else -> error("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        when (holder) {
            is ItemViewHolder -> {
                holder.bindView(item.card as JsonUtils.Card, onCardClicked)
            }
        }
    }

    override fun getItemCount(): Int =
        data.count()

    override fun getItemViewType(position: Int): Int =
        ITEM_VIEW_TYPE

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(card: JsonUtils.Card, onCardClicked: (String) -> Unit) {
            itemView.textViewMainItemTitle.text = card.name
            itemView.textViewMainItemValue.text = "${card.value.trimTrailingZero() ?: "-"}"
            itemView.textViewMainItemDescription.text = card.description

            itemView.linearLayoutMainItemEvents.removeAllViews()

            card.events.forEachIndexed { index, s ->
                val event = JsonUtils.events[s]

                val linearLayoutEvent =
                    LayoutInflater.from(itemView.context)
                        .inflate(R.layout.item_card_event, itemView.linearLayoutMainItemEvents, true) as LinearLayout

                val child = linearLayoutEvent.getChildAt(linearLayoutEvent.children.count() - 1)
                child?.background = if (card.events.count() == 1) {
                    itemView.context.getDrawableCompat(R.drawable.shape_cards_background_card_item_event_rounded_only_first)
                } else if (card.events.count() > 1 && (index + 1) == 1 && (index + 1) < card.events.count()) {
                    itemView.context.getDrawableCompat(R.drawable.shape_cards_background_card_item_event_rounded_first)
                } else if (card.events.count() > 1 && (index + 1) > 1 && (index + 1) < card.events.count()) {
                    itemView.context.getDrawableCompat(R.drawable.shape_cards_background_card_item_event_rounded_middle)
                } else {
                    itemView.context.getDrawableCompat(R.drawable.shape_cards_background_card_item_event_rounded_last)
                }

                child?.backgroundTintList = event?.color?.let {
                    ColorStateList.valueOf(Color.parseColor(it))
                } ?: ColorStateList.valueOf(itemView.context.getColorCompat(android.R.color.transparent))
            }

            itemView.setOnClickListener {
                onCardClicked.invoke(card.name)
            }
        }
    }

    data class Data(
        val card: JsonUtils.Card? = null
    )
}