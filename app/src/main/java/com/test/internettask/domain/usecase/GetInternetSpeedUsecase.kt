package com.test.internettask.domain.usecase

import android.app.Activity
import com.test.internettask.data.models.GetSpeedResult
import com.test.internettask.data.models.InternetSpeedModel
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
interface GetInternetSpeedUsecase {

    fun getInternetSpeed(activity: Activity): Flow<Response<NetworkResponseModel>>

    fun getInternetSpeedFromRepo(phoneNumber: String): Flow<Response<GetSpeedResult>>
}