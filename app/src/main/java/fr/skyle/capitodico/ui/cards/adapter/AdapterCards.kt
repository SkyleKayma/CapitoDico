package fr.skyle.capitodico.ui.cards.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import fr.openium.kotlintools.ext.getColorCompat
import fr.openium.kotlintools.ext.getDrawableCompat
import fr.skyle.capitodico.R
import fr.skyle.capitodico.ext.lastChild
import fr.skyle.capitodico.ext.trimTrailingZero
import fr.skyle.capitodico.utils.JsonUtils
import kotlinx.android.synthetic.main.list_item_card.view.*

class AdapterCards(
    private var data: MutableList<Data>,
    private var onCardClicked: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_VIEW_TYPE = 0
        const val EMPTY_VIEW_TYPE = 1

        const val EMPTY_VIEW_SIZE = 1
    }

    private val copyList = mutableListOf<Data>()

    init {
        initCopyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_VIEW_TYPE ->
                ItemViewHolder(inflater.inflate(R.layout.list_item_card, parent, false))
            EMPTY_VIEW_TYPE ->
                EmptyViewHolder(inflater.inflate(R.layout.list_item_card_empty, parent, false))
            else -> error("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                val item = copyList[position]
                holder.bindView(item.card as JsonUtils.Card, onCardClicked)
            }
        }
    }

    override fun getItemCount(): Int =
        if (copyList.count() > 0) copyList.count() else EMPTY_VIEW_SIZE

    override fun getItemViewType(position: Int): Int =
        if (copyList.isEmpty()) {
            EMPTY_VIEW_TYPE
        } else ITEM_VIEW_TYPE

    private fun initCopyList() {
        copyList.clear()
        copyList.addAll(data)
    }

    fun searchBy(queryText: String) {
        copyList.clear()

        if (queryText.isEmpty()) {
            initCopyList()
        } else {
            for (item in data) {
                if (item.card?.name?.toLowerCase()?.contains(queryText.toLowerCase()) == true) {
                    copyList.add(item)
                }
            }
        }

        notifyDataSetChanged()
    }

    fun refreshData(data: MutableList<Data>, latestQueryString: String) {
        this.data = data

        if (latestQueryString.isNotEmpty()) {
            searchBy(latestQueryString)
        } else {
            initCopyList()
            notifyDataSetChanged()
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(card: JsonUtils.Card, onCardClicked: (String) -> Unit) {
            itemView.textViewMainItemTitle.text = card.name
            itemView.textViewMainItemValue.text = "${card.value.trimTrailingZero() ?: "-"}"
            itemView.textViewMainItemDescription.text = card.description

            itemView.linearLayoutMainItemEvents.removeAllViews()

            card.events.forEachIndexed { index, s ->
                val event = JsonUtils.events[s]

                val viewEvent =
                    (LayoutInflater.from(itemView.context)
                        .inflate(R.layout.list_item_card_event, itemView.linearLayoutMainItemEvents, true) as LinearLayout).lastChild()

                viewEvent?.apply {
                    background = itemView.context.getDrawableCompat(
                        if (card.events.count() == 1) {
                            R.drawable.shape_cards_background_card_item_event_rounded_only_first
                        } else if (card.events.count() > 1 && (index + 1) == 1 && (index + 1) < card.events.count()) {
                            R.drawable.shape_cards_background_card_item_event_rounded_first
                        } else if (card.events.count() > 1 && (index + 1) > 1 && (index + 1) < card.events.count()) {
                            R.drawable.shape_cards_background_card_item_event_rounded_middle
                        } else {
                            R.drawable.shape_cards_background_card_item_event_rounded_last
                        }
                    )

                    backgroundTintList = ColorStateList.valueOf(
                        event?.color?.let { Color.parseColor(it) } ?: itemView.context.getColorCompat(android.R.color.transparent)
                    )
                }
            }

            itemView.setOnClickListener {
                onCardClicked.invoke(card.name)
            }
        }
    }

    class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    data class Data(
        val card: JsonUtils.Card? = null
    )
}