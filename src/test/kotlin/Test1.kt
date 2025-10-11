import femtioprocent.ansi.extentions.Extentions.*
import femtioprocent.sundry.Ansi
import kotlin.test.Test

class Test1 {

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
}