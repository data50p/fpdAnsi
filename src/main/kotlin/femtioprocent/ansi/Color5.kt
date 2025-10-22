package femtioprocent.ansi

import femtioprocent.ansi.extentions.pL

class Color5 {

    // ---------------------------------------------------- Predefined Colors -----------------------------------------------------

    enum class Color(val cr: Int, val cg: Int, val cb: Int) {
	DD(0, 0, 0),
	D(1, 1, 1),
	DL(2, 2, 2),
	WD(3, 3, 3),
	W(4, 4, 4),
	WL(5, 5, 5),

	RD(4, 0, 0),
	R(5, 0, 0),
	RL(5, 1, 1),
	OD(4, 1, 0),
	O(5, 3, 0),
	OL(5, 4, 1),
	GD(0, 4, 0),
	G(0, 5, 0),
	GL(2, 5, 2),
	YD(4, 4, 0),
	Y(5, 5, 0),
	YL(5, 5, 2),
	BD(0, 0, 4),
	B(0, 0, 5),
	BL(1, 1, 5),
	MD(4, 0, 4),
	M(5, 0, 5),
	ML(5, 2, 5),
	CD(0, 4, 4),
	C(0, 5, 5),
	CL(2, 5, 5),
    }

    enum class Color5(val bits: Int, val bgFlip: Int, val complement: String) { // no X-reference
	RED(1, 3, "GREEN"),
	GREEN(2, 3, "RED"),
	BLUE(4, 5, "YELLOW"),
	YELLOW(3, 2, "BLUE"),
	MAGENTA(5, 3, "CYAN"),
	CYAN(6, 2, "MAGENTA"),
	GRAY(7, 1, "GRAY")
    }

    val maxColor5Index get() = Color5.entries.size - 1
    val maxColor5Value get() = g1.size - 1

    private fun fgBg5(num: Int, s: String): String {
	return "\u001b[1;${num + 10};30m${s}\u001b[00m"
    }

    fun fg(color: Color, s: String): String {
	return fg5(color.cr, color.cg, color.cb, s)
    }

    fun bg(color: Color, s: String): String {
	return bg5(color.cr, color.cg, color.cb, s)
    }

    private fun fg(num: Int, s: String): String {
	return "\u001b[1;${num}m${s}\u001b[00m"
    }

    fun colorFun(color: Color): (String) -> String {
	return { s -> fg(color, s) }
    }

    fun dumpColor5(): List<String> {
	val list = mutableListOf<String>()

	Color.entries.forEach { acc ->
	    list += "ColorSamples: ${acc.name.pL(2)} :: ${
		fg(
		    acc,
		    "XXXXXXXXXXXX " + acc.cr + ' ' + acc.cg + ' ' + acc.cb + ' ' + acc.name
		)
	    }"
	}

	return list
    }

    fun color5ByIndex(ix: Int): Color5 {
	if (ix >= Color5.entries.size)
	    return Color5.entries[maxColor5Index]
	return Color5.entries[ix]
    }

    //                                              (  darker  )
    //                               (  lighter )
    //                                            ↓ max saturated
    private val g1 = listOf(5, 5, 5, 5, 5, 4, 3, 2, 1)
    private val g2 = listOf(4, 3, 2, 1, 0, 0, 0, 0, 0)

    fun color5(color: Color5, value: Int, s: String): String {
	val h1 = if ((color.bits and 1) == 1) g1 else g2
	val h2 = if ((color.bits and 2) == 2) g1 else g2
	val h3 = if ((color.bits and 4) == 4) g1 else g2
	val r = fg5(h1[value], h2[value], h3[value], s)
	return r
    }

    /**
     * value => 0..5
     */
    fun color5Bg(color: Color5, value: Int, s: String): String {
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

    fun color5FgBg(color: Color5, value: Int, bgColor: Color5, bgValue: Int, s: String): String {
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

    fun color5Num(r: Int, g: Int, b: Int): Int {
	return Color5Num(16 + b + 6 * g + 36 * r).rgb
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


}