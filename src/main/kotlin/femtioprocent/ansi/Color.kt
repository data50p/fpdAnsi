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

    fun normal(cc: LegacyColor, s: String): String {
	return "\u001b[0;${cc.cc}m${s}\u001b[00m"
    }

    fun bold(cc: LegacyColor, s: String): String {
	return "\u001b[1;${cc.cc}m${s}\u001b[00m"
    }

    fun faint(cc: LegacyColor, s: String): String {
	return "\u001b[2;${cc.cc}m${s}\u001b[00m"
    }

    fun underline(cc: LegacyColor, s: String): String {
	return "\u001b[4;${cc.cc}m${s}\u001b[00m"
    }

    fun italic(cc: LegacyColor, s: String): String {
	return "\u001b[3;${cc.cc}m${s}\u001b[00m"
    }

    fun crossed(cc: LegacyColor, s: String): String {
	return "\u001b[9;${cc.cc}m${s}\u001b[00m"
    }

    fun background(cc: LegacyColor, s: String): String {
	return "\u001b[0;${cc.cc + 10}m${s}\u001b[00m"
    }

    fun hiBoldIntensity(cc: LegacyColor, s: String): String {
	return "\u001b[1;${cc.cc + 60}m${s}\u001b[00m"
    }

    fun hiIntensityBackground(cc: LegacyColor, s: String): String {
	return "\u001b[1;${cc.cc + 70}m${s}\u001b[00m"
    }

    fun normal(cc: LegacyColor, bg: LegacyColor, s: String): String {
	return "\u001b[1;${bg.cc + 10};${cc.cc}m${s}\u001b[00m"
    }
}