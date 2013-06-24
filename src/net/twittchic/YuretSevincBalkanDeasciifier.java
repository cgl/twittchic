package net.twittchic;

import turkish.Deasciifier;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 6/24/13
 * Time: 8:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class YuretSevincBalkanDeasciifier {
    public YuretSevincBalkanDeasciifier() {
        Deasciifier d = new Deasciifier();

        d.setAsciiString("Hadi bir masal uyduralim, icinde mutlu, doygun, telassiz durdugumuz.");
        System.out.println(d.convertToTurkish());
    }
}
