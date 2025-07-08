package xstitchcatwalk.canvassize.viewmodel

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xstitchcatwalk.canvassize.R
import xstitchcatwalk.canvassize.data.SettingsManager
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class StitchersAppViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val aidaCounts: List<Int>,
    private val settingsManager: SettingsManager
) : ViewModel() {
    // Переменные для калькулятора канвы:
    private val _width = MutableStateFlow<String>("")
    val widthInStitches: StateFlow<String> = _width

    private val _height = MutableStateFlow<String>("")
    val heightInStitches: StateFlow<String> = _height

    private val _resultCanvas = MutableStateFlow<Map<Int, Pair<Float, Float>>?>(null)
    val result: StateFlow<Map<Int, Pair<Float, Float>>?> = _resultCanvas

    // Переменные для калькулятора расхода нитей:
    private val _stitches = MutableStateFlow<String>("")
    val stitches: StateFlow<String> = _stitches

    private val _strands = MutableStateFlow<String>("")
    val strands: StateFlow<String> = _strands

    private val _fabricCount = MutableStateFlow<String>("")
    val fabricCount: StateFlow<String> = _fabricCount

    private val _technique = MutableStateFlow<Int>(R.string.cross_stitch_technique)
    val technique: StateFlow<Int> = _technique

    private val _threadUsageResult = MutableStateFlow<Float?>(null)
    val threadUsageResult: StateFlow<Float?> = _threadUsageResult

    // Переменные для таймера вышивания:
    private val _TimerIsRunning = MutableStateFlow(false)
    val TimerIsRunning: StateFlow<Boolean> = _TimerIsRunning

    private val _elapsedTime = MutableStateFlow(0L) // в секундах
    val elapsedTime: StateFlow<Long> = _elapsedTime

    private val _showNotification = MutableStateFlow<Pair<Boolean, String>?>(null)
    val showNotification: StateFlow<Pair<Boolean, String>?> = _showNotification

    private var timerJob: Job? = null

    private var hourNotificationShown = false
    private var twoHoursNotificationShown = false

    private val hourMessage = context.getString(R.string.one_hour_stitching_notification)
    private val twoHoursMessage = context.getString(R.string.two_hours_stitching_notification)

    // Функции для обновления отображения параметров
    fun updateWidth(value: String) {
        _width.value = value
    }

    fun updateHeight(value: String) {
        _height.value = value
    }

    fun updateStitches(value: String) {
        _stitches.value = value
    }

    fun updateStrands(value: String) {
        _strands.value = value
    }

    fun updateFabricCount(value: String) {
        _fabricCount.value = value
    }

    fun updateTechnique(value: Int) {
        _technique.value = value
    }

    // Логика расчета размеров канвы БЕЗ припусков (полей)
    fun calculateCanvasSize() {
        val widthStitches = widthInStitches.value.toFloatOrNull() ?: 0f
        val heightStitches = heightInStitches.value.toFloatOrNull() ?: 0f

        _resultCanvas.value = aidaCounts.associate { count ->
            val widthCm = (widthStitches * 2.54f) / count
            val heightCm = (heightStitches * 2.54f) / count
            count to (widthCm to heightCm)
        }
    }

    // Логика расчета расхода нитей с запасом
    fun calculateThreadUsage() {
        val stitchesForCalculation = stitches.value.toFloatOrNull() ?: 0f
        val fabricCountForCalculation = fabricCount.value.toFloatOrNull() ?: 0f
        val strandsForCalculation = strands.value.toFloatOrNull() ?: 0f
        val usagePerCross = when(fabricCountForCalculation) {
            // Расчет с запасом. На 1 крестик на 14 каунте без запаса нужно примерно 2,7 см одной нити
            14f -> 0.036f
            16f -> 0.032f
            18f -> 0.028f
            else -> 0.036f * (14f / fabricCountForCalculation)
        }
        val techniqueFactor = when(technique.value) {
            R.string.halfcross_technique -> 0.75f
            R.string.backstitch_technique -> 1.5f
            R.string.cross_stitch_technique -> 1f
            else -> 1f
        }
        // Расчет проводится для стандартной пасмы в 8 нитей! Поэтому в конце делим расход на 8
        _threadUsageResult.value = (stitchesForCalculation * usagePerCross * (strandsForCalculation/2f) * techniqueFactor) / 8f
    }

    // Функции, описывающие работу ТАЙМЕРА вышивания
    fun toggleTimer() {
        _TimerIsRunning.value = !_TimerIsRunning.value
        if (_TimerIsRunning.value) {
            startTimer()
        } else {
            stopTimer()
        }
    }

    fun resetTimer() {
        stopTimer()
        _elapsedTime.value = 0L
        resetNotifications()
    }

    fun dismissNotification() {
        _showNotification.value = null
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (TimerIsRunning.value) {
                delay(1000L)
                _elapsedTime.value += 1
                checkForNotifications()
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        _TimerIsRunning.value = false
    }

/*    private fun checkForNotifications() {
        val minutes = elapsedTime.value / 60
        when {
            minutes >= 60L && !hourNotificationShown -> {
                _showNotification.value = true to hourMessage
                hourNotificationShown = true
            }
            minutes >= 120L && !twoHoursNotificationShown -> {
                _showNotification.value = true to twoHoursMessage
                twoHoursNotificationShown = true
            }
        }
    }*/

    fun resetNotifications() {
        hourNotificationShown = false
        twoHoursNotificationShown = false
        _showNotification.value = null
    }

    private fun checkForNotifications() {
        viewModelScope.launch {
            settingsManager.notificationsEnabledFlow.collect { enabled ->
                if (enabled) {
                    val minutes = elapsedTime.value / 60
                    when {
                        minutes >= 120L && !twoHoursNotificationShown -> {
                            _showNotification.value = true to twoHoursMessage
                            twoHoursNotificationShown = true
                        }
                        minutes >= 60L && !hourNotificationShown -> {
                            _showNotification.value = true to hourMessage
                            hourNotificationShown = true
                        }
                    }
                }
            }
        }
    }
}