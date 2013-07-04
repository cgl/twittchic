package net.twittchic;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.twittchic.*;

/**
 * Created with IntelliJ IDEA.
 * User: ogrenci1
 * Date: 7/3/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class Numbers {
    private static String[] birler = {"sıfır", "bir", "iki", "üç", "dört", "beş", "altı", "yedi", "sekiz", "dokuz",};
    private static String[] onlar = {"on", "yirmi", "otuz", "kırk", "elli", "altmış", "yetmiş", "seksen", "doksan"};
    private static String[] diger = {"yüz", "bin"};

    public static void process(List<Tweet> tweets) {
        int count = 0;
        for (Tweet t : tweets) {
            count++;
            for (int ind : t.getNumbers().keySet()) {
                try {
                    //System.out.println(t.getNumbers().get(ind)+" : "+getNum2String(Integer.parseInt(t.getNumbers().get(ind))));
                    t.addNumberResults(ind, getNum2String(Integer.parseInt(t.getNumbers().get(ind))));
                } catch (Exception ex) {
                    //System.out.println("Numbers.process error  : "+ex.getMessage()+"  id : "+ind+" line : "+count);
                    Pattern p = Pattern.compile("[0-9]+");
                    Matcher m = p.matcher(t.getNumbers().get(ind));
                    if (m.find()) {
                        String number = m.group();
                        String token = t.getNumbers().get(ind);
                        int index = token.indexOf(number);
                        String preFix = "";
                        String sufFix = "";
                        if (index != 0)
                            preFix = token.substring(0, index);
                        sufFix = token.substring(index + number.length());
                        int num = Integer.parseInt(number);
                        //System.out.println(t.getNumbers().get(ind)+" : "+preFix+""+getNum2String(num)+""+sufFix);
                        t.addNumberResults(ind, preFix + "" + getNum2String(num) + "" + sufFix);
                    }

                }
            }
        }
    }

    private static String getNum2String(int num) {
        String numStr = "";
        int remain = 0;
        remain = num / 1000;
        if (remain != 0) {
            if (remain >= 10) {
                numStr = getNum2String(remain) + "" + diger[1];
            } else if (remain == 1)
                numStr += diger[1];
            else
                numStr = birler[remain] + "" + diger[1];
            remain = 0;
        }
        remain = (num % 1000) / 100;
        if (remain != 0) {
            if (remain == 1)
                numStr += diger[0];
            else
                numStr += birler[remain] + "" + diger[0];
            remain = 0;
        }
        remain = (num % 100) / 10;
        if (remain != 0) {
            numStr += onlar[remain - 1];
            remain = 0;
        }
        remain = num % 10;
        if (remain != 0) {
            numStr += birler[remain];
        }
        return numStr;
    }
}
