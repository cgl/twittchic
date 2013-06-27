package net.twittchic;

import com.sun.deploy.util.StringUtils;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import turkish.Deasciifier;

import java.io.*;
import java.util.*;

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
    // PRIVATE
    private final String fFileName;
    private final String outFileName = "resources/output/parsed.txt";
    private final String needToNorm = "resources/output/parsed_oov_words.txt";
    private final String normed = "resources/output/parsed_iv_words.txt";

    private final String fEncoding = "utf-8";
    private final String INPUT_TEXT = "";
    private  List<Tweet> tweets;

    public Parser(String fileName) {
        tweets = new ArrayList<Tweet>();
        fFileName = fileName;
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
        String NL = System.getProperty("line.separator");
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(outFileName), fEncoding);
            for (Tweet tweet : this.tweets){
                //tweet.deasciify();
                out.write(tweet.toString()+ NL);
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    void process(int limit) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
        Writer out2 = new OutputStreamWriter(new FileOutputStream(needToNorm), fEncoding);
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        int i = 0;
        Tweet tweet;
        List<Tweet> tweets;
        int sum = 0;

        try {
            while (scanner.hasNextLine()){
                String s = scanner.nextLine().trim();
                tweet = new Tweet(s);
                this.tweets.add(tweet);
                i = 0;
                for (String token : s.split(" ")) {
                    i++;
                    if(token.length() == 0)
                        continue;
                    if(isNumeric(token)|
                        token.startsWith("http") | token.matches("(\\p{Punct})+|(\\.)+")){
                        continue;
                    }
                    else if(isNumeric(token.substring(0,1))){
                        tweet.addOov(token, i);
                        oovlist.put(token.toLowerCase(new Locale("utf-8")), "");
                    }
                    else if(token.startsWith("#"))  {
                        tweet.addHashtag(token, i);
                    }
                    else if(token.startsWith("@"))  {
                        tweet.addMention(token, i);
                    }
                    else if(z.kelimeDenetle(token)) {
                        ivlist.put(token.toLowerCase(Locale.forLanguageTag("tr-TR")), "");


                        tweet.addIv(token, i);
                        //ivlist.put(token.toLowerCase(new Locale("utf-8")),"-" );
                    }
                    else{

                        tweet.addOov(token,i);
                        oovlist.put(token.toLowerCase(Locale.forLanguageTag("tr-TR")), "");
                    }

                }
                ;
                if(oovlist.size() > limit)
                    break;

            }
            save(oovlist,needToNorm);
            save(ivlist,normed);
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
            double d = Double.parseDouble(str);

        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
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
        Parser deasc = new Parser(l);

            //ovv_statistics();
            try {
                deasc.process(1000);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            deasc.write();
        }

    public static void statistics(){
        String l = "resources/input/tokenized.txt";
        Parser deasc = new Parser(l);
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