package com.test.internettask.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
data class CoroutineScope(
    val mainScope: CoroutineDispatcher,
    val ioScope: CoroutineDispatcher,
    val defaultScope: CoroutineDispatcher
) {

    @Inject
    constructor() : this(Dispatchers.Main, Dispatchers.IO, Dispatchers.Default)

}