package com.maximmakarov.comparator.core.di

import com.maximmakarov.comparator.data.AppDatabase
import org.koin.dsl.module.applicationContext


val appModule = applicationContext {
    bean { this }
    bean { AppDatabase.buildDatabase(get()) }
}

val dataModule = applicationContext {
    //todo add repository layer
    bean { get<AppDatabase>().templateDao() }
    bean { get<AppDatabase>().itemDao() }
    bean { get<AppDatabase>().itemDataDao() }
    bean { get<AppDatabase>().attributeGroupDao() }
    bean { get<AppDatabase>().attributeDao() }
}