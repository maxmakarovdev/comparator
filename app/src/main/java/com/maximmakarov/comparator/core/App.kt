package com.maximmakarov.comparator.core

import android.app.Application
import com.maximmakarov.comparator.core.di.appModule
import org.koin.android.ext.android.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(listOf(appModule))
    }
}