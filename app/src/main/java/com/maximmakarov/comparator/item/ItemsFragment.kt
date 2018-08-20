package com.maximmakarov.comparator.item

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.ext.onClick
import kotlinx.android.synthetic.main.items_fragment.*


class ItemsFragment : BaseFragment() {

    override val layoutId = R.layout.items_fragment

    private lateinit var viewModel: ItemsViewModel
    private lateinit var adapter: ItemAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ItemsViewModel::class.java)

        val templateId = ItemsFragmentArgs.fromBundle(arguments).templateId
        viewModel.setArgs(templateId)
    }

    override fun initView() {
        setTitle(ItemsFragmentArgs.fromBundle(arguments).templateName)

        adapter = ItemAdapter()
        items.adapter = adapter
        items.itemAnimator = DefaultItemAnimator()
        items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        add.onClick {
            val action = ItemsFragmentDirections.actionAddOrViewItem()
            action.setTemplateId(ItemsFragmentArgs.fromBundle(arguments).templateId)
            findNavController(add).navigate(action)
        }
    }

    override fun subscribeUi() {
        viewModel.itemsData.observe(this, Observer { adapter.submitList(it) })
    }
}
