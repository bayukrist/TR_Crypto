package com.baykris.tr_crypto.data.repository.settings

import com.baykris.tr_crypto.data.local.prefs.PreferenceStorage
import javax.inject.Inject

/**
 * [SettingsRepository] is to manage preference for dark mode option
 */
class AccountRepository @Inject constructor(private val preferenceStorage: PreferenceStorage) {

    fun isDarkModeEnabled(): Boolean {
        return preferenceStorage.isDarkMode
    }

    fun setThemeMode(isDarkMode: Boolean) {
        preferenceStorage.isDarkMode = isDarkMode
    }
}