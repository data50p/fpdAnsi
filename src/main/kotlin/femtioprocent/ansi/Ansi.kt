package femtioprocent.ansi

import femtioprocent.ansi.Ansi.RGB
import femtioprocent.ansi.Ansi.rgbBg
import femtioprocent.ansi.Ansi.showC
import femtioprocent.ansi.extentions.pC
import femtioprocent.ansi.extentions.pL
import femtioprocent.ansi.extentions.pR
import kotlin.random.Random

/**
 * Write escape sequences for colored output.
 *
 *  https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
 *
 * A stack keep track of pushed color state, look in main at end for examples.
 *
 * @author larsno
 */

fun showRGB(rgb: Ansi.RGB, w: Int = 34) = rgb.showC()
fun showHSV(rgb: Ansi.RGB, w: Int = 32) = rgb.toHsv().toString().pR(w).rgbBg(rgb)
fun showRGB(hsv: Ansi.HSV, w: Int = 34) = hsv.toRGB().toString().pR(w).rgbBg(hsv.toRGB())
fun showHSV(hsv: Ansi.HSV, w: Int = 32) = hsv.showC()

object Ansi {

    fun CSI(suffix: String = ""): String {
        return "\u001b[$suffix"
    }

    fun hideCursor(): String {
        return CSI("?25l")
    }

    fun showCursor(): String {
        return CSI("?25h")
    }

    fun inverse(s: String): String {
        return "\u001b[7m$s\u001b[0m"
    }

    fun inverse(): String {
        return "\u001b[7m"
    }

    fun normal(): String {
        return "\u001b[0m"
    }

    fun goto(x: Int, y: Int): String {
        return "\u001b[${y};${x}H"
    }

    fun clear(): String {
        return "\u001b[2J"
    }

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


    // ---------- CSI for color type 5 (6 bits) ----------

    fun fg5(r: Int, g: Int, b: Int, s: String): String {
        val num = color5Num(r, g, b)
        return "\u001b[38;5;${num}m${s}\u001b[00m"
    }

    fun bg5(r: Int, g: Int, b: Int, s: String): String {
        val num = color5Num(r, g, b)
        val numfg = when {
            //r == 0 -> 231
            g == 5 -> 0
            //b == 5 -> 0
            r + g + b > 6 -> 0
            else -> 231
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


    // ---------- CSI for color type 2 (8 bits) ----------

    fun fg256(r: Int, g: Int, b: Int, s: String): String {
        return "\u001b[38;2;${r};${g};${b}m${s}\u001b[00m"
    }

    fun bg256(r: Int, g: Int, b: Int, s: String): String {
        val num = color5Num(r, g, b)
        val numfg = when {
            //r == 0 -> 231
            g > 230 -> 0
            //b == 5 -> 0
            r + g + b > 290 -> 0
            else -> 231
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

    // ---------------------------------------------------- Legacy Color -----------------------------------------------------

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
            'd' -> LegacyColor.D
            'r' -> LegacyColor.R
            'g' -> LegacyColor.G
            'y' -> LegacyColor.Y
            'b' -> LegacyColor.B
            'm' -> LegacyColor.M
            'c' -> LegacyColor.C
            'w' -> LegacyColor.W
            else -> LegacyColor.W
        }
        return num
    }

    fun fg(cc: LegacyColor, s: String): String {
        return fg(cc.cc, s)
    }

    fun fgBg5(cc: LegacyColor, s: String): String {
        return fgBg5(cc.cc, s)
    }

    /** Make sure to handle subtile rounding effect */
    fun Double.modSpecial(d: Double): Double {

        val m = (this - 0.0000000001) % d
        //println("mod: $this $d -> $m")
        return m
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
                256 -> (0..255).toList()
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

        fun similarRandom(factor: Double = 1.0): RGB = Ansi.RGB(
            cs,
            avg(r, Random.nextInt(cs), factor),
            avg(g, Random.nextInt(cs), factor),
            avg(b, Random.nextInt(cs), factor)
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

        private fun avg(i1: Int, i2: Int, factor: Double = 1.0) = ((i1 + factor * i2) / (factor+1).toDouble() + 0.5).toInt()

        fun average(other: RGB): RGB {
            require(cs == other.cs)
            return RGB(cs, avg(r, other.r), avg(g, other.g), avg(b, other.b))
        }

        fun rotR() = RGB(cs, b, r, g)
        fun rotL() = RGB(cs, g, b, r)
        fun mixRG() = RGB(cs, g, r, b)
        fun mixGB() = RGB(cs, r, b, g)
        fun mixRB() = RGB(cs, b, g, r)

        fun complementRGB(): RGB = RGB(cs, cs - r - 1, cs - g - 1, cs - b - 1)
        fun complement() = toHsv().let {it.clone(h = (it.h + 180.0) % 360.0).toRGB()}.toCubeSize(cs)

        fun moreOrLess(fr: (Int) -> Int, fg: (Int) -> Int, fb: (Int) -> Int) = RGB(cs, fr(r), fg(g), fb(b))
        fun moreOrLess(n: Int, fr: (Int) -> Int, fg: (Int) -> Int, fb: (Int) -> Int): RGB =
            if (n == 0) this else moreOrLess(fr, fg, fb).moreOrLess(n - 1, fr, fg, fb)

        fun moreOrLess(opRGB: String) = RGB(cs, opFun(opRGB[0])(r), opFun(opRGB[1])(g), opFun(opRGB[2])(b))
        fun moreOrLess(n: Int, op: String): RGB = if (n == 0) this else moreOrLess(op).moreOrLess(n - 1, op)

        fun opFun(ch: Char): (Int) -> Int {
            return when (ch) {
                '+' -> ::inc
                '-' -> ::dec
                else -> ::eq0
            }
        }

	fun toHue(value: Double) = toHsv().clone(h = value).toRGB().toCubeSize(cs)
	fun toSaturation(value: Double) = toHsv().clone(s = value).toRGB().toCubeSize(cs)
	fun toValue(value: Double) = toHsv().clone(v = value).toRGB().toCubeSize(cs)

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
            return toHsv().gradient(loops, rgb.toHsv()).map { it.toRGB() }
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
                rd -> ((gd - bd) / delta + 6.0) % 6.0
                gd -> ((bd - rd) / delta) + 2.0
                bd -> ((rd - gd) / delta) + 4.0
                else -> 1.0
            }

            val s = when (cmax) {
                0.0 -> 0.0
                else -> delta / cmax
            }

            val v = cmax

            return HSV(if (h.isNaN()) 0.0 else h, s, v)
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
            val hh =
                if (h.isNaN())
                    0.0
                else
                    h

            data class RGB(val r: Double, val g: Double, val b: Double)

            val c = v * s
            val x = c * (1 - Math.abs((hh / 60.0) % 2.0 - 1))
            val m = v - c
            val rgb = when {
                hh < 60 -> RGB(c, x, 0.0)
                hh < 120 -> RGB(x, c, 0.0)
                hh < 180 -> RGB(0.0, c, x)
                hh < 240 -> RGB(0.0, x, c)
                hh < 300 -> RGB(x, 0.0, c)
                else -> RGB(c, 0.0, x)
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
}

fun main() {

    println(Ansi.clear())
    (1..30).forEach { xy ->
        println(Ansi.goto(xy, xy) + "I'm at ($xy $xy)")
    }
}
