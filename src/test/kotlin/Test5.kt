import femtioprocent.ansi.Color5
import femtioprocent.ansi.ColorCS
import femtioprocent.ansi.ColorCS.rgbFg
import kotlin.test.Test

class Test5 {
    val verbose = false

    @Test
    fun testColor5() {
	Color5.ColorValue.entries.forEach {cv ->
	    println("Color5: ${Color5.fg5(cv, cv.toString())}")
            val b = ColorCS.RGB(0.3, 0.7, 0.999)
            println("ColorRGB: ${"hello".rgbFg(b)}")
        }
    }
}
