package com.yt8492.grpcchat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commitNow
import com.yt8492.grpcchat.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val chatFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? ChatFragment
            ?: ChatFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commitNow {
            replace(R.id.fragmentContainerView, chatFragment)
        }
    }
}
