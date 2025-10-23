package femtioprocent.ansi

object LegacyColor {

    enum class Code(val value: Int) {
	BLACK(30),
	RED(31),
	GREEN(32),
	YELLOW(33),
	BLUE(34),
	MAGENTA(35),
	CYAN(36),
	WHITE(37),
    }

    fun normal(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[0;${cc.value}m${s}\u001b[00m"
    }

    fun bold(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[1;${cc.value}m${s}\u001b[00m"
    }

    fun faint(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[2;${cc.value}m${s}\u001b[00m"
    }

    fun underline(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[4;${cc.value}m${s}\u001b[00m"
    }

    fun italic(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[3;${cc.value}m${s}\u001b[00m"
    }

    fun crossed(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[9;${cc.value}m${s}\u001b[00m"
    }

    fun background(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[0;${cc.value + 10}m${s}\u001b[00m"
    }

    fun hiBoldIntensity(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[1;${cc.value + 60}m${s}\u001b[00m"
    }

    fun hiIntensityBackground(cc: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[1;${cc.value + 70}m${s}\u001b[00m"
    }

    fun normal(cc: femtioprocent.ansi.LegacyColor.Code, bg: femtioprocent.ansi.LegacyColor.Code, s: String): String {
	return "\u001b[1;${bg.value + 10};${cc.value}m${s}\u001b[00m"
    }
}