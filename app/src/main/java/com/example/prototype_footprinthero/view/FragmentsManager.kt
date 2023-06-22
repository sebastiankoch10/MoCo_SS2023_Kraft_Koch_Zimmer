package com.example.prototype_footprinthero.view

/*
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

 */

