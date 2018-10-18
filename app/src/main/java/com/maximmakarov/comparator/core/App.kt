package com.maximmakarov.comparator.core

import android.app.Application
import com.maximmakarov.comparator.core.di.appModule
import com.maximmakarov.comparator.core.di.dataModule
import com.maximmakarov.comparator.core.di.domainModule
import com.maximmakarov.comparator.core.di.presentationModule
import org.koin.android.ext.android.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, dataModule, domainModule, presentationModule))
    }
}