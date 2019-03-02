package com.jrodriguezva.superhero.ui

import com.jrodriguezva.superhero.R
import com.jrodriguezva.superhero.ui.base.BaseFragmentActivity
import com.jrodriguezva.superhero.ui.superheroes.SuperheroesFragment


class MainActivity : BaseFragmentActivity() {

    override fun fragment() = SuperheroesFragment.newInstance()

    override fun getLayout() = R.layout.main_activity

}