import femtioprocent.sundry.Ansi
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class Test1 {

    @Test
    fun testM1() {
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 32, 64, 128, 256).forEach { n ->
            val cv1 = Ansi.CubeValue(n, 1, 2, 3)
            println("$cv1")
            val l = Ansi.Support.color2ValuesForColorCubeSizeAlt(n)
            println("$l")
            val l2 = Ansi.Support.color2ValuesForColorCubeSize(n)
            println("$l2")
            for (i in (0..<n)) {
                val d = l[i] - l2[i]
                print(" $d")
//                assert(d == -1 || d == 0 || d == 1)
            }
            println()
        }
    }
}