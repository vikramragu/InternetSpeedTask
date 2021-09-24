package com.test.internettask.util

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
sealed class Response<out T : Any> {
    object Loading : Response<Nothing>()
    data class Success<out T : Any>(val data: T) : Response<T>()
    data class Error(val exception: String? = "") : Response<Nothing>()
}