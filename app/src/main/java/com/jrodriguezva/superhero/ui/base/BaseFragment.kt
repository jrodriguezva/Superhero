package com.jrodiriguezva.rsskotlin.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jrodriguezva.superhero.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    fun hideToolbar() = (activity as? BaseActivity)?.hideToolbar()

    fun showToolbar() = (activity as? BaseActivity)?.showToolbar()

    fun showBackToolbar() {
        (activity as? BaseActivity)?.showBackToolbar()
        setHasOptionsMenu(true)
    }

    open fun onBackPressed() {}


}