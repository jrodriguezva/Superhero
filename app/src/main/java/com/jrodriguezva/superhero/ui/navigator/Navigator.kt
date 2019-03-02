package com.jrodriguezva.superhero.ui.navigator

import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.jrodriguezva.superhero.ui.SuperheroDetailActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor() {

    fun showSuperheroDetails(activity: FragmentActivity, superheroId: Long, navigationExtras: Extras) {
        val intent = SuperheroDetailActivity.callingIntent(activity, superheroId)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())
    }

    class Extras(val transitionSharedElement: View)
}