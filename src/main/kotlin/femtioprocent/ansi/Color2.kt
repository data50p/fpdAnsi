package femtioprocent.ansi

import femtioprocent.ansi.Ansi.modSpecial
import femtioprocent.ansi.Color2.RGB
import femtioprocent.ansi.Color2.HSV
import femtioprocent.ansi.extentions.pC
import femtioprocent.ansi.extentions.pL
import femtioprocent.ansi.extentions.pR
import kotlin.random.Random


class Color2 {

    fun showRGB(rgb: Color2.RGB, w: Int = 34) = rgb.showC()
    fun showHSV(rgb: Color2.RGB, w: Int = 32) = rgb.toHsv().toString().pR(w).rgbBg(rgb)
    fun showRGB(hsv: Color2.HSV, w: Int = 34) = hsv.toRGB().toString().pR(w).rgbBg(hsv.toRGB())
    fun showHSV(hsv: Color2.HSV, w: Int = 32) = hsv.showC()

    // ---------- CSI for color type 2 (8 bits) ----------

    fun fg256(r: Int, g: Int, b: Int, s: String): String {
	return "\u001b[38;2;${r};${g};${b}m${s}\u001b[00m"
    }

    fun bg256(r: Int, g: Int, b: Int, s: String): String {
	val num = Color5.color5Num(r, g, b)
	val numfg = when {
	    //r == 0 -> 231
	    g > 230         -> 0
	    //b == 5 -> 0
	    r + g + b > 290 -> 0
	    else            -> 231
	}
	return "\u001b[48;2;${r};${g};${b};38;5;${numfg}m${s}\u001b[00m"
    }

    fun fgbg256(fr: Int, fg: Int, fb: Int, br: Int, bg: Int, bb: Int, s: String): String {
	return "\u001b[38;2;${fr};${fg};${fb};48;2;${br};${bg};${bb}m${s}\u001b[00m"
    }


    // ---------------- cube sized colors ----------------

    fun csFg(cubeSize: Int, rix: Int, gix: Int, bix: Int, s: String): String {
	val r = Support.values256(cubeSize)[rix]
	val g = Support.values256(cubeSize)[gix]
	val b = Support.values256(cubeSize)[bix]
	return fg256(r, g, b, s)
    }

    fun csBg(cubeSize: Int, rix: Int, gix: Int, bix: Int, s: String): String {
	if (rix < 0 || gix < 0 || bix < 0)
	    System.err.println(" ---- ")
	val r = Support.values256(cubeSize)[rix]
	val g = Support.values256(cubeSize)[gix]
	val b = Support.values256(cubeSize)[bix]
	return bg256(r, g, b, s)
    }

    fun csFgBg(
	cubeSize: Int,
	rix: Int,
	gix: Int,
	bix: Int,
	cubeSizeBG: Int,
	brix: Int,
	bgix: Int,
	bbix: Int,
	s: String
    ): String {
	if (rix < 0 || gix < 0 || bix < 0 || rix >= cubeSize || gix >= cubeSize || bix >= cubeSize)
	    System.err.println(" ---- ")
	if (brix < 0 || bgix < 0 || bbix < 0 || brix >= cubeSizeBG || bgix >= cubeSizeBG || bbix >= cubeSizeBG)
	    System.err.println(" ---- ")
	val fr = Support.values256(cubeSize)[rix]
	val fg = Support.values256(cubeSize)[gix]
	val fb = Support.values256(cubeSize)[bix]
	val br = Support.values256(cubeSizeBG)[brix]
	val bg = Support.values256(cubeSizeBG)[bgix]
	val bb = Support.values256(cubeSizeBG)[bbix]
	return fgbg256(fr, fg, fb, br, bg, bb, s)
    }

    fun rgbFgBg(rgbFG: RGB, rgbBG: RGB, s: String): String {
	return csFgBg(
	    rgbFG.cs,
	    rgbFG.r,
	    rgbFG.g,
	    rgbFG.b,
	    rgbBG.cs,
	    rgbBG.r,
	    rgbBG.g,
	    rgbBG.b,
	    s
	)
    }

    object Support {

	var verbose = false
	private val colorValuesMap = mutableMapOf<Int, List<Int>>()

	/**
	 * Return list of color 256 based values for a Color Cube of size n
	 *
	 * cube size = 4: (0, 1, 2, 3) -> (0, 85, 171, 255)
	 */
	fun values256(n: Int): List<Int> {
	    require(n <= 256) { "cube size must be <= 256" }

	    return colorValuesMap[n] ?: when (n) {
		256  -> (0..255).toList()
		else -> {
		    val step = 255.00 / (n - 1)
		    (0..<n).map {
			(step * it + 0.5).toInt()
		    }
		}
	    }.also {
		colorValuesMap[n] = it
	    }
	}
    }

    data class RGB(val cs: Int, val r: Int, val g: Int, val b: Int) {

	constructor(cs: Int, rf: Double, gf: Double, bf: Double) :
		this(cs, fromDouble(rf, cs), fromDouble(gf, cs), fromDouble(bf, cs))

	fun toCubeSize(cs: Int = 256): RGB {
	    val f = (cs - 1).toDouble() / (this@RGB.cs - 1).toDouble()
	    val rr = r.toDouble() * f + 0.5
	    val gg = g.toDouble() * f + 0.5
	    val bb = b.toDouble() * f + 0.5
	    return RGB(cs, rr.toInt(), gg.toInt(), bb.toInt())
	}

	fun similarRandom(factor: Double = 1.0): RGB = RGB(
	    cs,
	    avg(r, Random.Default.nextInt(cs), factor),
	    avg(g, Random.Default.nextInt(cs), factor),
	    avg(b, Random.Default.nextInt(cs), factor)
	)

	fun colorSpan(): Int {
	    return Math.max(cs - Math.min(r, Math.min(g, b)) - 1, Math.max(r, Math.max(g, b)))
	}

	fun min(): Int {
	    return listOf(r, g, b).min()
	}

	fun max(): Int {
	    return listOf(r, g, b).max()
	}

	/**
	 * Generally not used for color calculations
	 */
	fun minus(other: RGB): RGB {
	    if (cs == other.cs)
		return RGB(cs, r - other.r, g - other.g, b - other.b)
	    return minus(other.toCubeSize(cs))
	}

	private fun avg(i1: Int, i2: Int, factor: Double = 1.0) = ((i1 + factor * i2) / (factor + 1).toDouble() + 0.5).toInt()

	fun average(other: RGB, factor: Double = 1.0): RGB {
	    if (cs == other.cs)
		return RGB(
		    cs,
		    avg(r, other.r, factor),
		    avg(g, other.g, factor),
		    avg(b, other.b, factor)
		)
	    return average(other.toCubeSize(cs), factor)
	}

	fun rotR() = RGB(cs, b, r, g)
	fun rotL() = RGB(cs, g, b, r)
	fun mixRG() = RGB(cs, g, r, b)
	fun mixGB() = RGB(cs, r, b, g)
	fun mixRB() = RGB(cs, b, g, r)

	fun inverse(): RGB = RGB(cs, cs - r - 1, cs - g - 1, cs - b - 1)
	fun magnet(): RGB {
	    val hsv = toHsv()
	    return when {
		hsv.v < 0.67 -> hsv.clone(v = 0.0, s = 1.0).toRGB()
		else         -> hsv.clone(v = 1.0, s = 0.0).toRGB()
	    }
	}

	fun complement() = toHsv().let { it.clone(h = (it.h + 180.0) % 360.0).toRGB() }.toCubeSize(cs)

	fun theComplementary(): List<RGB> {
	    return mutableListOf<RGB>().also {
		it += this
		it += this.complement()
	    }
	}

	fun theAnalogous(spread: Double): List<RGB> {
	    return mutableListOf<RGB>().also {
		it += this
		val hsv = this.toHsv()
		it += hsv.copy(h = (hsv.h + spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsv.copy(h = (hsv.h - spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
	    }
	}

	fun theSplitComplementary(spread: Double): List<RGB> {
	    return mutableListOf<RGB>().also {
		it += this
		val hsv = this.complement().toHsv()
		it += hsv.copy(h = (hsv.h + spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsv.copy(h = (hsv.h - spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
	    }
	}

	fun theTriadic(): List<RGB> {
	    return mutableListOf<RGB>().also {
		it += this
		it += this.rotR()
		it += this.rotL()
	    }
	}

	fun theRectangleTetradic(spread: Double): List<RGB> = theDoubleComplementary(spread)

	fun theDoubleComplementary(spread: Double): List<RGB> {
	    return mutableListOf<RGB>().also {

		val hsv = this.toHsv()
		val hsvc = this.complement().toHsv()

		it += hsv.copy(h = (hsv.h + spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsvc.copy(h = (hsvc.h - spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsvc.copy(h = (hsvc.h + spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsv.copy(h = (hsv.h - spread * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
	    }
	}

	fun theSquareTetradic(): List<RGB> {
	    return mutableListOf<RGB>().also {
		it += this

		val hsv = this.toHsv()

		it += hsv.copy(h = (hsv.h + 0.25 * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += this.complement()
		it += hsv.copy(h = (hsv.h + 0.75 * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
	    }
	}

	fun thePentagonal() = theDoubleSplitComplementary()

	fun theDoubleSplitComplementary(): List<RGB> {
	    return mutableListOf<RGB>().also {
		val hsv = this.toHsv()
		val hsvc = this.complement().toHsv()

		it += this
		it += hsv.copy(h = (hsv.h + 0.2 * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsv.copy(h = (hsv.h - 0.4 * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsvc.copy(h = (hsvc.h + 0.6 * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
		it += hsvc.copy(h = (hsvc.h - 0.8 * 360.0 + 360.0) % 360.0).toRGB().toCubeSize(cs)
	    }
	}


	fun moreOrLess(fr: (Int) -> Int, fg: (Int) -> Int, fb: (Int) -> Int) = RGB(cs, fr(r), fg(g), fb(b))
	fun moreOrLess(n: Int, fr: (Int) -> Int, fg: (Int) -> Int, fb: (Int) -> Int): RGB =
	    if (n == 0) this else moreOrLess(fr, fg, fb).moreOrLess(n - 1, fr, fg, fb)

	fun moreOrLess(opRGB: String) = RGB(cs, opFun(opRGB[0])(r), opFun(opRGB[1])(g), opFun(opRGB[2])(b))
	fun moreOrLess(n: Int, op: String): RGB = if (n == 0) this else moreOrLess(op).moreOrLess(n - 1, op)

	fun opFun(ch: Char): (Int) -> Int {
	    return when (ch) {
		'+'  -> ::inc
		'-'  -> ::dec
		else -> ::eq0
	    }
	}

	fun toHue(value: Double) = toHsv().clone(h = value).toRGB().toCubeSize(cs)
	fun toSaturation(value: Double) = toHsv().clone(s = value).toRGB().toCubeSize(cs)
	fun toValue(value: Double) = toHsv().clone(v = value).toRGB().toCubeSize(cs)

	fun addValue(value: Double): RGB {
	    val hsv = toHsv()
	    val vv = when {
		hsv.v < 0.3 -> value / 3.0
		hsv.v > 0.6 -> (value + 1) / 2.0
		else        -> value
	    }
	    val nval = hsv.v + (1.0 - hsv.v) * vv
	    return hsv.clone(v = nval).toRGB().toCubeSize(cs)
	}

	fun addSaturation(value: Double): RGB {
	    val hsv = toHsv()
	    val ss = when {
		hsv.s <= 1.0 -> 0.3
		hsv.s < 0.3  -> value / 3.0
		hsv.s > 0.71 -> (value + 1) / 2.0
		else         -> value
	    }
	    val nval = hsv.s + (1.0 - hsv.s) * ss
	    return hsv.clone(s = nval).toRGB().toCubeSize(cs)
	}

	fun subValue(value: Double): RGB {
	    val hsv = toHsv()
	    val vv = when {
		hsv.s <= 1.0 -> value
		hsv.v < 0.3  -> value / 3.0
		hsv.v > 0.6  -> (value + 1) / 2.0
		else         -> value
	    }
	    val nval = hsv.v - (hsv.v) * vv
	    return hsv.clone(v = nval).toRGB().toCubeSize(cs)
	}

	fun subSaturation(value: Double): RGB {
	    val hsv = toHsv()
	    val ss = when {
		hsv.s <= 1.0 -> 0.3
		hsv.s < 0.3  -> value / 3.0
		hsv.s > 0.71 -> (value + 1) / 2.0
		else         -> value
	    }
	    val nval = hsv.s + (1.0 - hsv.s) * ss
	    return hsv.clone(s = nval).toRGB().toCubeSize(cs)
	}

	fun toMaxValue() = toValue(1.0)
	fun toMaxSaturation() = toSaturation(1.0)

	fun hueGradient(loops: Int = 12, degree: Double = 360.0): List<RGB> {
	    val l = mutableListOf<RGB>()

	    val step = degree / loops
	    var hsv = toHsv()

	    repeat(loops) {
		val hsv2 = HSV((hsv.h + it * step + 360.0) % 360.0, hsv.s, hsv.v)
		l += hsv2.toRGB()
	    }
	    return l
	}

	fun saturationGradient(loops: Int = 12): List<RGB> {
	    val l = mutableListOf<RGB>()

	    val step = 0.999999 / (loops - 1)
	    var hsv = toHsv()

	    hsv = HSV(hsv.h, 0.0, hsv.v)

	    repeat(loops) {
		val hsv2 = HSV(hsv.h, (hsv.s + it * step).modSpecial(1.0), hsv.v)
		l += hsv2.toRGB()
	    }
	    return l
	}

	/**
	 * A problem when reaching black target (value == 0)
	 */
	fun valueGradient(loops: Int = 12): List<RGB> {
	    val l = mutableListOf<RGB>()

	    val step = 0.999999 / (loops - 1)
	    var hsv = toHsv()

	    val vv = hsv.v
	    hsv = HSV(hsv.h, hsv.s, 0.0)

	    repeat(loops) {
		val hsv2 = HSV(hsv.h, hsv.s, (hsv.v + it * step).modSpecial(1.0))
		l += hsv2.toRGB()
	    }
	    return l
	}

	fun gradient(loops: Int = 12, rgb: RGB): List<RGB> {
	    return toHsv().gradient(loops, rgb.toHsv()).map { it.toRGB().toCubeSize(cs) }
	}

	fun permutationGradient(): List<RGB> {
	    val l = mutableListOf<RGB>()
	    l += this
	    l += RGB(cs, r, b, g)
	    l += RGB(cs, b, r, g)
	    l += RGB(cs, b, g, r)
	    l += RGB(cs, g, b, r)
	    l += RGB(cs, g, r, b)

	    // 1 2 3
	    // 1 3 2
	    // 3 1 2
	    // 3 2 1
	    // 2 3 1
	    // 2 1 3

	    return l
	}


	fun inc(v: Int): Int {
	    val v1 = v + 1
	    if (v1 >= cs)
		return cs - 1
	    else
		return v1
	}

	fun dec(v: Int): Int {
	    val v1 = v - 1
	    if (v1 <= 0)
		return 0
	    else
		return v1
	}

	fun eq0(v: Int): Int = v

	fun byName(name: String): RGB {
	    return when (name) {
		"rotL"  -> rotL()
		"rotR"  -> rotR()
		"mixRG" -> mixRG()
		"mixGB" -> mixGB()
		"mixRB" -> mixRB()
		"compl" -> complement()
		"inv"   -> inverse()
		"val+"  -> addValue(0.12)
		"sat+"  -> addSaturation(0.3)
		"val-"  -> subValue(0.12)
		"sat-"  -> subSaturation(0.3)
		else    -> this
	    }
	}

	fun byNames(names: Collection<String>): RGB {
	    return names.fold(this) { acc, string -> acc.byName(string) }
	}

	fun toHsv(): HSV = (if (cs == 256) this else toCubeSize(256)).toHsv256()

	private fun toHsv256(): HSV {
	    require(cs == 256) { "Only 256 bits color" }

	    val rd = r / 255.0
	    val gd = g / 255.0
	    val bd = b / 255.0
	    val cmax = Math.max(rd, Math.max(gd, bd))
	    val cmin = Math.min(rd, Math.min(gd, bd))
	    val delta = cmax - cmin

	    val h = 60.0 * when (cmax) {
		rd if delta != 0.0 -> ((gd - bd) / delta + 6.0) % 6.0
		gd if delta != 0.0 -> ((bd - rd) / delta) + 2.0
		bd if delta != 0.0 -> ((rd - gd) / delta) + 4.0
		else               -> 0.0
	    }

	    val s = when (cmax) {
		0.0  -> 0.0
		else -> delta / cmax
	    }

	    val v = cmax

	    return HSV(h, s, v)
	}

	fun toLaconicStringRGB() = "$r,$g,$b"

	companion object Companion {
	    private fun fromDouble(f: Double, cs: Int): Int {
		val ff = if (f >= 1.0) 0.999 else f
		val v = cs * ff
		return v.toInt()
	    }
	}
    }


    data class HSV(val h: Double, val s: Double, val v: Double) {

	fun clone(h: Double = this.h, s: Double = this.s, v: Double = this.v): HSV {
	    return HSV(h, s, v)
	}

	fun gradient(loops: Int = 6, hsv2: HSV): List<HSV> {
	    val l = mutableListOf<HSV>()

	    val dh = hsv2.h - h
	    val ds = hsv2.s - s
	    val dv = hsv2.v - v

	    val steph = dh / (loops - 1)
	    val steps = ds / (loops - 1)
	    val stepv = dv / (loops - 1)

	    repeat(loops) {
		val hsv2 = HSV((h + it * steph) % 360.0, (s + it * steps).modSpecial(1.0), (v + it * stepv).modSpecial(1.0))
		// avoid v == 1.000
		l += hsv2
	    }
	    return l
	}

	private fun avg(d1: Double, d2: Double) = (d1 + d2) / 2.0

	fun averageHue(other: HSV): HSV {
	    return HSV(avg(h, other.h), s, v)
	}

	fun averageSaturation(other: HSV): HSV {
	    return HSV(h, avg(s, other.s), v)
	}

	fun averageValue(other: HSV): HSV {
	    return HSV(h, s, avg(v, other.v))
	}

	fun average(other: HSV): HSV {
	    return HSV(
		avg(h, other.h),
		avg(s, other.s),
		avg(v, other.v)
	    )
	}

	fun toRGB(): RGB {
	    data class RGB(val r: Double, val g: Double, val b: Double)

	    val c = v * s
	    val x = c * (1 - Math.abs((h / 60.0) % 2.0 - 1))
	    val m = v - c
	    val rgb = when {
		h < 60  -> RGB(c, x, 0.0)
		h < 120 -> RGB(x, c, 0.0)
		h < 180 -> RGB(0.0, c, x)
		h < 240 -> RGB(0.0, x, c)
		h < 300 -> RGB(x, 0.0, c)
		else    -> RGB(c, 0.0, x)
	    }
	    val r = ((rgb.r + m) * 255 + 0.5).toInt()
	    val g = ((rgb.g + m) * 255 + 0.5).toInt()
	    val b = ((rgb.b + m) * 255 + 0.5).toInt()

	    return RGB(256, r, g, b)
	}

	override fun toString(): String {
	    return "HSV{%7.3f,%7.3f,%7.3f}".format(h, s, v)
	}
    }

    // ---------------------- higher ordered functions ---------------------------

    fun csFg(cubeSize: Int, r: Int, g: Int, b: Int): (String) -> String = { s -> rgbFg(RGB(cubeSize, r, g, b))(s) }
    fun csBg(cubeSize: Int, r: Int, g: Int, b: Int): (String) -> String = { s -> rgbBg(RGB(cubeSize, r, g, b))(s) }

    fun csFg(cubeSize: Int): (r: Int, g: Int, b: Int, String) -> String = { r, g, b, s -> csFg(cubeSize, r, g, b, s) }
    fun csBg(cubeSize: Int): (r: Int, g: Int, b: Int, String) -> String = { r, g, b, s -> csBg(cubeSize, r, g, b, s) }

    fun csFgRGB(cubeSize: Int): (r: Int, g: Int, b: Int) -> (String) -> String =
	{ r, g, b -> { s -> csFg(cubeSize, r, g, b, s) } }

    fun csBgRGB(cubeSize: Int): (r: Int, g: Int, b: Int) -> (String) -> String =
	{ r, g, b -> { s -> csBg(cubeSize, r, g, b, s) } }

    fun rgbFg(rgb: RGB): (String) -> String = { s -> csFg(rgb.cs, rgb.r, rgb.g, rgb.b, s) }

    fun rgbBg(rgb: RGB): (String) -> String = { s -> csBg(rgb.cs, rgb.r, rgb.g, rgb.b, s) }

    fun String.rgbFg(rgb: RGB) = csFg(rgb.cs, rgb.r, rgb.g, rgb.b, this)
    fun String.rgbBg(rgb: RGB) = csBg(rgb.cs, rgb.r, rgb.g, rgb.b, this)

    fun csRgb(cubeSize: Int): (r: Int, g: Int, b: Int) -> RGB = { r, g, b -> RGB(cubeSize, r, g, b) }

    fun RGB.showC(w: Int = 34, prfx: String = "", f: (RGB) -> String = { it.toString() }) = (prfx + f(this)).pC(w).rgbBg(this)
    fun RGB.showL(w: Int = 34, prfx: String = "", f: (RGB) -> String = { it.toString() }) = (prfx + f(this)).pR(w).rgbBg(this)
    fun RGB.showR(w: Int = 34, prfx: String = "", f: (RGB) -> String = { it.toString() }) = (prfx + f(this)).pL(w).rgbBg(this)

    fun HSV.showC(w: Int = 32, prfx: String = "") = (prfx + toString()).pC(w).rgbBg(this.toRGB())
    fun HSV.showL(w: Int = 32, prfx: String = "") = (prfx + toString()).pR(w).rgbBg(this.toRGB())
    fun HSV.showR(w: Int = 32, prfx: String = "") = (prfx + toString()).pL(w).rgbBg(this.toRGB())


}