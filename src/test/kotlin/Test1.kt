import femtioprocent.ansi.extentions.ansiBgColor
import femtioprocent.ansi.extentions.ansiColor
import femtioprocent.sundry.Ansi
import femtioprocent.sundry.Ansi.cvBg
import kotlin.random.Random
import kotlin.test.Test

class Test1 {

    fun pr(cv: Ansi.CubeValue) : String {
	return "   $cv ".cvBg(cv)
    }

    fun prHSV(cv: Ansi.CubeValue) : String {
	return "   ${cv.toHsv()} ".cvBg(cv)
    }

    fun pr256(cv: Ansi.CubeValue) : String {
	return "   ${cv.toCubeSize(256)} ".cvBg(cv)
    }

    fun prCS(cv: Ansi.CubeValue, cs: Int) : String {
	return "   ${cv.toCubeSize(cs)} ".cvBg(cv)
    }

    @Test
    fun testM1() {
        (1..256).forEach { n ->
            val l = Ansi.Support.values256(n)
            println("${l.size} $l")
        }
    }

    @Test
    fun testBoolean() {
        listOf(true, false).forEach { n ->
            println("${n.ansiColor()}")
        }
    }

    @Test
    fun testColor() {
        listOf(Ansi.LegacyColor.M, Ansi.LegacyColor.R, Ansi.LegacyColor.G, Ansi.LegacyColor.B).forEach {
            println("${it.toString().ansiColor(it)}")
        }
    }

    @Test
    fun testColor2() {
        listOf(Ansi.LegacyColor.M, Ansi.LegacyColor.R, Ansi.LegacyColor.G, Ansi.LegacyColor.B).forEach {
            println("${it.toString().ansiBgColor(it)}")
        }
    }

    @Test
    fun testColorB() {
        listOf(Ansi.Color.M, Ansi.Color.R, Ansi.Color.G, Ansi.Color.B).forEach {
            println("${it.toString().ansiColor(it)}")
        }
    }

    @Test
    fun testColor5() {
        listOf(Ansi.Color5.MAGENTA, Ansi.Color5.RED, Ansi.Color5.GREEN, Ansi.Color5.BLUE).forEach {
            println("${it.toString().ansiColor(it, 3)}")
        }
    }

    @Test
    fun testHueGradient() {
        val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
        val cv256 = cvBase.toCubeSize(256)
        println("${" $cvBase ".cvBg(cvBase)}  ${" ${cvBase.toHsv()} ".cvBg(cvBase)}   ${" $cv256 ".cvBg(cv256)}\n")
        val g1 = cv256.hueGradient(12)
        g1.map { it.first }.forEach {
            println("${"      $it ".cvBg(it)}")
        }
    }

    @Test
    fun testHueGradient2() {
        val cvBase = Ansi.CubeValue(8, 0, 1, 7)
        println("${" $cvBase ".cvBg(cvBase)}")
        val cv256 = cvBase.toCubeSize(256)
        println("${" $cv256 ".cvBg(cv256)}")
        val g1 = cvBase.hueGradient(12)
        g1.map { it.first }.forEach {
            println("${" $it ".cvBg(it)}")
        }
    }

    @Test
    fun testSaturationGradient3() {
        val cvBase = Ansi.CubeValue(8, 0, 1, 7)
        println("${" $cvBase ".cvBg(cvBase)}")
        val g1 = cvBase.saturationGradientValues(12)
        g1.forEach {
            println("${" $it ".cvBg(it)}")
        }
    }

    @Test
    fun testValueGradient4() {
        val cvBase = Ansi.CubeValue(8, 0, 1, 7)
        println("${" $cvBase ".cvBg(cvBase)}")
        val g1 = cvBase.valueGradientValues(12)
        g1.forEach {
            println("${" $it ".cvBg(it)}")
        }
    }

    @Test
    fun testSaturationGradient4() {
        val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
        println("${" $cvBase ".cvBg(cvBase)}  ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
        println()
        val g1 = cvBase.saturationGradientValues(12)
        g1.forEach {
            println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}")
        }
        println()
        val g2 = cvBase.saturationGradientToMax(12).map {it.first}
        g2.reversed().forEach {
            println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}")
        }
        println()
        val g3 = cvBase.saturationGradientToMin(12).map {it.first}
        g3.forEach {
            println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}")
        }
    }

    @Test
    fun tesValueGradient6() {
        val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
        println("${" $cvBase ".cvBg(cvBase)}  ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
        println()
        val g1 = cvBase.valueGradientValues(12)
        g1.forEach {
            println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}")
        }
        println()
        val g2 = cvBase.valueGradientToMax(12).map {it.first}
        g2.reversed().forEach {
            println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}")
        }
        println()
        val g3 = cvBase.valueGradientToMin(12).map {it.first}
        g3.forEach {
            println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}")
        }
    }

    @Test
    fun tesHueGradientColCol2() {
	val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val cvBase2 = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	println("    ${" $cvBase ".cvBg(cvBase)} ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
	println("    ${" $cvBase2 ".cvBg(cvBase2)} ${" ${cvBase2.toHsv()} ".cvBg(cvBase2)}")
	println()
	val g1 = cvBase.gradient(12, cvBase2)
	g1.map{it.first}.forEach {
	    println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}  ${"    ${it.complement().toHsv()} ".cvBg(it.complement())}  ${"    ${it.complementRGB().toHsv()} ".cvBg(it.complementRGB())}")
	}
    }

    @Test
    fun tesHueGradientA() {
	val cvBase = Ansi.CubeValue(8, Random.nextInt(8), Random.nextInt(8), Random.nextInt(8))
	val cvBase2 = Ansi.CubeValue(8, 7, 7, 0)
	println("    ${" $cvBase ".cvBg(cvBase)} ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
	println("    ${" $cvBase2 ".cvBg(cvBase2)} ${" ${cvBase2.toHsv()} ".cvBg(cvBase2)}")
	println()
	cvBase.gradient(12, cvBase2).map{it.first}.forEach {
	    println("${"     $it ".cvBg(it)}  ${" ${it.toHsv()} ".cvBg(it)}  ${"    ${it.complement().toHsv()} ".cvBg(it.complement())}  ${"    ${it.complementRGB().toHsv()} ".cvBg(it.complementRGB())}")
	}
    }

    @Test
    fun tesHueGradientAS() {
	val cvBase = Ansi.CubeValue(8, 2,5,1)
	val cvBase2 = Ansi.CubeValue(8, 7, 7, 0)
	println("    ${" $cvBase ".cvBg(cvBase)} ${" ${cvBase.toHsv()} ".cvBg(cvBase)}")
	println("    ${" $cvBase2 ".cvBg(cvBase2)} ${" ${cvBase2.toHsv()} ".cvBg(cvBase2)}")
	println()
	cvBase.gradient(12, cvBase2).map{it.first}.forEach {
	    listOf(pr(it), prHSV(it), prHSV(it.complement()), prHSV(it.complementRGB())).forEach { print(" $it") }; println()
	}
    }
}