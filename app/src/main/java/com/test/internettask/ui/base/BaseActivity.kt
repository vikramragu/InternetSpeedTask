package com.test.internettask.ui.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerAppCompatActivity

/**
 * Written by Vikram Ragu on 22-09-2021
 *
 **/
abstract class BaseActivity<DB : ViewDataBinding> : DaggerAppCompatActivity() {

    lateinit var dataBinding: DB

    abstract val binding: Int

    abstract fun created()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, binding)
        created()
    }


}