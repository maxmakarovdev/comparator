package com.maximmakarov.comparator.item

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.ext.inputDialog
import com.maximmakarov.comparator.core.ext.onClick
import com.maximmakarov.comparator.core.ext.showKeyboard
import com.maximmakarov.comparator.core.ext.visibleOrGone
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.data.model.Items
import com.maximmakarov.comparator.data.model.Template
import kotlinx.android.synthetic.main.items_fragment.*
import kotlinx.coroutines.experimental.launch


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
        setHasOptionsMenu(true)

        adapter = ItemAdapter {
            compare.visibleOrGone(adapter.getSelectedItems().isNotEmpty())
        }
        items.adapter = adapter
        items.itemAnimator = DefaultItemAnimator()
        items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        add.onClick {
            val action = ItemsFragmentDirections.actionAddOrViewItem(Item(null, template.id!!, "", null))
            findNavController(add).navigate(action)
        }

        compare.onClick {
            val action = ItemsFragmentDirections.actionCompareItems(Items(adapter.getSelectedItems()))
            findNavController(compare).navigate(action)
        }
    }

    private fun showSetNameDialog() {
        activity?.inputDialog(R.string.template_edit_name, getTitle(), R.string.template_edit_name_hint,
                R.string.apply,
                { _, name ->
                    setTitle(name)
                    launch { viewModel.saveChanges(name) }
                },
                R.string.cancel, {}
        )
        activity?.showKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_edit -> {
                showSetNameDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun subscribeUi() {
        viewModel.itemsData.observe(this, Observer { adapter.submitList(it) })
    }
}
