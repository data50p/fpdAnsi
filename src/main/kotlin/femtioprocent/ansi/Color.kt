package femtioprocent.ansi

object Color {

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

    fun legacyColorCode(ch: Char): Color.LegacyColor {
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

    fun fg(cc: LegacyColor, s: String): String {
	return "\u001b[1;${cc.cc}m${s}\u001b[00m"
    }

    fun fgBg5(cc: LegacyColor, s: String): String {
	return "\u001b[1;${cc.cc + 10};30m${s}\u001b[00m"
    }
}