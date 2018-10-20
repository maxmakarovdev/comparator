package com.maximmakarov.comparator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.interactor.ITemplateInteractor
import com.maximmakarov.comparator.presentation.items.list.ItemsViewModel
import com.maximmakarov.comparator.utils.getLiveDataValue
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ItemsViewModelTest {
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ItemsViewModel

    private lateinit var interactor: IItemInteractor
    private lateinit var templateInteractor: ITemplateInteractor

    @Before
    fun setUp() {
        interactor = mock()
        templateInteractor = mock()

        viewModel = ItemsViewModel(interactor, templateInteractor)
    }

    @Test
    fun testGetItems() {
        viewModel.setArgs(testTemplate)
        getLiveDataValue(viewModel.itemsData)

        verify(interactor, times(1)).getItems(testTemplate.id!!)
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun testSaveChanges() {
        whenever(interactor.getItems(testTemplate.id!!))
                .thenReturn(MutableLiveData<List<Item>>().apply { postValue(listOf()) })

        viewModel.setArgs(testTemplate)
        getLiveDataValue(viewModel.itemsData)
        viewModel.saveChanges(testTemplate.name)

        verify(interactor, times(1)).getItems(testTemplate.id!!)
        verify(templateInteractor, times(1)).updateTemplate(testTemplate.id!!, testTemplate.name)
        verifyNoMoreInteractions(interactor)
    }
}