package com.test.internettask.util

import android.util.Log
import com.speedchecker.android.sdk.Public.SpeedTestListener
import com.speedchecker.android.sdk.Public.SpeedTestResult
import com.test.internettask.data.models.NetworkResponseModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

/**
 * Written by Vikram Ragu on 24-09-2021
 *
 **/
@ExperimentalCoroutinesApi
class SpeedTestListenerCallback(private val scope: ProducerScope<Response<NetworkResponseModel>>) :
    SpeedTestListener {

    override fun onTestStarted() {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnTestStarted")
    }

    override fun onFetchServerFailed() {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnFetchServerFailed")
        scope.trySend(Response.Success(NetworkResponseModel.PingingFailed))
    }

    override fun onFindingBestServerStarted() {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnFindingBestServerStarted")
    }

    override fun onTestFinished(speedTestResult: SpeedTestResult?) {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnTestFinished $speedTestResult")

        val finalStr = "Server: ${speedTestResult!!.server.Domain}" +
                "Ping: ${speedTestResult.ping} ms" +
                "Download speed: ${speedTestResult.downloadSpeed} Mb/s " +
                "Upload speed: ${speedTestResult.uploadSpeed} Mb/s " +
                "Connection type: ${speedTestResult.connectionTypeHuman}"
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnTestFinished $finalStr")

        scope.trySend(
            Response.Success(
                NetworkResponseModel.FindNetworkAvailabilityModel(
                    true,
                    speedTestResult.downloadSpeed.toString(),
                    speedTestResult.uploadSpeed.toString()
                )
            )
        )
    }

    override fun onPingStarted() {
        scope.trySend(Response.Success(NetworkResponseModel.Pinging))
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnPingStarted")
    }

    override fun onPingFinished(p0: Int, p1: Int) {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnPingFinished")
    }

    override fun onDownloadTestStarted() {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnDownloadTestStarted")
    }

    override fun onDownloadTestProgress(i: Int, speedMbs: Double, transferredMb: Double) {
        scope.trySend(Response.Success(NetworkResponseModel.DownloadTest("$speedMbs")))
        Log.d(
            SpeedTestListenerCallback::class.simpleName,
            " OnDownloadTestProgress - $i% -> $speedMbs Mb/s\nTransferredMb: $transferredMb"
        )
    }

    override fun onDownloadTestFinished(downloadSpeed: Double) {
        scope.trySend(Response.Success(NetworkResponseModel.DownloadTest("$downloadSpeed")))
        Log.d(
            SpeedTestListenerCallback::class.simpleName,
            "OnDownloadTestFinished $downloadSpeed Mb/s"
        )
    }

    override fun onUploadTestStarted() {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnUploadTestStarted")
    }

    override fun onUploadTestProgress(i: Int, speedMbs: Double, transferredMb: Double) {
        scope.trySend(Response.Success(NetworkResponseModel.UploadTest("$speedMbs")))
        Log.d(
            SpeedTestListenerCallback::class.simpleName,
            " OnUploadTestProgress - $i% -> $speedMbs Mb/s\nTransferredMb: $transferredMb"
        )
    }

    override fun onUploadTestFinished(uploadSpeed: Double) {
        scope.trySend(Response.Success(NetworkResponseModel.UploadTest("$uploadSpeed")))
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnUploadTestFinished $uploadSpeed")
    }

    override fun onTestWarning(p0: String?) {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnTestWarning ${p0}")
    }

    override fun onTestFatalError(error: String?) {
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnTestInterrupted $error")
        scope.trySend(Response.Success(NetworkResponseModel.OnError(error?:"")))
    }

    override fun onTestInterrupted(error: String?) {
        scope.trySend(Response.Success(NetworkResponseModel.OnError(error?:"")))
        Log.d(SpeedTestListenerCallback::class.simpleName, "OnTestInterrupted ${error}")
    }
}