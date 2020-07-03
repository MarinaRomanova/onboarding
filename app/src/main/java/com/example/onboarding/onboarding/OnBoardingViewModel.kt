package com.example.onboarding.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OnBoardingViewModel : ViewModel() {
    private var _currentScreenIndex: Int = 0

    private val _currentIndex : MutableLiveData<Int> = MutableLiveData()
    val currentIndex: LiveData<Int>
        get() = _currentIndex

    private val screens = OnBoardingScreen.values()

    private val _shouldFinish : MutableLiveData<Boolean> = MutableLiveData()
    val shouldFinish: LiveData<Boolean>
        get() = _shouldFinish

    private val _shouldSkip : MutableLiveData<Boolean> = MutableLiveData()
    val shouldSkip: LiveData<Boolean>
        get() = _shouldSkip

    fun goToNextScreenOrFinish() {
        if (_currentScreenIndex < screens.count()) {
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

    private fun updateButtons() {
        _shouldFinish.value = _currentScreenIndex == screens.count() - 1
        _shouldSkip.value = _currentScreenIndex == 0
    }

    fun slideToPage(position: Int) {
        _currentScreenIndex = position
        updateButtons()
    }
}

class OnBoardingViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return OnBoardingViewModel() as T
    }
}

