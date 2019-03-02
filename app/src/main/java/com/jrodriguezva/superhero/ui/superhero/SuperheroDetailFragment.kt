package com.jrodriguezva.superhero.ui.superhero

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jrodiriguezva.rsskotlin.presentation.base.BaseFragment
import com.jrodriguezva.superhero.databinding.SuperheroDetailFragmentBinding
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.ui.superheroes.SuperheroDetailViewModel
import com.jrodriguezva.superhero.utils.extension.loadUrlAndPostponeEnterTransition
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.superhero_detail_fragment.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class SuperheroDetailFragment : BaseFragment() {

    private lateinit var viewModel: SuperheroDetailViewModel

    @Inject
    lateinit var superheroDetailsAnimator: SuperheroDetailsAnimator

    companion object {
        private const val SUPERHERO_ID = "superheroId"

        fun newInstance(superheroId: Long): SuperheroDetailFragment {
            val superheroDetailFragment = SuperheroDetailFragment()
            val arguments = Bundle()
            arguments.putLong(SUPERHERO_ID, superheroId)
            superheroDetailFragment.arguments = arguments
            return superheroDetailFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindings = SuperheroDetailFragmentBinding.inflate(inflater, container, false)
        bindings.setLifecycleOwner(this)
        bindings.viewModel = viewModel
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.start((arguments?.getLong(SUPERHERO_ID) ?: -1))
        }
    }

    override fun onStart() {
        super.onStart()
        showBackToolbar()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SuperheroDetailViewModel::class.java)
        viewModel.superhero.observe(this, Observer { renderDetails(it) })
        activity?.let { superheroDetailsAnimator.postponeEnterTransition(it) }
    }

    override fun onBackPressed() {
        superheroDetailsAnimator.fadeInvisible(scrollView, clFeedDetails)
    }

    private fun renderDetails(superhero: Superhero?) {
        superhero?.let {
            activity?.let {
                superheroImage.loadUrlAndPostponeEnterTransition(superhero.photo, it)
                it.toolbar.title = superhero.name
            }
        }
        superheroDetailsAnimator.fadeVisible(scrollView, clFeedDetails)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
