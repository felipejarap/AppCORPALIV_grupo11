package com.example.appcorpaliv_grupo11.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * DEFINICIÓN DE COLORES DEL TEMA (PALETA DE COLORES MATERIAL 3)
 *
 * Contiene los valores hexadecimales codificados en objetos [Color] de Jetpack Compose.
 * Define las paletas de colores tanto para el Modo Claro como para el Modo Oscuro,
 * diseñadas bajo los estándares de accesibilidad visual WCAG AAA (alto contraste de contraste y luminancia).
 */

// =========================================================================
// PALETA MODO CLARO (ALTO CONTRASTE Y ACCESIBILIDAD WCAG AAA)
// =========================================================================

/** Color primario institucional (Azul Corpaliv) utilizado en botones principales, encabezados y barras de navegación. */
val PrimaryAzulCorpaliv = Color(0xFF005AC1)
/** Color del texto/iconos superpuestos sobre el color primario en modo claro. */
val OnPrimaryWhite = Color(0xFFFFFFFF)

/** Color secundario (Teal / Verde azulado) para elementos de énfasis secundario, chips y badges. */
val SecondaryTeal = Color(0xFF006A60)
/** Color del texto/iconos superpuestos sobre el color secundario. */
val OnSecondaryWhite = Color(0xFFFFFFFF)

/** Color terciario (Ámbar) para acentos cálidos, alertas suaves y destacados. */
val TertiaryAmbar = Color(0xFF7D5200)
/** Color del texto/iconos superpuestos sobre el color terciario. */
val OnTertiaryWhite = Color(0xFFFFFFFF)

/** Color para la representación de errores en campos de texto, avisos y botones destructivos. */
val ErrorCarmesi = Color(0xFFBA1A1A)
/** Color del texto/iconos sobre contenedores de error. */
val OnErrorWhite = Color(0xFFFFFFFF)

/** Color de fondo para tarjetas, contenedores e interfases principales en modo claro. */
val SurfaceGrisClaro = Color(0xFFF8F9FF)
/** Color del texto primario escrito sobre las superficies en modo claro. */
val OnSurfaceGrisOscuro = Color(0xFF191C20)

/** Color de variante para contenedores secundarios, bordes y separadores. */
val SurfaceVariantClaro = Color(0xFFE1E2EC)
/** Color del texto secundario o descriptivo sobre variantes de superficie en modo claro. */
val OnSurfaceVariantClaro = Color(0xFF44474F)

// =========================================================================
// PALETA MODO OSCURO (BAJA LUMINANCIA Y ALTO CONTRASTE RECEPTIVO)
// =========================================================================

/** Color primario adapado a modo oscuro (Azul pastel de alta visibilidad sobre fondos oscuros). */
val DarkPrimary = Color(0xFFAEC6FF)
/** Color del texto/iconos superpuestos sobre el primario oscuro. */
val DarkOnPrimary = Color(0xFF002E69)

/** Color secundario pastel (Teal suave accesible). */
val DarkSecondary = Color(0xFF80D5C4)
/** Color del texto sobre el secundario oscuro. */
val DarkOnSecondary = Color(0xFF003731)

/** Color terciario suave (Ámbar tenue). */
val DarkTertiary = Color(0xFFFFB85D)
/** Color del texto sobre el terciario oscuro. */
val DarkOnTertiary = Color(0xFF422B00)

/** Color de fondo general de la pantalla en modo oscuro (Negro estándar Material Design). */
val DarkBackground = Color(0xFF121212)
/** Color del texto principal sobre el fondo oscuro (Blanco suave para evitar fatiga visual). */
val DarkOnBackground = Color(0xFFE2E2E6)

/** Color de superficie para tarjetas y componentes elevados en modo oscuro. */
val DarkSurface = Color(0xFF1E1E1E)
/** Color del texto sobre las tarjetas de superficie oscuras. */
val DarkOnSurface = Color(0xFFE2E2E6)

/** Color de variante de superficie en modo oscuro para divisorios y tarjetas secundarias. */
val DarkSurfaceVariant = Color(0xFF2B2D33)
/** Color del texto secundario sobre variantes de superficie oscuras. */
val DarkOnSurfaceVariant = Color(0xFFC4C6D0)

/** Color para errores e indicaciones críticas en modo oscuro (Rojo salmón pastel). */
val DarkError = Color(0xFFFFB4AB)
/** Color del texto sobre tarjetas o contenedores de error en modo oscuro. */
val DarkOnError = Color(0xFF690005)