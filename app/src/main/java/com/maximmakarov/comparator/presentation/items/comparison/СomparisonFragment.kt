package com.maximmakarov.comparator.presentation.items.comparison

import android.view.LayoutInflater
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.data.model.Items
import com.maximmakarov.comparator.domain.model.Row
import kotlinx.android.synthetic.main.comparison_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.maximmakarov.comparator.core.extensions.getColorCompat

class ComparisonFragment : BaseFragment() {

    override val layoutId = R.layout.comparison_fragment

    private val viewModel: ComparisonViewModel by viewModel()

    override fun initViewModel() {
        val items = ComparisonFragmentArgs.fromBundle(arguments).items
        viewModel.setArgs((items as Items).items)
    }

    override fun initView() {
        setTitle(R.string.comparison_title)
    }

    override fun subscribeUi() {
        viewModel.tableData.observe(this, Observer {
            fillTable(it)
        })
    }

    private fun fillTable(data: List<Row>) {
        data.forEachIndexed { i, rowData ->
            val row = TableRow(context)
            rowData.cells.forEachIndexed { j, cellData ->
                val view = LayoutInflater.from(context).inflate(if (cellData.isGroup) R.layout.table_row_group else R.layout.table_cell, row, false)
                val textView = view.findViewById<TextView>(R.id.text)
                textView.text = cellData.text

                if (cellData.isGroup) {
                    view.layoutParams = (view.layoutParams as TableRow.LayoutParams).apply { span = data[0].cells.size }
                }

                view.setBackgroundColor(view.context.getColorCompat(
                        when (cellData.score) {
                            1 -> R.color.red_100
                            2 -> R.color.deep_orange_50
                            4 -> R.color.light_green_50
                            5 -> R.color.green_100
                            else -> if (i == 0) R.color.grey_200 else R.color.grey_white_1000
                        }))

                row.addView(view)
            }
            table.addView(row)
        }
    }
}