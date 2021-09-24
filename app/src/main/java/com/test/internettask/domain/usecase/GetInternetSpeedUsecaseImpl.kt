package com.test.internettask.domain.usecase

import android.app.Activity
import com.test.internettask.data.models.GetSpeedResult
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.domain.repository.GetInternetSpeedFromFirebaseRepository
import com.test.internettask.domain.repository.GetInternetSpeedRepository
import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
class GetInternetSpeedUsecaseImpl @Inject constructor(
    val repository: GetInternetSpeedRepository,
    val networkRepository: GetInternetSpeedFromFirebaseRepository
) :
    GetInternetSpeedUsecase {

    override fun getInternetSpeed(activity: Activity): Flow<Response<NetworkResponseModel>> {
        return repository.getInternetSpeed(activity)
    }

    override fun getInternetSpeedFromRepo(phoneNumber: String): Flow<Response<GetSpeedResult>> {
        return networkRepository.getInternetSpeedFromRepo(phoneNumber)
    }
}