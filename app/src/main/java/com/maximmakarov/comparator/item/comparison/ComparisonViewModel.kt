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
        val tableData = mutableListOf<Row>()

        tableData.add(Row().apply {
            add()
            items!!.forEach { add(text = it.name) }
        })

        val firstItemGroups = data[0].second
        var row: Row
        var attrs: List<ItemDataWithAttr>
        for (groupIndex in 0 until firstItemGroups.size) {

            tableData.add(Row().apply { add(text = firstItemGroups[groupIndex].first.name, isGroup = true) })

            attrs = firstItemGroups[groupIndex].second
            for (attrIndex in 0 until attrs.size) {
                row = Row().apply { add(text = attrs[attrIndex].attribute.name) }

                for (i in 0 until data.size) {
                    val d = data[i].second[groupIndex].second[attrIndex].data
                    row.add(text = d.answer, score = d.score ?: 0)
                }

                tableData.add(row)
            }
        }
        return tableData
    }

    class Row(val cells: MutableList<Cell> = mutableListOf()) {
        fun add(text: String = "", isGroup: Boolean = false, score: Int = 0) {
            cells.add(Cell(text, isGroup, score))
        }
    }

    class Cell(val text: String, val isGroup: Boolean, val score: Int)
}