package femtioprocent.demo;

import femtioprocent.ansi.Ansi;
import femtioprocent.ansi.appl.AnsiDemo;

public class Demo {
    public static void main(String[] args) {
	if ( args.length > 0 && args.equals("AnsiDemo") ) {
	    AnsiDemo.main(args);
	    System.exit(0);
	}

	// Since library is written in Kotlin, do expect some odd things when calling it.

	/// ////////// Following is for legacy colors (a very few values indeed).

	System.out.println("Red " +  " -> " + Ansi.INSTANCE.fg(Ansi.LegacyColor.R, " Hello "));

	/// ////////// Following is for predefined colors (6 bits).

	System.out.println("Green Dark " +  " -> " + Ansi.INSTANCE.fg(Ansi.Color.GD, " Hello "));
	System.out.println("Green      " +  " -> " + Ansi.INSTANCE.fg(Ansi.Color.G, " Hello "));
	System.out.println("Green Light" +  " -> " + Ansi.INSTANCE.fg(Ansi.Color.GL, " Hello "));


	/// ////////// Following is for special 6 bits color (216 rgb values). Escape code type 5.

	// A predefined color

	Ansi.Color5 rgb5 = Ansi.Color5.MAGENTA;

	System.out.println("" + rgb5.toString() + " -> " + Ansi.INSTANCE.color5(rgb5, 7, " Hello "));


	/// ////////// Following is for full 24 bits color. Escape code type 2.

	// Create a color with values from 0 to 99 (100 values per red, green, and blue).
	// 100 is the cube size in rgb space. values from 2 up to 256 (including) is supported.

	Ansi.RGB rgb = new Ansi.RGB(100, 12, 34, 67);

	// Create a random color in a specified color cube size

	Ansi.RGB randColor = AnsiDemo.Companion.randomRGB(8);

	System.out.println("" + rgb.toString() + " -> " + Ansi.INSTANCE.rgbBg(" Hello ", rgb));
    }
}
