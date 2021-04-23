package com.baykris.tr_crypto.ui.home.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baykris.tr_crypto.core.common.BaseViewModel
import com.baykris.tr_crypto.data.repository.settings.SettingsRepository
import javax.inject.Inject

class WalletViewModel @ViewModelInject constructor(private val repository: SettingsRepository) : BaseViewModel() {

    private val _isDarkMode = MutableLiveData(repository.isDarkModeEnabled())
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    fun onThemeChanged(isDarkMode: Boolean) {
        repository.setThemeMode(isDarkMode)
        this@WalletViewModel._isDarkMode.value = isDarkMode
    }
}