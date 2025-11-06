package femtioprocent.ansi.appl

import femtioprocent.ansi.Color2.RGB
import femtioprocent.ansi.Color2.rgbBg
import femtioprocent.ansi.Color2.rgbFg
import femtioprocent.ansi.extentions.pL
import femtioprocent.ansi.extentions.pR
import kotlin.random.Random
import kotlin.time.measureTime

class AnsiDemoG : AnsiDemo() {

    override fun demo(narg: Int) {
	val from = if (narg > 0) narg else 2
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
		    val r = Random.nextInt(loremList.size - 13)
		    val ss = listOf(0, 2, 4, 6, 8, 10).map { lorem(r + it, 2) }

		    val list = listOf(
			rgb.toHsv().gradient(cubeSize + 1, RGB(256, 0, 0, 1).toHsv()),
			rgb.toHsv().gradient(cubeSize + 1, RGB(256, 40, 40, 40).toHsv()),
			rgb.toHsv().gradient(cubeSize + 1, RGB(256, Random.nextInt(75), Random.nextInt(75), Random.nextInt(75)).toHsv()),
			rgb.toHsv().gradient(cubeSize + 1, RGB(256, 255, 255, 255).toHsv()),
			rgb.toHsv().gradient(cubeSize + 1, RGB(256, 200, 200, 200).toHsv()),
			rgb.toHsv().gradient(cubeSize + 1, RGB(256, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)).toHsv()),
		    )

		    (0..cubeSize).forEach { n ->
			val nn = n.toString().pL(2)
			var ix = 0
			list.forEachIndexed { ix, it ->
			    print(" $nn " + rgbBg(it[n].toRGB())(" ${ss[ix]} ··· "))
			}
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