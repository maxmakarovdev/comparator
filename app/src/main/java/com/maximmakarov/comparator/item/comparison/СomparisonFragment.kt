package com.maximmakarov.comparator.item.comparison

import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
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
        data.forEach {
            val row = TableRow(activity)
            it.cells.forEach {
                row.addView(TextView(activity).apply { setText(it) })
            }
            table.addView(row)
        }
    }
}