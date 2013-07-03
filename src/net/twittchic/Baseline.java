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
<<<<<<< HEAD
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

        System.out.println("Cem asagidaki 2 satiri ekledi");
        List<Tweet> b4 = soundLevDict.process(tweets);
        control.process(b4);

        //control.processB1B2B3(tweets);
        //control.processB1B2B3(b3);
        System.out.println("------------------------------------------------");
=======
        baseline1234();

    }

    public static void baseline1234(){
        List<Tweet> tweets = deserializeTweets();
        Control control = new Control();
        System.out.println("B0 ------------------------------------------------");
        control.process_old(tweets);
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
        control.process(tweets);
>>>>>>> origin/cagil
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
