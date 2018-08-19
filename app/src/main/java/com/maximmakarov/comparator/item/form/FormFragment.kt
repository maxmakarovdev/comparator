package com.maximmakarov.comparator.item.form

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.data.model.AttributeGroup
import com.maximmakarov.comparator.data.repository.ItemDataWithAttr
import kotlinx.android.synthetic.main.form_fragment.*

class FormFragment : BaseFragment() {

    override val layoutId = R.layout.form_fragment

    private lateinit var viewModel: FormViewModel

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)

        val templateId = FormFragmentArgs.fromBundle(arguments).templateId
        val itemId = FormFragmentArgs.fromBundle(arguments).itemId
        viewModel.setArgs(templateId, itemId)
    }

    override fun initView() {
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(tabSelectedListener)
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
