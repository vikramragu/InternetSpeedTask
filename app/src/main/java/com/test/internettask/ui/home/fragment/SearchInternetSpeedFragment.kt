package com.test.internettask.ui.home.fragment

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.test.internettask.BR
import com.test.internettask.R
import com.test.internettask.data.models.GetSpeedResult
import com.test.internettask.databinding.FragmentSearchSpeedBinding
import com.test.internettask.ui.base.BaseFragment
import com.test.internettask.ui.home.viewmodel.SearchInternetSpeedViewModel
import com.test.internettask.util.Response
import com.test.internettask.util.extensions.gone
import com.test.internettask.util.extensions.show
import com.test.internettask.util.extensions.showShortToast
import com.test.internettask.util.extensions.textChangeListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
@FlowPreview
@ExperimentalCoroutinesApi
class SearchInternetSpeedFragment :
    BaseFragment<SearchInternetSpeedViewModel, FragmentSearchSpeedBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_search_speed

    override val bindingVariable: Int
        get() = BR.viewModel

    override val viewModelClass: Class<SearchInternetSpeedViewModel>
        get() = SearchInternetSpeedViewModel::class.java

    override fun viewCreationCompleted() {
        init()
    }


    private fun init() {
        databinding.searchView.textChangeListener().debounce(350).onEach {
            Log.d(SearchInternetSpeedFragment::class.simpleName, "The Entered text is $it")
            getLastKnownSpeed(it)
        }.launchIn(lifecycleScope)
    }


    private fun getLastKnownSpeed(phoneNumber: String) {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.searchByPhoneNumber(phoneNumber).onStart {
                    databinding.progressBar.show()
                }.collect {

                    when (it) {
                        is Response.Error -> {
                            databinding.progressBar.gone()
                        }
                        Response.Loading -> {

                        }
                        is Response.Success -> {
                            databinding.progressBar.gone()
                            handleSuccessResponse(it)
                        }
                    }
                }
            }
        }
    }


    private fun handleSuccessResponse(response: Response.Success<GetSpeedResult>) {

        when (response.data) {
            is GetSpeedResult.GetSpeedResultFromFireStoreFailure -> {
                requireContext().showShortToast(response.data.failureMessage)
            }

            is GetSpeedResult.GetSpeedResultFromFireStoreSuccess -> {
                databinding.apply {
                    this.upSpeed = response.data.data.speed
                    this.timeStamp = response.data.data.timeStamp
                    executePendingBindings()
                }
            }
        }
    }

}