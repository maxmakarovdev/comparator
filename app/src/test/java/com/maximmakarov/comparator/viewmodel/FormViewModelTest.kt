package com.maximmakarov.comparator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.domain.interactor.IItemInteractor
import com.maximmakarov.comparator.domain.model.AttributeData
import com.maximmakarov.comparator.presentation.items.form.FormViewModel
import com.maximmakarov.comparator.utils.getLiveDataValue
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class FormViewModelTest {
    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FormViewModel

    private lateinit var interactor: IItemInteractor

    @Before
    fun setUp() {
        interactor = mock()

        viewModel = FormViewModel(interactor)
    }

    @Test
    fun testGetItemData() {
        viewModel.setArgs(testItems[0])
        getLiveDataValue(viewModel.itemData)

        verify(interactor, times(1)).getItemData(testItems[0].templateId, testItems[0].id)
        verifyNoMoreInteractions(interactor)
    }

    @Test
    fun testSaveChanges(){
        whenever(interactor.getItemData(testItems[0].templateId, testItems[0].id))
                .thenReturn(MutableLiveData<List<Pair<AttributeGroup, List<AttributeData>>>>().apply { postValue(listOf()) })

        viewModel.setArgs(testItems[0])
        getLiveDataValue(viewModel.itemData)
        viewModel.saveChanges(testItems[0].name)

        verify(interactor, times(1)).getItemData(testItems[0].templateId, testItems[0].id)
        verify(interactor, times(1)).saveItemData(any(), any())
        verifyNoMoreInteractions(interactor)
    }
}