import femtioprocent.ansi.appl.AnsiDemo
import femtioprocent.ansi.extentions.ansiBgColor
import femtioprocent.ansi.extentions.ansiColor
import femtioprocent.ansi.Ansi
import femtioprocent.ansi.Ansi.cvBg
import femtioprocent.sundry.Sundry
import kotlin.random.Random
import kotlin.test.Test

class Test1 {

    fun String.pR(w: Int = 1) = "%-${w}s".format(this)
    fun String.pL(w: Int = 1) = "%${w}s".format(this)
    fun String.pC(w: Int = 1) = if (w <= length) this else pL(length + (w - length) / 2).pR(w)

    fun pr(cv: Ansi.CubeValue, w: Int = 48) = cv.toString().pR(w).cvBg(cv)
    fun prHSV(cv: Ansi.CubeValue, w: Int = 32) = cv.toHsv().toString().pR(w).cvBg(cv)
    fun prCS(cv: Ansi.CubeValue, cs: Int, w: Int = 44) = cv.toCubeSize(cs).toString().pR(w).cvBg(cv)
    fun pr256(cv: Ansi.CubeValue, w: Int = 48) = prCS(cv, 256, w)


    fun List<Any>.pri(delim: String = "") {
	println(map { " $it" }.joinToString(delim))
    }

    fun List<Any>.frm(delim: String = ""): String {
	return map { " $it" }.joinToString(delim)
    }



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
	val cvBase = Ansi.CubeValue(256, 255, 0, 0)
	val cv256 = cvBase.toCubeSize(256)
	cvBase.also { listOf(pr(it), prHSV(it)).pri() }
	println()
	val g1 = cv256.hueGradient(18)
	g1.forEach {
	    listOf(pr(it), prHSV(it)).pri()
	}
    }

    @Test
    fun testHueGradient() {
	println("testHueGradient")
	val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val cv256 = cvBase.toCubeSize(256)
	cvBase.also { listOf(pr(it), prHSV(it)).pri() }
	println()
	val g1 = cv256.hueGradient(18)
	g1.forEach {
	    listOf(pr(it), prHSV(it)).pri()
	}
    }

    @Test
    fun testHueGradient2() {
	println("testHueGradient2")
	val cvBase = Ansi.CubeValue(8, 1, 2, 6)
	cvBase.also { listOf(pr(it), prHSV(it)).pri() }
	val cv256 = cvBase.toCubeSize(256)
	cv256.also { listOf(pr(it), prHSV(it)).pri() }
	println()
	val g1 = cvBase.hueGradient(12)
	g1.forEach {
	    listOf(pr(it), prHSV(it)).pri()
	}
    }


    @Test
    fun testSaturationGradient6() {
	println("testSaturationGradient6")
	val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	cvBase.also { listOf(pr(it), prHSV(it)).pri() }
	println()
	val g1 = cvBase.saturationGradient(12)
	g1.forEach {
	    listOf(pr(it), prHSV(it)).pri()
	}
	println()

	val hsvBase = cvBase.toHsv()
	val g2 = hsvBase.gradient(12, hsvBase.clone(s = 1.0))
	g2.reversed().forEach {
	    listOf(pr(it.toRGB()), prHSV(it.toRGB())).pri()
	}
	println()

	val g3 = hsvBase.gradient(12, hsvBase.clone(s = 0.0))
	g3.forEach {
	    listOf(pr(it.toRGB()), prHSV(it.toRGB())).pri()
	}
    }

    @Test
    fun testValueGradient6() {
	println("testValueGradient6")
	val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	cvBase.also { listOf(pr(it), prHSV(it)).pri() }
	println()

	val g1 = cvBase.valueGradient(12)
	g1.forEach {
	    listOf(pr(it), prHSV(it)).pri()
	}
	println()

	val hsvBase = cvBase.toHsv()
	val g2 = hsvBase.gradient(12, hsvBase.clone(v = 1.0))
	g2.reversed().forEach {
	    listOf(pr(it.toRGB()), prHSV(it.toRGB())).pri()
	}
	println()

	val g3 = hsvBase.gradient(12, hsvBase.clone(v = 0.0))
	g3.forEach {
	    listOf(pr(it.toRGB()), prHSV(it.toRGB())).pri()
	}
    }

    @Test
    fun testHueGradientColCol() {
	println("testHueGradientColCol")
	val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val cvBase2 = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	println("    ${" $cvBase ".cvBg(cvBase)} ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
	println("    ${" $cvBase2 ".cvBg(cvBase2)} ${" ${cvBase2.toHsv()} ".cvBg(cvBase2)}")
	println()
	val g1 = cvBase.gradient(12, cvBase2)
	g1.forEach {
	    listOf(pr(it), prHSV(it), prHSV(it.complement()), prHSV(it.complementRGB())).pri()
	}
    }

    @Test
    fun testHueGradientA() {
	println("testHueGradientA")
	val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val cvBase2 = Ansi.CubeValue(8, 7, 7, 0)
	println("    ${" $cvBase ".cvBg(cvBase)} ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
	println("    ${" $cvBase2 ".cvBg(cvBase2)} ${" ${cvBase2.toHsv()} ".cvBg(cvBase2)}")
	println()
	cvBase.gradient(12, cvBase2).forEach {
	    listOf(pr(it), prHSV(it), prHSV(it.complement()), prCS(it.complementRGB(), 8)).pri()
	}
    }

    @Test
    fun testHueGradientAS() {
	println("testHueGradientAS")
	val cvBase = Ansi.CubeValue(8, 2, 5, 1)
	val cvBase2 = Ansi.CubeValue(8, 7, 7, 0)
	println("    ${" $cvBase ".cvBg(cvBase)} ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
	println("    ${" $cvBase2 ".cvBg(cvBase2)} ${" ${cvBase2.toHsv()} ".cvBg(cvBase2)}")
	println()
	cvBase.gradient(12, cvBase2).forEach {
	    listOf(pr(it), prHSV(it), prHSV(it.complement()), prHSV(it.complementRGB())).pri()
	}
    }

    @Test
    fun testValMin() {
	println("testValMin")
	val cvRand = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val max2 = 12

	val g1 = cvRand.gradient(max2, cvRand.toHsv().clone(v = 0.0).toRGB())
	val g2 = cvRand.gradient(max2, cvRand.toHsv().clone(v = 0.05).toRGB())
	val g3 = cvRand.toHsv().gradient(max2, cvRand.toHsv().clone(v = 0.0)).map {it.toRGB()}

	(0..<max2).forEach { n ->
	    println("${pr(g1[n])} ${pr(g2[n])} ${pr(g3[n])}")
	}

	println()
    }
}