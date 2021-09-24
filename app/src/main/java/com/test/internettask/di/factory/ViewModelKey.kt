package com.test.internettask.di.factory

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.MapKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@MapKey
annotation class ViewModelKey(val viewmodel: KClass<out ViewModel>)