package net.twittchic;

import com.sun.org.apache.regexp.internal.RE;
import net.twittchic.constants.Constants;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 6/24/13
 * Time: 8:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Parser {
    static TreeMap<String, String> oovlist = new TreeMap<String,String>();
    static TreeMap<String, String> ivlist = new TreeMap<String,String>();

    private final String INPUT_TEXT = "";

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    private List<Tweet> tweets;

    public Parser() {
        tweets = new ArrayList<Tweet>();
    }

    public String ovv_statistics(){
        int sum = 0;
        int iv = 0;
        for (Tweet tweet : this.tweets){
            sum += tweet.getOovs().size();
            iv  += tweet.getIvs().size();
            //log(" " + (tweet.getOovs().size()));
        }
        return " Unique Ovv size " + oovlist.size() + " Unique Iv size " + ivlist.size() + " Ovvs " + sum + " Ivs " + iv;
    }
    public void write(){
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(Constants.outFileName), Constants.fEncoding);
            for (Tweet tweet : this.tweets){
                //tweet.deasciify();
                out.write(tweet.toString()+ Constants.NL);
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void process(int limit) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(Constants.fFileName), Constants.fEncoding);
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        int i;
        Tweet tweet;
        try {
            while (scanner.hasNextLine()){
                String s = scanner.nextLine().trim();
                tweet = new Tweet(s, false);
                this.tweets.add(tweet);
                i = 0;
                for (String token : s.split(" ")) {
                    i++;
                    if(token.length() == 0)
                        continue;
                    if(isNumeric(token)){
                        tweet.addNumbers(i,token);
                    }
                    else if(token.startsWith("http") | token.matches("(\\p{Punct})+|(\\.)+")){
                        continue;
                    }
                    else if(token.startsWith("#"))  {
                        tweet.addHashtag(token, i);
                    }
                    else if(token.startsWith("@"))  {
                        tweet.addMention(token, i);
                    }
                    else if(isNumeric(token)){
                        tweet.addOov(token, i);
                        oovlist.put(token.toLowerCase(new Locale("utf-8")), "");
                    }
                    else if(z.kelimeDenetle(token)) {
                        ivlist.put(token.toLowerCase(Locale.forLanguageTag("tr-TR")), "");


                        tweet.addIv(token, i);
                        //ivlist.put(token.toLowerCase(new Locale("utf-8")),"-" );
                    }
                    else{

                        tweet.addOov(token,i);
                        oovlist.put(token.toLowerCase(Locale.forLanguageTag("tr-TR")), "");
                        /*  // iv_words.txt dosyasına deasciify edilmiş kelimleri ekler
                        turkish.Deasciifier d = new turkish.Deasciifier();
                        d.setAsciiString(z.asciiyeDonustur(token));
                        token = d.convertToTurkish();
                        if(z.kelimeDenetle(token))
                            ivlist.put(token.toLowerCase(Locale.forLanguageTag("tr-TR")), "");
                            */
                    }
                }
                if(oovlist.size() > limit)
                    break;
            }
            save(oovlist,Constants.needToNorm);
            save(ivlist,Constants.normed);
        }
        finally{
            scanner.close();
        }
    }


    private void log(String aMessage){
        System.out.println(aMessage);
    }
    public static boolean isNumeric(String str)
    {
        try
        {
            if(str.matches("[A-Za-z]*[0-9]+[A-Za-z]*\'?[A-Za-z]*"))
            {
                if(!str.matches("[xX].*"))
                    return  true;
            }
            return  false;
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
    }
    private static void save(TreeMap<String,String> dictionary, String wordlist) throws IOException {
        FileWriter file = new FileWriter(wordlist);
        BufferedWriter writer = new BufferedWriter(file);
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            if(entry.getValue().isEmpty())
                writer.append(entry.getKey());
            else
                writer.append(entry.getKey() + ";" + entry.getValue());    //TO DO space required in between
            writer.newLine();
        }
        writer.close();
        file.close();
    }


    public void serializeTweet(){
        if(this.tweets.size()>0){
            OutputStream file = null;
            OutputStream buffer = null;
            ObjectOutput output = null;
            try
            {
                file = new FileOutputStream( Constants.tweetsFile );
                buffer = new BufferedOutputStream( file );
                output = new ObjectOutputStream( buffer );
                try{
                    output.writeObject(tweets);
                }catch (IOException ex)
                {
                    System.out.println("Serialize edilirken hata gerçekleşti 1 !!! : "+ex.getMessage());
                }
                finally{
                    output.close();
                }
            }catch (IOException ex)
            {
                System.out.println("Serialize edilirken hata gerçekleşti 2 !!! : "+ex.getMessage());
            }
        }

    }
    public static void main(String[] args) {
        String l;
        /*
        if (args.length < 1){

            Scanner v = new Scanner(System.in);
            l = v.next();
        }*/

        if (args.length > 1)
            l = args[1];
        Parser deasc = new Parser();

            //ovv_statistics();
            try {
                deasc.process(999999);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            //deasc.write();
            deasc.serializeTweet();
        }

    public static void statistics(){
        String l = "resources/input/tokenized.txt";
        Parser deasc = new Parser();
        for (int limit : new int[]{500,700, 1000, 1500, 2000}){
            try {
                deasc.process(limit);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            deasc.log("Tweets: " + deasc.tweets.size() + deasc.ovv_statistics());
        }

    }

}
