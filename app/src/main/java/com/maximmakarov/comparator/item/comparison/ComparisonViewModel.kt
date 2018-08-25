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
            cells.add("")
            cells.addAll(items!!.map { it.name })
        })

        val firstItemGroups = data[0].second
        var row: Row
        var attrs: List<ItemDataWithAttr>
        for (groupIndex in 0 until firstItemGroups.size) {

            tableData.add(Row(mutableListOf(firstItemGroups[groupIndex].first.name)))

            attrs = firstItemGroups[groupIndex].second
            for (attrIndex in 0 until attrs.size) {
                row = Row(mutableListOf(attrs[attrIndex].attribute.name))

                for (i in 0 until data.size) {
                    row.cells.add(data[i].second[groupIndex].second[attrIndex].data.answer)
                }

                tableData.add(row)
            }
        }
        return tableData
    }

    class Row(val cells: MutableList<String> = mutableListOf())
}