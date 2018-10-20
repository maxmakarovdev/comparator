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
import com.maximmakarov.comparator.presentation.items.comparison.ComparisonViewModel
import com.maximmakarov.comparator.presentation.items.form.FormViewModel
import com.maximmakarov.comparator.presentation.items.list.ItemsViewModel
import com.maximmakarov.comparator.presentation.templates.detail.TemplateDetailViewModel
import com.maximmakarov.comparator.presentation.templates.list.TemplatesViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val dataModule = module {
    single { AppDatabase.buildDatabase(get()) }
    single { get<AppDatabase>().templateDao() }
    single { get<AppDatabase>().attributeGroupDao() }
    single { get<AppDatabase>().attributeDao() }
    single { get<AppDatabase>().itemDao() }
    single { get<AppDatabase>().itemAttrDataDao() }

    single { TemplateRepository(get(), get(), get()) as ITemplateRepository }
    single { ItemRepository(get(), get()) as IItemRepository }
}

val domainModule = module {
    single { TemplateInteractor(get()) as ITemplateInteractor }
    single { ItemInteractor(get(), get()) as IItemInteractor }
}

val presentationModule = module {
    viewModel { TemplatesViewModel(get()) }
    viewModel { TemplateDetailViewModel(get()) }
    viewModel { ItemsViewModel(get(), get()) }
    viewModel { FormViewModel(get()) }
    viewModel { ComparisonViewModel(get()) }
}
