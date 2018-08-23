package com.maximmakarov.comparator.item.comparison

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.repository.ItemDataWithAttr
import com.maximmakarov.comparator.data.repository.ItemRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class ComparisonViewModel : ViewModel(), KoinComponent {

    private val repository: ItemRepository by inject()
    private val inputData: MutableLiveData<List<Item>> = MutableLiveData()
    private var items: List<Item>? = null
    val tableData: LiveData<List<Row>> =
            Transformations.map(Transformations.switchMap(inputData) {
                repository.getItemsData(it[0].templateId, it.map { it.id!! }.toTypedArray())
            }) { transformData(it) }

    fun setArgs(itemsArg: List<Item>) {
        if (items == null) { //set args only once
            items = itemsArg
            inputData.value = items
        }
    }

    private fun transformData(data: List<Pair<Int, List<Pair<AttributeGroup, List<ItemDataWithAttr>>>>>): List<Row> {
        //todo optimize this algorithm
        val tableData: MutableList<Row> = mutableListOf()
        tableData.add(Row().apply {
            cells.add("")
            cells.addAll(items!!.map { it.name })
        })

        //list of columns
        val preparedData: MutableList<MutableList<String>> = mutableListOf()
        val column1 = mutableListOf<String>()
        data[0].second.forEach {
            //for each group
            column1.add(it.first.name)
            column1.addAll(it.second.map { it.attribute.name })
        }
        preparedData.add(column1)

        items!!.forEach { item ->
            val column = mutableListOf<String>()
            data.find { it.first == item.id }!!.second.forEach {
                column.add("")
                column.addAll(it.second.map { it.data.answer })
            }
            preparedData.add(column)
        }

        //transpose matrix
        for (i in 0 until data[0].second.size)
            data[0].second.forEach {
                val row = Row()
                for (j in 0 until preparedData.size) {
                    row.cells.add(preparedData[j][i])
                }
                tableData.add(row)
            }

        return tableData
    }

    class Row(val cells: MutableList<String> = mutableListOf())
}