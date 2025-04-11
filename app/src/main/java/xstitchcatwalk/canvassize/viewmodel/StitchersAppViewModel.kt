package xstitchcatwalk.canvassize.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class StitchersAppViewModel : ViewModel() {

    val canvasCounts = listOf(14, 16, 18)

    private val _width = MutableStateFlow<String>("")
    val widthInStitches: StateFlow<String> = _width

    private val _height = MutableStateFlow<String>("")
    val heightInStitches: StateFlow<String> = _height

    private val _result = MutableStateFlow<Map<Int, Pair<Float, Float>>?>(null)
    val result: StateFlow<Map<Int, Pair<Float, Float>>?> = _result

    fun updateWidth(value: String) {
        _width.value = value
    }

    fun updateHeight(value: String) {
        _height.value = value
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
}