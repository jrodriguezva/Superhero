package com.jrodriguezva.superhero.ui.superheroes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jrodiriguezva.rsskotlin.presentation.base.BaseFragment
import com.jrodriguezva.superhero.databinding.SuperheroesFragmentBinding
import com.jrodriguezva.superhero.ui.navigator.Navigator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SuperheroesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    private lateinit var viewModel: SuperheroesViewModel

    private val adapter = SuperheroesAdapter()

    companion object {
        fun newInstance() = SuperheroesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindings = SuperheroesFragmentBinding.inflate(inflater, container, false)
        bindings.setLifecycleOwner(this)
        bindings.viewModel = viewModel
        bindings.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        bindings.recyclerView.itemAnimator = DefaultItemAnimator()
        bindings.recyclerView.setHasFixedSize(true)
        bindings.recyclerView.adapter = adapter
        adapter.clickListener = { id, navigationExtras ->
            activity?.let { navigator.showSuperheroDetails(it, id, navigationExtras) }
        }
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.start()
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SuperheroesViewModel::class.java)
    }

}
