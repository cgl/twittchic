package net.twittchic.constants;

import java.util.Locale;

public class Constants {
    // Files
//asli tokenized.txt olacak
    public static String fFileName = "resources/input/tokenized.txt";
    public static String tweetsFile = "resources/input/tweets.ser";
    public static String outFileName = "resources/output/parsed.txt";
    public static String b1 = "resources/output/baseline/b1.txt";
    public static String b2 = "resources/output/baseline/b2.txt";
    public static String b3 = "resources/output/baseline/b3.txt";
    public static String b4 = "resources/output/baseline/b4.txt";

    public static String ann1 = "resources/input/annotation1.txt";


    public static String needToNorm = "resources/output/parsed_oov_words.txt";
    public static String normed = "resources/output/parsed_iv_words.txt";
    public static String vocabulary = "resources/input/vocab";
    public static String trainingFileName = "resources/output/train1Annotated.txt";

    public static String substituteFileName = "resources/input/substitutes.txt";


    public static String NL = System.getProperty("line.separator");
    public static final String fEncoding = "utf-8";

    public static final Locale locale = Locale.forLanguageTag("tr-TR");
}
