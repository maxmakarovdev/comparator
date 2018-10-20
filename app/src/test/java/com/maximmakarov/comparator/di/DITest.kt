package com.maximmakarov.comparator.di

import android.content.Context
import com.maximmakarov.comparator.core.di.*
import com.maximmakarov.comparator.data.dao.*
import com.maximmakarov.comparator.data.database.AppDatabase
import com.maximmakarov.comparator.domain.boundary.IItemRepository
import com.maximmakarov.comparator.domain.boundary.ITemplateRepository
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import com.maximmakarov.comparator.presentation.items.form.FormViewModel
import com.maximmakarov.comparator.presentation.items.list.ItemsViewModel
import com.maximmakarov.comparator.presentation.templates.detail.TemplateDetailViewModel
import com.maximmakarov.comparator.presentation.templates.list.TemplatesViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.test.KoinTest
import org.koin.test.checkModules
import kotlin.test.assertNotNull

@RunWith(JUnit4::class)
class DITest : KoinTest {

    private val appModule = module {
        single { mock<Context>() }
    }

    @Test
    fun checkDependencyTree() {
        checkModules(listOf(appModule, dataModule, domainModule, presentationModule))
    }

    @Test
    fun testAppModuleInjections() {
        startKoin(listOf(appModule))
        assertNotNull(get<Context>())
        stopKoin()
    }

    @Test
    fun testDataModuleInjections() {
        startKoin(listOf(appModule, dataModule))
        assertNotNull(get<AppDatabase>())
        assertNotNull(get<TemplateDao>())
        assertNotNull(get<ItemDao>())
        assertNotNull(get<ItemAttrDataDao>())
        assertNotNull(get<AttributeDao>())
        assertNotNull(get<AttributeGroupDao>())
        assertNotNull(get<ITemplateRepository>())
        assertNotNull(get<IItemRepository>())
        stopKoin()
    }

    @Test
    fun testDomainModuleInjections() {
        startKoin(listOf(module {
            single { mock<ITemplateRepository>() }
            single { mock<IItemRepository>() }
        }, domainModule))
        assertNotNull(get<ITemplateInteractor>())
        assertNotNull(get<IItemInteractor>())
        stopKoin()
    }

    @Test
    fun testPresentationModuleInjections() {
        startKoin(listOf(module {
            single { mock<ITemplateInteractor>() }
            single { mock<IItemInteractor>() }
        }, presentationModule))
        assertNotNull(get<TemplatesViewModel>())
        assertNotNull(get<TemplateDetailViewModel>())
        assertNotNull(get<ItemsViewModel>())
        assertNotNull(get<FormViewModel>())
        assertNotNull(get<FormViewModel>())
        stopKoin()
    }
}