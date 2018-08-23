package com.maximmakarov.comparator.template.detail

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.ext.inputDialog
import com.maximmakarov.comparator.core.ext.showKeyboard
import com.maximmakarov.comparator.core.ext.visibleOrGone
import com.maximmakarov.comparator.data.model.Template
import kotlinx.android.synthetic.main.template_detail_fragment.*
import kotlinx.coroutines.experimental.launch


class TemplateDetailFragment : BaseFragment() {

    override val layoutId = R.layout.template_detail_fragment

    private lateinit var viewModel: TemplateDetailViewModel

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(TemplateDetailViewModel::class.java)

        viewModel.setArgs(TemplateDetailFragmentArgs.fromBundle(arguments).template as Template)
    }

    override fun initView() {
        val template = TemplateDetailFragmentArgs.fromBundle(arguments).template as Template
        if (template.name.isBlank()) {
            setTitle(R.string.template_new)
            showSetNameDialog()
        } else {
            setTitle(template.name)
        }
        setHasOptionsMenu(true)

        arrayOf(attributesTitle, attributes).visibleOrGone(template.id == null)
    }

    private fun showSetNameDialog() {
        activity?.inputDialog(R.string.template_edit_name, getTitle(), R.string.template_new,
                R.string.apply, { d, name -> setTitle(name); d.dismiss() },
                R.string.cancel, { it.dismiss() }
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
                    viewModel.saveChanged(getTitle(), attributes.text.toString())
                }
                findNavController(view!!).popBackStack()
                return true
            }
            R.id.action_edit -> {
                showSetNameDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun subscribeUi() {
        viewModel.templateData.observe(this, Observer {
            //not yet
        })
    }
}