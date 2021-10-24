package com.amosh.vestiaireweatherapp.ui.mainScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.amosh.vestiaireweatherapp.R
import com.amosh.vestiaireweatherapp.databinding.FragmentMainScreenBinding
import com.amosh.vestiaireweatherapp.models.appModels.AppMessage
import com.amosh.vestiaireweatherapp.models.appModels.AppResponseState
import com.amosh.vestiaireweatherapp.models.response.ForecastData
import com.amosh.vestiaireweatherapp.ui.BaseFragment
import com.amosh.vestiaireweatherapp.ui.ForecastViewModel
import com.amosh.vestiaireweatherapp.utils.Constants
import com.amosh.vestiaireweatherapp.utils.setIsVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainScreenFragment : BaseFragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForecastViewModel by activityViewModels()
    private val mAdapter by lazy {
        ForecastAdapter(object : ForecastAdapter.ActionsListener {
            override fun onItemClicked(item: ForecastData) {
                onItemDetailsClicked(item)
            }
        })
    }

    override fun getLayoutRoot(inflater: LayoutInflater): View {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onBindingDestroy() {
        _binding = null
    }

    override fun afterCreation(bundle: Bundle?) {
        initRecyclerView()
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getForecasts()
        }
        initObservables()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getForecasts()
    }

    private fun initRecyclerView() {
        with(binding) {
            rvForecast.apply {
                adapter = mAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            mAdapter.submitList(mutableListOf())
        }
    }

    private fun initObservables() {
        with(viewModel) {
            forecast.observe(viewLifecycleOwner, {
                when (it) {
                    is AppResponseState.Error -> {
                        showLoading(false)
                        showMessage(
                            AppMessage(
                                AppMessage.FAILURE,
                                it.message
                            )
                        )
                    }
                    is AppResponseState.Loading -> {
                        showLoading(true)
                    }
                    is AppResponseState.Success -> {
                        showOfflineBanner(false)
                        showLoading(false)
                        mAdapter.submitList(it.data?.list ?: mutableListOf())
                        binding.swipeContainer.isRefreshing = false
                        setData(it.data?.list?.get(0))
                    }
                    is AppResponseState.CachedData -> {
                        showOfflineBanner(true)
                        showLoading(false)
                        mAdapter.submitList(it.data?.list ?: mutableListOf())
                        binding.swipeContainer.isRefreshing = false
                        setData(it.data?.list?.get(0))
                    }
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(data: ForecastData?) {
        with(binding) {
            tvDegree.text = "${data?.temp?.avgTemp}°"
            tvStatus.text = data?.weather?.get(0)?.description
            tvHigherLower.text =
                "${data?.temp?.max?.roundToInt()}°/${data?.temp?.min?.roundToInt()}°"
        }
    }


    private fun showOfflineBanner(isCachedData: Boolean) {
        binding.tvCacheDisclaimer.setIsVisible(isCachedData)
    }

    private fun onItemDetailsClicked(item: ForecastData) {
        findNavController().navigate(
            R.id.action_mainScreenFragment_to_detailsScreenFragment,
            bundleOf(
                Constants.DATA to item
            ),
            null,
            FragmentNavigatorExtras(
                binding.tvDegree to "tvDegree",
                binding.tvStatus to "tvStatus",
                binding.tvHigherLower to "tvHigherLower"
            )
        )
    }

    private fun showLoading(showLoading: Boolean) {
        binding.progressOverlay.root.setIsVisible(showLoading)
    }
}