package com.maximmakarov.comparator.core.di

import com.maximmakarov.comparator.data.AppDatabase
import org.koin.dsl.module.applicationContext


val appModule = applicationContext {
    bean { this }
    bean { AppDatabase.buildDatabase(get()) }
}