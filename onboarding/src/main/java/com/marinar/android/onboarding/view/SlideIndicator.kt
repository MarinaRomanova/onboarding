package com.example.onboarding.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.marinar.android.onboarding.R

class SlideIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var selectedDrawable: Drawable? =
        ContextCompat.getDrawable(context!!, R.drawable.indicator_selected)
    private var unselectedDrawable: Drawable? =
        ContextCompat.getDrawable(context!!, R.drawable.indicator_unselected)

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
            (it as ImageView).setImageDrawable(
                (if (it == getChildAt(position))
                    selectedDrawable
                else
                    unselectedDrawable)
            )
        }
    }

    fun setSelectedDrawableRes(drawable: Drawable) {
        this.selectedDrawable = drawable
    }

    fun setUnselectedDrawableRes(drawable: Drawable) {
        this.unselectedDrawable = drawable
    }
}