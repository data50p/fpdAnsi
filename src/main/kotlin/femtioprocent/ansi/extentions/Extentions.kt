package femtioprocent.ansi.extentions

import femtioprocent.ansi.Ansi

class Extentions

fun Boolean.ansiColor(): String {
    return if (this) Ansi.fg(Ansi.LegacyColor.G, "T") else Ansi.fg(Ansi.LegacyColor.R, "f")
}

fun String.ansiColor(cc: Ansi.LegacyColor): String {
    return Ansi.fg(cc, this)
}

fun String.ansiBgColor(cc: Ansi.LegacyColor): String {
    return Ansi.fgBg5(cc, this)
}

fun String.ansiColor(cc: Ansi.Color): String {
    return Ansi.fg(cc, this)
}

fun String.ansiColor(cc: Ansi.Color5, value: Int): String {
    return Ansi.color5Bg(cc, value, this)
}
