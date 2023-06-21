package com.example.prototype_footprinthero.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prototype_footprinthero.model.MainViewModel

class FragmentManager {
    fun getFragments(viewModel: MainViewModel): List<Fragment> {
        return listOf(
            DayViewFragment(),
            //weekdayOverviewFragment(),
            //weeklyOverviewFragment()
        )
    }



    /*private fun weekdayOverviewFragment(): Fragment {
        // Logik für WeekdayOverviewFragment hier
    }

    private fun weeklyOverviewFragment(): Fragment {
        // Logik für WeeklyOverviewFragment hier
    }

     */
}

class DayViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val viewModel: MainViewModel = viewModel()
                DayView(viewModel.co2DataList)
            }
        }
    }
}

