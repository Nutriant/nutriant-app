package com.dicoding.nutrient.ui.fragments.dashboard

import com.dicoding.nutrient.ui.activities.ChangeLanguageActivity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.nutrient.R
import com.dicoding.nutrient.data.Result
import com.dicoding.nutrient.databinding.ActivityProfileFragmentBinding
import com.dicoding.nutrient.databinding.CustomPopupDialogBinding
import com.dicoding.nutrient.ui.activities.ChangePasswordActivity
import com.dicoding.nutrient.ui.activities.DashboardWithBotNavActivity
import com.dicoding.nutrient.ui.activities.PersonalDataActivity
import com.dicoding.nutrient.ui.fragments.childfragment.BottomSheetAboutAppsFragment
import com.dicoding.nutrient.ui.viewmodels.LogoutViewModel
import com.dicoding.nutrient.ui.viewmodels.ProfileViewModel
import com.dicoding.nutrient.ui.viewmodels.UserPreferencesViewModel
import com.dicoding.nutrient.ui.viewmodels.ViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: ActivityProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var logoutViewModel: LogoutViewModel
    private lateinit var userPreferencesViewModel: UserPreferencesViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var loadingDialog: SweetAlertDialog

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

        initComponents()
        initViewModel()
        setupAction()
        setupComponent()
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
            userPreferencesViewModel.getTokenValue().observe(viewLifecycleOwner) { token ->
                val logoutObserver = object : Observer<Result<Int>> {
                    override fun onChanged(value: Result<Int>) {
                        when (value) {
                            is Result.Loading -> {
                                dialog.dismiss()
                                loadingDialog.show()
                            }

                            is Result.Success -> {
                                dialog.dismiss()
                                loadingDialog.dismiss()
                                requireActivity().finishAffinity()
                                logoutViewModel.logout(token).removeObserver(this)
                            }

                            is Result.Error -> {
                                dialog.dismiss()
                                loadingDialog.dismiss()
                                Toast.makeText(
                                    requireContext(),
                                    value.errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                                logoutViewModel.logout(token).removeObserver(this)
                            }

                            else -> {}
                        }
                    }
                }

                logoutViewModel.logout(token).observe(viewLifecycleOwner, logoutObserver)
            }
        }
    }

    private fun setupAction() {
        binding.layoutSignOut.setOnClickListener {
            showDialog()
        }

        binding.layoutPersonalData.setOnClickListener {
            startActivity(Intent(requireContext(), PersonalDataActivity::class.java))
        }

        binding.layoutChangePassword.setOnClickListener {
            startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
        }

        binding.layoutChangeLanguage.setOnClickListener {
            startActivity(Intent(requireContext(), ChangeLanguageActivity::class.java))
        }

        binding.layoutFnBHistory.setOnClickListener {
            (activity as DashboardWithBotNavActivity).replaceFragment(HistoryFragment())
        }

        binding.layoutAboutApps.setOnClickListener {
            val bottomSheetFragment = BottomSheetAboutAppsFragment()
            bottomSheetFragment.show(childFragmentManager, "AboutAppsBottomSheet")
        }
    }

    private fun setupComponent() {
        userPreferencesViewModel.getUsername().observe(viewLifecycleOwner) { username ->
            binding.tvUsername.text = username
        }

        userPreferencesViewModel.getTokenValue().observe(viewLifecycleOwner) { token ->
            profileViewModel.getMyProfile(token)
            profileViewModel.userData.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.apply {
                            layoutProfile.visibility = View.GONE
                            loadingProfile.visibility = View.VISIBLE
                        }
                    }

                    is Result.Success -> {
                        binding.apply {
                            layoutProfile.visibility = View.VISIBLE
                            loadingProfile.visibility = View.GONE
                        }
                        val getData = result.data.data
                        binding.tvHeightProfile.text =
                            getString(R.string.centi_meter, getData.height)
                        binding.tvWeightProfile.text = getString(R.string.kilo_gram, getData.weight)
                        binding.tvAgeProfile.text = getString(R.string.years_old, getData.age)
                        Glide.with(requireContext())
                            .load("http://${getData.image}")
                            .apply(
                                RequestOptions().placeholder(R.drawable.avatar_dummy).fitCenter()
                            )
                            .into(binding.imgProfile)
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

    private fun initComponents() {
        loadingDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        loadingDialog.apply {
            titleText = getString(R.string.loading)
            progressHelper.barColor = ContextCompat.getColor(requireContext(), R.color.greenApps)
            setCancelable(false)
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        logoutViewModel = ViewModelProvider(requireActivity(), factory)[LogoutViewModel::class.java]
        userPreferencesViewModel =
            ViewModelProvider(requireActivity(), factory)[UserPreferencesViewModel::class.java]
        profileViewModel =
            ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]
    }
}
