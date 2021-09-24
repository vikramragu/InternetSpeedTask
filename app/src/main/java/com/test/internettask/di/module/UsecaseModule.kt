package com.test.internettask.di.module

import com.test.internettask.domain.usecase.GetInternetSpeedUsecase
import com.test.internettask.domain.usecase.GetInternetSpeedUsecaseImpl
import com.test.internettask.domain.usecase.SendInternetSpeedUsecase
import com.test.internettask.domain.usecase.SendInternetSpeedusecaseImpl
import dagger.Binds
import dagger.Module

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
@Module
abstract class UsecaseModule {

    @Binds
    abstract fun bindSendInternetSpeedUsecase(sendInternetUsecaseImpl: SendInternetSpeedusecaseImpl): SendInternetSpeedUsecase

    @Binds
    abstract fun bindGetInternetSpeed(getInternetSpeedUsecaseImpl: GetInternetSpeedUsecaseImpl): GetInternetSpeedUsecase

}