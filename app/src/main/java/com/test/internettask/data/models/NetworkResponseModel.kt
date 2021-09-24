package com.test.internettask.data.models

import androidx.lifecycle.ViewModelProvider

/**
 * Written by Vikram Ragu on 24-09-2021
 *
 **/
sealed class NetworkResponseModel {

    data class FindNetworkAvailabilityModel(
        val isAvailable: Boolean,
        val downStreamSpeed: String? = "",
        val upstreamSpeed: String? = ""
    ) : NetworkResponseModel()

    object Pinging : NetworkResponseModel()

    object PingingFailed : NetworkResponseModel()

    object OnInternetConnected : NetworkResponseModel()

    data class DownloadTest(val speed: String) : NetworkResponseModel()

    data class UploadTest(val speed: String) : NetworkResponseModel()

    data class OnError(val error: String) : NetworkResponseModel()
}