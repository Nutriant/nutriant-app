package com.dicoding.nutrient.ui.fragments.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityHistoryFragmentBinding
import com.dicoding.nutrient.ui.adapters.HistoryLabelListAdapter
import com.dicoding.nutrient.utils.Dummy
import com.dicoding.nutrient.utils.DummyHistoryLabel

class HistoryFragment : Fragment() {

    private var _binding : ActivityHistoryFragmentBinding? = null
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
}