package net.twittchic;

import com.google.common.base.Charsets;
import net.twittchic.constants.Constants;
import zemberek.core.DoubleValueSet;
import zemberek.spelling.SingleWordSpellChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cagil
 * Date: 7/4/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedParser extends Parser{
    SingleWordSpellChecker dt;
    public ExtendedParser() throws IOException {
        super();
        dt = new SingleWordSpellChecker(1.4, true);
        System.out.println("Loading vocabulary");
        //List<String> list = Files.readAllLines(new File("/Users/cagil/Documents/zemberek-nlp/500.txt").toPath(), Charsets.UTF_8);
        List<String> list = Files.readAllLines(new File("resources/input/dictionary.txt").toPath(), Charsets.UTF_8);

        //List<String> list = Files.readAllLines(new File("/Users/cagil/Documents/allvoc.txt").toPath(), Charsets.UTF_8);
        System.out.println("Building tree");
        dt.buildDictionary(list);
        System.out.println("Tree is ready");
    }

    public static void main(String[] args) throws IOException {
        serializeTrainingTweets();
        //serializeAllTweets();
        //deasc.write();
        //deasc.serializeTweet(Constants.tweetsFile);
    }

    public static void serializeTrainingTweets() throws IOException {
        ExtendedParser deasc = new ExtendedParser();
        deasc.process();
        deasc.serializeTweet(Constants.tweetsFile);
    }

    public static void serializeAllTweets() throws IOException {
        ExtendedParser deasc = new ExtendedParser();
        deasc.process();
        deasc.serializeTweet(Constants.allTweetsFile);
    }


    @Override
    public boolean denetle(String token){
        DoubleValueSet<String> res = dt.decode(token.toLowerCase(Constants.locale));
        for (String re : res) {
            double v = res.get(re);
            if(v == 0.0){
                System.out.println(token);
                return true;
            }
        }

        return false;
    }
            /*
        Random rnd = new Random(0xbeefcafe);
        List<String> testSet = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            testSet.add(list.get(rnd.nextInt(list.size())));
        }
        testSet.add("Lirasina");

        Stopwatch sw = new Stopwatch().start();
        int i = 0;
        System.out.println(testSet.size());
        for (String s : testSet) {
            //System.out.println(s);
            DoubleValueSet<String> res = dt.decode(dt.process(s));
            for (String re : res) {
                System.out.println(re + " " + res.get(re));
            }
            i = i + res.size();
        }
        */
}
