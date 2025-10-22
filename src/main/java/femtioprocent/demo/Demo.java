package femtioprocent.demo;

import femtioprocent.ansi.appl.AnsiDemo;
import femtioprocent.ansi.Color;
import femtioprocent.ansi.Color2;
import femtioprocent.ansi.Color2.RGB;
import femtioprocent.ansi.Color5;
import kotlin.jvm.functions.Function2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo {
    public static void main(String[] args) {
        // Since library is written in Kotlin, do expect some odd things when calling it.

        /// ////////// Following is for legacy colors (a very few values indeed). Type 1 color spec

        System.out.println("Color.INSTANCE.fg(Color.LegacyColor.R, \"Hello\")  " + " -> " + Color.INSTANCE.fg(Color.LegacyColor.R, " Hello "));
        System.out.println("Color.INSTANCE.fg(Color.LegacyColor.G, \"Hello\")  " + " -> " + Color.INSTANCE.fg(Color.LegacyColor.G, " Hello "));
        System.out.println("Color.INSTANCE.fg(Color.LegacyColor.B, \"Hello\")  " + " -> " + Color.INSTANCE.fg(Color.LegacyColor.B, " Hello "));
        System.out.println("Color.INSTANCE.fgBg5(Color.LegacyColor.Y, \"Hello\") " + " -> " + Color.INSTANCE.fgBg5(Color.LegacyColor.Y, " Hello "));
        System.out.println("Color.INSTANCE.fgBg5(Color.LegacyColor.C, \"Hello\") " + " -> " + Color.INSTANCE.fgBg5(Color.LegacyColor.C, " Hello "));
        System.out.println("Color.INSTANCE.fgBg5(Color.LegacyColor.M, \"Hello\") " + " -> " + Color.INSTANCE.fgBg5(Color.LegacyColor.M, " Hello "));
        System.out.println();
        /// ////////// Following is for predefined colors (6 bits).

        System.out.println("Color5.INSTANCE.fg(Color5.Color.DD)" + Color5.INSTANCE.fg(Color5.Color.DD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.D)" + Color5.INSTANCE.fg(Color5.Color.D, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.DL)" + Color5.INSTANCE.fg(Color5.Color.DL, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.WD)" + Color5.INSTANCE.fg(Color5.Color.WD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.W)" + Color5.INSTANCE.fg(Color5.Color.W, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.WL)" + Color5.INSTANCE.fg(Color5.Color.WL, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.RD)" + Color5.INSTANCE.fg(Color5.Color.RD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.R)" + Color5.INSTANCE.fg(Color5.Color.R, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.RL)" + Color5.INSTANCE.fg(Color5.Color.RL, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.OD)" + Color5.INSTANCE.fg(Color5.Color.OD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.O)" + Color5.INSTANCE.fg(Color5.Color.O, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.OL)" + Color5.INSTANCE.fg(Color5.Color.OL, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.GD)" + Color5.INSTANCE.fg(Color5.Color.GD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.G)" + Color5.INSTANCE.fg(Color5.Color.G, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.fg(Color5.Color.GL)" + Color5.INSTANCE.fg(Color5.Color.GL, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.YD)" + Color5.INSTANCE.bg(Color5.Color.YD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.Y)" + Color5.INSTANCE.bg(Color5.Color.Y, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.YL)" + Color5.INSTANCE.bg(Color5.Color.YL, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.BD)" + Color5.INSTANCE.bg(Color5.Color.BD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.B)" + Color5.INSTANCE.bg(Color5.Color.B, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.BL)" + Color5.INSTANCE.bg(Color5.Color.BL, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.MD)" + Color5.INSTANCE.bg(Color5.Color.MD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.M)" + Color5.INSTANCE.bg(Color5.Color.M, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.ML)" + Color5.INSTANCE.bg(Color5.Color.ML, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.CD)" + Color5.INSTANCE.bg(Color5.Color.CD, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.C)" + Color5.INSTANCE.bg(Color5.Color.C, " XXXXXXXX "));
        System.out.println("Color5.INSTANCE.bg(Color5.Color.CL)" + Color5.INSTANCE.bg(Color5.Color.CL, " XXXXXXXX "));


        /// ////////// Following is for special 6 bits color (216 rgb values). Escape code type 5.

        // A predefined color

        Color5.Color5 rgb5 = Color5.Color5.MAGENTA;
        System.out.println("");
        System.out.println("Calling Color5.INSTANCE.color5(Color5.Color5.<COLOR_ENUM>, 4, \"Hello 4\")");
        System.out.println(Color5.INSTANCE.color5(Color5.Color5.RED, 4, "Hello 4"));
        System.out.println(Color5.INSTANCE.color5(Color5.Color5.GREEN, 4, "Hello 4"));
        System.out.println(Color5.INSTANCE.color5(Color5.Color5.BLUE, 4, "Hello 4"));
        System.out.println(Color5.INSTANCE.color5(Color5.Color5.YELLOW, 4, "Hello 4"));
        System.out.println(Color5.INSTANCE.color5(Color5.Color5.MAGENTA, 4, "Hello 4"));
        System.out.println(Color5.INSTANCE.color5(Color5.Color5.CYAN, 4, "Hello 4"));
        System.out.println(Color5.INSTANCE.color5(Color5.Color5.GRAY, 4, "Hello 4"));


        /// ////////// Following is for full 24 bits color. Escape code type 2.

        // Create a color with values from 0 to 99 (100 values per red, green, and blue).
        // 100 is the cube size in rgb space. values from 2 up to 256 (including) is supported.

        RGB rgb = new RGB(100, 12, 34, 64);

        // Create a random color in a specified color cube size

        System.out.println("");
        System.out.println("" + rgb.toString() + " -> " + Color2.INSTANCE.rgbBg(" Hello ", rgb));
        System.out.println(Color2.INSTANCE.showC(rgb, 48, "Some blue: ", RGB::toString));
        System.out.println(Color2.INSTANCE.showC(rgb.inverse(), 32, "inverse: ", rgb1 -> "R,G,B = " + rgb1.toLaconicStringRGB()));

        System.out.println("");

        RGB fromCol = AnsiDemo.Companion.randomRGB(100).toSaturation(0.2).toValue(0.5);
        RGB toCol = fromCol.complement().toMaxSaturation().toMaxValue();
        List<Color2.HSV> list = fromCol.toHsv().gradient(12, toCol.toHsv());

        System.out.println("From " + fromCol + "  to " + toCol);

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c = list.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(Color2.INSTANCE.rgbBg(" " + ix + " ", c));
            System.out.print(Color2.INSTANCE.rgbFg(" XXXXX", c));
        }
        System.out.println();

        Function2<String, RGB, String> fg = Color2.INSTANCE::rgbFg;
        Function2<String, RGB, String> bg = Color2.INSTANCE::rgbBg;

        var cnt = 0;
        for (Color2.HSV hsv : list) {
            RGB c2 = hsv.toRGB();
            if (cnt > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + cnt++ + " ", c2.rotL()));
            System.out.print(fg.invoke(" XXXXX", c2.rotR()));
        }
        System.out.println();


        var list2 = rev(list);//.reversed();
        for (int ix = 0; ix < list2.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toMaxSaturation()));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxSaturation()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toMaxValue()));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxValue()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toValue(0.6)));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxValue().toValue(0.6)));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toSaturation(0.1)));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxValue().toSaturation(0.1)));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.mixRG()));
            System.out.print(fg.invoke(" XXXXX", c2.mixRG()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.mixRB()));
            System.out.print(fg.invoke(" XXXXX", c2.mixRB()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.mixGB()));
            System.out.print(fg.invoke(" XXXXX", c2.mixGB()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.rotL()));
            System.out.print(fg.invoke(" XXXXX", c2.rotL()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.rotR()));
            System.out.print(fg.invoke(" XXXXX", c2.rotR()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.inverse()));
            System.out.print(fg.invoke(" XXXXX", c2.inverse()));
        }
        System.out.println();
    }


    static <T> List<T> mix(List<T> list) {
        List<T> list0 = new ArrayList<T>();
        list0.addAll(list);
        List<T> list2 = new ArrayList<T>(list.size());
        Random r = new Random();
        while (list0.size() > 0) {
            int ix = r.nextInt(list0.size());
            list2.add(list0.get(ix));
            list0.remove(ix);
        }
        return list2;
    }

    static <T> List<T> rev(List<T> list) {
        List<T> list2 = new ArrayList<T>(list.size());
        for(int ix = 0; ix < list.size(); ix++) {
            list2.add(list.get(list.size() - ix - 1));
        }
        return list2;
    }
}
