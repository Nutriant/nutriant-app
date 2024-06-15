package com.dicoding.nutrient.ui.fragments.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityHistoryFragmentBinding
import com.dicoding.nutrient.ui.adapters.HistoryLabelListAdapter
import com.dicoding.nutrient.ui.viewmodels.FoodViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory
import com.dicoding.nutrient.utils.Dummy
import com.dicoding.nutrient.utils.DummyHistoryLabel

class HistoryFragment : Fragment() {

    private var _binding : ActivityHistoryFragmentBinding? = null
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var foodViewModel: FoodViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityHistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        observeViewModel()
        showHistoryLabel()
    }

    private fun showHistoryLabel(){
        val rvHistory = binding.rvHistory
        rvHistory.layoutManager = LinearLayoutManager(requireContext())
        rvHistory.adapter = HistoryLabelListAdapter(DummyHistoryLabel.getHistoryLabel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel(){
        userPreferencesViewModel.getTokenValue().observe(viewLifecycleOwner){ token ->
            foodViewModel.getFoods(token)
            foodViewModel.resultGetFood.observe(viewLifecycleOwner){ result ->
                when (result){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {

                    }
                    is Result.ServerError -> {

                    }
                    else -> {}
                }
            }
        }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        userPreferencesViewModel = ViewModelProvider(requireActivity(), factory)[UserPreferencesViewModel::class.java]
        foodViewModel = ViewModelProvider(requireActivity(), factory)[FoodViewModel::class.java]
    }
}