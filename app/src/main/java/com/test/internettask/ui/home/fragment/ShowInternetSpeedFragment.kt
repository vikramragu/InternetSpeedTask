package com.test.internettask.ui.home.fragment

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.test.internettask.BR
import com.test.internettask.R
import com.test.internettask.data.models.NetworkResponseModel
import com.test.internettask.databinding.FragmentShowInternetSpeedBinding
import com.test.internettask.ui.base.BaseFragment
import com.test.internettask.ui.home.viewmodel.ShowInternetSpeedViewModel
import com.test.internettask.util.Response
import com.test.internettask.util.extensions.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class ShowInternetSpeedFragment :
    BaseFragment<ShowInternetSpeedViewModel, FragmentShowInternetSpeedBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_show_internet_speed

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModelClass: Class<ShowInternetSpeedViewModel>
        get() = ShowInternetSpeedViewModel::class.java

    private var phoneNumber: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    private val permissionArrayOreo =
        arrayOf(
            READ_SMS,
            READ_PHONE_NUMBERS,
            READ_PHONE_STATE,
            ACCESS_FINE_LOCATION,
        )

    private val permissionArrayPreOreo = arrayOf(READ_PHONE_STATE, READ_SMS, ACCESS_FINE_LOCATION)

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            Log.d("Permission status", granted.toString())
            if (granted.values.any { it.not() }) {
                requestPermission()
            } else {
                getConnectivitySpeed()
            }
        }


    @SuppressLint("HardwareIds")
    override fun viewCreationCompleted() {

        databinding.tvUploadTitle.text =
            getString(R.string._upload_speed).plus("\n").plus(getString(R.string._mbps))
        databinding.tvDownloadTitle.text =
            getString(R.string._download_speed).plus("\n").plus(getString(R.string._mbps))

        requestPermission()

        databinding.btnSubmit.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                if (requireContext().checkPhoneNumberSmsPermissionOreo()) {
                    phoneNumber = viewModel.getTelephonyManagerInstance().line1Number.takeLast(10)
                }
            } else {

                if (requireContext().checkPhoneNumberSmsPermission()) {
                    phoneNumber = viewModel.getTelephonyManagerInstance().line1Number.takeLast(10)
                }
            }

            Log.d("Phone number", viewModel.getTelephonyManagerInstance().line1Number.takeLast(10))

            sendPhoneNumberAndInternetSpeed()
        }

        databinding.btnSearch.setOnClickListener {

            val directions =
                ShowInternetSpeedFragmentDirections.actionShowInternetSpeedFragmentToSearchInternetSpeedFragment()
            findNavController().navigate(directions)

        }
    }


    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestPermission.launch(permissionArrayOreo)
        } else {
            requestPermission.launch(permissionArrayPreOreo)
        }
    }


    private fun sendPhoneNumberAndInternetSpeed() {

        Log.d(ShowInternetSpeedFragment::class.simpleName, "Find out internet speed")

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.sendInternetSpeed(
                databinding.uploadSpeed!!,
                phoneNumber
            ).flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .onStart {
                    databinding.fullScreenLoading.show()
                }
                .catch {

                }.collect {
                    when (it) {
                        is Response.Success -> {
                            Log.d(ShowInternetSpeedFragment::class.simpleName, "Successfully added")
                            databinding.fullScreenLoading.gone()
                            requireContext().showShortToast(it.data)
                        }

                        is Response.Error -> {
                            databinding.fullScreenLoading.gone()
                            requireContext().showShortToast(it.toString())
                        }
                        else -> {
                            databinding.fullScreenLoading.gone()
                        }
                    }
                }
        }
    }


    private fun getConnectivitySpeed() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.getInternetSpeedUsecase(requireActivity())
                    .collect {
                        when (it) {
                            Response.Loading -> {
                                databinding.apply {
                                    this.downloadSpeed = getString(R.string._calculating)
                                    this.uploadSpeed = getString(R.string._calculating)
                                }
                            }

                            is Response.Success -> {
                                handleInternetSpeedResponse(it.data)
                            }

                            is Response.Error -> {
                                databinding.apply {
                                    this.uploadSpeed = getString(R.string._no_internet)
                                    this.downloadSpeed = getString(R.string._no_internet)
                                }
                                databinding.btnSubmit.disabled()
                            }
                        }
                    }
            }
        }
    }


    private fun handleInternetSpeedResponse(response: NetworkResponseModel) {

        when (response) {
            is NetworkResponseModel.DownloadTest -> {
                databinding.downloadSpeed = response.speed
            }
            is NetworkResponseModel.FindNetworkAvailabilityModel -> {
                databinding.apply {
                    this.downloadSpeed = response.downStreamSpeed
                    this.uploadSpeed = response.upstreamSpeed
                    this.timeStamp = this@ShowInternetSpeedFragment.viewModel.getTimeStamp()
                }
                databinding.btnSubmit.enabled()
            }

            is NetworkResponseModel.OnError -> {
                requireContext().showShortToast(response.error)
            }

            NetworkResponseModel.OnInternetConnected -> {
                databinding.apply {
                    this.downloadSpeed = getString(R.string._calculating)
                    this.uploadSpeed = getString(R.string._calculating)
                }
            }

            NetworkResponseModel.Pinging -> {
                databinding.apply {
                    this.downloadSpeed = getString(R.string._pinging)
                    this.uploadSpeed = getString(R.string._pinging)
                }
            }
            NetworkResponseModel.PingingFailed -> {
                databinding.apply {
                    this.downloadSpeed = getString(R.string._ping_failed)
                    this.uploadSpeed = getString(R.string._ping_failed)
                }
            }
            is NetworkResponseModel.UploadTest -> {
                databinding.uploadSpeed = response.speed
            }
        }
    }

}