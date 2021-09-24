package com.test.internettask.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.test.internettask.data.models.GetSpeedResult
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.domain.usecase.GetInternetSpeedUsecase
import com.test.internettask.util.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
class SearchInternetSpeedViewModel @Inject constructor(val getInternetSpeedUsecase: GetInternetSpeedUsecase) :
    ViewModel() {


    fun searchByPhoneNumber(phoneNumber : String): Flow<Response<GetSpeedResult>> {
       return getInternetSpeedUsecase.getInternetSpeedFromRepo(phoneNumber)
    }

}