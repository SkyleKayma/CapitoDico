package fr.skyle.capitodico.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import fr.openium.kotlintools.ext.getColorCompat
import fr.openium.kotlintools.ext.gone
import fr.openium.kotlintools.ext.show
import fr.skyle.capitodico.R
import kotlinx.android.synthetic.main.item_filter_activation_selector_item.view.*

/**
 * Created by lgodart on 26/04/2018.
 */
class ItemFilterActivationSelectorItem(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    @ColorInt
    private var defaultColor: Int

    private var isChecked: Boolean = true

    init {
        View.inflate(context, R.layout.item_filter_activation_selector_item, this)

        defaultColor = context.getColorCompat(R.color.ca_color_primary)

        reset()
    }

    fun initView(color: String) {
        this.defaultColor = Color.parseColor(color)

        ((frameLayoutFilterActivationSelector.background as LayerDrawable)
            .findDrawableByLayerId(R.id.layer_list_filter_activation_stroke) as GradientDrawable).setColor(
            this.defaultColor
        )
    }

    fun isChecked(isChecked: Boolean) {
        this.isChecked = isChecked

        if (this.isChecked) {
            imageViewFilterActivationSelector.show()
        } else {
            imageViewFilterActivationSelector.gone()
        }
    }

    fun isChecked(): Boolean =
        this.isChecked

    fun reset() {
        isChecked(true)
    }
}