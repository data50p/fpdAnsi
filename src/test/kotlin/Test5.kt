import femtioprocent.ansi.Color5
import femtioprocent.ansi.ColorCube
import femtioprocent.ansi.ColorCube.rgbFg
import kotlin.test.Test

class Test5 {
    val verbose = false

    @Test
    fun testColor5() {
        Color5.ColorValue.entries.forEach {cv ->
            println("Color5: ${Color5.fg5(cv, cv.toString())}")
            val b = ColorCube.RGB(0.3, 0.7, 0.999)
            println("ColorRGB: ${"hello".rgbFg(b)}")
        }
    }

    @Test
    fun testColor5Hex() {
        val col = ColorCube.RGB(0.3, 0.7, 0.999)
        val s = col.toCSSColor()
        println("ColorCube: $s")
    }
}
