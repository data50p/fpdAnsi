package femtioprocent.ansi

/**
 * Write escape sequences for colored output.
 *
 *  https://en.wikipedia.org/wiki/ANSI_escape_code#8-bit
 *
 * A stack keep track of pushed color state, look in main at end for examples.
 *
 * @author larsno
 */


object Ansi {

    fun CSI(suffix: String = ""): String {
	return "\u001b[$suffix"
    }

    fun hideCursor(): String {
	return CSI("?25l")
    }

    fun showCursor(): String {
	return CSI("?25h")
    }

    fun goto(x: Int, y: Int): String {
	return "\u001b[${y + 1};${x + 1}H"
    }

    fun clear(): String {
	return "\u001b[2J"
    }





    // ---------- CSI for color type 5 (6 bits) ----------





    // ---------------------------------------------------- Legacy Color -----------------------------------------------------



    /** Make sure to handle subtile rounding effect */
    fun Double.modSpecial(d: Double): Double {

	val m = (this - 0.0000000001) % d
	//println("mod: $this $d -> $m")
	return m
    }


}

fun main() {

    println(Ansi.clear())
    (1..30).forEach { xy ->
	println(Ansi.goto(xy, xy) + "I'm at ($xy $xy)")
    }
}
