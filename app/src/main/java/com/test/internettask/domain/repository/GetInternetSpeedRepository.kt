package com.test.internettask.domain.repository

import android.app.Activity
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
interface GetInternetSpeedRepository {

    fun getInternetSpeed(activity : Activity): Flow<Response<NetworkResponseModel>>

}