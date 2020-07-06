package com.example.onboarding.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.marinar.android.onboarding.R

class SlideIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var selectedTint: Int = Color.GREEN
    private var unselectedTint: Int = Color.WHITE

    private val items = mutableListOf<ImageView>()

    var sliderCount: Int = 2
        set(value) {
            field = value
            initSlideIndicators()
            setSelected(0)
        }

    init {
        orientation = HORIZONTAL
    }

    private fun initSlideIndicators() {
        items.clear()

        for (i in 0 until sliderCount) {
            val iv = ImageView(context)
            iv.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.indicator_unselected))
            val marginInDp = if (i != sliderCount - 1) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8f,
                resources.displayMetrics
            ) else 0f
            val params = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f
            )
            params.setMargins(0, 0, marginInDp.toInt(), 0)
            iv.layoutParams = params
            addView(iv)
        }
    }

    fun setSelected(position: Int) {
        if (position > sliderCount - 1) return
        children.forEach {
            (it as ImageView).setColorFilter(
                (if (it == getChildAt(position))
                    selectedTint
                else
                    unselectedTint)
            )
        }
    }

    fun setSelectedDrawableTint(@ColorInt colorRes: Int) {
        this.selectedTint = colorRes
    }

    fun setUnselectedDrawableTint(@ColorInt colorRes: Int) {
        this.unselectedTint = colorRes
    }
}