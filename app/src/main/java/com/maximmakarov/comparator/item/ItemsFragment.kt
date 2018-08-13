package com.maximmakarov.comparator.item

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximmakarov.comparator.BaseFragment
import com.maximmakarov.comparator.R
import kotlinx.android.synthetic.main.items_fragment.*


class ItemsFragment : BaseFragment() {

    override val layoutId = R.layout.items_fragment

    private lateinit var viewModel: ItemsViewModel
    private lateinit var adapter: ItemAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ItemsViewModel::class.java)
    }

    override fun initView() {
        adapter = ItemAdapter()
        items.adapter = adapter
        items.itemAnimator = DefaultItemAnimator()
        items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    override fun subscribeUi() {
        val templateId = ItemsFragmentArgs.fromBundle(arguments).templateId
        viewModel.getItems(templateId).observe(this, Observer { adapter.submitList(it) })
    }
}
