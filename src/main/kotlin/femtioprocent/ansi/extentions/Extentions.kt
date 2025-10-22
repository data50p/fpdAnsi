package femtioprocent.ansi.extentions

import femtioprocent.ansi.Color
import femtioprocent.ansi.Color5

fun String.pR(w: Int = 1) = "%-${w}s".format(this)
fun String.pL(w: Int = 1) = "%${w}s".format(this)
fun String.pC(w: Int = 1) = if (w <= length) this else pL(length + (w - length) / 2).pR(w)

fun Boolean.ansiColor() = if (this) Color.fg(Color.LegacyColor.G, "T") else Color.fg(Color.LegacyColor.R, "f")
fun String.ansiColor(cc: Color.LegacyColor) = Color.fg(cc, this)
fun String.ansiBgColor(cc: Color.LegacyColor) = Color.fgBg5(cc, this)
// fun String.ansiColor(cc: Color.LegacyColor) = Color.fg(cc, this)
fun String.ansiColor(cc: Color5.Color5, value: Int) = Color5.color5Bg(cc, value, this)
