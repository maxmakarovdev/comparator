package com.maximmakarov.comparator.presentation.templates.detail

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.maximmakarov.comparator.R
import com.maximmakarov.comparator.core.BaseFragment
import com.maximmakarov.comparator.core.extensions.inputDialog
import com.maximmakarov.comparator.core.extensions.showKeyboard
import com.maximmakarov.comparator.core.extensions.visibleOrGone
import com.maximmakarov.comparator.data.model.Template
import kotlinx.android.synthetic.main.template_detail_fragment.*
import kotlinx.coroutines.experimental.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class TemplateDetailFragment : BaseFragment() {

    override val layoutId = R.layout.template_detail_fragment

    private val viewModel: TemplateDetailViewModel by viewModel()

    override fun initViewModel() {
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

        attributes.visibleOrGone(template.id == null)
    }

    private fun showSetNameDialog() {
        activity?.inputDialog(R.string.template_edit_name, getTitle(), R.string.template_edit_name_hint,
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
                    viewModel.saveChanges(getTitle(), attributes.text.toString())
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