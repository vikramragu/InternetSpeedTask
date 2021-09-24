package com.test.internettask.di.module

import com.test.internettask.ui.home.NetworkSpeedActivity
import com.test.internettask.ui.home.fragment.SearchInternetSpeedFragment
import com.test.internettask.ui.home.fragment.ShowInternetSpeedFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
@Module
abstract class ComponentModule {

    @ContributesAndroidInjector
    abstract fun bindActivity(): NetworkSpeedActivity

    @ContributesAndroidInjector
    abstract fun bindShowInternetSpeedFragment(): ShowInternetSpeedFragment

    @ContributesAndroidInjector
    abstract fun bindSearchInternetSpeedFragment() : SearchInternetSpeedFragment
}