package com.base.basemvvm.presentation.core

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.ViewPager

/**
 * Tác dụng chính của class này là để kiểm tra fragment trong tablayout hiện tại có đang được focus tới không
 */
abstract class BaseTabFragment<T : ViewBinding>(bindingInflater: (layoutInflater: LayoutInflater) -> T) : BaseFragment<T>(bindingInflater) {
    // parent
    val tabSelecting: Int
        get() = getViewPager()?.currentItem ?: -1

    abstract fun getViewPager(): ViewPager?
}