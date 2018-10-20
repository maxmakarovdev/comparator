package com.maximmakarov.comparator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import com.maximmakarov.comparator.presentation.templates.list.TemplatesViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TemplatesViewModelTest {
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TemplatesViewModel

    private lateinit var interactor: ITemplateInteractor

    @Before
    fun setUp() {
        interactor = mock()

        viewModel = TemplatesViewModel(interactor)
    }

    @Test
    fun testGetTemplates() {
        viewModel.templatesData

        verify(interactor, times(1)).getTemplates()
        verifyNoMoreInteractions(interactor)
    }
}