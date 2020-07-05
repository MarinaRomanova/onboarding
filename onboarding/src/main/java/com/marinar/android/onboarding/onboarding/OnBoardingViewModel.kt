package com.marinar.android.onboarding.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OnBoardingViewModel(val screens: Int = 2) : ViewModel() {
    private var _currentScreenIndex: Int = 0

    // region live data
    private val _currentIndex : MutableLiveData<Int> = MutableLiveData()
    val currentIndex: LiveData<Int>
        get() = _currentIndex

    private val _shouldFinish : MutableLiveData<Boolean> = MutableLiveData()
    val shouldFinish: LiveData<Boolean>
        get() = _shouldFinish

    private val _shouldSkip : MutableLiveData<Boolean> = MutableLiveData()
    val shouldSkip: LiveData<Boolean>
        get() = _shouldSkip
    //endregion

    fun goToNextScreen() {
        if (_currentScreenIndex < screens - 1) {
            _currentScreenIndex++
            _currentIndex.value = _currentScreenIndex
        }

        updateButtons()
    }

    fun goToPreviousScreenOrFinish() {
        if (_currentScreenIndex > 0) {
            _currentScreenIndex--
            _currentIndex.value = _currentScreenIndex
        }

        updateButtons()
    }

    fun slideToPage(position: Int) {
        _currentScreenIndex = position
        updateButtons()
    }

    private fun updateButtons() {
        _shouldFinish.value = _currentScreenIndex == screens - 1
        _shouldSkip.value = _currentScreenIndex == 0
    }
}

class OnBoardingViewModelFactory(val screens: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return modelClass.getConstructor(Int::class.java).newInstance(screens)
    }
}

