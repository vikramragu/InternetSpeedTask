package com.test.internettask.domain.usecase

import android.util.Log
import com.test.internettask.domain.repository.SendInternetSpeedRepository
import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
class SendInternetSpeedusecaseImpl @Inject constructor(val sendInternetSpeedRepository: SendInternetSpeedRepository) :
    SendInternetSpeedUsecase {

    override fun sendInternetSpeed(internetSpeed: String, timeStamp: String, phoneNumber: String): Flow<Response<String>> {
        return sendInternetSpeedRepository.internetSpeed(internetSpeed, timeStamp, phoneNumber)
    }

}