package femtioprocent.ansi

import femtioprocent.ansi.extentions.pL

object Color5 {

    // ---------------------------------------------------- Predefined Colors -----------------------------------------------------

    enum class ColorValue(val cr: Int, val cg: Int, val cb: Int) {
	DARK_BLACK(0, 0, 0),
	BLACK(1, 1, 1),
	LIGHT_BLACK(2, 2, 2),
	DARK_WHITE(3, 3, 3),
	WHITE(4, 4, 4),
	LIGHT_WHITE(5, 5, 5),

	DARK_RED(4, 0, 0),
	RED(5, 0, 0),
	LIGHT_RED(5, 1, 1),
	DARK_ORANGE(4, 1, 0),
	ORANGE(5, 3, 0),
	LIGHT_ORANGE(5, 4, 1),
	DARK_GREEN(0, 4, 0),
	GREEN(0, 5, 0),
	LIGHT_GREEN(2, 5, 2),
	DARK_YELLOW(4, 4, 0),
	YELLOW(5, 5, 0),
	LIGHT_YELLOW(5, 5, 2),
	DARK_BLUE(0, 0, 4),
	BLUE(0, 0, 5),
	LIGHT_BLUE(1, 1, 5),
	DARK_MAGENTA(4, 0, 4),
	MAGENTA(5, 0, 5),
	LIGHT_MAGENTA(5, 2, 5),
	DARK_CYAN(0, 4, 4),
	CYAN(0, 5, 5),
	LIGHT_CYAN(2, 5, 5),
    }

    enum class Code(val bits: Int, val bgFlip: Int, val complement: String) { // no X-reference
	RED(1, 3, "GREEN"),
	GREEN(2, 3, "RED"),
	BLUE(4, 5, "YELLOW"),
	YELLOW(3, 2, "BLUE"),
	MAGENTA(5, 3, "CYAN"),
	CYAN(6, 2, "MAGENTA"),
	GRAY(7, 1, "GRAY")
    }

    val maxCodeIndex get() = Code.entries.size - 1
    val maxColor5Value get() = g1.size - 1

    fun colorFun(color: Color5.ColorValue): (String) -> String {
	return { s -> femtioprocent.ansi.Color5.fg(color, s) }
    }

    fun fg(colorValue: ColorValue, s: String): String {
	return fg5(colorValue.cr, colorValue.cg, colorValue.cb, s)
    }

    fun bg(colorValue: ColorValue, s: String): String {
	return bg5(colorValue.cr, colorValue.cg, colorValue.cb, s)
    }

    fun fg(num: Int, s: String): String {
	return "\u001b[1;${num}m${s}\u001b[00m"
    }

    fun dumpColor5(): List<String> {
	val list = mutableListOf<String>()

	ColorValue.entries.forEach { cc ->
	    list += "ColorSamples: ${cc.name.pL(2)} :: ${
		fg(cc, "XXXXXXXXXXXX " + cc.cr + ' ' + cc.cg + ' ' + cc.cb + ' ' + cc.name)
	    }"
	}

	return list
    }

    fun color5ByIndex(ix: Int): Code {
	if (ix >= Code.entries.size)
	    return Code.entries[maxCodeIndex]
	return Code.entries[ix]
    }

    //                                              (  darker  )
    //                               (  lighter )
    //                                            ↓ max saturated
    private val g1 = listOf(5, 5, 5, 5, 5, 4, 3, 2, 1)
    private val g2 = listOf(4, 3, 2, 1, 0, 0, 0, 0, 0)

    fun color5(color: Code, value: Int, s: String): String {
	val h1 = if ((color.bits and 1) == 1) g1 else g2
	val h2 = if ((color.bits and 2) == 2) g1 else g2
	val h3 = if ((color.bits and 4) == 4) g1 else g2
	val r = fg5(h1[value], h2[value], h3[value], s)
	return r
    }

    /**
     * value => 0..5
     */
    fun color5Bg(color: Code, value: Int, s: String): String {
	val valueS = 8 - value
	val h1 = if (color.bgFlip < value) 0 else 5
	val h2 = if (color.bgFlip < value) 0 else 5
	val h3 = if (color.bgFlip < value) 0 else 5
	val bh1 = if ((color.bits and 1) == 1) g1 else g2
	val bh2 = if ((color.bits and 2) == 2) g1 else g2
	val bh3 = if ((color.bits and 4) == 4) g1 else g2
	val r = fgbg5(h1, h2, h3, bh1[valueS], bh2[valueS], bh3[valueS], s)
	return r
    }

    fun color5FgBg(color: Code, value: Int, bgColor: Code, bgValue: Int, s: String): String {
	val bgValueS = 8 - bgValue
	val h1 = if ((color.bits and 1) == 1) g1 else g2
	val h2 = if ((color.bits and 2) == 2) g1 else g2
	val h3 = if ((color.bits and 4) == 4) g1 else g2
	val bh1 = if ((bgColor.bits and 1) == 1) g1 else g2
	val bh2 = if ((bgColor.bits and 2) == 2) g1 else g2
	val bh3 = if ((bgColor.bits and 4) == 4) g1 else g2
	val r = fgbg5(h1[value], h2[value], h3[value], bh1[bgValueS], bh2[bgValueS], bh3[bgValueS], s)
	return r
    }

    @JvmInline
    value class Color5Num(val rgb: Int) {
	constructor(r: Int, g: Int, b: Int) : this(color5Num(r, g, b))
    }

    fun fg5(r: Int, g: Int, b: Int, s: String): String {
	val num = color5Num(r, g, b)
	return "\u001b[38;5;${num}m${s}\u001b[00m"
    }

    fun bg5(r: Int, g: Int, b: Int, s: String): String {
	val num = color5Num(r, g, b)
	val numfg = when {
	    //r == 0 -> 231
	    g == 5        -> 0
	    //b == 5 -> 0
	    r + g + b > 6 -> 0
	    else          -> 231
	}
	return "\u001b[48;5;${num};38;5;${numfg}m${s}\u001b[00m"
    }

    fun fgbg5(r: Int, g: Int, b: Int, br: Int, bg: Int, bb: Int, s: String): String {
	val num = color5Num(r, g, b)
	val bnum = color5Num(br, bg, bb)
	return "\u001b[38;5;${num};48;5;${bnum}m${s}\u001b[00m"
    }

    fun fgbg5(r: Int, g: Int, b: Int, bgNum: Color5Num, s: String): String {
	val num = color5Num(r, g, b)
	return "\u001b[38;5;${num};48;5;${bgNum.rgb}m${s}\u001b[00m"
    }

    fun color5Num(r: Int, g: Int, b: Int): Int {
	return 16 + b + 6 * g + 36 * r
    }
}