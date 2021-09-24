package com.test.internettask.util.callbackinterface

import android.app.Activity
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.speedchecker.android.sdk.SpeedcheckerSDK
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

/**
 * Written by Vikram Ragu on 23-09-2021
 *
 **/
@ExperimentalCoroutinesApi
class NetworkCallback constructor(
    val activity: Activity,
    val producerScope: ProducerScope<Response<NetworkResponseModel>>,
    val connectivityManager: ConnectivityManager
) :
    ConnectivityManager.NetworkCallback() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        Log.d(NetworkCallback::class.simpleName, "On Available")

        /*val internetSpeedBuilder = InternetSpeedBuilder(activity)
        internetSpeedBuilder.setOnEventInternetSpeedListener(InternetSpeedCheck(producerScope))*/

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

        if (networkCapabilities != null) {
            producerScope.trySend(Response.Success(NetworkResponseModel.OnInternetConnected))
            SpeedcheckerSDK.SpeedTest.startTest(activity)
        } else {
            Log.d(NetworkCallback::class.simpleName, "Network Capabilities is null")
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        Log.d(NetworkCallback::class.simpleName, "On Lost")

        producerScope.trySend(
            Response.Error(
                "Oops!! No internet connection"
            )
        )
    }
}