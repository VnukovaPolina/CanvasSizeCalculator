package xstitchcatwalk.canvassize.ui.theme


import android.R.id.primary
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import xstitchcatwalk.canvassize.R

val PlayfairDisplayFamily = FontFamily(
    Font(R.font.playfair_display_semibold, FontWeight.SemiBold),
    Font(R.font.playfair_display_bold, FontWeight.Bold),
    Font(R.font.playfair_display_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.playfair_display, FontWeight.Normal),
    Font(R.font.playfair_display_medium, FontWeight.Medium)
)

val playfairFamily = FontFamily(
    Font(R.font.playfair, FontWeight.Normal)
)

val interFamily = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val manropeFamily = FontFamily(
    Font(R.font.manrope_light, FontWeight.Light),
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = playfairFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = playfairFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = PlayfairDisplayFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = PlayfairDisplayFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.5.sp
    ),

    labelLarge = TextStyle(
        fontFamily = playfairFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    ),

    labelMedium = TextStyle(
        fontFamily = playfairFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)