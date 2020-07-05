package com.marinar.android.onboarding.onboarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var screens = arrayListOf<Fragment>()

    override fun getItemCount(): Int = screens.count()

    override fun createFragment(position: Int): Fragment = screens[position]
}
