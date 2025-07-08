package xstitchcatwalk.canvassize.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "app_settings")

    private object PreferencesKeys {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
    }

    val themeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] ?: false
        }

    suspend fun toggleTheme() {
        context.dataStore.edit { preferences ->
            val current = preferences[PreferencesKeys.DARK_THEME] ?: false
            preferences[PreferencesKeys.DARK_THEME] = !current
        }
    }
}