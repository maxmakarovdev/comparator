package com.maximmakarov.comparator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.presentation.items.comparison.ComparisonViewModel
import com.maximmakarov.comparator.utils.getLiveDataValue
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
class ComparisonViewModelTest {
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ComparisonViewModel

    private lateinit var interactor: IItemInteractor

    @Before
    fun setUp() {
        interactor = mock()

        viewModel = ComparisonViewModel(interactor)
    }

    @Test
    fun testGetItemsTransformedData() {
        viewModel.setArgs(testItems)
        getLiveDataValue(viewModel.tableData)

        verify(interactor, times(1)).getItemsTransformedData(testItems[0].templateId, testItems)
        verifyNoMoreInteractions(interactor)
    }
}