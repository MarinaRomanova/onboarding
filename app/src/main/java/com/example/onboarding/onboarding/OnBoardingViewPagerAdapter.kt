package com.example.onboarding.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.onboarding.R

class OnBoardingViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var screens = OnBoardingScreen.values()

    override fun getItemCount(): Int = screens.count()

    override fun createFragment(position: Int): Fragment = OnBoardingStepFragment.newInstance(screens[position])
}

enum class OnBoardingScreen(@StringRes val titleRes: Int, @StringRes val messageRes: Int, @DrawableRes val imageRes: Int) {
    WELCOME(R.string.onboarding_welcome_title, R.string.onboarding_welcome_message, R.drawable.ic_ctk_logo),
    OFFER(R.string.onboarding_offer_title, R.string.onboarding_offer_message, R.drawable.ic_offer_search),
    MISSION(R.string.onboarding_mission_title, R.string.onboarding_mission_message, R.drawable.ic_mission),
    SETTINGS( R.string.onboarding_settings_title, R.string.onboarding_settings_message, R.drawable.ic_settings)
}