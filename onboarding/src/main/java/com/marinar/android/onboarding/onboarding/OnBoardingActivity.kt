package com.marinar.android.onboarding.onboarding

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.marinar.android.onboarding.R
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.layout_button_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_button_bottom_sheet.intro_btn_finish
import kotlinx.android.synthetic.main.layout_button_bottom_sheet.intro_btn_next
import kotlinx.android.synthetic.main.layout_button_bottom_sheet.intro_btn_skip

abstract class OnBoardingActivity : AppCompatActivity() {

    //region Public Params
    var transformer: ViewPager2.PageTransformer? = null
    //endregion

    //region Abstract Params
    abstract val colors: List<Int>
    abstract val fragments: ArrayList<Fragment>
    abstract val skipText: String?
    abstract val previousText: String?
    abstract val finishText: String?
    //endregion

    private val viewModel: OnBoardingViewModel by viewModels { OnBoardingViewModelFactory(fragments.size) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        pager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    override fun onBackPressed() {
        if (pager.currentItem > 0) viewModel.goToPreviousScreenOrFinish()
        else  super.onBackPressed()
    }

    private fun setupViews() {
        val adapter = OnBoardingViewPagerAdapter(this)
        adapter.screens = fragments

        pager.apply {
            this.adapter = adapter
            registerOnPageChangeCallback(onPageChangeCallback)
            setPageTransformer(transformer ?: DepthPageTransformer())
        }

        viewModel.currentIndex.observe(this, Observer {
            pager.setCurrentItem(it, true)
        })

        slider_indicator.sliderCount = (pager.adapter as OnBoardingViewPagerAdapter).itemCount

        setupButtons()
    }

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.slideToPage(position)
            if (colors.isNotEmpty())
                pager.setBackgroundColor(getBackgroundColor(position))
            slider_indicator.setSelected(position)
            onPageChanged?.onPageChanged(position)
            if (moving_iv != null) translateImage(position)
        }
    }

    private fun setText(btn: Button, text: String?) {
        if (text == null) return
        btn.text = text
    }

    private fun setupButtons() {
        setText(intro_btn_finish, finishText)

        viewModel.shouldFinish.observe(this, Observer { shouldFinish ->
            intro_btn_finish.apply {
                visibility = if (shouldFinish) View.VISIBLE else View.GONE
                setOnClickListener {
                    if (shouldFinish)
                        clickListener?.onFinish()
                else
                        viewModel.goToNextScreen() }
            }

            intro_btn_next.visibility = if (shouldFinish) View.GONE else View.VISIBLE
        })

        viewModel.shouldSkip.observe(this, Observer { shouldSkip ->
            intro_btn_skip.apply {

                setText(this, if (shouldSkip) skipText else  previousText)

                setOnClickListener {
                    if (shouldSkip)
                        clickListener?.onSkip()
                    else
                        viewModel.goToPreviousScreenOrFinish()
                }
            }
        })

        intro_btn_next.setOnClickListener {
            viewModel.goToNextScreen()
        }
    }

    private fun getBackgroundColor(position: Int) = ContextCompat.getColor(this, colors[position])

    private fun translateImage(screen: Int) {
        val intervalPx = Resources.getSystem().displayMetrics.widthPixels / pager.adapter!!.itemCount - 1
        val position: Float = (intervalPx * screen).toFloat()
        val animator = ObjectAnimator.ofFloat(moving_iv, View.TRANSLATION_X, position)
        animator.start()
    }

    //region Bar Colors
    fun setBarColors(
        @ColorInt barColorRes: Int,
        @ColorInt textColor: Int,
        @ColorInt accentColor: Int
    ) {
        slider_indicator.apply {
            setSelectedDrawableTint(accentColor)
            setUnselectedDrawableTint(textColor)
        }
        bottomSheetLayout.setBackgroundColor(barColorRes)
        intro_btn_finish.setTextColor(textColor)
        intro_btn_skip.setTextColor(textColor)
        intro_btn_next.setColorFilter(textColor)
    }
    //endregion

    //region Images
    fun setMovingImageDrawable(drawable: Drawable) {
        moving_iv.setImageDrawable(drawable)
    }

    fun setBackgroundDrawable(drawable: Drawable) {
        status_background_iv.setImageDrawable(drawable)
    }
    //endregion

    //region Listeners
    private var clickListener: ClickListener? = null

    interface OnPageChangedListener {
        fun onPageChanged(position: Int)
    }

    interface ClickListener {
        fun onFinish()
        fun onSkip()
    }

    fun setClickListener(listener: ClickListener) {
        this.clickListener = listener
    }

    private var onPageChanged: OnPageChangedListener? = null

    fun setOnPageChangedListener(listener: OnPageChangedListener) {
        onPageChanged = listener
    }
    //endregion
}
