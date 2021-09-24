package com.test.internettask.domain.repository

import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
interface SendInternetSpeedRepository {

    fun internetSpeed(speed: String, timeStamp: String, phoneNumber: String) : Flow<Response<String>>

}