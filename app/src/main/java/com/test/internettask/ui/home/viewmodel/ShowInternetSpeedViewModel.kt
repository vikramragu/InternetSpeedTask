package com.test.internettask.ui.home.viewmodel

import android.app.Activity
import android.telephony.PhoneNumberUtils
import android.telephony.TelephonyManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.test.internettask.data.models.InternetSpeedModel
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.domain.usecase.GetInternetSpeedUsecase
import com.test.internettask.domain.usecase.SendInternetSpeedUsecase
import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class ShowInternetSpeedViewModel @Inject constructor(
    private val getUsecase: GetInternetSpeedUsecase,
    private val setUsecase: SendInternetSpeedUsecase,
    private val telephonyManager: TelephonyManager,
    private val simpleDateFormat: SimpleDateFormat
) : ViewModel() {


    fun getInternetSpeedUsecase(activity: Activity): Flow<Response<NetworkResponseModel>> {
        return getUsecase.getInternetSpeed(activity)
    }

    fun sendInternetSpeed(
        uploadSpeed: String,
        phoneNumber: String
    ): Flow<Response<String>> {
        return setUsecase.sendInternetSpeed(uploadSpeed, getTimeStamp(), phoneNumber)
    }

    fun getTelephonyManagerInstance() = telephonyManager

    private fun getTimeStamp() = simpleDateFormat.format(Date())

}
