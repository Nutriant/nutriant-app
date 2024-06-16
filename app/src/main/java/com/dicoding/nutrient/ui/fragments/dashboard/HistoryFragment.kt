package com.dicoding.nutrient.ui.fragments.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.data.model.response.foods.DataFoods
import com.dicoding.nutrient.data.model.response.foods.GetFoodResponse
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
    }

    private fun showHistoryLabel(historyFoods: ArrayList<DataFoods>){
        val rvHistory = binding.rvHistory
        rvHistory.layoutManager = LinearLayoutManager(requireContext())
        rvHistory.adapter = HistoryLabelListAdapter(historyFoods)
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
                        binding.apply {
                            loadingHistory.visibility = View.VISIBLE
                            rvHistory.visibility = View.GONE
                        }
                    }
                    is Result.Success -> {
                        binding.apply {
                            loadingHistory.visibility = View.GONE
                            rvHistory.visibility = View.VISIBLE

                            showHistoryLabel(result.data.data)
                        }
                    }
                    is Result.ServerError -> {
                        Toast.makeText(requireContext(), result.serverError, Toast.LENGTH_LONG)
                            .show()
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