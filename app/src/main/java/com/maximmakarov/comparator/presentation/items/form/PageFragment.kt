package com.maximmakarov.comparator.presentation.items.form

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.extensions.getDrawableCompat
import kotlinx.android.synthetic.main.page_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


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

    private val viewModel: FormViewModel by sharedViewModel(from = { parentFragment })
    private lateinit var adapter: PageAdapter

    override fun initView() {
        adapter = PageAdapter()
        attributes.adapter = adapter
        attributes.itemAnimator = DefaultItemAnimator()
        attributes.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        attributes.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL).apply {
            context?.getDrawableCompat(R.drawable.divider)?.let { setDrawable(it) }
        })
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