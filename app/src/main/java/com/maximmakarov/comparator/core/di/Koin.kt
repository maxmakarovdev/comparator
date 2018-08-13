package com.maximmakarov.comparator.core.di

import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.repository.ItemRepository
import com.maximmakarov.comparator.data.repository.TemplateRepository
import org.koin.dsl.module.applicationContext


val appModule = applicationContext {
    bean { this }
    bean { AppDatabase.buildDatabase(get()) }
}

val dataModule = applicationContext {
    bean { TemplateRepository(get()) }
    bean { ItemRepository(get()) }

}