package com.test.internettask.di.module

import androidx.lifecycle.ViewModel
import com.test.internettask.di.factory.ViewModelKey
import com.test.internettask.ui.home.viewmodel.SearchInternetSpeedViewModel
import com.test.internettask.ui.home.viewmodel.ShowInternetSpeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ShowInternetSpeedViewModel::class)
    abstract fun bindShowInternetSpeedViewModel(viewModel: ShowInternetSpeedViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SearchInternetSpeedViewModel::class)
    abstract fun bindSearchInternetSpeedViewModel(viewModel: SearchInternetSpeedViewModel): ViewModel
}