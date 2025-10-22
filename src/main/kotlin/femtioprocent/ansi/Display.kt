package femtioprocent.ansi

import femtioprocent.ansi.Color2

class Display(val w: Int, val h: Int) {
    data class Cell(var fg: Color2.RGB, var bg: Color2.RGB, var ch: Char)

    val grid = Array<Cell>(w * h) {
	val fg = Color2.RGB(2, 1, 1, 1)
	val bg = Color2.RGB(2, 0, 0, 0)
	Cell(fg, bg, ' ')
    }

    val gridCurrent = Array<Cell>(w * h) {
	val fg = Color2.RGB(2, 0, 1, 1)
	val bg = Color2.RGB(2, 0, 0, 0)
	Cell(fg, bg, ' ')
    }

    init {
    }

    fun set(x: Int, y: Int, fg: Color2.RGB, bg: Color2.RGB, ch: Char) {
	grid[x + y * w] = Cell(fg, bg, ch)
    }

    fun setTextOnly(x: Int, y: Int, s: String) {
	s.forEachIndexed { ix, ch ->
	    grid[ix + x + y * w].ch = ch
	}
    }

    fun setText(x: Int, y: Int, fg: Color2.RGB, s: String) {
	s.forEachIndexed { ix, ch ->
	    grid[ix + x + y * w].fg = fg
	    grid[ix + x + y * w].ch = ch
	}
    }

    fun setText(x: Int, y: Int, s: String) {
	var ix0 = x + y * w
	val fg = grid[ix0].bg.inverse().magnet()
	var row = 0

	var ix = 0
	s.forEach { ch ->
	    if (ch == '\n') {
		ix = 0
		row++
		ix0 = x + (y + row) * w
	    } else {
		grid[ix + ix0].fg = fg
		grid[ix + ix0].ch = ch
		ix++
	    }
	}
    }

    fun hitWall(x: Int, y: Int, s: String): Boolean {
	return y < 0 || y >= h || x < 0 || (x + s.length) >= w
    }

    fun hitWallX(x: Int, y: Int, s: String): Boolean {
	return x < 0 || (x + s.length) >= w
    }

    fun hitWallY(x: Int, y: Int, s: String): Boolean {
	return y < 0 || y >= h
    }

    fun print(origo: Boolean = true) {
	var dirty = true
	print(Ansi.hideCursor())
	if (origo)
	    print(Ansi.hideCursor() + Ansi.goto(0, 0))
	var cntA = 0
	var cntB = 0
	(0..<h).forEach { y ->
	    (0..<w).forEach { x ->
		val ix = y * w + x
		if (origo && grid[ix].fg == gridCurrent[ix].fg && grid[ix].bg == gridCurrent[ix].bg && grid[ix].ch == gridCurrent[ix].ch) {
		    dirty = true
		    cntA++
		} else {
		    if (origo && dirty)
			print(Ansi.goto(x, y))
		    print(Color2.rgbFgBg(grid[ix].fg, grid[ix].bg, grid[ix].ch.toString()))
		    dirty = false
		    gridCurrent[ix].fg = grid[ix].fg
		    gridCurrent[ix].bg = grid[ix].bg
		    gridCurrent[ix].ch = grid[ix].ch
		    cntB++
		}
	    }
	    println()
	}
	println(Ansi.showCursor()) // + "cntAB $cntA $cntB")
    }

    fun fill(bg: Color2.RGB) {
	(0..<w).forEach { x ->
	    (0..<h).forEach { y ->
		set(x, y, bg, bg, ' ')
	    }
	}
    }

    fun rect(x: Int, y: Int, w: Int, h: Int, bg: Color2.RGB) {
	(x..<x + w).forEach { x ->
	    (y..<y + h).forEach { y ->
		set(x, y, bg, bg, ' ')
	    }
	}
    }

    val blank = "                                                                                  "
//	val blank = "012345678901234567890123456789012345678901234567890"

    inner class Item(var x: Int, var y: Int, var fg: Color2.RGB, var s: String) {
	var velox = 1
	var veloy = 1

	fun moveTo(x: Int, y: Int) {
	    setText(x, y, blank.substring(0, s.length))
	    this.x = x
	    this.y = y
	    setText(x, y, s)
	}

	fun step() {
	    if (!hitWall(x, y, s))
		setText(x, y, blank.substring(0, s.length))

	    if (hitWallX(x, y, s))
		velox *= -1
	    if (hitWallY(x, y, s))
		veloy *= -1

	    this.x += velox
	    this.y += veloy

	    if (!hitWall(x, y, s))
		setText(x, y, s)
	}
    }
}