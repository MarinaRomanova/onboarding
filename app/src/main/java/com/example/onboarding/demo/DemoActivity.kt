package com.example.onboarding.demo

import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.onboarding.R
import com.marinar.android.onboarding.onboarding.OnBoardingActivity


class DemoActivity : OnBoardingActivity(), OnBoardingActivity.OnPageChangedListener, OnBoardingActivity.ClickListener {

    override val colors: List<Int>
        get() = arrayListOf(R.color.blue_a30, R.color.green_300_a50, R.color.blueGrey_300_a50, R.color.pink)
    override val fragments: ArrayList<Fragment>
        get() {
            val screens = arrayListOf<DemoOnBoardingStepFragment>()
            DemoOnBoardingScreen.values().forEach {
                screens.add(DemoOnBoardingStepFragment.newInstance(it))
            }
            return  screens as ArrayList<Fragment>
        }

    override val skipText = "passer"
    override val previousText = "précedent"
    override val finishText = "finir"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBarColors(null, //use the color of pager background
            ContextCompat.getColor(this, R.color.primaryText),
            ContextCompat.getColor(this, R.color.accent))

        getDrawable(R.drawable.ic_plane)?.let { setMovingImageDrawable(it)}
        getDrawable(R.drawable.bg_city_skyline)?.let { setDividerDrawable(it) }

        setClickListener(this)
    }

    override fun onPageChanged(position: Int) {

    }

    override fun onFinish() {
        Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show()
    }

    override fun onSkip() {
        Toast.makeText(this, "Skip for now!", Toast.LENGTH_SHORT).show()
    }
}