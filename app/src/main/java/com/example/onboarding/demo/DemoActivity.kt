package com.example.onboarding.demo

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.onboarding.R
import com.example.onboarding.onboarding.OnBoardingScreen
import com.example.onboarding.onboarding.OnBoardingStepFragment
import com.marinar.android.onboarding.onboarding.OnBoardingActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.fragment_onboarding_step.*


class DemoActivity: OnBoardingActivity(), OnBoardingActivity.OnPageChangedListener {
    private val intervalPx = (Resources.getSystem().displayMetrics.widthPixels ) / OnBoardingScreen.values().count() - 1

    override val colors: List<Int>
        get() = arrayListOf(R.color.blue_a30, R.color.green_300_a50, R.color.blueGrey_300_a50, R.color.pink)
    override val fragments: ArrayList<Fragment>
        get() {
            val screens = arrayListOf<OnBoardingStepFragment>()
            OnBoardingScreen.values().forEach {
                screens.add(OnBoardingStepFragment.newInstance(it))
            }
            return  screens as ArrayList<Fragment>
        }

    private fun translateTruck(screen: Int) {
        val position: Float = (intervalPx * screen).toFloat()
        //val animator = ObjectAnimator.ofFloat(truck_iv, View.TRANSLATION_X, position)
        //animator.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setOnPageChangedListener(this)
    }

    override fun onPageChanged(position: Int) {
        translateTruck(position)
    }
}