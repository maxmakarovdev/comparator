package com.maximmakarov.comparator.core.di

import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.data.repository.ItemRepository
import com.maximmakarov.comparator.data.repository.TemplateRepository
import com.maximmakarov.comparator.domain.boundary.IItemRepository
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import com.maximmakarov.comparator.domain.interactor.ItemInteractor
import com.maximmakarov.comparator.domain.interactor.TemplateInteractor
import org.koin.dsl.module.applicationContext


val appModule = applicationContext {
    bean { this }
    bean { AppDatabase.buildDatabase(get()) }
}

val dataModule = applicationContext {
    bean { TemplateRepository(get()) as ITemplateRepository }
    bean { ItemRepository(get()) as IItemRepository }
}

val domainModule = applicationContext {
    bean { ItemInteractor(get()) as IItemInteractor }
    bean { TemplateInteractor(get()) as ITemplateInteractor }
}

