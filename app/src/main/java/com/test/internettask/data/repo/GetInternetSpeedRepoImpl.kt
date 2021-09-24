package com.test.internettask.data.repo

import android.app.Activity
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.speedchecker.android.sdk.SpeedcheckerSDK
import com.test.internettask.data.models.InternetSpeedModel
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.domain.repository.GetInternetSpeedRepository
import com.test.internettask.util.Response
import com.test.internettask.util.SpeedTestListenerCallback
import com.test.internettask.util.callbackinterface.NetworkCallback
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
@ExperimentalCoroutinesApi
class GetInternetSpeedRepoImpl @Inject constructor(
    val connectivityManager: ConnectivityManager
) : GetInternetSpeedRepository {

    override fun getInternetSpeed(activity: Activity): Flow<Response<NetworkResponseModel>> {

        return callbackFlow {

            trySend(Response.Loading)

            initSpeedChecker(activity,this)

            val networkCallBack = NetworkCallback(activity, this, connectivityManager)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(networkCallBack)
            } else {
                val networkRequest =
                    NetworkRequest.Builder()
                        .build()
                connectivityManager.registerNetworkCallback(networkRequest, networkCallBack)
            }

            awaitClose {
                SpeedcheckerSDK.SpeedTest.interruptTest()
                SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(null)
                Log.d(GetInternetSpeedRepoImpl::class.simpleName, "Await close")
                connectivityManager.unregisterNetworkCallback(networkCallBack)
            }
        }
    }

    private fun initSpeedChecker(activity: Activity,scope : ProducerScope<Response<NetworkResponseModel>>) {
        SpeedcheckerSDK.init(activity)
        SpeedcheckerSDK.askPermissions(activity)
        SpeedcheckerSDK.SpeedTest.setOnSpeedTestListener(SpeedTestListenerCallback(scope))
    }



}