import femtioprocent.ansi.Color
import femtioprocent.ansi.Color.LegacyColor
import femtioprocent.ansi.Color2
import femtioprocent.ansi.Color2.RGB
import femtioprocent.ansi.Color2.rgbBg
import femtioprocent.ansi.Color2.showC
import femtioprocent.ansi.Color2.showL
import femtioprocent.ansi.Color2.showRGB
import femtioprocent.ansi.Color5
import femtioprocent.ansi.appl.AnsiDemo
import femtioprocent.ansi.appl.AnsiDemo.Companion.randomRGB
import femtioprocent.ansi.appl.prList
import femtioprocent.ansi.extentions.ansiBgColor
import femtioprocent.ansi.extentions.ansiColor
import kotlin.random.Random
import kotlin.test.Test

class Test1 {
    val verbose = false

    @Test
    fun testM1() {
	(1..256).forEach { n ->
	    val l = Color2.Support.values256(n)
	    assert(l.size == n)
	    assert(l.first() == 0)
	    if (l.size > 1)
		assert(l.last() == 255)
	}
    }

    @Test
    fun testLegacy() {

	val m = mapOf<String, LegacyColor>(
	    "RED" to LegacyColor.R,
	    "GREEN" to LegacyColor.G,
	    "BLUE" to LegacyColor.B,
	    "YELLOW" to LegacyColor.Y,
	    "CYAN" to LegacyColor.C,
	    "MAGENTA" to LegacyColor.M,
	    "WHITE" to LegacyColor.W,
	    "BLACK" to LegacyColor.D,
	)

	m.entries.forEach { (text, col) ->
	    println(Color.normal(col, " ${text} normal "))
	    println(Color.bold(col, " ${text} bold "))
	    println(Color.faint(col, " ${text} faint "))
	    println(Color.crossed(col, " ${text} crossed "))
	    println(Color.hiBoldIntensity(col, " ${text} bold intense "))
	    println(Color.underline(col, " ${text} underline "))
	    println(Color.italic(col, " ${text} italic "))
	    println(Color.background(col, " ${text} background "))
	    println(Color.hiIntensityBackground(col, " ${text} intense background "))
	}
    }

    @Test
    fun testLegacy2() {
	println(Color.normal(LegacyColor.B, LegacyColor.Y, " Blue Yellow "))
    }

    @Test
    fun testRGBtoHSV() {
	(4..256).forEach { cs ->
	    val rgb = AnsiDemo.randomRGB(cs)
	    val rgb256 = rgb.toCubeSize(256)
	    val hsv = rgb256.toHsv()
	    val rgb_256 = hsv.toRGB()
	    val rgb_N = rgb_256.toCubeSize(cs)
	    val rgbDelta256 = rgb_256.minus(rgb256)
	    val rgbDelta = rgb_N.minus(rgb)
	    val d256 = " ∆ ${rgbDelta256.toLaconicStringRGB()}"
	    val d = " ∆ ${rgbDelta.toLaconicStringRGB()}"

	    if (verbose)
		listOf(
		    rgb.showL(),
		    rgb256.showL(),
		    rgb256.toHsv().showL(),
		    rgb_256.showL(),
		    rgb_N.showL()
		).prList(" -> ", "$d256  $d")

	    assert(rgbDelta256.toLaconicStringRGB() == "0,0,0")
	    assert(rgbDelta.toLaconicStringRGB() == "0,0,0")
	}
    }

    @Test
    fun testRGBtoHSV2() {
	(2..256).forEach {
	    val rgb0 = randomRGB(it)
	    val rgb1 = rgb0.toCubeSize(256)
	    val rgb2 = rgb1.toCubeSize(it)

	    assert(rgb0.minus(rgb2).toLaconicStringRGB() == "0,0,0")
	    if (verbose)
		listOf(
		    rgb0.showL(),
		    rgb1.showL(),
		    rgb2.showL()
		).prList(" -> ", " ∆ ${rgb0.minus(rgb2).toLaconicStringRGB()}")
	}
    }

    @Test
    fun testBoolean() {
	println("testBoolean")
	listOf(true, false).forEach { n ->
	    println("${n.ansiColor()}")
	}
    }

    @Test
    fun testColor() {
	println("testColor")
	listOf(Color.LegacyColor.M, Color.LegacyColor.R, Color.LegacyColor.G, Color.LegacyColor.B).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor2() {
	println("testColor2")
	listOf(Color.LegacyColor.M, Color.LegacyColor.R, Color.LegacyColor.G, Color.LegacyColor.B).forEach {
	    println("${it.toString().ansiBgColor(it)}")
	}
    }

    @Test
    fun testColorB() {
	println("testColorB")
	listOf(Color.LegacyColor.M, Color.LegacyColor.R, Color.LegacyColor.G, Color.LegacyColor.B).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor5() {
	println("testColor5")
	listOf(Color5.Color5.MAGENTA, Color5.Color5.RED, Color5.Color5.GREEN, Color5.Color5.BLUE).forEach {
	    println("${it.toString().ansiColor(it, 3)}")
	}
    }

    @Test
    fun testHueGradientRed() {
	println("testHueGradientRed")
	val rgbBase = Color2.RGB(256, 255, 0, 0)
	val rgb256 = rgbBase.toCubeSize(256)
	rgbBase.also { listOf(showRGB(it), it.toHsv().showC()).prList() }
	println()
	val g1 = rgb256.hueGradient(18)
	g1.forEach {
	    listOf(showRGB(it), it.toHsv().showC()).prList()
	}
    }

    @Test
    fun testHueGradient() {
	println("testHueGradient")
	val rgbBase = Color2.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val rgb256 = rgbBase.toCubeSize(256)
	rgbBase.also { listOf(showRGB(it), it.toHsv().showC()).prList() }
	println()
	val g1 = rgb256.hueGradient(18)
	g1.forEach {
	    listOf(showRGB(it), it.toHsv().showC()).prList()
	}
    }

    @Test
    fun testHueGradient2() {
	println("testHueGradient2")
	val rgbBase = Color2.RGB(8, 1, 2, 6)
	rgbBase.also { listOf(showRGB(it), it.toHsv().showC()).prList() }
	val rgb256 = rgbBase.toCubeSize(256)
	rgb256.also { listOf(showRGB(it), it.toHsv().showC()).prList() }
	println()
	val g1 = rgbBase.hueGradient(12)
	g1.forEach {
	    listOf(showRGB(it), it.toHsv().showC()).prList()
	}
    }


    @Test
    fun testSaturationGradient6() {
	println("testSaturationGradient6")
	val rgbBase = Color2.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	rgbBase.also { listOf(showRGB(it), it.toHsv().showC()).prList() }
	println()
	val g1 = rgbBase.saturationGradient(12)
	g1.forEach {
	    listOf(showRGB(it), it.toHsv().showC()).prList()
	}
	println()

	val hsvBase = rgbBase.toHsv()
	val g2 = hsvBase.gradient(12, hsvBase.clone(s = 1.0))
	g2.reversed().forEach {
	    listOf(showRGB(it.toRGB()), it.toRGB().toHsv().showC()).prList()
	}
	println()

	val g3 = hsvBase.gradient(12, hsvBase.clone(s = 0.0))
	g3.forEach {
	    listOf(showRGB(it.toRGB()), it.showC()).prList()
	}
    }

    @Test
    fun testValueGradient6() {
	println("testValueGradient6")
	val rgbBase = Color2.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	rgbBase.also { listOf(showRGB(it), it.toHsv().showC()).prList() }
	println()

	val g1 = rgbBase.valueGradient(12)
	g1.forEach {
	    listOf(showRGB(it), it.toHsv().showC()).prList()
	}
	println()

	val hsvBase = rgbBase.toHsv()
	val g2 = hsvBase.gradient(12, hsvBase.clone(v = 1.0))
	g2.reversed().forEach {
	    listOf(showRGB(it.toRGB()), it.showC()).prList()
	}
	println()

	val g3 = hsvBase.gradient(12, hsvBase.clone(v = 0.0))
	g3.forEach {
	    listOf(showRGB(it.toRGB()), it.showC()).prList()
	}
    }

    @Test
    fun testHueGradientColCol() {
	println("testHueGradientColCol")
	val rgbBase = Color2.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val rgbBase2 = Color2.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	println("    ${" $rgbBase ".rgbBg(rgbBase)} ${" ${rgbBase.toHsv()} ".rgbBg(rgbBase)}")
	println("    ${" $rgbBase2 ".rgbBg(rgbBase2)} ${" ${rgbBase2.toHsv()} ".rgbBg(rgbBase2)}")
	println()
	val g1 = rgbBase.gradient(12, rgbBase2)
	g1.forEach {
	    listOf(showRGB(it), it.toHsv().showC(), it.complement().toHsv().showC(), it.inverse().toHsv().showC()).prList()
	}
    }

    @Test
    fun testHueGradientA() {
	println("testHueGradientA")
	val rgbBase = Color2.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val rgbBase2 = Color2.RGB(8, 7, 7, 0)
	println("    ${" $rgbBase ".rgbBg(rgbBase)} ${" ${rgbBase.toHsv()} ".rgbBg(rgbBase)}")
	println("    ${" $rgbBase2 ".rgbBg(rgbBase2)} ${" ${rgbBase2.toHsv()} ".rgbBg(rgbBase2)}")
	println()
	rgbBase.gradient(12, rgbBase2).forEach {
	    listOf(showRGB(it), it.toHsv().showC(), it.complement().toHsv().showC(), it.inverse().toCubeSize(8).showC()).prList()
	}
    }

    @Test
    fun testHueGradientAS() {
	println("testHueGradientAS")
	val rgbBase = Color2.RGB(8, 2, 5, 1)
	val rgbBase2 = Color2.RGB(8, 7, 7, 0)
	println("    ${" $rgbBase ".rgbBg(rgbBase)} ${" ${rgbBase.toHsv()} ".rgbBg(rgbBase)}")
	println("    ${" $rgbBase2 ".rgbBg(rgbBase2)} ${" ${rgbBase2.toHsv()} ".rgbBg(rgbBase2)}")
	println()
	rgbBase.gradient(12, rgbBase2).forEach {
	    listOf(showRGB(it), it.toHsv().showC(), it.complement().toHsv().showC(), it.inverse().toHsv().showC()).prList()
	}
    }

    @Test
    fun testValMin() {
	println("testValMin")
	val rgbRand = Color2.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val max2 = 12

	val g1 = rgbRand.gradient(max2, rgbRand.toHsv().clone(v = 0.0).toRGB())
	val g2 = rgbRand.gradient(max2, rgbRand.toHsv().clone(v = 0.05).toRGB())
	val g3 = rgbRand.toHsv().gradient(max2, rgbRand.toHsv().clone(v = 0.0))

	(0..<max2).forEach { n ->
	    println("${showRGB(g1[n])} ${showRGB(g2[n])} ${showRGB(g3[n])}")
	}

	println()
    }

    @Test
    fun testAverage() {
	println("testAverage rgb")
	val max2 = 12
	val cubeSize = 100

	val rgbRand = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))
	(0..<max2).forEach { n ->
	    val rgbRand2 = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))

	    val rgbAv = rgbRand.average(rgbRand2)

	    println("${showRGB(rgbRand)} ${showRGB(rgbAv)} ${showRGB(rgbRand2)}")
	}

	println()
    }

    @Test
    fun testAverageHue() {
	println("testAverage Hue")
	val max2 = 12
	val cubeSize = 100

	val rgbRand = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))
	(0..<max2).forEach { n ->
	    val rgbRand2 = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))

	    val rgbAv = rgbRand.toHsv().averageHue(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)

	    println("${showRGB(rgbRand)} ${showRGB(rgbAv)} ${showRGB(rgbRand2)}")
	}

	println()
    }

    @Test
    fun testAverageSaturation() {
	println("testAverage Saturation")
	val max2 = 12
	val cubeSize = 100

	val rgbRand = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))
	(0..<max2).forEach { n ->
	    val rgbRand2 = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))

	    val rgbAv = rgbRand.toHsv().averageSaturation(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)

	    println("${showRGB(rgbRand)} ${showRGB(rgbAv)} ${showRGB(rgbRand2)}")
	}

	println()
    }

    @Test
    fun testAverageValue() {
	println("testAverage Value")
	val max2 = 12
	val cubeSize = 100

	val rgbRand = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))
	(0..<max2).forEach { n ->
	    val rgbRand2 = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))

	    val rgbAv = rgbRand.toHsv().averageValue(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)

	    println("${showRGB(rgbRand)} ${showRGB(rgbAv)} ${showRGB(rgbRand2)}")
	}
	println()
    }

    @Test
    fun testAverageHSV() {
	println("testAverage HSV")
	val max2 = 12
	val cubeSize = 100

	val rgbRand = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))
	(0..<max2).forEach { n ->
	    val rgbRand2 = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))

	    val rgbAv = rgbRand.toHsv().average(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)

	    println("${showRGB(rgbRand)} ${showRGB(rgbAv)} ${showRGB(rgbRand2)}")
	}
	println()
    }

    @Test
    fun testAverageRGBHSV() {
	println("testAverage Color2 HSV")
	val max2 = 12
	val cubeSize = 100

	val rgbRand = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))
	(0..<max2).forEach { n ->
	    val rgbRand2 = Color2.RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))

	    val rgbAv = rgbRand.average(rgbRand2)
	    val hsvAv = rgbRand.toHsv().average(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)

	    println(
		"${
		    rgbRand.showC(20, f = RGB::toLaconicStringRGB)
		} ${
		    rgbAv.showC(20, f = RGB::toLaconicStringRGB)
		}  ${
		    hsvAv.showC(20, f = RGB::toLaconicStringRGB)
		} ${
		    rgbRand2.showC(20, f = RGB::toLaconicStringRGB)
		}"
	    )
	}
	println()
    }
}
