package fr.skyle.capitodico.ui.cards.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import fr.openium.kotlintools.ext.getColorCompat
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
            itemView.textViewMainItemValue.text = "${card.value.trimTrailingZero() ?: "-"}pts"
            itemView.textViewMainItemDescription.text = card.description

            itemView.linearLayoutMainItemEvents.removeAllViews()

            card.events.forEach {
                val event = JsonUtils.events[it]

                val linearLayoutEvent = View.inflate(itemView.context, R.layout.item_card_event, null) as LinearLayout
                linearLayoutEvent.backgroundTintList = event?.color?.let {
                    ColorStateList.valueOf(Color.parseColor(it))
                } ?: ColorStateList.valueOf(itemView.context.getColorCompat(android.R.color.transparent))

                itemView.linearLayoutMainItemEvents.addView(linearLayoutEvent)
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