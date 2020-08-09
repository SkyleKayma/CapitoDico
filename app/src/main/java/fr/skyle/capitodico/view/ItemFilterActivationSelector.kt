package fr.skyle.capitodico.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import fr.skyle.capitodico.R
import fr.skyle.capitodico.utils.JsonUtils
import kotlinx.android.synthetic.main.item_filter_activation_selector.view.*

/**
 * Created by lgodart on 26/04/2018.
 */
class ItemFilterActivationSelector(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private val views = mutableListOf<ItemFilterActivationSelectorItem>()

    init {
        View.inflate(context, R.layout.item_filter_activation_selector, this)
        inflateChildViews()
    }

    private fun inflateChildViews() {
        val events = JsonUtils.events

        events.forEach {
            val view = ItemFilterActivationSelectorItem(context)
            views.add(view)

            view.setOnClickListener {
                view.isChecked(!view.isChecked())
            }

            view.initView(it.value.color)
            flexBoxLayoutFilterActivationSelector.addView(view)
        }
    }

    fun reset() {
        views.forEach {
            it.reset()
        }
    }
}