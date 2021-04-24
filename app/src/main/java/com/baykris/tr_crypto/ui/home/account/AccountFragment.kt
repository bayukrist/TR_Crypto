package com.baykris.tr_crypto.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.baykris.tr_crypto.R
//import com.baykris.tr_crypto.databinding.FragmentSettingsBinding
import com.baykris.tr_crypto.core.common.MainNavigationFragment
import com.baykris.tr_crypto.databinding.FragmentAccountBinding
import com.baykris.tr_crypto.databinding.FragmentWalletBinding
import com.baykris.tr_crypto.util.ThemeHelper
import com.baykris.tr_crypto.util.ThemeMode
import com.baykris.tr_crypto.util.extensions.doOnChange
import dagger.hilt.android.AndroidEntryPoint

import timber.log.Timber

@AndroidEntryPoint
class AccountFragment : MainNavigationFragment() {

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentAccountBinding
     

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?




    ): View? {

        binding = FragmentAccountBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@AccountFragment.viewModel
            }
        observeViewModel()
        return binding.root
    }



    override fun initializeViews() {

    }

    override fun observeViewModel() {
        viewModel.isDarkMode.doOnChange(this) {
            Timber.d("On Theme changed")
            ThemeHelper.applyTheme(if (it) ThemeMode.Dark else ThemeMode.Light)
        }
    }
}