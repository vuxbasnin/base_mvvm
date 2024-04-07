package com.base.basemvvm.presentation.feature.m01

import com.base.basemvvm.data.DemoRepository
import com.base.basemvvm.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class M01ViewModel @Inject constructor(private val demoRepository: DemoRepository): BaseViewModel() {
}