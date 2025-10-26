package femtioprocent.ansi.appl

import femtioprocent.ansi.Color2
import femtioprocent.ansi.extentions.pL

class AnsiDemocc : AnsiDemo() {

    override fun demo(narg: Int) {
	val minCube = if (narg == 0) 1 else narg
	val maxCube = if (narg == 0) 8 else narg

	println("")
	println("")
	println("")
	println(" -------------- Color type 2 -------------")

	fun f1(v: Int): String = v.toString().pL(1)
	fun f2(v: Int): String = v.toString().pL(2)
	fun f3(v: Int): String = v.toString().pL(3)

	val colors = Color2.Support::values256

	(minCube..maxCube).forEach { cubeSize ->
	    println("")
	    println("Using 256 colors on Fg: (r, g, b, s) cube size: $cubeSize, total colors ${cubeSize * cubeSize * cubeSize}")
	    colors(cubeSize).forEach { r ->
		colors(cubeSize).forEach { g ->
		    colors(cubeSize).forEach { b ->
			val fg = Color2.emitFg2(r, g, b, "Color2.fg256(${f3(r)},${f3(g)},${f3(b)}) ")
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
			print(" " + Color2.emitBg2(r, g, b, " Color2.bg256(${f3(r)},${f3(g)},${f3(b)}) "))
		    }
		    println("")
		}
	    }
	}

	println("")

	(minCube..maxCube).forEach { cubeSize ->
	    println("")
	    println("Using ColorCube Values on Fg: (cubeSize)(r, g, b, s) cube size: $cubeSize, total colors ${cubeSize * cubeSize * cubeSize}")
	    val csFg = Color2.csFg(cubeSize)
	    val fn = if (cubeSize <= 10) ::f1 else ::f2
	    (0..<cubeSize).forEach { r ->
		(0..<cubeSize).forEach { g ->
		    (0..<cubeSize).forEach { b ->
			val fg = csFg(r, g, b, "Color2.csFg(${cubeSize})(${fn(r)},${fn(g)},${fn(b)}) ")
			print("  $fg")
		    }
		    println("")
		}
	    }
	}
	val painter = Color2.csBg(4, 3, 0, 2)

	(minCube..maxCube).forEach { cubeSize ->
	    println("")
	    println("Using ColorCube Values on Bg: (cubeSize, r, g, b, s) cube size: $cubeSize, total colors ${cubeSize * cubeSize * cubeSize}")
	    val fn = if (cubeSize <= 10) ::f1 else ::f2
	    (0..<cubeSize).forEach { r ->
		(0..<cubeSize).forEach { g ->
		    (0..<cubeSize).forEach { b ->
			val bg =
			    Color2.csBg(cubeSize, r, g, b, "Color2.csBg(${cubeSize},${fn(r)},${fn(g)},${fn(b)}) ")
			print("  $bg")
		    }
		    println("")
		}
	    }
	}
    }
}