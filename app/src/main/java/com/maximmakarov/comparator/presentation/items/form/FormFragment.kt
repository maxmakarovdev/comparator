package com.maximmakarov.comparator.presentation.items.form

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayout
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.extensions.inputDialog
import com.maximmakarov.comparator.core.extensions.showKeyboard
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.model.Item
import com.maximmakarov.comparator.domain.model.AttributeData
import kotlinx.android.synthetic.main.form_fragment.*
import kotlinx.coroutines.experimental.launch

class FormFragment : BaseFragment() {

    override val layoutId = R.layout.form_fragment

    private lateinit var viewModel: FormViewModel

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)

        viewModel.setArgs(FormFragmentArgs.fromBundle(arguments).item as Item)
    }

    override fun initView() {
        val item = FormFragmentArgs.fromBundle(arguments).item as Item
        if (item.name.isBlank()) {
            setTitle(R.string.item_new)
            showSetNameDialog()
        } else {
            setTitle(item.name)
        }
        setHasOptionsMenu(true)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(tabSelectedListener)
    }

    private fun showSetNameDialog() {
        activity?.inputDialog(R.string.item_edit_name, getTitle(), R.string.item_edit_name_hint,
                R.string.apply, { _, name -> setTitle(name) },
                R.string.cancel, {}
        )
        activity?.showKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_apply, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_apply -> {
                launch {
                    viewModel.saveChanges(getTitle())
                }
                Navigation.findNavController(view!!).popBackStack()
                return true
            }
            R.id.action_edit -> {
                showSetNameDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}
        override fun onTabUnselected(tab: TabLayout.Tab?) {}
        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position
        }
    }

    override fun subscribeUi() {
        viewModel.itemData.observe(this, Observer { data ->
            tabs.removeAllTabs()
            val adapter = PagerAdapter(childFragmentManager, data)
            for (i in 0 until data.size) tabs.addTab(tabs.newTab().setText(adapter.getPageTitle(i)))
            viewPager.adapter = adapter
        })
    }

    class PagerAdapter(fm: FragmentManager?, val data: List<Pair<AttributeGroup, List<AttributeData>>>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int) = PageFragment.newInstance(data[position].first.id!!)
        override fun getCount(): Int = data.size
        override fun getPageTitle(position: Int): CharSequence = data[position].first.name
    }
}
