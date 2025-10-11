import femtioprocent.sundry.Ansi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class Test1 {

    @Test
    fun testM1() {
        (1..256).forEach { n ->
            val cv1 = Ansi.CubeValue(n, 1, 2, 3)
            println("$cv1")
            val l = Ansi.Support.color2ValuesForColorCubeSizeAlt(n)
            println("$l")
            val l2 = Ansi.Support.color2ValuesForColorCubeSizeAlt(n)
            println("$l2")
            for (i in (0..<n)) {
                assert(l[i] == l2[i])
            }
        }
    }
}