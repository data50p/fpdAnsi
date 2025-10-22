package femtioprocent.ansi

class LegacyColor {
    enum class LegacyColor(val cc: Int) {
	D(30),
	R(31),
	G(32),
	Y(33),
	B(34),
	M(35),
	C(36),
	W(37),
    }

    fun legacyColorCode(ch: Char): LegacyColor {
	val num = when (ch) {
	    'd'  -> LegacyColor.D
	    'r'  -> LegacyColor.R
	    'g'  -> LegacyColor.G
	    'y'  -> LegacyColor.Y
	    'b'  -> LegacyColor.B
	    'm'  -> LegacyColor.M
	    'c'  -> LegacyColor.C
	    'w'  -> LegacyColor.W
	    else -> LegacyColor.W
	}
	return num
    }

    fun fg(cc: femtioprocent.ansi.LegacyColor, s: String): String {
	return Ansi.fg(cc.cc, s)
    }

    fun fgBg5(cc: femtioprocent.ansi.LegacyColor, s: String): String {
	return Ansi.fgBg5(cc.cc, s)
    }

}