package com.m3sv.dotttask.feature.details

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.details_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ((requireActivity().applicationContext) as DetailsInjector).inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get<DetailsViewModelImpl>()
        viewModel.id = requireNotNull(requireNotNull(arguments).getString(VENUE_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            v.setPadding(0, insets.systemWindowInsetTop, 0, insets.systemWindowInsetBottom)
            insets
        }
        val recyclerView = view as RecyclerView
        val detailsAdapter = DetailsAdapter()
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = detailsAdapter
        }

        viewModel.details.observe(viewLifecycleOwner, Observer { details ->
            detailsAdapter.submitList(details)
        })
    }

    companion object {
        private const val VENUE_ID = "venue_id_key"

        fun arguments(venueId: String) = Bundle().apply {
            putString(VENUE_ID, venueId)
        }
    }
}
