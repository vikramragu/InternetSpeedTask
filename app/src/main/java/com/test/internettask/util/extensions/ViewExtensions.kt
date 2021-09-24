package com.test.internettask.util.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
fun Context.showShortToast(value: String) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(value: String) {
    Toast.makeText(this, value, Toast.LENGTH_LONG).show()
}

fun View.enabled() {
    this.isEnabled = true
}


fun View.disabled() {
    this.isEnabled = false
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}


@ExperimentalCoroutinesApi
fun SearchView.textChangeListener(): Flow<String> {

    return callbackFlow<String> {

        val listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if ((newText.isNullOrBlank() || newText.isNullOrEmpty()).not()) {
                    trySend(newText!!)
                }
                return false
            }
        }

        this@textChangeListener.setOnQueryTextListener(listener)

        awaitClose {
            this@textChangeListener.setOnQueryTextListener(null)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun Context.checkPhoneNumberSmsPermissionOreo(): Boolean {

    return (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_SMS
    ) == PackageManager.PERMISSION_GRANTED && (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED) &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) ==
            PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_PHONE_STATE
    ) == PackageManager.PERMISSION_GRANTED)
}


fun Context.checkPhoneNumberSmsPermission(): Boolean {
    return (ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_SMS
    ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.READ_PHONE_STATE
    ) == PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}