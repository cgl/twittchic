package net.twittchic;

import turkish.Deasciifier;

import java.io.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 6/24/13
 * Time: 8:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class YuretSevincBalkanDeasciifier {
    public YuretSevincBalkanDeasciifier(String fileName) {
        fFileName = fileName;
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    void process() throws IOException {
        StringBuilder text = new StringBuilder();
        String NL = System.getProperty("line.separator");
        Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
        Writer out = new OutputStreamWriter(new FileOutputStream(outFileName), fEncoding);
        Deasciifier d = new Deasciifier();
        try {
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                d.setAsciiString(s);
                text.append(s + NL);
                text.append(d.convertToTurkish() + NL);
            }
            out.write(String.valueOf(text));
        }
        finally{
            scanner.close();

            out.close();
        }
    }

    // PRIVATE
    private final String fFileName;
    private final String outFileName = "resources/output/deasciified.txt";
    private final String fEncoding = "utf-8";
    private final String INPUT_TEXT = "";
    private String FIXED_TEXT = "But soft! what code in yonder program breaks?";

    private void log(String aMessage){
        System.out.println(aMessage);
    }

    public static void main(String[] args) {
        String l;
        /*
        if (args.length < 1){

            Scanner v = new Scanner(System.in);
            l = v.next();
        }*/

        l = "resources/input/tokenized.txt";
        if (args.length > 1)
            l = args[1];
        YuretSevincBalkanDeasciifier deasc = new YuretSevincBalkanDeasciifier(l);

    }

}
