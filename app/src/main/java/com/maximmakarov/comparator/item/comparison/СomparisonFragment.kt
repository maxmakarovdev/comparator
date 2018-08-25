package com.maximmakarov.comparator.item.comparison

import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.ext.getColorCompat
import com.maximmakarov.comparator.data.model.Items
import kotlinx.android.synthetic.main.comparison_fragment.*


class ComparisonFragment : BaseFragment() {

    override val layoutId = R.layout.comparison_fragment

    private lateinit var viewModel: ComparisonViewModel

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ComparisonViewModel::class.java)

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

    private fun fillTable(data: List<ComparisonViewModel.Row>) {
        val rowParams = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT)
        val padding = resources.getDimensionPixelSize(R.dimen.padding_small)
        data.forEach {
            val row = TableRow(activity)
            it.cells.forEach {
                val view = TextView(activity).apply { text = it }
                view.layoutParams = rowParams
                view.setPadding(padding, padding, padding, padding)
                view.setBackgroundColor(view.context.getColorCompat(R.color.grey_white_1000))
                row.addView(view)
            }
            table.addView(row)
        }
    }
}