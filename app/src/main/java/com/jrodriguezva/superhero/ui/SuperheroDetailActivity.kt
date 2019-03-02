package com.jrodriguezva.superhero.ui

import android.content.Context
import android.content.Intent
import com.jrodriguezva.superhero.R
import com.jrodriguezva.superhero.ui.base.BaseFragmentActivity
import com.jrodriguezva.superhero.ui.superhero.SuperheroDetailFragment


class SuperheroDetailActivity : BaseFragmentActivity() {
    companion object {
        private const val INTENT_EXTRA_ID_SUPERHERO = "idSuperhero"

        fun callingIntent(context: Context, superheroId: Long): Intent {
            val intent = Intent(context, SuperheroDetailActivity::class.java)
            intent.putExtra(INTENT_EXTRA_ID_SUPERHERO, superheroId)
            return intent
        }
    }

    override fun fragment() = SuperheroDetailFragment.newInstance(intent.getLongExtra(INTENT_EXTRA_ID_SUPERHERO, -1))

    override fun getLayout() = R.layout.main_activity

}