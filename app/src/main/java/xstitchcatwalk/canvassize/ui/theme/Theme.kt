package xstitchcatwalk.canvassize.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primaryOldGoldDark,
    secondary = secondaryBeigeDark,
    tertiary = tetriaryGreenDark,
    primaryContainer = primaryContainerGoldDark,
    secondaryContainer = secondaryContainerBeigeDark,
    tertiaryContainer = tetriaryContainerGreenDark,
    onPrimary = onPrimaryOldGoldDark,
    onPrimaryContainer = onPrimaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    onTertiaryContainer = onTetriaryContainerDark,
    surface = Color(0xFF18120b),
    onSurface = onSurfaceDark,
    outline = outlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = primaryOldGoldLight,
    secondary = secondaryBeigeLight,
    tertiary = tetriaryGreenLight,
    primaryContainer = primaryContainerGoldLight,
    secondaryContainer = secondaryContainerBeigeLight,
    tertiaryContainer = tetriaryContainerGreenLight,
    onPrimary = onPrimaryOldGoldLight,
    onPrimaryContainer = onPrimaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    onTertiaryContainer = onTetriaryContainerLight,
    surface = Color(0xFFfff8f4),
    onSurface = onSurfaceLight,
    outline = outlineLight
)

@Composable
fun CanvasSizeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}