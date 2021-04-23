package com.baykris.tr_crypto.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.baykris.tr_crypto.core.common.BaseViewModel
import com.baykris.tr_crypto.data.repository.settings.SettingsRepository
import javax.inject.Inject

class HomeActivityViewModel @ViewModelInject constructor(private val repository: SettingsRepository) : BaseViewModel() {

    fun isDarkModeOn () : Boolean{
        return repository.isDarkModeEnabled()
    }
}