package com.marinar.android.onboarding.onboarding

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.marinar.android.onboarding.R
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.layout_button_bottom_sheet.*

abstract class OnBoardingActivity : AppCompatActivity() {

    private var onPageChanged: OnPageChangedListener? = null

    fun setOnPageChangedListener(listener: OnPageChangedListener) {
        onPageChanged = listener
    }
    abstract val colors: List<Int>
    abstract val fragments : ArrayList<Fragment>

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

    private fun setupViews() {
        val adapter = OnBoardingViewPagerAdapter(this)
        adapter.screens = fragments

        pager.apply {
            this.adapter = adapter
            registerOnPageChangeCallback(onPageChangeCallback)
            setPageTransformer(DepthPageTransformer())
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
            translateImage(position)
        }
    }

    private fun setupButtons() {
        viewModel.shouldFinish.observe(this, Observer { shouldFinish ->
            intro_btn_finish.apply {
                if (shouldFinish) {
                    visibility = View.VISIBLE
                    setOnClickListener { viewModel.goToNextScreenOrFinish() }
                } else {
                    visibility = View.GONE
                }
            }
            intro_btn_next.visibility = if (shouldFinish) View.GONE else View.VISIBLE
        })

        viewModel.shouldSkip.observe(this, Observer { shouldSkip ->
            intro_btn_skip.apply {
                text = getString(if (shouldSkip) R.string.onboarding_btn_skip else R.string.onboarding_btn_previous)
                setOnClickListener { viewModel.goToPreviousScreenOrFinish() }
            }
        })

        intro_btn_next.setOnClickListener {
            viewModel.goToNextScreenOrFinish()
        }

        intro_btn_skip.setOnClickListener {
            viewModel.goToNextScreenOrFinish()
        }
    }

   private fun getBackgroundColor(position: Int) = ContextCompat.getColor(this, colors[position])

    interface OnPageChangedListener {
        fun onPageChanged(position: Int)
    }

    private fun translateImage(screen: Int) {
        val intervalPx = (Resources.getSystem().displayMetrics.widthPixels ) / pager.adapter!!.itemCount - 1
        val position: Float = (intervalPx * screen).toFloat()
        val animator = ObjectAnimator.ofFloat(truck_iv, View.TRANSLATION_X, position)
        animator.start()
    }
}
