package com.base.basemvvm.presentation.core

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.base.basemvvm.core.utils.InternetUtil
import com.base.basemvvm.core.utils.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
abstract class BaseActivity: AppCompatActivity() {
    companion object {
        const val TIME = 5000L
    }

    var isBack = false
    val pref get() = PreferenceHelper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                InternetUtil.internetState.collect{
                    if (!it)
                        Timber.e("BaseActivity => onCreate => Khong co ket noi")
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                lifecycleScope.launch {
                    Timber.e("BaseActivity => onCreate => onBackPress ${supportFragmentManager.backStackEntryCount}")
                    if (supportFragmentManager.backStackEntryCount == 0) {
                        if (!isBack) {
                            Toast.makeText(this@BaseActivity, "Click 1 lần nữa để thoát app", Toast.LENGTH_SHORT).show()
                            isBack = true
                            delay(TIME)
                            isBack = false
                        } else {
                            finish()
                        }
                    } else {
                        supportFragmentManager.popBackStack()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}