package com.jrodriguezva.superhero.ui.base

/**
 * AuA-Android.
 *
 * @author - JRodriguez
 * @since - 11/5/18
 */

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * Base Activity class
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    fun hideToolbar() {
        supportActionBar?.hide()
    }

    fun showToolbar() {
        supportActionBar?.show()
    }

    fun showBackToolbar() {
        showToolbar()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = fragmentInjector
}