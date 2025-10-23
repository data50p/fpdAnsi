import femtioprocent.ansi.Color1
import femtioprocent.ansi.Color1.ColorCode
import femtioprocent.ansi.Color5
import femtioprocent.ansi.extentions.ansiBgColor
import femtioprocent.ansi.extentions.ansiColor
import kotlin.test.Test

class Test1 {
    val verbose = false

    val colorCodes = mapOf<String, femtioprocent.ansi.Color1.ColorCode>(
	"RED" to ColorCode.RED,
	"GREEN" to ColorCode.GREEN,
	"BLUE" to ColorCode.BLUE,
	"YELLOW" to ColorCode.YELLOW,
	"CYAN" to ColorCode.CYAN,
	"MAGENTA" to ColorCode.MAGENTA,
	"WHITE" to ColorCode.WHITE,
	"BLACK" to ColorCode.BLACK,
    )

    @Test
    fun testLegacy() {
	colorCodes.entries.forEach { (text, col) ->
	    println(Color1.normal(col, " ${text} normal "))
	    println(Color1.bold(col, " ${text} bold "))
	    println(Color1.faint(col, " ${text} faint "))
	    println(Color1.crossed(col, " ${text} crossed "))
	    println(Color1.hiBoldIntensity(col, " ${text} bold intense "))
	    println(Color1.underline(col, " ${text} underline "))
	    println(Color1.italic(col, " ${text} italic "))
	    println(Color1.background(col, " ${text} background "))
	    println(Color1.hiIntensityBackground(col, " ${text} intense background "))
	}
    }

    @Test
    fun testLegacy2() {
	colorCodes.forEach { fg ->
	    colorCodes.forEach { bg ->
		println(Color1.normal(fg.value, bg.value, "    ${fg.key} text on ${bg.key} background    "))
	    }
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
	listOf(ColorCode.MAGENTA, ColorCode.RED, ColorCode.GREEN, ColorCode.BLUE).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor2() {
	println("testColor2")
	listOf(ColorCode.MAGENTA, ColorCode.RED, ColorCode.GREEN, ColorCode.BLUE).forEach {
	    println("${it.toString().ansiBgColor(it)}")
	}
    }

    @Test
    fun testColorB() {
	println("testColorB")
	listOf(ColorCode.MAGENTA, ColorCode.RED, ColorCode.GREEN, ColorCode.BLUE).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor5() {
	println("testColor5")
	listOf(Color5.Color5Code.MAGENTA, Color5.Color5Code.RED, Color5.Color5Code.GREEN, Color5.Color5Code.BLUE).forEach {
	    println("${it.toString().ansiColor(it, 3)}")
	}
    }
}
