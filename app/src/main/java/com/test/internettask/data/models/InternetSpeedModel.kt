package com.test.internettask.data.models

import com.google.firebase.firestore.Exclude

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
data class InternetSpeedModel(
    val speed: String = "",
    val timeStamp: String = "",
    val phoneNumber: String = "",
    @Exclude
    val docId: String = ""
)