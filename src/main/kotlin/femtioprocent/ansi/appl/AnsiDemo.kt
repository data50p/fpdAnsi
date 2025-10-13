package femtioprocent.ansi.appl

import femtioprocent.ansi.Ansi
import femtioprocent.ansi.Ansi.rgbBg
import femtioprocent.ansi.Ansi.rgbFg
import femtioprocent.ansi.Version
import femtioprocent.sundry.Sundry
import kotlin.random.Random
import kotlin.time.measureTime

val lorem =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum urna elit, viverra in eros nec, accumsan fringilla leo." +
            " Aliquam dolor sapien, gravida eu lobortis ut, ornare eu odio. Donec aliquet pharetra ligula, eu dapibus dui egestas quis." +
            " Proin et fermentum nibh. Nulla ullamcorper rutrum sapien id tristique. Phasellus viverra facilisis augue, eget ullamcorper sapien lacinia quis." +
            " Integer ultricies, libero vel luctus sodales, ipsum sem maximus eros, quis mattis justo nisl vel risus." +
            " Vivamus mi diam, interdum a laoreet placerat, feugiat sit amet purus. Phasellus vulputate semper finibus." +
            " Integer maximus eleifend pharetra. Nulla congue eleifend finibus. Nunc posuere varius ornare." +
            " Maecenas pharetra auctor elit, non accumsan felis elementum id. Maecenas at rhoncus purus. Curabitur nec mi ac mi mollis pulvinar ut ut arcu."

val loremList = lorem.replace(".", "").replace(",", "").split(" ")
fun lorem(m: Int, n: Int): String = loremList.drop(m).take(n).joinToString(" ")


class AnsiDemo {

    fun main(arg: String) {
        println(Version.info())
        ansiColorDemo(arg)
    }

    fun String.pR(w: Int = 1) = "%-${w}s".format(this)
    fun String.pL(w: Int = 1) = "%${w}s".format(this)
    fun String.pC(w: Int = 1) = if (w <= length) this else pL(length + (w - length) / 2).pR(w)

    fun pr(cv: Ansi.RGB, w: Int = 40) = cv.toString().pR(w).rgbBg(cv)
    fun prHSV(cv: Ansi.RGB, w: Int = 32) = cv.toHsv().toString().pR(w).rgbBg(cv)
    fun prCS(cv: Ansi.RGB, cs: Int, w: Int = 40) = cv.toCubeSize(cs).toString().pR(w).rgbBg(cv)
    fun pr256(cv: Ansi.RGB, w: Int = 40) = prCS(cv, 256, w)

    fun List<Any>.pri(delim: String = "") {
        println(map { " $it" }.joinToString(delim))
    }

    fun List<Any>.frm(delim: String = ""): String {
        return map { " $it" }.joinToString(delim)
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

            Am("D") -> {
                Ansi.dumpColor5().forEach { println(it) }
            }

            Am("d") -> {
                println("")
                println(Ansi.csFg(4)(1, 2, 3, "XXXXXXXXXXX Ansi.csFg(4)(1, 2, 3, s)"))
                println(Ansi.csBg(4)(3, 2, 1, "XXXXXXXXXXX Ansi.csBg(4)(1, 2, 3, s)"))
                println(Ansi.csFg(4, 3, 2, 3)("XXXXXXXXXXX Ansi.csFg(4, 3, 2, 3)(s)"))
                println(Ansi.csBg(4, 3, 0, 1)("XXXXXXXXXXX Ansi.csBg(4, 3, 0, 1)(s)"))
                println(Ansi.csFgRGB(4)(0, 2, 3)("XXXXXXXXXXX Ansi.csFgCv(4)(0, 2, 3)(s)"))
                println(Ansi.csBgRGB(4)(3, 0, 2)("XXXXXXXXXXX Ansi.csBgCv(4)(3, 0, 2)(s)"))
                println()


                val cube4 = Ansi.csRgb(4)
                val cube5 = Ansi.csRgb(5)

                val magenta = cube4(3, 0, 3)
                val yello = cube4(3, 3, 1)
                val lightMagenta = cube4(3, 2, 3)
                val lightGreen = cube4(1, 3, 2)
                val cv256 = Ansi.csRgb(256)(32, 155, 240)

                val painter = rgbBg(lightGreen)

                ((4..16) + listOf(256))
                    .map { lightGreen.toCubeSize(it) }
                    .map { cv -> cv to cv.toCubeSize(4) }
                    .map { (cv, cv2) -> listOf(pr(lightGreen), pr(cv), pr(cv2)).pri(" -> ") }

                ((4..16) + listOf(256))
                    .map { cv256.toCubeSize(it) }
                    .map { cv -> cv to cv.toCubeSize(256) }
                    .map { (cv, cv2) -> listOf(pr(cv256), pr(cv), pr(cv2)).pri(" -> ") }

                ((4..16) + listOf(256)).forEach { cs ->
                    val cv = Ansi.RGB(cs, Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))
                    val cv256 = cv.toCubeSize(256)
                    val hsv = cv256.toHsv()
                    val cv_256 = hsv.toRGB()
                    val cv_N = cv_256.toCubeSize(cs)
                    listOf(pr(cv), pr(cv256), prHSV(cv256), pr(cv_256), pr(cv_N)).pri(" -> ")

                    val hV = hsv.clone(v = 1.0)
                    listOf(pr(hV.toRGB())).pri(" -> ")

                    println()
                }

                println()
                (2..256).forEach {
                    val cv0 = Ansi.RGB(it, Random.nextInt(it), Random.nextInt(it), Random.nextInt(it))
                    val cv1 = cv0.toCubeSize(256)
                    val cv2 = cv1.toCubeSize(it)
                    println(
                        Sundry.padLeft(it.toString(), 3, ' ') +
                                listOf(pr(cv0), pr(cv1), pr(cv2)).frm(" -> ") +
                                " ∆ ${cv0.r - cv2.r},${cv0.g - cv2.g},${cv0.b - cv2.b}"
                    )
                }


                val lightMagenta256 = lightMagenta.toCubeSize(256)

                val lightGreen256 = lightGreen.toCubeSize(256)
                val lightMagenta4 = lightMagenta256.toCubeSize(4)
                val lightGreen4 = lightGreen256.toCubeSize(4)
                val lightMagenta8 = lightMagenta256.toCubeSize(8)
                val lightGreen8 = lightGreen256.toCubeSize(8)

                println(Ansi.rgbFgBg(yello, cube5(0, 0, 4), "${lorem(0, 4)}"))
                println(Ansi.csFgBg(3, 2, 0, 2, 4, 1, 0, 1, " QWERTY123 "))
                println()

                println(painter("ZZZZZZZZZZZZZ Ansi.cvBg( Ansi.csCv(4)(3, 2, 3) )(s)") + "   ${lightGreen}")
                println(rgbBg(lightGreen256)("ZZZZZZZZZZZZZ Ansi.cvBg( Ansi.csCv(4)(3, 2, 3) )(s)") + "   ${lightGreen256}")
                println(rgbBg(lightGreen4)("ZZZZZZZZZZZZZ Ansi.cvBg( Ansi.csCv(4)(3, 2, 3) )(s)") + "   ${lightGreen4}")
                println(rgbBg(lightGreen8)("ZZZZZZZZZZZZZ Ansi.cvBg( Ansi.csCv(4)(3, 2, 3) )(s)") + "   ${lightGreen8}")

                println("ZZZZZZZZZZZZZ  String.cvFg(Ansi.csCv(4)(3, 2, 3))".rgbFg(lightMagenta) + "   ${lightMagenta}")
                println("ZZZZZZZZZZZZZ  String.cvFg(Ansi.csCv(4)(3, 2, 3))".rgbFg(lightMagenta256) + "   ${lightMagenta256}")
                println("ZZZZZZZZZZZZZ  String.cvFg(Ansi.csCv(4)(3, 2, 3))".rgbFg(lightMagenta4) + "   ${lightMagenta4}")
                println("ZZZZZZZZZZZZZ  String.cvFg(Ansi.csCv(4)(3, 2, 3))".rgbFg(lightMagenta8) + "   ${lightMagenta8}")

                println("${lightGreen} ${lightMagenta}   ${lightGreen256} ${lightMagenta256}")
                println()


                val red = Ansi.csRgb(256)(255, 0, 0)

                val w = " 236,120,136 ".length
                fun pr1(cv: Ansi.RGB): String = Sundry.padCenter(cv.toLaconicStringRGB(), w, ' ')
                fun pr2(
                    cv: Ansi.RGB

                )
                        : String = Sundry.padCenter("XX", 4, ' ')

                println("RGB Gradient")
                red.hueGradient(12).forEach { it2 ->
                    print("${pr1(it2)} ".rgbBg(it2))
                }
                println()
                println()


                println("RGB Gradient 180°")
                repeat(8) {
                    Ansi.RGB(256, Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))
                        .hueGradient(36, 180.0).forEach { it2 ->
                            print(" XX ".rgbBg(it2))
                        }
                    println()
                }
                println()

                println("RGB Gradient -180°")
                repeat(8) {
                    Ansi.RGB(256, Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))
                        .hueGradient(36, -180.0).forEach { it2 ->
                            print(" XX ".rgbBg(it2))
                        }
                    println()
                }
                println()

                println("RGB Gradient 45°")
                repeat(8) {
                    Ansi.RGB(256, Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))
                        .hueGradient(36, 45.0).forEach { it2 ->
                            print(" XX ".rgbBg(it2))
                        }
                    println()
                }
                println()

                println("Gradient color1 -> color2")
                repeat(8) {
                    val cvRandG1 =
                        Ansi.RGB(256, Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))
                    val cvRandG2 =
                        Ansi.RGB(256, Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))

                    cvRandG1.gradient(144, cvRandG2).forEach { it2 ->
                        print("·".rgbBg(it2))
                    }
                    println()

                    cvRandG1.toHsv().gradient(144, cvRandG2.toHsv()).forEach { it2 ->
                        print("·".rgbBg(it2.toRGB()))
                    }
                    println()
                }
                println()


                val max2_ = 10
                val maxR = 8

                println()
                println()
                println()

                repeat(maxR) { repeatIx ->

                    val max2 = if (repeatIx == maxR - 1) max2_ * 3 + 3 else max2_
                    val pr = if (repeatIx == maxR - 1) ::pr2 else ::pr1
                    val ww = if (repeatIx == maxR - 1) 4 * max2 else w * max2

                    val cubSiz = listOf(4, 7, 11, 16, 32, 64, 128, 256).shuffled().first()

                    val cvRand = Ansi.RGB(cubSiz, Random.nextDouble(1.0), Random.nextDouble(1.0), Random.nextDouble(1.0))
                    println("            " + Sundry.padCenter("Base color $cvRand", ww, ' ').rgbBg(cvRand))

                    val www = ww / 4
                    print("            ")
                    print(Sundry.padCenter("mxV ${cvRand.toMaxValue().toLaconicStringRGB()}", www, ' ').rgbBg(cvRand.toMaxValue()))
                    print(Sundry.padCenter("mxS ${cvRand.toMaxSaturation().toLaconicStringRGB()}", www, ' ').rgbBg(cvRand.toMaxSaturation()))
                    print(Sundry.padCenter("cmpl ${cvRand.complement().toLaconicStringRGB()}", www, ' ').rgbBg(cvRand.complement()))
                    print(Sundry.padCenter("comp ${cvRand.complementRGB().toLaconicStringRGB()}", ww - 3 * www, ' ').rgbBg(cvRand.complementRGB()))
                    println()

                    print("Hue    360° ")
                    cvRand.hueGradient(max2).forEach { it2 ->
                        print("${pr(it2)}".rgbBg(it2))
                    }
                    println()

                    print("Satur  rgb  ")
                    cvRand.saturationGradient(max2).forEach { it2 ->
                        print("${pr(it2)}".rgbBg(it2))
                    }
                    println()

                    print("SatMin hsv1 ")
                    cvRand.toHsv().gradient(max2, cvRand.toHsv().clone(s = 0.0, v = 1.0)).forEach { it2 ->
                        print("${pr(it2.toRGB())}".rgbBg(it2.toRGB()))
                    }
                    println()

                    print("SatMin hsv  ")
                    cvRand.toHsv().gradient(max2, cvRand.toHsv().clone(s = 0.0)).forEach { it2 ->
                        print("${pr(it2.toRGB())}".rgbBg(it2.toRGB()))
                    }
                    println()

                    print("SatMax hsv  ")
                    cvRand.toHsv().gradient(max2, cvRand.toHsv().clone(s = 1.0)).forEach { it2 ->
                        print("${pr(it2.toRGB())}".rgbBg(it2.toRGB()))
                    }
                    println()

                    print("Value  rgb  ")
                    cvRand.valueGradient(max2).forEach { it2 ->
                        print("${pr(it2)}".rgbBg(it2))
                    }
                    println()

                    print("ValMax rgb  ")
                    cvRand.toHsv().gradient(max2, cvRand.toHsv().clone(v = 1.0)).forEach { it2 ->
                        print("${pr(it2.toRGB())}".rgbBg(it2.toRGB()))
                    }
                    println()

                    print("ValMax hsv  ")
                    cvRand.toHsv().gradient(max2, cvRand.toHsv().clone(v = 1.0)).forEach { it2 ->
                        print("${pr(it2.toRGB())}".rgbBg(it2.toRGB()))
                    }
                    println()

                    print("ValMin rbg  ")
                    val cvRand256 = cvRand.toCubeSize(256)
                    cvRand.gradient(max2, Ansi.RGB(256, cvRand256.r / 128, cvRand256.g / 128, cvRand256.b / 128)).forEach { it2 ->
                        print("${pr(it2)}".rgbBg(it2))
                    }
                    println()

                    print("ValMin hsv  ")
                    cvRand.toHsv().gradient(max2, cvRand.toHsv().clone(v = 0.0)).forEach { it2 ->
                        print("${pr(it2.toRGB())}".rgbBg(it2.toRGB()))
                    }
                    println()

                    print("ValMin rbg0 ")
                    cvRand.gradient(max2, Ansi.RGB(256, 0, 0, 0)).forEach { it2 ->
                        print("${pr(it2)}".rgbBg(it2))
                    }
                    println()
                    println()
                }

                println()
                println("Permutation Gradient")
                val cs = 5

                val rgb = (0..<cs).shuffled().take(3)
                val cvRand = Ansi.RGB(cs, rgb[0], rgb[1], rgb[2])
                cvRand.permutationGradient().forEach { it1 ->
                    it1.permutationGradient().forEach { it2 ->
                        print("${pr1(it2)} ".rgbBg(it2))
                    }
                    println()
                }
                println()
            }

            Am("ca") -> {
                println("\ncolor5s calling: Ansi.color5(ix1, ix2, string)")
                println("color5 ix1: (ix2, string)...")
                (0..Ansi.maxColor5Index).forEach { ix1 ->
                    print("color5 $ix1: ")
                    print(Sundry.padLeft(Ansi.color5ByIndex(ix1).name, 7, ' '))
                    (0..Ansi.maxColor5Value).forEach { ix2 ->
                        print(Ansi.color5(Ansi.color5ByIndex(ix1), ix2, "  ($ix2 ##########)"))
                    }
                    println("")
                }
                println("\ncolor5s calling: Ansi.color5Bg(ix1, ix2, string)")
                println("color5 ix1: (ix2, string)...")
                (0..Ansi.maxColor5Index).forEach { ix1 ->
                    print("color5 $ix1: ")
                    print(Sundry.padLeft(Ansi.color5ByIndex(ix1).name, 7, ' ') + "  ")
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
                    print(Sundry.padLeft(Ansi.color5ByIndex(fix1).name, 7, ' ') + "  ")
                    print(Sundry.padLeft(Ansi.color5ByIndex(ix1).name, 7, ' ') + "  ")
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

            Am("cb") -> {

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

            Am("cc") -> {

                val minCube = if (narg == 0) 1 else narg
                val maxCube = if (narg == 0) 16 else narg

                println("")
                println("")
                println("")
                println(" -------------- Color type 2 -------------")

                fun f1(v: Int): String = Sundry.padLeft(v.toString(), 1, ' ')
                fun f2(v: Int): String = Sundry.padLeft(v.toString(), 2, ' ')
                fun f3(v: Int): String = Sundry.padLeft(v.toString(), 3, ' ')

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

            Am("G") -> {
                val from = if (narg > 0) narg else 1
                val to = if (narg > 0) narg else 10

                (from..to).forEach { cubeSize ->
                    val cvList = if (cubeSize < 3)
                        listOf(Random.nextInt(cubeSize - 1), Random.nextInt(cubeSize - 1), Random.nextInt(cubeSize - 1))
                    else
                        (0..<cubeSize).shuffled().take(3)
                    val cvBase = Ansi.RGB(cubeSize, cvList[0], cvList[1], cvList[2])

                    cvBase.permutationGradient().forEach { rgb_ ->
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
                            val ll = rgb.toHsv().gradient(cubeSize + 1, Ansi.RGB(256, 0, 0, 1).toHsv())
                            val lh = rgb.toHsv().gradient(cubeSize + 1, Ansi.RGB(256, 255, 255, 255).toHsv())
                            (0..cubeSize).forEach { n ->
                                val nn = Sundry.padLeft(n.toString(), 2, ' ')
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
                                (0..cvBase.colorSpan()).forEach { n ->
                                    "+=-".forEach { ch1 ->
                                        "+=-".forEach { ch2 ->
                                            val op = "$ch0$ch1$ch2"
                                            val ns = Sundry.padLeft(n.toString(), 2, ' ')
                                            op.let {
                                                val rgb1 = rgb.moreOrLess(n, "$it")
                                                print(
                                                    " $ns " + rgbBg(rgb1)(
                                                        " ${
                                                            Sundry.padRight(
                                                                rgb1.toLaconicStringRGB(),
                                                                8,
                                                                ' '
                                                            )
                                                        } $it "
                                                    )
                                                )
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

                if (!false) {
                    repeat(3) {
                        listOf(2, 3, 4, 5, 6, 7, 8, 16, 32, 64, 128, 256).forEach {
                            measureTime {
                                val cvBase = Ansi.RGB(
                                    it,
                                    Random.nextDouble(1.0),
                                    Random.nextDouble(1.0),
                                    Random.nextDouble(1.0)
                                )
                                (0..it).forEach { n ->
                                    val xyz = rgbBg(cvBase.moreOrLess(n, cvBase::inc, cvBase::eq0, cvBase::dec))
                                }
                            }.also { mt ->
                                println("it took ${it}·$it : ${mt}")
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun main() {
            AnsiDemo().main("G3")
        }

        @JvmStatic
        fun main(args: Array<String>) {
            AnsiDemo().main(if (args.size > 0) args[0] else "d")
        }
    }
}
