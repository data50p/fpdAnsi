package femtioprocent.ansi.appl

import femtioprocent.ansi.Color2.RGB
import femtioprocent.ansi.Color2.showC
import femtioprocent.ansi.Display
import femtioprocent.ansi.extentions.pL

class AnsiDemoT : AnsiDemo() {

    override fun demo(narg: Int) {
	val rgb = randomRGB(256)

	listOf<RGB>(rgb, rgb.byName("rotL"), rgb.byNames(listOf("rotL", "compl")), rgb.byNames(listOf("rotL", "compl", "val+"))).pr()

	val fmt = "%-27s"
	rgb.theComplementary().pr(String.format(fmt, "Complementary"))
	rgb.theAnalogous(0.06).pr(String.format(fmt, "Analogous"))
	rgb.theSplitComplementary(0.06).pr(String.format(fmt, "Split Complementary"))
	rgb.theTriadic().pr(String.format(fmt, "Triadic"))
	rgb.theDoubleComplementary(0.1).pr(String.format(fmt, "Double Complementary"))
	rgb.theSquareTetradic().pr(String.format(fmt, "Square Tetradic"))
	rgb.theDoubleSplitComplementary().pr(String.format(fmt, "Double Split Complementary"))


	println()
	var c1 = rgb.toValue(0.1)
	repeat(16) {
	    c1 = c1.byName("val+")
	    print(" ${c1.showC(w = 8, f = { "    " })}")
	}
	println()
	c1 = rgb.toValue(1.0)
	repeat(16) {
	    c1 = c1.byName("val-")
	    print(" ${c1.showC(w = 8, f = { "    " })}")
	}
	println()
	c1 = rgb.toSaturation(0.1)
	repeat(16) {
	    c1 = c1.byName("sat+")
	    print(" ${c1.showC(w = 8, f = { "    " })}")
	}
	println()

	println("Theme")

	val shadow = false

	val lowSatList = mutableListOf<RGB>()
	with(rgb) {
	    lowSatList += toSaturation(0.03).toValue(0.97)
	    lowSatList += toSaturation(0.06).toValue(0.95)
	    lowSatList += toSaturation(0.1).toValue(0.85)
	    lowSatList += toSaturation(0.1).toValue(0.70)
	    lowSatList += toSaturation(0.3).toValue(0.90)
	    lowSatList += toSaturation(0.5).toValue(0.75)
	}
	val lowSatList2 = mutableListOf<RGB>()
	lowSatList.forEach { lowSatList2 += it; lowSatList2 += it }

	lowSatList.pr("lowSat: ")

	lowSatList2.forEachIndexed { ix, rgbTheme ->
	    val z2 = ix % 2 == 0
	    val z = if (z2) RGB::theSplitComplementary else RGB::theAnalogous

	    val colZ = if (z2) rgb.theSplitComplementary(0.06) else rgb.theAnalogous(0.06)

	    val theme1 = listOf<RGB>(
		rgbTheme,
		rgbTheme.byNames(listOf("val+", "val+", "val+", "val+", "val+", "val+", "val+", "val+", "val+", "val+", "val+")),
		rgbTheme.toValue(0.8),
		rgbTheme.byNames(listOf("val-", "val-", "val-")),
		z(rgbTheme, if (z2) 0.16 else 0.08)[1],
		z(rgbTheme, if (z2) 0.16 else 0.08)[2],
		colZ[1],
		colZ[2],
	    )

	    val theme2 = listOf<RGB>(
		theme1[0],
		theme1[0].toValue(theme1[0].toHsv().v * 0.9),
		theme1[0].toSaturation(theme1[0].toHsv().s * 0.5),
		theme1[4],
		theme1[5],
		theme1[1].theSquareTetradic()[1],
		theme1[1].theSquareTetradic()[3],
		theme1[0].toSaturation(0.1).toValue(0.7),
		theme1[6].toSaturation(0.6).toMaxValue(),
		theme1[7].toSaturation(0.6).toMaxValue(),
	    )

	    theme1.pr("Theme1 ${ix.toString().pL(2)} ")
	    theme2.pr("Theme2 ${ix.toString().pL(2)} ")

	    println()
	    println(rgbTheme.toHsv().showC())
	    println()

	    val display = Display(125, 24)

	    fun prRect(x: Int, y: Int, w: Int, h: Int, col: RGB, s: String) {
		display.rect(x, y, w, h, col)
		val prfx = if (s.isNotEmpty()) "$s\n" else ""
		display.setText(x, y, "$prfx${col.toLaconicStringRGB().replace(",", "\n")}")
	    }


	    var xx = 0
	    var yy = 0

	    display.fill(theme1[1])

	    prRect(0, 0, 15, 5, theme1[1], "")

	    if (shadow) display.rect(5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(5, yy + 3, 15, 5, theme1[0], "Hello World")

	    xx = 25
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[0].toValue(theme1[0].toHsv().v * 0.9), "little darker")

	    xx = 50
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[0].toSaturation(theme1[0].toHsv().s * 0.5), "low sat")

	    xx = 75
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[4], if (z2) "compl" else "analogue")

	    xx = 100
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[5], if (z2) "compl" else "analogue")


	    xx = 0
	    yy = 10
	    if (shadow) display.rect(5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(5, yy + 3, 15, 5, theme1[1].theSquareTetradic()[1], "left")

	    xx = 25
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[1].theSquareTetradic()[3], "right")

	    xx = 50
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[0].toSaturation(0.1).toValue(0.7), "lowsat+darker")

	    xx = 75
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[6].toSaturation(0.6).toMaxValue(), "sat")

	    xx = 100
	    if (shadow) display.rect(xx + 5 + 1, yy + 3 + 1, 15, 5, theme1[2])
	    prRect(xx + 5, yy + 3, 15, 5, theme1[7].toSaturation(0.6).toMaxValue(), "sat")

	    display.print(false)
	}
    }
}