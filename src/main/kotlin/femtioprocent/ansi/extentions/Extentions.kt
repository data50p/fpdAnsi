package femtioprocent.ansi.extentions

import femtioprocent.ansi.LegacyColor
import femtioprocent.ansi.Color5

fun String.pR(w: Int = 1) = "%-${w}s".format(this)
fun String.pL(w: Int = 1) = "%${w}s".format(this)
fun String.pC(w: Int = 1) = if (w <= length) this else pL(length + (w - length) / 2).pR(w)

fun Boolean.ansiColor() = if (this) LegacyColor.bold(LegacyColor.Code.GREEN, "T") else LegacyColor.bold(LegacyColor.Code.RED, "f")
fun String.ansiColor(cc: LegacyColor.Code) = LegacyColor.bold(cc, this)
fun String.ansiBgColor(cc: LegacyColor.Code) = LegacyColor.normal(cc, this)
// fun String.ansiColor(cc: Color.LegacyColor) = Color.fg(cc, this)
fun String.ansiColor(cc: Color5.Code, value: Int) = Color5.color5Bg(cc, value, this)
