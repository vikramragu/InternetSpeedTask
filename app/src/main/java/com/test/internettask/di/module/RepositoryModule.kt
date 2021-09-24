package com.test.internettask.di.module

import com.test.internettask.data.repo.GetInternetSpeedFromFirebaseRepoImpl
import com.test.internettask.data.repo.GetInternetSpeedRepoImpl
import com.test.internettask.data.repo.SendInternetSpeedRepoImpl
import com.test.internettask.domain.repository.GetInternetSpeedFromFirebaseRepository
import com.test.internettask.domain.repository.GetInternetSpeedRepository
import com.test.internettask.domain.repository.SendInternetSpeedRepository
import dagger.Binds
import dagger.Module

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindSendInternetSpeedRepo(sendInternetSpeedRepoImpl: SendInternetSpeedRepoImpl): SendInternetSpeedRepository

    @Binds
    abstract fun bindGetInternetSpeedRepo(getInternetSpeedRepository: GetInternetSpeedRepoImpl): GetInternetSpeedRepository

    @Binds
    abstract fun bindGetInternetSpeedFromFirebaseRepo(getInternetSpeedFromFirebaseRepoImpl: GetInternetSpeedFromFirebaseRepoImpl): GetInternetSpeedFromFirebaseRepository
}