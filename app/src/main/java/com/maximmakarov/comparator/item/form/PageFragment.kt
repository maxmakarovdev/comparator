package com.maximmakarov.comparator.item.form

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.R
import kotlinx.android.synthetic.main.page_fragment.*


class PageFragment : BaseFragment() {
    companion object {
        private const val BUNDLE_TEMPLATE_ID = "BUNDLE_TEMPLATE_ID"
        private const val BUNDLE_ITEM_ID = "BUNDLE_ITEM_ID"
        private const val BUNDLE_GROUP_ID = "BUNDLE_GROUP_ID"

        fun newInstance(templateId: Int, itemId: Int, groupId: Int) =
                PageFragment().apply {
                    arguments = Bundle().apply {
                        putInt(BUNDLE_TEMPLATE_ID, templateId)
                        putInt(BUNDLE_ITEM_ID, itemId)
                        putInt(BUNDLE_GROUP_ID, groupId)
                    }
                }
    }

    override val layoutId = R.layout.page_fragment

    private lateinit var viewModel: FormViewModel
    private lateinit var adapter: PageAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)
    }

    override fun initView() {
        adapter = PageAdapter()
        attributes.adapter = adapter
        attributes.itemAnimator = DefaultItemAnimator()
        attributes.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        attributes.addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
    }

    override fun subscribeUi() {
        val templateId = arguments?.getInt(BUNDLE_TEMPLATE_ID)
        val itemId = arguments?.getInt(BUNDLE_ITEM_ID)
        val groupId = arguments?.getInt(BUNDLE_GROUP_ID)
        if (templateId != null && itemId != null && groupId != null) {
            viewModel.getItemData(templateId, itemId).observe(this, Observer { data ->
                adapter.submitList(data.find { it.first.id == groupId }?.second)
            })
        }
    }
}