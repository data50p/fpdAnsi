package femtioprocent.ansi.extentions

import femtioprocent.ansi.Ansi

fun String.pR(w: Int = 1) = "%-${w}s".format(this)
fun String.pL(w: Int = 1) = "%${w}s".format(this)
fun String.pC(w: Int = 1) = if (w <= length) this else pL(length + (w - length) / 2).pR(w)

fun Boolean.ansiColor() = if (this) Ansi.fg(Ansi.LegacyColor.G, "T") else Ansi.fg(Ansi.LegacyColor.R, "f")
fun String.ansiColor(cc: Ansi.LegacyColor) = Ansi.fg(cc, this)
fun String.ansiBgColor(cc: Ansi.LegacyColor) =Ansi.fgBg5(cc, this)
fun String.ansiColor(cc: Ansi.Color) = Ansi.fg(cc, this)
fun String.ansiColor(cc: Ansi.Color5, value: Int) = Ansi.color5Bg(cc, value, this)
