package femtioprocent.ansi.appl

import femtioprocent.ansi.Ansi
import femtioprocent.ansi.Ansi.RGB
import femtioprocent.ansi.Ansi.rgbBg
import femtioprocent.ansi.Ansi.rgbFg
import femtioprocent.ansi.Ansi.showC
import femtioprocent.ansi.Ansi.showL
import femtioprocent.ansi.Version
import femtioprocent.ansi.extentions.pC
import femtioprocent.ansi.extentions.pL
import femtioprocent.ansi.extentions.pR
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.measureTime

const val lorem =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum urna elit, viverra in eros nec, accumsan fringilla leo." +
	    " Aliquam dolor sapien, gravida eu lobortis ut, ornare eu odio. Donec aliquet pharetra ligula, eu dapibus dui egestas quis." +
	    " Proin et fermentum nibh. Nulla ullamcorper rutrum sapien id tristique. Phasellus viverra facilisis augue, eget ullamcorper sapien lacinia quis." +
	    " Integer ultricies, libero vel luctus sodales, ipsum sem maximus eros, quis mattis justo nisl vel risus." +
	    " Vivamus mi diam, interdum a laoreet placerat, feugiat sit amet purus. Phasellus vulputate semper finibus." +
	    " Integer maximus eleifend pharetra. Nulla congue eleifend finibus. Nunc posuere varius ornare." +
	    " Maecenas pharetra auctor elit, non accumsan felis elementum id. Maecenas at rhoncus purus. Curabitur nec mi ac mi mollis pulvinar ut ut arcu."

val loremList = lorem.replace(".", "").replace(",", "").split(" ")
fun lorem(m: Int, n: Int): String = loremList.drop(m).take(n).joinToString(" ")

fun List<Any>.frmList(delim: String = "", suffix: String = ""): String {
    return map { it }.joinToString(delim) + suffix
}

fun List<Any>.prList(delim: String = "", suffix: String = "") {
    println(frmList(delim) + suffix)
}

class AnsiDemo {

    fun main(arg: String) {
	println(Version.info())
	ansiColorDemo(arg)
    }

    private fun ansiColorDemo(arg: String) {

	var narg: Int = try {
	    val num = arg.filter { it >= '0' && it <= '9' }
	    if (num.isNotEmpty())
		num.toInt()
	    else
		0
	} catch (_: NumberFormatException) {
	    0
	}

	fun Am(ch: String): Boolean {
	    return arg.isEmpty() || arg.contains(ch)
	}

	when {
//<editor-fold desc="empty arg">
	    arg.isEmpty()
		    || Am("h") -> {
		println("--ansi=D      ->  Dump legacy colors")
		println("--ansi=d      ->  demo")

		println("--ansi=ca     ->  Dump type 5 colors")
		println("--ansi=cb     ->  Type 5 colors (6 bits)")
		println("--ansi=cc(#)  ->  Type 2 colors (8 bits), # = color cube size")
		println("--ansi=G(#)   ->  Gradients on colors, # = color cube size")
		println("--ansi=L      ->  Logs demo")
		println("--ansi=h      ->  https://en.wikipedia.org/wiki/Help!")
		System.exit(2)
	    }
//</editor-fold>

	    Am("D")            -> {
		Ansi.dumpColor5().forEach { println(it) }
	    }

	    Am("d")            -> {
		println("")
		println(Ansi.csFg(4)(1, 2, 3, "XXXXXXXXXXX Ansi.csFg(4)(1, 2, 3, s)"))
		println(Ansi.csBg(4)(3, 2, 1, "XXXXXXXXXXX Ansi.csBg(4)(1, 2, 3, s)"))
		println(Ansi.csFg(4, 3, 2, 3)("XXXXXXXXXXX Ansi.csFg(4, 3, 2, 3)(s)"))
		println(Ansi.csBg(4, 3, 0, 1)("XXXXXXXXXXX Ansi.csBg(4, 3, 0, 1)(s)"))
		println(Ansi.csFgRGB(4)(0, 2, 3)("XXXXXXXXXXX Ansi.csFgRGB(4)(0, 2, 3)(s)"))
		println(Ansi.csBgRGB(4)(3, 0, 2)("XXXXXXXXXXX Ansi.csBgRGB(4)(3, 0, 2)(s)"))
		println()


		val cube4 = Ansi.csRgb(4)
		val cube5 = Ansi.csRgb(5)

		val yellow = cube4(3, 3, 1)
		val lightMagenta = cube4(3, 2, 3)
		val lightGreen = cube4(1, 3, 2)
		val rgb256 = Ansi.csRgb(256)(32, 155, 240)

		val painter = rgbBg(lightGreen)

		((4..16) + listOf(256))
		    .map { lightGreen.toCubeSize(it) }
		    .map { rgb -> rgb to rgb.toCubeSize(4) }
		    .forEach { (rgb, rgb2) -> listOf(lightGreen.showL(), rgb.showL(), rgb2.showL()).prList(" -> ") }

		((4..16) + listOf(256))
		    .map { rgb256.toCubeSize(it) }
		    .map { rgb -> rgb to rgb.toCubeSize(256) }
		    .forEach { (rgb, rgb2) -> listOf(rgb256.showL(), rgb.showL(), rgb2.showL()).prList(" -> ") }
		println()

		((2..256)).forEach { cs ->
		    val rgb = randomRGB(cs)
		    val rgb256 = rgb.toCubeSize(256)
		    val hsv = rgb256.toHsv()
		    val rgb_256 = hsv.toRGB()
		    val rgb_N = rgb_256.toCubeSize(cs)

		    listOf(
			rgb.showL(),
			rgb256.showL(),
			rgb256.toHsv().showL(),
			rgb_256.showL(),
			rgb_N.showL()
		    ).prList(" -> ")
		}


		val lightMagenta256 = lightMagenta.toCubeSize(256)

		val lightGreen256 = lightGreen.toCubeSize(256)
		val lightMagenta4 = lightMagenta256.toCubeSize(4)
		val lightGreen4 = lightGreen256.toCubeSize(4)
		val lightMagenta8 = lightMagenta256.toCubeSize(8)
		val lightGreen8 = lightGreen256.toCubeSize(8)

		println(Ansi.rgbFgBg(yellow, cube5(0, 0, 4), lorem(0, 4)))
		println(Ansi.csFgBg(3, 2, 0, 2, 4, 1, 0, 1, " QWERTY123 "))
		println()

		println(painter("ZZZZZZZZZZZZZ Ansi.rgbBg( Ansi.csrgb(4)(3, 2, 3) )(s)") + "   ${lightGreen}")
		println(rgbBg(lightGreen256)("ZZZZZZZZZZZZZ Ansi.rgbBg( Ansi.csRGB(4)(3, 2, 3) )(s)") + "   ${lightGreen256}")
		println(rgbBg(lightGreen4)("ZZZZZZZZZZZZZ Ansi.rgbBg( Ansi.csRGB(4)(3, 2, 3) )(s)") + "   ${lightGreen4}")
		println(rgbBg(lightGreen8)("ZZZZZZZZZZZZZ Ansi.rgbBg( Ansi.csRGB(4)(3, 2, 3) )(s)") + "   ${lightGreen8}")

		println("ZZZZZZZZZZZZZ  String.rgbFg(Ansi.csRGB(4)(3, 2, 3))".rgbFg(lightMagenta) + "   ${lightMagenta}")
		println("ZZZZZZZZZZZZZ  String.rgbFg(Ansi.csRGB(4)(3, 2, 3))".rgbFg(lightMagenta256) + "   ${lightMagenta256}")
		println("ZZZZZZZZZZZZZ  String.rgbFg(Ansi.csRGB(4)(3, 2, 3))".rgbFg(lightMagenta4) + "   ${lightMagenta4}")
		println("ZZZZZZZZZZZZZ  String.rgbFg(Ansi.csRGB(4)(3, 2, 3))".rgbFg(lightMagenta8) + "   ${lightMagenta8}")

		listOf(lightGreen, lightMagenta, lightGreen256, lightMagenta256).prList(" ")
		println()


		val cellSample1 = "236,120,136"
		val cellSample2 = "·"
		val cellWidth1 = cellSample1.length + 2
		val cellWidth2 = cellSample2.length + 0
		fun formatter1(rgb: RGB) = rgb.toLaconicStringRGB().pC(cellWidth1)
		fun formatter2(_rgb: RGB) = "·".pC(cellWidth2)
		fun formatter3(_rgb: RGB, ch: Char = '·') = "$ch".pC(cellWidth2)

		val red = Ansi.csRgb(256)(255, 0, 0)
		println("RGB Gradient")
		red.hueGradient(12).forEach { it2 ->
		    print("${formatter1(it2)} ".rgbBg(it2))
		}
		println()
		println()

		println("RGB Gradients")
		red.hueGradient(6).forEach { it1 ->
		    it1.hueGradient(6).forEach { it2 ->
			print("${formatter1(it2)} ".rgbBg(it2))
		    }
		    println()
		}
		println()
		println()

		println("RGB Gradient 180°")
		repeat(8) {
		    randomRGB().hueGradient(36, 180.0).forEach { it2 ->
			print(" XX ".rgbBg(it2))
		    }
		    println()
		}
		println()

		println("RGB Gradient -180°")
		repeat(8) {
		    randomRGB().hueGradient(36, -180.0).forEach { it2 ->
			print(" XX ".rgbBg(it2))
		    }
		    println()
		}
		println()

		println("RGB Gradient 45°")
		repeat(8) {
		    randomRGB().hueGradient(36, 45.0).forEach { it2 ->
			print(" XX ".rgbBg(it2))
		    }
		    println()
		}
		println()

		println("Gradient color1 -> color2")
		repeat(8) {
		    val rgbRandG1 = randomRGB()
		    val rgbRandG2 = randomRGB()

		    rgbRandG1.gradient(144, rgbRandG2).forEach { it2 ->
			print("·".rgbBg(it2))
		    }
		    println()
		}
		println()

		true.let {
		    val max2 = 8
		    val cubeSize = 100

		    println("        color-1           RGB              HSV              Hue              Saturation       Value             color-2")
		    repeat(max2) {
			val rgbRand = RGB(cubeSize, Random.nextInt(cubeSize), Random.nextInt(cubeSize), Random.nextInt(cubeSize))
			(0..<max2).forEach {
			    val rgbRand2 = rgbRand.similarRandom(0.85)

			    val rgbAv = rgbRand.average(rgbRand2)
			    val hsvAv = rgbRand.toHsv().average(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)
			    val hsvHAv = rgbRand.toHsv().averageHue(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)
			    val hsvSAv = rgbRand.toHsv().averageSaturation(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)
			    val hsvVAv = rgbRand.toHsv().averageValue(rgbRand2.toHsv()).toRGB().toCubeSize(cubeSize)

			    println(
				"Avg ${
				    rgbRand.showC(16, f = RGB::toLaconicStringRGB)
				}  ${
				    rgbAv.showC(16, f = RGB::toLaconicStringRGB)
				} ${
				    hsvAv.showC(16, f = RGB::toLaconicStringRGB)
				} ${
				    hsvHAv.showC(16, f = RGB::toLaconicStringRGB)
				} ${
				    hsvSAv.showC(16, f = RGB::toLaconicStringRGB)
				} ${
				    hsvVAv.showC(16, f = RGB::toLaconicStringRGB)
				}  ${
				    rgbRand2.showC(16, f = RGB::toLaconicStringRGB)
				}"
			    )
			}
			println()
		    }
		    println()
		}

		println()
		println()
		println()


		val maxRepeat = 8
		repeat(maxRepeat) { repeatIx ->

		    val defaultLoops = 10
		    val lastItem = repeatIx == maxRepeat - 1

		    val (loops: Int, formatter: (RGB) -> String, cellWidth: Int) =
			if (lastItem)
			    Triple(defaultLoops * cellWidth1, ::formatter2, cellWidth2)
			else
			    Triple(defaultLoops, ::formatter1, cellWidth1)

		    val tableWidth = cellWidth * loops
		    val cubeSize = listOf(4, 7, 11, 16, 32, 64, 128, 256).shuffled().first()

		    val rgbRand = randomRGB(cubeSize)
		    println("            " + "Base color $rgbRand".pC(tableWidth).rgbBg(rgbRand))

		    val tL = RGB::toLaconicStringRGB
		    val col = 6
		    val www = tableWidth / col

		    print("            ")
		    print(rgbRand.toMaxValue().showC(www, "mxV ", tL))
		    print(rgbRand.toMaxSaturation().showC(www, "mxS ", tL))
		    print(rgbRand.complement().showC(www, "cmpl ", tL))
		    print(rgbRand.toSaturation(0.5).showC(www, "smid ", tL))
		    print(rgbRand.toValue(0.5).showC(tableWidth - (col - 1) * www, "vmid ", tL))
		    print(rgbRand.toSaturation(0.5).toValue(0.5).showC(www, "svmid ", tL))
		    println()

		    print("            ")
		    print(rgbRand.rotL().showC(www, "rotL ", tL))
		    print(rgbRand.rotR().showC(www, "rotR ", tL))
		    print(rgbRand.mixRG().showC(www, "mixRG ", tL))
		    print(rgbRand.mixRB().showC(www, "mixRB ", tL))
		    print(rgbRand.mixGB().showC(tableWidth - (col - 1) * www, "mixGB ", tL))
		    print(rgbRand.inverse().showC(www, "inv ", tL))
		    println()

		    fun printForm(rgb: RGB) = print(formatter(rgb).rgbBg(rgb))
		    fun printForm(hsv: Ansi.HSV) = hsv.toRGB().also { print(formatter(it).rgbBg(it)) }

		    print("Hue    360° ")
		    rgbRand.hueGradient(loops).forEach { printForm(it) }
		    println()

		    print("Satur  rgb  ")
		    rgbRand.saturationGradient(loops).forEach { printForm(it) }
		    println()

		    print("Sat Min v1  ")
		    rgbRand.toHsv().gradient(loops, rgbRand.toHsv().clone(s = 0.0, v = 1.0)).forEach { printForm(it) }
		    println()

		    print("Sat Min     ")
		    rgbRand.toHsv().gradient(loops, rgbRand.toHsv().clone(s = 0.0)).forEach { printForm(it) }
		    println()

		    print("Sat Max     ")
		    rgbRand.toHsv().gradient(loops, rgbRand.toHsv().clone(s = 1.0)).forEach { printForm(it) }
		    println()

		    print("Sat Max v1  ")
		    rgbRand.toHsv().gradient(loops, rgbRand.toHsv().clone(s = 1.0, v = 1.0)).forEach { printForm(it) }
		    println()

		    print("Value  rgb  ")
		    rgbRand.valueGradient(loops).forEach { printForm(it) }
		    println()

		    print("Val Max rgb ")
		    rgbRand.gradient(loops, rgbRand.toHsv().clone(v = 1.0).toRGB()).forEach { printForm(it) }
		    println()

		    print("Val Max hsv ")
		    rgbRand.toHsv().gradient(loops, rgbRand.toHsv().clone(v = 1.0)).forEach { printForm(it) }
		    println()

		    print("Val Min rgb ")
		    val rgbRand256 = rgbRand.toCubeSize(256)
		    rgbRand.gradient(loops, RGB(256, rgbRand256.r / 128, rgbRand256.g / 128, rgbRand256.b / 128)).forEach { printForm(it) }
		    println()

		    print("Val Min hsv ")
		    rgbRand.toHsv().gradient(loops, rgbRand.toHsv().clone(v = 0.0)).forEach { printForm(it) }
		    println()

		    print("Val Min rgb0")
		    rgbRand.gradient(loops, RGB(256, 0, 0, 0)).forEach { printForm(it) }
		    println()
		    println()
		}

		println()
		println("Permutation Gradient")
		val cs = 5

		val rgb = (0..<cs).shuffled().take(3)
		val rgbRand = RGB(cs, rgb[0], rgb[1], rgb[2])
		rgbRand.permutationGradient().forEach { it1 ->
		    it1.permutationGradient().forEach { it2 ->
			print("${formatter1(it2)} ".rgbBg(it2))
		    }
		    println()
		}
		println()


		println()
		println("---")

		val verb = true
		val N = 24 * 3
		true.let {
		    val other = randomRGB(256)//.toSaturation(Random.nextDouble(1.0))
		    val rgbRand0 = randomRGB(256).average(other)
		    println("Base color: ${rgbRand0.showL()}  ${rgbRand0.toHsv()}  -- other: ${other.showL()}  ${other.toHsv()}")
		    repeat(N) { n ->
			val value = n.toDouble() / N
			val hsv = rgbRand0.toHsv()
			val rgbRand = rgbRand0.toHsv().gradient(N, rgbRand0.toHsv().clone(s = value))
			var rgb2 = rgbRand[n].toRGB()
			//if ( verb ) println("other color: $value ${hsv} ${rgbRand0.toHsv().clone(s = value)}  -- ${rgbRand0.toHsv().clone(s = value).toRGB()} ${rgb2.showC()}")
			//rgbRand.forEach { println("  " + it.showR()) }
			(1..2 * N).forEach {
			    rgb2 = rgb2.average(rgb2.toValue(0.2), 0.1 + 0.3 / it.toDouble())
			    print("${formatter3(rgb2, ' ')}".rgbBg(rgb2))
			}
			println()
		    }
		}

		if ( true) {
		    println()
		    println()
		    val rgb = randomRGB(256)
		    println("${rgb.showC()} ${rgb.byName("rotL").showC()} ${rgb.byNames(listOf("rotL", "compl")).showC()} ${rgb.byNames(listOf("rotL", "compl", "val+")).showC()}")

		    println()
		    var c1 = rgb.toValue(0.1)
		    repeat(16) {
			c1 = c1.byName("val+")
			print(" ${c1.showC(w=8, f = {"    "})}")
		    }
		    println()
		    c1 = rgb.toValue(1.0)
		    repeat(16) {
			c1 = c1.byName("val-")
			print(" ${c1.showC(w=8, f = {"    "})}")
		    }
		    println()
		    c1 = rgb.toSaturation(0.1)
		    repeat(16) {
			c1 = c1.byName("sat+")
			print(" ${c1.showC(w=8, f = {"    "})}")
		    }
		    println()
		}
	    }

	    Am("Z")            -> {
		val display = Ansi.Display(250, 75)

		val rgbRand00 = randomRGB(256)

		val item = display.Item(0, 0, Ansi.RGB(2, 1, 1, 1), "Hello")
		val item2 = display.Item(20, 12, Ansi.RGB(2, 1, 1, 1), "World")
                item2.velox = 2
		val items = listOf(item, item2)

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
			println("Base color: ${rgbRand0.showL()}  ${rgbRand0.toHsv()}  -- other: ${other.showL()}  ${other.toHsv()}")

			mt1 = measureTime {
			    repeat(rows) { row ->
				if (frame % 1 == 0) {
				    val value = row.toDouble() / rows
				    val hsv = rgbRand0.toHsv()
				    val rgbRand = rgbRand0.toHsv().gradient(rows, rgbRand0.toHsv().clone(s = value))
				    var rgb2 = rgbRand[row].toRGB()
				    //if ( verb ) println("other color: $value ${hsv} ${rgbRand0.toHsv().clone(s = value)}  -- ${rgbRand0.toHsv().clone(s = value).toRGB()} ${rgb2.showC()}")
				    //rgbRand.forEach { println("  " + it.showR()) }
				    (0..<display.w).forEach { col ->
					rgb2 = rgb2.average(rgb2.toValue(0.2), 0.1 + 0.3 / (1 + col.toDouble()))
					display.set(col, row, rgbRand0, rgb2, ' ')
				    }
				}
			    }
			}
		    }
		    items.forEach { it.step() }
		    val mt2 = measureTime {
			display.print()
		    }
		    //print(Ansi.hideCursor() + Ansi.goto(0, display.h + 1) + Ansi.clear())
		    //println(" mt $mt1 $mt2")
		}
	    }

	    Am("ca")           -> {
		println("\ncolor5s calling: Ansi.color5(ix1, ix2, string)")
		println("color5 ix1: (ix2, string)...")
		(0..Ansi.maxColor5Index).forEach { ix1 ->
		    print("color5 $ix1: ")
		    print(Ansi.color5ByIndex(ix1).name.pL(7))
		    (0..Ansi.maxColor5Value).forEach { ix2 ->
			print(Ansi.color5(Ansi.color5ByIndex(ix1), ix2, "  ($ix2 ##########)"))
		    }
		    println("")
		}
		println("\ncolor5s calling: Ansi.color5Bg(ix1, ix2, string)")
		println("color5 ix1: (ix2, string)...")
		(0..Ansi.maxColor5Index).forEach { ix1 ->
		    print("color5 $ix1: ")
		    print(Ansi.color5ByIndex(ix1).name.pL(7))
		    (0..Ansi.maxColor5Value).forEach { ix2 ->
			print(Ansi.color5Bg(Ansi.color5ByIndex(ix1), ix2, "  ($ix2 ##########)"))
		    }
		    println("")
		}
		println("\ncolor5s calling: Ansi.color5FgBg(ix1, ix2, string)")
		println("color5 ix1: (ix2, string)...")
		(0..Ansi.maxColor5Index).forEach { ix1 ->
		    val complement = Ansi.Color5.valueOf(Ansi.color5ByIndex(ix1).complement)
		    val fix1 = complement.ordinal
		    val fgcolor5 = Ansi.color5ByIndex(fix1)
		    print("color5 $fix1 $ix1: ")
		    print(Ansi.color5ByIndex(fix1).name.pL(7) + "  ")
		    print(Ansi.color5ByIndex(ix1).name.pL(7) + "  ")

		    (0..Ansi.maxColor5Value).forEach { ix2 ->
			val fix2 = ix2 // Ansi.maxColor5Value - ix2
			print(
			    Ansi.color5FgBg(
				fgcolor5,
				fix2,
				Ansi.color5ByIndex(ix1),
				ix2,
				"  ($fix2 $ix2 ##########)"
			    )
			)
		    }
		    println("")
		}
	    }

	    Am("cb")           -> {

		println("")
		println("")
		println("")
		println(" -------------- Color type 5 -------------")

		val ran5 = 0..5

		println("")
		println("fg5(r, g, b) only")
		ran5.forEach { r ->
		    ran5.forEach { g ->
			ran5.forEach { b ->
			    val fg = Ansi.fg5(r, g, b, "Ansi.fg5($r,$g,$b) ")
			    print("  $fg")
			}
			println("")
		    }
		}

		println("")
		println("bg5(r, g, b) only")
		ran5.forEach { r ->
		    ran5.forEach { g ->
			ran5.forEach { b ->
			    print(" " + Ansi.bg5(r, g, b, " Ansi.bg5($r,$g,$b) "))
			}
			println("")
		    }
		}

		println("")
		println("bg5(r, g, b) only")
		ran5.forEach { b ->
		    ran5.forEach { r ->
			ran5.forEach { g ->
			    print(" " + Ansi.bg5(r, g, b, " Ansi.bg5($r,$g,$b) "))
			}
			println("")
		    }
		}

		println("")
		println("bg5(r, g, b) only")
		ran5.forEach { g ->
		    ran5.forEach { b ->
			ran5.forEach { r ->
			    print(" " + Ansi.bg5(r, g, b, " Ansi.bg5($r,$g,$b) "))
			}
			println("")
		    }
		}

		println("")
		ran5.forEach { w ->
		    println("")
		    println("fgbg5(r, g, b, grays $w)")
		    ran5.forEach { r ->
			ran5.forEach { g ->
			    ran5.forEach { b ->
				val bg = Ansi.Color5Num(w, w, w)
				print(Ansi.fgbg5(r, g, b, bg, " Ansi.fgbg5($r,$g,$b,$bg) "))
			    }
			    println("")
			}
		    }
		}

		println("")
		println("fgbg5(r, g, b, br, bg, bb) on complement")
		ran5.forEach { br ->
		    ran5.forEach { bg ->
			ran5.forEach { bb ->
			    val fr = 5 - br
			    val fg = 5 - bg
			    val fb = 5 - bb
			    print(Ansi.fgbg5(fr, fg, fb, br, bg, bb, " Ansi.fgbg5($fr,$fg,$fb, $br,$bg,$bb) "))
			}
			println("")
		    }
		}
	    }

	    Am("cc")           -> {

		val minCube = if (narg == 0) 1 else narg
		val maxCube = if (narg == 0) 8 else narg

		println("")
		println("")
		println("")
		println(" -------------- Color type 2 -------------")

		fun f1(v: Int): String = v.toString().pL(1)
		fun f2(v: Int): String = v.toString().pL(2)
		fun f3(v: Int): String = v.toString().pL(3)

		val colors = Ansi.Support::values256

		(minCube..maxCube).forEach { cubeSize ->
		    println("")
		    println("Using 256 colors on Fg: (r, g, b, s) cube size: $cubeSize, total colors ${cubeSize * cubeSize * cubeSize}")
		    colors(cubeSize).forEach { r ->
			colors(cubeSize).forEach { g ->
			    colors(cubeSize).forEach { b ->
				val fg = Ansi.fg256(r, g, b, "Ansi.fg256(${f3(r)},${f3(g)},${f3(b)}) ")
				print("  $fg")
			    }
			    println("")
			}
		    }
		}

		println("")
		(minCube..maxCube).forEach { cubeSize ->
		    println("")
		    println("Using 256 colors on Bg: (r, g, b, s) cube size: $cubeSize, total colors ${cubeSize * cubeSize * cubeSize}")
		    colors(cubeSize).forEach { r ->
			if (cubeSize > 2)
			    println("")
			colors(cubeSize).forEach { g ->
			    colors(cubeSize).forEach { b ->
				print(" " + Ansi.bg256(r, g, b, " Ansi.bg256(${f3(r)},${f3(g)},${f3(b)}) "))
			    }
			    println("")
			}
		    }
		}

		println("")

		(minCube..maxCube).forEach { cubeSize ->
		    println("")
		    println("Using ColorCube Values on Fg: (cubeSize)(r, g, b, s) cube size: $cubeSize, total colors ${cubeSize * cubeSize * cubeSize}")
		    val csFg = Ansi.csFg(cubeSize)
		    val fn = if (cubeSize <= 10) ::f1 else ::f2
		    (0..<cubeSize).forEach { r ->
			(0..<cubeSize).forEach { g ->
			    (0..<cubeSize).forEach { b ->
				val fg = csFg(r, g, b, "Ansi.csFg(${cubeSize})(${fn(r)},${fn(g)},${fn(b)}) ")
				print("  $fg")
			    }
			    println("")
			}
		    }
		}
		val painter = Ansi.csBg(4, 3, 0, 2)

		(minCube..maxCube).forEach { cubeSize ->
		    println("")
		    println("Using ColorCube Values on Bg: (cubeSize, r, g, b, s) cube size: $cubeSize, total colors ${cubeSize * cubeSize * cubeSize}")
		    val fn = if (cubeSize <= 10) ::f1 else ::f2
		    (0..<cubeSize).forEach { r ->
			(0..<cubeSize).forEach { g ->
			    (0..<cubeSize).forEach { b ->
				val bg =
				    Ansi.csBg(cubeSize, r, g, b, "Ansi.csBg(${cubeSize},${fn(r)},${fn(g)},${fn(b)}) ")
				print("  $bg")
			    }
			    println("")
			}
		    }
		}
	    }

	    Am("G")            -> {
		val from = if (narg > 0) narg else 1
		val to = if (narg > 0) narg else 10

		(from..to).forEach { cubeSize ->
		    val rgbList = if (cubeSize < 3)
			listOf(Random.nextInt(cubeSize - 1), Random.nextInt(cubeSize - 1), Random.nextInt(cubeSize - 1))
		    else
			(0..<cubeSize).shuffled().take(3)
		    val rgbBase = RGB(cubeSize, rgbList[0], rgbList[1], rgbList[2])

		    rgbBase.permutationGradient().forEach { rgb_ ->
			val rgb = if (rgb_.cs != cubeSize) rgb_.toCubeSize(cubeSize) else rgb_

			println("")
			println(
			    "Cube size ${cubeSize} -- gradients : " + rgbFg(rgb)(" $rgb") + "    " + rgbBg(
				rgb
			    )("  color  ")
			)
			println()


			val `+` = rgb::inc
			val `-` = rgb::dec
			val `=` = rgb::eq0

			measureTime {
			    val r = Random.nextInt(loremList.size - 8)
			    val ss = listOf(0, 2, 4).map { lorem(r + it, 2) }
			    val ll = rgb.toHsv().gradient(cubeSize + 1, RGB(256, 0, 0, 1).toHsv())
			    val lh = rgb.toHsv().gradient(cubeSize + 1, RGB(256, 255, 255, 255).toHsv())
			    (0..cubeSize).forEach { n ->
				val nn = n.toString().pL(2)
				print(" $nn " + rgbBg(rgb.moreOrLess(n, `=`, `=`, `=`))(" ${ss[0]} === "))
				print(" $nn " + rgbBg(rgb.moreOrLess(n, `+`, `+`, `+`))(" ${ss[1]} +++ "))
				print(" $nn " + rgbBg(lh[n].toRGB())(" ${ss[2]} ··· "))
				print(" $nn " + rgbBg(rgb.moreOrLess(n, `-`, `-`, `-`))(" ${ss[2]} --- "))
				print(" $nn " + rgbBg(ll[n].toRGB())(" ${ss[2]} ··· "))
				println()
			    }
			}.also { println("$it") }
			println()

			measureTime {
			    "+=-".forEach { ch0 ->
				(0..rgbBase.colorSpan()).forEach { n ->
				    "+=-".forEach { ch1 ->
					"+=-".forEach { ch2 ->
					    val op = "$ch0$ch1$ch2"
					    val ns = n.toString().pL(2)
					    op.let {
						val rgb1 = rgb.moreOrLess(n, it)
						print(" $ns " + rgbBg(rgb1)(" ${rgb1.toLaconicStringRGB().pR(8)} $it "))
					    }
					}
				    }
				    println()
				}
				println()
			    }
			}.also { println("$it") }
			println()
		    }
		}

		true.let {
		    repeat(3) {
			listOf(2, 3, 4, 5, 6, 7, 8, 16, 32, 64, 128, 256).forEach {
			    measureTime {
				val rgbBase = randomRGB(it)
				(0..it).forEach { n ->
				    val xyz = rgbBg(rgbBase.moreOrLess(n, rgbBase::inc, rgbBase::eq0, rgbBase::dec))
				}
			    }.also { mt ->
				println("it took $it·$it : $mt")
			    }
			}
		    }
		}
	    }
	}
    }

    companion object {

	fun randomRGB(cubeSize: Int = 256): RGB = RGB(
	    cubeSize,
	    Random.nextInt(cubeSize),
	    Random.nextInt(cubeSize),
	    Random.nextInt(cubeSize)
	)

	fun main() {
	    AnsiDemo().main("G3")
	}

	@JvmStatic
	fun main(args: Array<String>) {
	    AnsiDemo().main(if (args.size > 0) args[0] else "d")
	}
    }
}

