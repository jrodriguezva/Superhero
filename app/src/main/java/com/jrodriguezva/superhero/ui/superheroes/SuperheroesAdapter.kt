package com.jrodriguezva.superhero.ui.superheroes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.superhero.databinding.SuperheroItemBinding
import com.jrodriguezva.superhero.domain.model.Superhero
import com.jrodriguezva.superhero.ui.navigator.Navigator
import com.jrodriguezva.superhero.utils.BindableAdapter
import kotlinx.android.synthetic.main.superhero_detail_fragment.view.*
import kotlin.properties.Delegates

class SuperheroesAdapter : RecyclerView.Adapter<SuperheroesAdapter.ViewHolder>(), BindableAdapter<List<Superhero>> {
    override fun setItems(data: List<Superhero>) {
        collection = data
    }

    private var collection: List<Superhero> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (Long, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(SuperheroItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(viewHolder: SuperheroesAdapter.ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(private val superheroItemBinding: SuperheroItemBinding) :
        RecyclerView.ViewHolder(superheroItemBinding.root) {
        fun bind(superhero: Superhero, clickListener: (Long, Navigator.Extras) -> Unit) {
            superheroItemBinding.superhero = superhero
            superhero.id?.let { superheroId ->
                itemView.setOnClickListener {
                    clickListener(
                        superheroId,
                        Navigator.Extras(itemView.superheroImage)
                    )
                }
            }
            superheroItemBinding.executePendingBindings()

        }
    }
}