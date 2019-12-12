package com.yt8492.grpcchat

import com.yt8492.grpcchat.infra.InfraModule
import com.yt8492.grpcchat.ui.ChatFragmentModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, ChatFragmentModule::class, InfraModule::class]
)
interface AppComponent : AndroidInjector<App>