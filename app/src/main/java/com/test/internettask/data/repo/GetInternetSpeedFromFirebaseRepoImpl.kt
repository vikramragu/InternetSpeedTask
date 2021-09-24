package com.test.internettask.data.repo

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.test.internettask.data.models.GetSpeedResult
import com.test.internettask.data.models.InternetSpeedModel
import com.test.internettask.domain.repository.GetInternetSpeedFromFirebaseRepository
import com.test.internettask.util.COLLECTION_NAME
import com.test.internettask.util.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * Written by Vikram Ragu on 24-09-2021
 *
 **/
@ExperimentalCoroutinesApi
class GetInternetSpeedFromFirebaseRepoImpl @Inject constructor(val firebaseFirestore: FirebaseFirestore) :
    GetInternetSpeedFromFirebaseRepository {

    override fun getInternetSpeedFromRepo(phoneNumber: String): Flow<Response<GetSpeedResult>> {

        return callbackFlow {

            firebaseFirestore.collection(COLLECTION_NAME)
                .whereEqualTo("phoneNumber", phoneNumber)
                .orderBy("timeStamp", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener {
                    val modelList = it.toObjects(InternetSpeedModel::class.java)

                    modelList.forEach { model ->
                        Log.d(
                            GetInternetSpeedFromFirebaseRepoImpl::class.simpleName,
                            model.toString()
                        )
                        trySend(
                            Response.Success(
                                GetSpeedResult.GetSpeedResultFromFireStoreSuccess(
                                    model
                                )
                            )
                        )
                    }
                }.addOnFailureListener {
                    Log.d(
                        GetInternetSpeedFromFirebaseRepoImpl::class.simpleName,
                        it.message.toString()
                    )
                    trySend(
                        Response.Error(
                            it.message?.toString() ?: ""
                        )
                    )
                }

            awaitClose()
        }
    }

}