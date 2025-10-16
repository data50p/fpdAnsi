package femtioprocent.demo;

import femtioprocent.ansi.Ansi;
import femtioprocent.ansi.appl.AnsiDemo;
import kotlin.jvm.functions.Function2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo {
    public static void main(String[] args) {
        if (args.length > 0 && args.equals("AnsiDemo")) {
            AnsiDemo.main(args);
            System.exit(0);
        }

        // Since library is written in Kotlin, do expect some odd things when calling it.

        /// ////////// Following is for legacy colors (a very few values indeed). Type 1 color spec

        System.out.println("Ansi.INSTANCE.fg(Ansi.LegacyColor.R, \"Hello\")  " + " -> " + Ansi.INSTANCE.fg(Ansi.LegacyColor.R, " Hello "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.LegacyColor.G, \"Hello\")  " + " -> " + Ansi.INSTANCE.fg(Ansi.LegacyColor.G, " Hello "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.LegacyColor.B, \"Hello\")  " + " -> " + Ansi.INSTANCE.fg(Ansi.LegacyColor.B, " Hello "));
        System.out.println("Ansi.INSTANCE.fgBg5(Ansi.LegacyColor.Y, \"Hello\") " + " -> " + Ansi.INSTANCE.fgBg5(Ansi.LegacyColor.Y, " Hello "));
        System.out.println("Ansi.INSTANCE.fgBg5(Ansi.LegacyColor.C, \"Hello\") " + " -> " + Ansi.INSTANCE.fgBg5(Ansi.LegacyColor.C, " Hello "));
        System.out.println("Ansi.INSTANCE.fgBg5(Ansi.LegacyColor.M, \"Hello\") " + " -> " + Ansi.INSTANCE.fgBg5(Ansi.LegacyColor.M, " Hello "));
        System.out.println();
        /// ////////// Following is for predefined colors (6 bits).

        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.DD)" + Ansi.INSTANCE.fg(Ansi.Color.DD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.D)" + Ansi.INSTANCE.fg(Ansi.Color.D, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.DL)" + Ansi.INSTANCE.fg(Ansi.Color.DL, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.WD)" + Ansi.INSTANCE.fg(Ansi.Color.WD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.W)" + Ansi.INSTANCE.fg(Ansi.Color.W, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.WL)" + Ansi.INSTANCE.fg(Ansi.Color.WL, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.RD)" + Ansi.INSTANCE.fg(Ansi.Color.RD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.R)" + Ansi.INSTANCE.fg(Ansi.Color.R, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.RL)" + Ansi.INSTANCE.fg(Ansi.Color.RL, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.OD)" + Ansi.INSTANCE.fg(Ansi.Color.OD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.O)" + Ansi.INSTANCE.fg(Ansi.Color.O, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.OL)" + Ansi.INSTANCE.fg(Ansi.Color.OL, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.GD)" + Ansi.INSTANCE.fg(Ansi.Color.GD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.G)" + Ansi.INSTANCE.fg(Ansi.Color.G, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.fg(Ansi.Color.GL)" + Ansi.INSTANCE.fg(Ansi.Color.GL, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.YD)" + Ansi.INSTANCE.bg(Ansi.Color.YD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.Y)" + Ansi.INSTANCE.bg(Ansi.Color.Y, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.YL)" + Ansi.INSTANCE.bg(Ansi.Color.YL, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.BD)" + Ansi.INSTANCE.bg(Ansi.Color.BD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.B)" + Ansi.INSTANCE.bg(Ansi.Color.B, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.BL)" + Ansi.INSTANCE.bg(Ansi.Color.BL, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.MD)" + Ansi.INSTANCE.bg(Ansi.Color.MD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.M)" + Ansi.INSTANCE.bg(Ansi.Color.M, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.ML)" + Ansi.INSTANCE.bg(Ansi.Color.ML, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.CD)" + Ansi.INSTANCE.bg(Ansi.Color.CD, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.C)" + Ansi.INSTANCE.bg(Ansi.Color.C, " XXXXXXXX "));
        System.out.println("Ansi.INSTANCE.bg(Ansi.Color.CL)" + Ansi.INSTANCE.bg(Ansi.Color.CL, " XXXXXXXX "));


        /// ////////// Following is for special 6 bits color (216 rgb values). Escape code type 5.

        // A predefined color

        Ansi.Color5 rgb5 = Ansi.Color5.MAGENTA;
        System.out.println("");
        System.out.println("Calling Ansi.INSTANCE.color5(Ansi.Color5.<COLOR_ENUM>, 4, \"Hello 4\")");
        System.out.println(Ansi.INSTANCE.color5(Ansi.Color5.RED, 4, "Hello 4"));
        System.out.println(Ansi.INSTANCE.color5(Ansi.Color5.GREEN, 4, "Hello 4"));
        System.out.println(Ansi.INSTANCE.color5(Ansi.Color5.BLUE, 4, "Hello 4"));
        System.out.println(Ansi.INSTANCE.color5(Ansi.Color5.YELLOW, 4, "Hello 4"));
        System.out.println(Ansi.INSTANCE.color5(Ansi.Color5.MAGENTA, 4, "Hello 4"));
        System.out.println(Ansi.INSTANCE.color5(Ansi.Color5.CYAN, 4, "Hello 4"));
        System.out.println(Ansi.INSTANCE.color5(Ansi.Color5.GRAY, 4, "Hello 4"));


        /// ////////// Following is for full 24 bits color. Escape code type 2.

        // Create a color with values from 0 to 99 (100 values per red, green, and blue).
        // 100 is the cube size in rgb space. values from 2 up to 256 (including) is supported.

        Ansi.RGB rgb = new Ansi.RGB(100, 12, 34, 64);

        // Create a random color in a specified color cube size

        System.out.println("");
        System.out.println("" + rgb.toString() + " -> " + Ansi.INSTANCE.rgbBg(" Hello ", rgb));
        System.out.println(Ansi.INSTANCE.showC(rgb, 48, "Some blue: ", Ansi.RGB::toString));
        System.out.println(Ansi.INSTANCE.showC(rgb.inverse(), 32, "inverse: ", rgb1 -> "R,G,B = " + rgb1.toLaconicStringRGB()));

        System.out.println("");

        Ansi.RGB fromCol = AnsiDemo.Companion.randomRGB(100).toSaturation(0.2).toValue(0.5);
        Ansi.RGB toCol = fromCol.complement().toMaxSaturation().toMaxValue();
        List<Ansi.HSV> list = fromCol.toHsv().gradient(12, toCol.toHsv());

        System.out.println("From " + fromCol + "  to " + toCol);

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c = list.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(Ansi.INSTANCE.rgbBg(" " + ix + " ", c));
            System.out.print(Ansi.INSTANCE.rgbFg(" XXXXX", c));
        }
        System.out.println();

        Function2<String, Ansi.RGB, String> fg = Ansi.INSTANCE::rgbFg;
        Function2<String, Ansi.RGB, String> bg = Ansi.INSTANCE::rgbBg;

        var cnt = 0;
        for (Ansi.HSV hsv : list) {
            Ansi.RGB c2 = hsv.toRGB();
            if (cnt > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + cnt++ + " ", c2.rotL()));
            System.out.print(fg.invoke(" XXXXX", c2.rotR()));
        }
        System.out.println();


        var list2 = rev(list);//.reversed();
        for (int ix = 0; ix < list2.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toMaxSaturation()));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxSaturation()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toMaxValue()));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxValue()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toValue(0.6)));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxValue().toValue(0.6)));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.toSaturation(0.1)));
            System.out.print(fg.invoke(" XXXXX", c2.toMaxValue().toSaturation(0.1)));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.mixRG()));
            System.out.print(fg.invoke(" XXXXX", c2.mixRG()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.mixRB()));
            System.out.print(fg.invoke(" XXXXX", c2.mixRB()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.mixGB()));
            System.out.print(fg.invoke(" XXXXX", c2.mixGB()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.rotL()));
            System.out.print(fg.invoke(" XXXXX", c2.rotL()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
            if (ix > 0)
                System.out.print(" -> ");
            System.out.print(bg.invoke(" " + ix + " ", c2.rotR()));
            System.out.print(fg.invoke(" XXXXX", c2.rotR()));
        }
        System.out.println();

        for (int ix = 0; ix < list.size(); ix++) {
            Ansi.RGB c2 = list2.get(ix).toRGB();
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
