package com.violadin.debtorpit.ui.statistics

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.violadin.debtorpit.R
import com.violadin.debtorpit.databinding.StatisticsFragmentBinding
import com.violadin.debtorpit.navigation.NavigationManager
import com.violadin.debtorpit.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.roundToInt

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    @Inject
    lateinit var navigationManager: NavigationManager
    private lateinit var binding: StatisticsFragmentBinding
    private val viewModel: StatisticsFragmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).changeHeader(R.string.statistics)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            changeStateOfDiagrams(statsVariantCircle, statsVariantLinear)

            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                viewModel.totalDebts.collect { pair ->
                    pair?.let {
                        val totalSum = pair.first + pair.second
                        val percent = (max(pair.first, pair.second) / totalSum * 100).roundToInt()

                        circleDiagramView.setInternalArcProportion((pair.first / totalSum).toFloat())
                        circleDiagramView.setExternalArcProportion((pair.second / totalSum).toFloat())
                        circleDiagramView.setText("$percent%")

                        linearDiagramView.setValues(pair.second.toFloat(), pair.first.toFloat())

                        debtForMeDiagram.setText(pair.second.toString())
                        debtForMeDiagram.setArcProportion((pair.second / totalSum).toFloat())
                        myDebtDiagram.setText(pair.first.toString())
                        myDebtDiagram.setArcProportion((pair.first / totalSum).toFloat())
                    }
                }
            }

            statsVariantCircle.setOnClickListener {
                changeStateOfDiagrams(statsVariantCircle, statsVariantLinear)
            }

            statsVariantLinear.setOnClickListener {
                changeStateOfDiagrams(statsVariantLinear, statsVariantCircle)

            }
        }
    }

    private fun changeStateOfDiagrams(clickedView: ImageView, oldView: ImageView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            clickedView.drawable.colorFilter =
                BlendModeColorFilter(requireContext().getColor(R.color.base_UI), BlendMode.SRC_ATOP)
            oldView.drawable.colorFilter = BlendModeColorFilter(
                requireContext().getColor(R.color.base_text),
                BlendMode.SRC_ATOP
            )
        } else {
            clickedView.setColorFilter(R.color.base_UI, PorterDuff.Mode.SRC_ATOP)
            oldView.setColorFilter(R.color.base_text, PorterDuff.Mode.SRC_ATOP)
        }

        with(binding) {
            when (clickedView.id) {
                statsVariantCircle.id -> {
                    linearDiagramView.visibility = View.INVISIBLE
                    circleDiagramView.visibility = View.VISIBLE
                }
                statsVariantLinear.id -> {
                    circleDiagramView.visibility = View.INVISIBLE
                    linearDiagramView.visibility = View.VISIBLE
                }
            }
        }
    }
}