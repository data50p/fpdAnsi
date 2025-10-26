package femtioprocent.ansi.appl

import femtioprocent.ansi.Ansi
import femtioprocent.ansi.Color2.RGB
import femtioprocent.ansi.Display
import kotlin.time.Duration
import kotlin.time.measureTime

class AnsiDemoA : AnsiDemo() {

    override fun demo() {
	val display = Display(250, 75)

	val rgbRand00 = randomRGB(256)

	val item = display.MovingItem(0, 0, RGB(2, 1, 1, 1), "Hello", 1, 1)
	val item2 = display.MovingItem(20, 12, RGB(2, 1, 1, 1), "World", -1, 2)
	val item3 = display.MovingItem(60, 20, RGB(2, 1, 1, 1), "Hello", 2, 1)
	val item4 = display.MovingItem(20, 12, RGB(2, 1, 1, 1), "World", -2, -2)
	val items = listOf<Display.MovingItem>(item, item2, item3, item4)

	var mt1 = Duration.ZERO
	val frames = 1250
	print(Ansi.hideCursor() + Ansi.goto(0, 0) + Ansi.clear())
	repeat(frames) { frame ->
	    val verb = true
	    val rows = display.h
	    true.let {
		val other = rgbRand00.toValue(frame / frames.toDouble()) //randomRGB(256)//.toSaturation(Random.nextDouble(1.0))
		val rgbRand0 = rgbRand00.toMaxValue().rotL().average(other)
		print(Ansi.hideCursor() + Ansi.goto(0, rows + 1))
		//println("Base color: ${rgbRand0.showL()}  ${rgbRand0.toHsv()}  -- other: ${other.showL()}  ${other.toHsv()}")

		mt1 = measureTime {
		    repeat(rows) { row ->
			if (frame % 8 == 0) {
			    val value = row.toDouble() / rows
			    val hsv = rgbRand0.toHsv()
			    val rgbRand = rgbRand0.toHsv().gradient(rows, rgbRand0.toHsv().clone(s = value))
			    var rgb2 = rgbRand[row].toRGB()
			    //if ( verb ) println("other color: $value ${hsv} ${rgbRand0.toHsv().clone(s = value)}  -- ${rgbRand0.toHsv().clone(s = value).toRGB()} ${rgb2.showC()}")
			    //rgbRand.forEach { println("  " + it.showR()) }
			    (0..<display.w).forEach { col ->
				rgb2 = rgb2.average(rgb2.toValue(0.15), 0.05 + 0.01 / (9 + col.toDouble()))
				display.set(col, row, rgbRand0, rgb2, ' ')
			    }
			}
		    }
		}
	    }
	    items.forEach { it.step() }
	    val mt2 = measureTime {
		display.print()
		Thread.sleep(16)
	    }
	    //print(Color2.hideCursor() + Color2.goto(0, display.h + 1) + Color2.clear())
	    //println(" mt $mt1 $mt2")
	}
    }
}