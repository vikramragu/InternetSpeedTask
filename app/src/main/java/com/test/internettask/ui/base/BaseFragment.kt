package com.test.internettask.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
abstract class BaseFragment<VM : ViewModel, DB : ViewDataBinding> : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM
    lateinit var databinding: DB

    abstract val layoutRes: Int
    abstract val bindingVariable: Int
    abstract val viewModelClass: Class<VM>
    abstract fun viewCreationCompleted()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(BaseFragment::class.simpleName,"onCreateView *** ")
        databinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        databinding.lifecycleOwner = this
        return databinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(BaseFragment::class.simpleName,"onViewCreated *** ")
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass)
        databinding.setVariable(bindingVariable, viewModel)
        databinding.executePendingBindings()
        viewCreationCompleted()
    }
}