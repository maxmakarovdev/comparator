package com.maximmakarov.comparator.item.form

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
import com.maximmakarov.comparator.core.ext.inputDialog
import com.maximmakarov.comparator.core.ext.showKeyboard
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.repository.ItemDataWithAttr
import kotlinx.android.synthetic.main.form_fragment.*
import kotlinx.coroutines.experimental.launch

class FormFragment : BaseFragment() {

    override val layoutId = R.layout.form_fragment

    private lateinit var viewModel: FormViewModel

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)

        val templateId = FormFragmentArgs.fromBundle(arguments).templateId
        val itemId = FormFragmentArgs.fromBundle(arguments).itemId
        viewModel.setArgs(templateId, if (itemId != 0) itemId else null)
    }

    override fun initView() {
        val itemName = FormFragmentArgs.fromBundle(arguments).itemName
        setTitle(if (itemName.isBlank()) getString(R.string.item_new) else itemName)
        setHasOptionsMenu(true)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(tabSelectedListener)
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
                activity?.inputDialog(R.string.item_edit_name, getTitle(), R.string.item_new,
                        R.string.apply, { d, name -> setTitle(name); d.dismiss() },
                        R.string.cancel, { it.dismiss() }
                )
                activity?.showKeyboard()
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
//            viewPager.offscreenPageLimit = data.size
            viewPager.adapter = adapter
        })
    }

    class PagerAdapter(fm: FragmentManager?, val data: List<Pair<AttributeGroup, List<ItemDataWithAttr>>>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int) = PageFragment.newInstance(data[position].first.id!!)
        override fun getCount(): Int = data.size
        override fun getPageTitle(position: Int): CharSequence = data[position].first.name
    }
}
