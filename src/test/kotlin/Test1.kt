import femtioprocent.ansi.Color1
import femtioprocent.ansi.Color1.Code
import femtioprocent.ansi.Color5
import femtioprocent.ansi.extentions.ansiBgColor
import femtioprocent.ansi.extentions.ansiColor
import kotlin.test.Test

class Test1 {
    val verbose = false

    val codes = mapOf<String, femtioprocent.ansi.Color1.Code>(
	"RED" to Code.RED,
	"GREEN" to Code.GREEN,
	"BLUE" to Code.BLUE,
	"YELLOW" to Code.YELLOW,
	"CYAN" to Code.CYAN,
	"MAGENTA" to Code.MAGENTA,
	"WHITE" to Code.WHITE,
	"BLACK" to Code.BLACK,
    )

    @Test
    fun testLegacy() {
	codes.entries.forEach { (text, col) ->
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
	codes.forEach { fg ->
	    codes.forEach { bg ->
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
	listOf(Code.MAGENTA, Code.RED, Code.GREEN, Code.BLUE).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor2() {
	println("testColor2")
	listOf(Code.MAGENTA, Code.RED, Code.GREEN, Code.BLUE).forEach {
	    println("${it.toString().ansiBgColor(it)}")
	}
    }

    @Test
    fun testColorB() {
	println("testColorB")
	listOf(Code.MAGENTA, Code.RED, Code.GREEN, Code.BLUE).forEach {
	    println("${it.toString().ansiColor(it)}")
	}
    }

    @Test
    fun testColor5() {
	println("testColor5")
	listOf(Color5.Code.MAGENTA, Color5.Code.RED, Color5.Code.GREEN, Color5.Code.BLUE).forEach {
	    println("${it.toString().ansiColor(it, 3)}")
	}
    }
}
