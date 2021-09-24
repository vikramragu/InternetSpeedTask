package com.test.internettask.domain.repository

import android.app.Activity
import com.test.internettask.data.models.GetSpeedResult
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow

/**
 * Written by Vikram Ragu on 24-09-2021
 *
 **/
interface GetInternetSpeedFromFirebaseRepository {

    fun getInternetSpeedFromRepo(phoneNumber : String): Flow<Response<GetSpeedResult>>

}