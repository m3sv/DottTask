package com.m3sv.dotttask

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.m3sv.dotttask.feature.details.DetailsFragment
import com.m3sv.dotttask.feature.map.MapFragment

class AppFragmentFactory : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        when (className) {
            MapFragment::class.java.name -> MapFragment()
            DetailsFragment::class.java.name -> DetailsFragment()
            else -> super.instantiate(classLoader, className)
        }

}
