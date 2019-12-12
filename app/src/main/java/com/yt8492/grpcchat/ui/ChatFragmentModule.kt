package com.yt8492.grpcchat.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ChatFragmentModule {
    @ContributesAndroidInjector
    fun chatFragment(): ChatFragment
}