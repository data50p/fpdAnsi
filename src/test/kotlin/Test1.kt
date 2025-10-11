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
}