package com.maximmakarov.comparator.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        subscribeUi()
    }

    open fun initViewModel() {}

    open fun initView() {}

    open fun subscribeUi() {}

    fun setTitle(@StringRes title: Int) {
        setTitle(getString(title))
    }

    fun setTitle(title: String) {
        (activity as AppCompatActivity?)?.supportActionBar?.title = title
    }

    fun getTitle(): String {
        return (activity as AppCompatActivity?)?.supportActionBar?.title?.toString() ?: ""
    }
}