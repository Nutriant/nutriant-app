package com.dicoding.nutrient.ui.fragments.dashboard

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.nutrient.R
import com.dicoding.nutrient.databinding.ActivityProfileFragmentBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogBinding
import com.dicoding.nutrient.ui.activities.PersonalDataActivity
import com.google.android.material.button.MaterialButton

class ProfileFragment : Fragment() {
    private var _binding: ActivityProfileFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutSignOut.setOnClickListener {
            showDialog()
        }

        binding.layoutPersonalData.setOnClickListener{
            startActivity(Intent(requireContext(), PersonalDataActivity::class.java))
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        val dialogBinding = CustomPopupDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.show()

        dialogBinding.btDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btDialogLogout.setOnClickListener {
            // Code for logout
            dialog.dismiss()
        }
    }
}
