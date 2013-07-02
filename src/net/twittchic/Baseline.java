package net.twittchic;

import net.twittchic.constants.Constants;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
    public static void main(String[] args) {
        String l;
        /*
        if (args.length < 1){

            Scanner v = new Scanner(System.in);
            l = v.next();
        }*/
        if (args.length > 1)
            l = args[1];
        Parser parser = new Parser();

        try {
            parser.process(999999999);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //deasc.write();
        List<Tweet> tweets = parser.getTweets();
        Deasciifier d = new Deasciifier(tweets);
        d.process();

        //parser.setTweets(tweets);
        //
        // write(soundLevDict.process(tweets));
        //write(tweets);
        Control control = new Control();
        control.process(tweets);

        write(tweets,Constants.b1);
        List<Tweet> b2 = zemberekDegreeOne(tweets);
        control.process(b2);
        write(b2,Constants.b2);
        List<Tweet> b3 = zemberekrandom(tweets);
        //write(b3,Constants.b3);
        List<Tweet> b4 = soundLevDict.process(tweets);
        //write(b4,Constants.b4);

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
