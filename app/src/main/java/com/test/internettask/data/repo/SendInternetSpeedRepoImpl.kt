package com.test.internettask.data.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.test.internettask.data.models.InternetSpeedModel
import com.test.internettask.domain.repository.SendInternetSpeedRepository
import com.test.internettask.domain.usecase.SendInternetSpeedusecaseImpl
import com.test.internettask.util.COLLECTION_NAME
import com.test.internettask.util.DOCUMENT_ADDED
import com.test.internettask.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.text.SimpleDateFormat
import javax.inject.Inject

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
class SendInternetSpeedRepoImpl @Inject constructor(
    val firebaseFirestore: FirebaseFirestore
) : SendInternetSpeedRepository {

    @ExperimentalCoroutinesApi
    override fun internetSpeed(
        speed: String,
        timeStamp: String,
        phoneNumber: String
    ): Flow<Response<String>> {

        val internetSpeed = InternetSpeedModel(speed, timeStamp, phoneNumber)

        return callbackFlow {

            firebaseFirestore.collection(COLLECTION_NAME).document()
                .set(internetSpeed)
                .addOnSuccessListener {
                    Log.d(
                        SendInternetSpeedRepoImpl::class.simpleName,
                        "Document Added Successfully"
                    )
                    trySend(Response.Success(DOCUMENT_ADDED))
                }
                .addOnFailureListener {
                    trySend(Response.Error(it.message ?: ""))
                }
            awaitClose {
                Log.d(SendInternetSpeedRepoImpl::class.simpleName, "AwaitClose")
            }
        }
    }
}