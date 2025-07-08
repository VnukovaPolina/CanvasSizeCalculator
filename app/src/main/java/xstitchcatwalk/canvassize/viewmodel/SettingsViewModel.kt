package xstitchcatwalk.canvassize.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import xstitchcatwalk.canvassize.data.SettingsManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {
    val isDarkTheme: Flow<Boolean> = settingsManager.themeFlow

    fun toggleTheme() {
        viewModelScope.launch {
            settingsManager.toggleTheme()
        }
    }

    val notificationsEnabled: Flow<Boolean> = settingsManager.notificationsEnabledFlow

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            settingsManager.setNotificationsEnabled(enabled)
        }
    }
}