package com.maximmakarov.comparator.item

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.ext.onClick
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.Template
import kotlinx.android.synthetic.main.items_fragment.*


class ItemsFragment : BaseFragment() {

    override val layoutId = R.layout.items_fragment

    private lateinit var viewModel: ItemsViewModel
    private lateinit var adapter: ItemAdapter

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ItemsViewModel::class.java)

        viewModel.setArgs(ItemsFragmentArgs.fromBundle(arguments).template as Template)
    }

    override fun initView() {
        val template = ItemsFragmentArgs.fromBundle(arguments).template as Template
        setTitle(template.name)

        adapter = ItemAdapter()
        items.adapter = adapter
        items.itemAnimator = DefaultItemAnimator()
        items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        add.onClick {
            val action = ItemsFragmentDirections.actionAddOrViewItem(Item(null, template.id!!, "", null))
            findNavController(add).navigate(action)
        }
    }

    override fun subscribeUi() {
        viewModel.itemsData.observe(this, Observer { adapter.submitList(it) })
    }
}
