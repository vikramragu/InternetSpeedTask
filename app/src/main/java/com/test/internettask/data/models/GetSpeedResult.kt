package com.test.internettask.data.models

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
sealed class GetSpeedResult {

    data class GetSpeedResultFromFireStoreSuccess(val data: InternetSpeedModel) : GetSpeedResult()

    data class GetSpeedResultFromFireStoreFailure(val failureMessage: String) : GetSpeedResult()
}