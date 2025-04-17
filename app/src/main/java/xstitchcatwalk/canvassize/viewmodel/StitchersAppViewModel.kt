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

    private val _result = MutableStateFlow<Map<Int, Pair<Float, Float>>?>(null)
    val result: StateFlow<Map<Int, Pair<Float, Float>>?> = _result

    private val _stitches = MutableStateFlow<String>("")
    val stitches: StateFlow<String> = _stitches

    private val _strands = MutableStateFlow<String>("")
    val strands: StateFlow<String> = _strands


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

    fun calculateCanvasSize() {
        val widthStitches = widthInStitches.value.toFloatOrNull() ?: 0f
        val heightStitches = heightInStitches.value.toFloatOrNull() ?: 0f

        _result.value = canvasCounts.associate { count ->
            val widthCm = (widthStitches * 2.54f) / count
            val heightCm = (heightStitches * 2.54f) / count
            count to (widthCm to heightCm)
        }
    }

    fun calculateThreadUsage(
        stitches: Int,
        fabricCount: Int,
        strands: Int,
        technique: Int
    ): Float {
        val usagePerCross = when(fabricCount) {
            14 -> 0.036f
            16 -> 0.032f
            18 -> 0.028f
            else -> 0.036f * (14f / fabricCount)
        }
        val techniqueFactor = when(technique) {
            R.string.halfcross_technique -> 0.75f
            R.string.backstitch_technique -> 1.5f
            R.string.cross_stitch_technique -> 1f
            else -> {TODO()}
        }
        return stitches * usagePerCross * (strands/2f) * techniqueFactor
    }
}