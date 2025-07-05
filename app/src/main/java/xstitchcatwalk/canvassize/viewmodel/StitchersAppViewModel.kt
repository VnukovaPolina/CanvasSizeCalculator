package xstitchcatwalk.canvassize.viewmodel

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import xstitchcatwalk.canvassize.R
import xstitchcatwalk.canvassize.data.FabricCounts

open class StitchersAppViewModel(
    private val canvasCounts: List<Int> = FabricCounts.counts
) : ViewModel() {
    private val _width = MutableStateFlow<String>("")
    val widthInStitches: StateFlow<String> = _width

    private val _height = MutableStateFlow<String>("")
    val heightInStitches: StateFlow<String> = _height

    private val _resultCanvas = MutableStateFlow<Map<Int, Pair<Float, Float>>?>(null)
    val result: StateFlow<Map<Int, Pair<Float, Float>>?> = _resultCanvas

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

    fun calculateCanvasSize() {
        val widthStitches = widthInStitches.value.toFloatOrNull() ?: 0f
        val heightStitches = heightInStitches.value.toFloatOrNull() ?: 0f

        _resultCanvas.value = canvasCounts.associate { count ->
            val widthCm = (widthStitches * 2.54f) / count
            val heightCm = (heightStitches * 2.54f) / count
            count to (widthCm to heightCm)
        }
    }

    fun calculateThreadUsage() {
        val stitchesForCalculation = stitches.value.toFloatOrNull() ?: 0f
        val fabricCountForCalculation = fabricCount.value.toFloatOrNull() ?: 0f
        val strandsForCalculation = strands.value.toFloatOrNull() ?: 0f
        val usagePerCross = when(fabricCountForCalculation) {
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
}