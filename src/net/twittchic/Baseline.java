package net.twittchic;

import com.google.common.base.Charsets;
import net.twittchic.constants.Constants;
import zemberek.core.DoubleValueSet;
import zemberek.spelling.SingleWordSpellChecker;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static net.twittchic.Recommendations.zemberekDegreeOne;
import static net.twittchic.Recommendations.zemberekrandom;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 7/1/13
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class Baseline {
    public static void main(String[] args) throws IOException {
        //baseline1234();
        SingleWordSpellChecker dt = new SingleWordSpellChecker(1.4, true);
        System.out.println("Loading vocabulary");
        //List<String> list = Files.readAllLines(new File("/Users/cagil/Documents/zemberek-nlp/500.txt").toPath(), Charsets.UTF_8);
        List<String> list = Files.readAllLines(new File("/Users/cagil/Documents/allvoc.txt").toPath(), Charsets.UTF_8);

        System.out.println("Building tree");
        dt.buildDictionary(list);
        System.out.println("Tree is ready");
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

        DoubleValueSet<String> res = dt.decode("çapa");
        for (String re : res) {
            System.out.println(re + " " + res.get(re));
        }
    }

    public static void baseline1234(){
        List<Tweet> tweets = deserializeTweets();
        Control control = new Control();
        //System.out.println("B0 ------------------------------------------------");
        //control.process_old(tweets);
        System.out.println("B1 ------------------------------------------------");
        Deasciifier d = new Deasciifier(tweets);
        d.process();
        control.process_old(tweets);
        System.out.println("B2 ------------------------------------------------");
        zemberekDegreeOne(tweets);
        control.process_old(tweets);
        System.out.println("B3 ------------------------------------------------");
        zemberekrandom(tweets);
        control.process_old(tweets);
        System.out.println("B4 ------------------------------------------------");
        soundLevDict.process(tweets);
        control.process_old(tweets);
    }

    public static List<Tweet> deserializeTweets()
    {
        List<Tweet> tweets = null;
        try{
            InputStream file = new FileInputStream( Constants.tweetsFile);
            InputStream buffer = new BufferedInputStream( file );
            ObjectInput input = new ObjectInputStream ( buffer );
            try{
                tweets = (List<Tweet>)input.readObject();
            }
            finally{
                input.close();
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println("DESerialize edilirken hata gerçekleşti 1 !!! : "+ex.getMessage());
        }
        catch(IOException ex){
            System.out.println("DESerialize edilirken hata gerçekleşti 1 !!! : "+ex.getMessage());
        }
        return  tweets;
    }
    public static void write(List<Tweet> tweets,String filename){
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(filename), Constants.fEncoding);
            for (Tweet tweet : tweets){
                //tweet.deasciify();
                out.write(tweet.ovvsToString()+ Constants.NL);
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }



             /*
    public static void evaluation(List<Tweet> tweets,String filename){
            Scanner scanner = new Scanner(new FileInputStream(Constants.ann1), Constants.fEncoding);
            int i;
            Tweet tweet;
            try {
                while (scanner.hasNextLine()){
                    String s = scanner.nextLine().trim();
                    String [] words = s.split(" ");
        }
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(filename), Constants.fEncoding);
            for (Tweet tweet : tweets){
                //tweet.deasciify();
                out.write(tweet.ovvsToString()+ Constants.NL);
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }    */
}
