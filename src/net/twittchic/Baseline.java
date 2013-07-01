package net.twittchic;

import net.twittchic.Deasciifier;
import net.twittchic.Parser;
import net.twittchic.Tweet;
import net.twittchic.constants.Constants;
import net.twittchic.soundLevDict;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

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

        /*try {
            //parser.process(999999999);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }    */
        //deasc.write();
        List<Tweet> tweets = parser.getTweets();
        Deasciifier d = new Deasciifier(tweets);
        d.process();

        //parser.setTweets(tweets);
        //
        // write(soundLevDict.process(tweets));
        //write(tweets);
        Control control = new Control();
        control.process();
    }


    public static void write(List<Tweet> tweets){
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(Constants.outFileName), Constants.fEncoding);
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

}
