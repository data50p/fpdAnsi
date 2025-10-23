package femtioprocent.ansi.extentions

import femtioprocent.ansi.Color1
import femtioprocent.ansi.Color5

fun String.pR(w: Int = 1) = "%-${w}s".format(this)
fun String.pL(w: Int = 1) = "%${w}s".format(this)
fun String.pC(w: Int = 1) = if (w <= length) this else pL(length + (w - length) / 2).pR(w)

fun Boolean.ansiColor() = if (this) Color1.bold(Color1.ColorCode.GREEN, "T") else Color1.bold(Color1.ColorCode.RED, "f")
fun String.ansiColor(cc: Color1.ColorCode) = Color1.bold(cc, this)
fun String.ansiBgColor(cc: Color1.ColorCode) = Color1.normal(cc, this)
// fun String.ansiColor(cc: Color.LegacyColor) = Color.fg(cc, this)
fun String.ansiColor(cc: Color5.Color5Code, value: Int) = Color5.color5Bg(cc, value, this)
