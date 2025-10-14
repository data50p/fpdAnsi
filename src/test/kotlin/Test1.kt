import femtioprocent.ansi.extentions.ansiBgColor
import femtioprocent.ansi.extentions.ansiColor
import femtioprocent.ansi.Ansi
import femtioprocent.ansi.Ansi.rgbBg
import femtioprocent.ansi.appl.showRGB
import femtioprocent.ansi.appl.frmCS
import femtioprocent.ansi.appl.showHSV
import femtioprocent.ansi.appl.prList
import kotlin.random.Random
import kotlin.test.Test

class Test1 {

    @Test
    fun testM1() {
	(1..256).forEach { n ->
	    val l = Ansi.Support.values256(n)
	    assert(l.size == n)
	    assert(l.first() == 0)
	    if (l.size > 1)
		assert(l.last() == 255)
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
	listOf(Ansi.LegacyColor.M, Ansi.LegacyColor.R, Ansi.LegacyColor.G, Ansi.LegacyColor.B).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor2() {
	println("testColor2")
	listOf(Ansi.LegacyColor.M, Ansi.LegacyColor.R, Ansi.LegacyColor.G, Ansi.LegacyColor.B).forEach {
	    println("${it.toString().ansiBgColor(it)}")
	}
    }

    @Test
    fun testColorB() {
	println("testColorB")
	listOf(Ansi.Color.M, Ansi.Color.R, Ansi.Color.G, Ansi.Color.B).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor5() {
	println("testColor5")
	listOf(Ansi.Color5.MAGENTA, Ansi.Color5.RED, Ansi.Color5.GREEN, Ansi.Color5.BLUE).forEach {
	    println("${it.toString().ansiColor(it, 3)}")
	}
    }

    @Test
    fun testHueGradientRed() {
	println("testHueGradientRed")
	val rgbBase = Ansi.RGB(256, 255, 0, 0)
	val rgb256 = rgbBase.toCubeSize(256)
	rgbBase.also { listOf(showRGB(it), showHSV(it)).prList() }
	println()
	val g1 = rgb256.hueGradient(18)
	g1.forEach {
	    listOf(showRGB(it), showHSV(it)).prList()
	}
    }

    @Test
    fun testHueGradient() {
	println("testHueGradient")
	val rgbBase = Ansi.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val rgb256 = rgbBase.toCubeSize(256)
	rgbBase.also { listOf(showRGB(it), showHSV(it)).prList() }
	println()
	val g1 = rgb256.hueGradient(18)
	g1.forEach {
	    listOf(showRGB(it), showHSV(it)).prList()
	}
    }

    @Test
    fun testHueGradient2() {
	println("testHueGradient2")
	val rgbBase = Ansi.RGB(8, 1, 2, 6)
	rgbBase.also { listOf(showRGB(it), showHSV(it)).prList() }
	val rgb256 = rgbBase.toCubeSize(256)
	rgb256.also { listOf(showRGB(it), showHSV(it)).prList() }
	println()
	val g1 = rgbBase.hueGradient(12)
	g1.forEach {
	    listOf(showRGB(it), showHSV(it)).prList()
	}
    }


    @Test
    fun testSaturationGradient6() {
	println("testSaturationGradient6")
	val rgbBase = Ansi.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	rgbBase.also { listOf(showRGB(it), showHSV(it)).prList() }
	println()
	val g1 = rgbBase.saturationGradient(12)
	g1.forEach {
	    listOf(showRGB(it), showHSV(it)).prList()
	}
	println()

	val hsvBase = rgbBase.toHsv()
	val g2 = hsvBase.gradient(12, hsvBase.clone(s = 1.0))
	g2.reversed().forEach {
	    listOf(showRGB(it.toRGB()), showHSV(it.toRGB())).prList()
	}
	println()

	val g3 = hsvBase.gradient(12, hsvBase.clone(s = 0.0))
	g3.forEach {
	    listOf(showRGB(it.toRGB()), showHSV(it.toRGB())).prList()
	}
    }

    @Test
    fun testValueGradient6() {
	println("testValueGradient6")
	val rgbBase = Ansi.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	rgbBase.also { listOf(showRGB(it), showHSV(it)).prList() }
	println()

	val g1 = rgbBase.valueGradient(12)
	g1.forEach {
	    listOf(showRGB(it), showHSV(it)).prList()
	}
	println()

	val hsvBase = rgbBase.toHsv()
	val g2 = hsvBase.gradient(12, hsvBase.clone(v = 1.0))
	g2.reversed().forEach {
	    listOf(showRGB(it.toRGB()), showHSV(it.toRGB())).prList()
	}
	println()

	val g3 = hsvBase.gradient(12, hsvBase.clone(v = 0.0))
	g3.forEach {
	    listOf(showRGB(it.toRGB()), showHSV(it.toRGB())).prList()
	}
    }

    @Test
    fun testHueGradientColCol() {
	println("testHueGradientColCol")
	val rgbBase = Ansi.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val rgbBase2 = Ansi.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	println("    ${" $rgbBase ".rgbBg(rgbBase)} ${" ${rgbBase.toHsv()} ".rgbBg(rgbBase)}")
	println("    ${" $rgbBase2 ".rgbBg(rgbBase2)} ${" ${rgbBase2.toHsv()} ".rgbBg(rgbBase2)}")
	println()
	val g1 = rgbBase.gradient(12, rgbBase2)
	g1.forEach {
	    listOf(showRGB(it), showHSV(it), showHSV(it.complement()), showHSV(it.complementRGB())).prList()
	}
    }

    @Test
    fun testHueGradientA() {
	println("testHueGradientA")
	val rgbBase = Ansi.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val rgbBase2 = Ansi.RGB(8, 7, 7, 0)
	println("    ${" $rgbBase ".rgbBg(rgbBase)} ${" ${rgbBase.toHsv()} ".rgbBg(rgbBase)}")
	println("    ${" $rgbBase2 ".rgbBg(rgbBase2)} ${" ${rgbBase2.toHsv()} ".rgbBg(rgbBase2)}")
	println()
	rgbBase.gradient(12, rgbBase2).forEach {
	    listOf(showRGB(it), showHSV(it), showHSV(it.complement()), frmCS(it.complementRGB(), 8)).prList()
	}
    }

    @Test
    fun testHueGradientAS() {
	println("testHueGradientAS")
	val rgbBase = Ansi.RGB(8, 2, 5, 1)
	val rgbBase2 = Ansi.RGB(8, 7, 7, 0)
	println("    ${" $rgbBase ".rgbBg(rgbBase)} ${" ${rgbBase.toHsv()} ".rgbBg(rgbBase)}")
	println("    ${" $rgbBase2 ".rgbBg(rgbBase2)} ${" ${rgbBase2.toHsv()} ".rgbBg(rgbBase2)}")
	println()
	rgbBase.gradient(12, rgbBase2).forEach {
	    listOf(showRGB(it), showHSV(it), showHSV(it.complement()), showHSV(it.complementRGB())).prList()
	}
    }

    @Test
    fun testValMin() {
	println("testValMin")
	val rgbRand = Ansi.RGB(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val max2 = 12

	val g1 = rgbRand.gradient(max2, rgbRand.toHsv().clone(v = 0.0).toRGB())
	val g2 = rgbRand.gradient(max2, rgbRand.toHsv().clone(v = 0.05).toRGB())
	val g3 = rgbRand.toHsv().gradient(max2, rgbRand.toHsv().clone(v = 0.0))

	(0..<max2).forEach { n ->
	    println("${showRGB(g1[n])} ${showRGB(g2[n])} ${showRGB(g3[n])}")
	}

	println()
    }
}