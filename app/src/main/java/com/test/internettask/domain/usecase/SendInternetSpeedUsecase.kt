package com.test.internettask.domain.usecase

import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
interface SendInternetSpeedUsecase {

    fun sendInternetSpeed(
        internetSpeed: String,
        timeStamp: String,
        phoneNumber: String
    ): Flow<Response<String>>

}