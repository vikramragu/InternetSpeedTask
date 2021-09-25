package com.test.internettask.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
@Module
class NetworkModule {

    companion object {
        val TIMESTAMP = "dd-MM-yyyy HH:mm:ss aa"
    }


    @Singleton
    @Provides
    fun getFireStoreInstance() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun getTimeStampFormat() = SimpleDateFormat(TIMESTAMP, Locale.getDefault())

    @Singleton
    @Provides
    fun getConnectivityManager(application: Application): ConnectivityManager {
        return application.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Singleton
    @Provides
    fun getTelephonyManager(application: Application): TelephonyManager {
        return application.applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

}