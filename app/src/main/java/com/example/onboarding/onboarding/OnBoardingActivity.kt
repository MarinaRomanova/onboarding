package com.example.onboarding.onboarding

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.onboarding.R
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.layout_button_bottom_sheet.*

class OnBoardingActivity : AppCompatActivity() {
    private val rgbEvaluator = ArgbEvaluator()
    private val colors = arrayOf(R.color.blue_a30, R.color.mission_status_background_late, R.color.green_300_a50, R.color.blueGrey_300_a50)

    private val viewModel: OnBoardingViewModel by viewModels { OnBoardingViewModelFactory() }

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
        pager.adapter = OnBoardingViewPagerAdapter(this)
        pager.registerOnPageChangeCallback(onPageChangeCallback)

        viewModel.currentIndex.observe(this, Observer {
            pager.setCurrentItem(it, true)
        })


        slider_indicator.sliderCount = OnBoardingScreen.values().count()

        setupButtons()
    }

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            val colorUpdate: Int = rgbEvaluator.evaluate(positionOffset, colors[position],
                colors[ if (position == colors.size - 1) position else position + 1]) as Int
            pager.setBackgroundColor(colorUpdate)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.slideToPage(position)
            translateTruck(position)
            pager.setBackgroundColor(getBackgroundColor(position))
            slider_indicator.setSelected(position)
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

    private fun translateTruck(screen: Int) {
        val intervalPx = (Resources.getSystem().displayMetrics.widthPixels ) / OnBoardingScreen.values().count() - 1
        val position: Float = (intervalPx * screen).toFloat()
        val animator = ObjectAnimator.ofFloat(truck_iv, View.TRANSLATION_X, position)
        animator.start()
    }

   private fun getBackgroundColor(position: Int) = ContextCompat.getColor(this, colors[position])
}