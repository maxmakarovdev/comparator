package com.maximmakarov.comparator.item.form

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import kotlinx.android.synthetic.main.page_fragment.*


class PageFragment : BaseFragment() {
    companion object {
        private const val BUNDLE_GROUP_ID = "BUNDLE_GROUP_ID"

        fun newInstance(groupId: Int) =
                PageFragment().apply {
                    arguments = Bundle().apply {
                        putInt(BUNDLE_GROUP_ID, groupId)
                    }
                }
    }

    override val layoutId = R.layout.page_fragment

    private lateinit var viewModel: FormViewModel
    private lateinit var adapter: PageAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(parentFragment!!).get(FormViewModel::class.java) //using parent (FormFragment) scope
    }

    override fun initView() {
        adapter = PageAdapter()
        attributes.adapter = adapter
        attributes.itemAnimator = DefaultItemAnimator()
        attributes.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        attributes.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
    }

    override fun subscribeUi() {
        val groupId = arguments?.getInt(BUNDLE_GROUP_ID)
        if (groupId != null) {
            viewModel.itemData.observe(this, Observer { data ->
                adapter.submitList(data.find { it.first.id == groupId }?.second)
            })
        }
    }
}