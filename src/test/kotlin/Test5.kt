import femtioprocent.ansi.Color5
import kotlin.test.Test

class Test5 {
    val verbose = false

    @Test
    fun testColor5() {
	Color5.ColorValue.entries.forEach {cv ->
	    println("Color5: ${Color5.fg5(cv, cv.toString())}")
	}
    }
}
