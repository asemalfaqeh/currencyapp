import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF006B58)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFF7BF8D8)
val md_theme_light_onPrimaryContainer = Color(0xFF002019)
val md_theme_light_secondary = Color(0xFF4B635B)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFCDE9DE)
val md_theme_light_onSecondaryContainer = Color(0xFF07201A)
val md_theme_light_tertiary = Color(0xFF426277)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFC6E7FF)
val md_theme_light_onTertiaryContainer = Color(0xFF001E2D)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFBFDFA)
val md_theme_light_onBackground = Color(0xFF191C1B)
val md_theme_light_surface = Color(0xFFFBFDFA)
val md_theme_light_onSurface = Color(0xFF191C1B)
val md_theme_light_surfaceVariant = Color(0xFFDBE5E0)
val md_theme_light_onSurfaceVariant = Color(0xFF3F4945)
val md_theme_light_outline = Color(0xFF6F7975)
val md_theme_light_inverseOnSurface = Color(0xFFEFF1EE)
val md_theme_light_inverseSurface = Color(0xFF2E3130)
val md_theme_light_inversePrimary = Color(0xFF5DDBBC)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF006B58)
val md_theme_light_outlineVariant = Color(0xFFBFC9C4)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFF5DDBBC)
val md_theme_dark_onPrimary = Color(0xFF00382D)
val md_theme_dark_primaryContainer = Color(0xFF005142)
val md_theme_dark_onPrimaryContainer = Color(0xFF7BF8D8)
val md_theme_dark_secondary = Color(0xFFB2CCC3)
val md_theme_dark_onSecondary = Color(0xFF1D352E)
val md_theme_dark_secondaryContainer = Color(0xFF334C44)
val md_theme_dark_onSecondaryContainer = Color(0xFFCDE9DE)
val md_theme_dark_tertiary = Color(0xFFA9CBE3)
val md_theme_dark_onTertiary = Color(0xFF0F3447)
val md_theme_dark_tertiaryContainer = Color(0xFF294A5E)
val md_theme_dark_onTertiaryContainer = Color(0xFFC6E7FF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF191C1B)
val md_theme_dark_onBackground = Color(0xFFE1E3E0)
val md_theme_dark_surface = Color(0xFF191C1B)
val md_theme_dark_onSurface = Color(0xFFE1E3E0)
val md_theme_dark_surfaceVariant = Color(0xFF3F4945)
val md_theme_dark_onSurfaceVariant = Color(0xFFBFC9C4)
val md_theme_dark_outline = Color(0xFF89938F)
val md_theme_dark_inverseOnSurface = Color(0xFF191C1B)
val md_theme_dark_inverseSurface = Color(0xFFE1E3E0)
val md_theme_dark_inversePrimary = Color(0xFF006B58)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFF5DDBBC)
val md_theme_dark_outlineVariant = Color(0xFF3F4945)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFF006B58)


val freshColor = Color(0xFF44FF78)
val staleColor = Color(0xFFFF9E44)

val primaryColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF86A8FC)
    else Color(0xFF283556)

val headerColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF0C0C0C)
    else Color(0xFF946bc6)

val surfaceColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF161616)
    else Color(0xFFFFFFFF)

val textColor
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFFFFFFFF)
    else Color(0xFF000000)