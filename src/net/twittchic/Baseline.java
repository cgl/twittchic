package net.twittchic;

import net.twittchic.constants.Constants;

import java.io.*;
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

        /*if (args.length < 1){

            Scanner v = new Scanner(System.in);
            l = v.next();
        }*/
        if (args.length > 1)
            l = args[1];

        List<Tweet> tweets = deserializeTweets();
        Deasciifier d = new Deasciifier(tweets);
        d.process();
        List<Tweet> b2 = zemberekDegreeOne(tweets);
        List<Tweet> b3 = zemberekrandom(b2);
        Control control = new Control();
        control.process(tweets);
        //control.processB1B2B3(tweets);
        //control.processB1B2B3(b3);
        System.out.println("------------------------------------------------");
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
