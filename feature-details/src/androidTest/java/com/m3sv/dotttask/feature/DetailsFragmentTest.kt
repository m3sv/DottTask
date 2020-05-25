package com.m3sv.dotttask.feature

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.m3sv.dotttask.feature.details.Detail
import com.m3sv.dotttask.feature.details.DetailsFragment
import com.m3sv.dotttask.feature.details.DetailsViewModel
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    private fun launchFragment() {
        val fragmentArgs = DetailsFragment.arguments("")

        val factory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return DetailsFragment().apply { viewModelFactory = DetailsFragmentFactory() }
            }
        }

        scenario = launchFragmentInContainer<DetailsFragment>(fragmentArgs, factory = factory)
    }

    @Test
    fun testDetailsScreen() {
        launchFragment()

        detailsRobot {
            hasNameDetail()
            hasAddressDetail()
            hasLatLngDetail()
        }
    }
}

private class DetailsFragmentFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DummyDetailsViewModel() as T
}

// For test purposes keep everything here
private const val NAME = "name"
private const val ADDRESS = "address"
private const val LAT_LNG = "latLng"

private class DummyDetailsViewModel : ViewModel(), DetailsViewModel {
    override var id: String = ""
    override val details: LiveData<List<Detail>> =
        MutableLiveData(
            listOf(
                Detail(NAME),
                Detail(ADDRESS),
                Detail(LAT_LNG)
            )
        )
}

private fun detailsRobot(block: DetailsRobot.() -> Unit) = DetailsRobot().apply(block)

private class DetailsRobot {
    fun hasNameDetail() {
        // TODO assert that RecyclerView has item with name
    }

    fun hasAddressDetail() {
        // TODO assert that RecyclerView has item with street
    }

    fun hasLatLngDetail() {
        // TODO assert that RecyclerView has item with latLng
    }
}
