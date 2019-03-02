package com.jrodriguezva.superhero.ui.base

import android.os.Bundle
import com.jrodiriguezva.rsskotlin.presentation.base.BaseFragment
import com.jrodriguezva.superhero.R
import com.jrodriguezva.superhero.utils.extension.inTransaction
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.toolbar.*


/**
 * Base Fragment Activity class
 *
 * @see android.support.v7.app.AppCompatActivity
 */
abstract class BaseFragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        setSupportActionBar(toolbar)
        addFragment(savedInstanceState)
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(R.id.container) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(R.id.container, fragment())
        }

    abstract fun fragment(): BaseFragment

    abstract fun getLayout(): Int
}