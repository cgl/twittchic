package net.twittchic;

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
    // PRIVATE
    private final String fFileName;
    private final String outFileName = "resources/output/parsed.txt";
    private final String needToNorm = "resources/output/parsed_oov_words.txt";
    private final String fEncoding = "utf-8";
    private final String INPUT_TEXT = "";
    private String FIXED_TEXT = "But soft! what code in yonder program breaks?";
    private  List<Tweet> tweets;

    public Parser(String fileName) {
        tweets = new ArrayList<Tweet>();
        fFileName = fileName;
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        write();

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

    void process() throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(fFileName), fEncoding);
        Writer out2 = new OutputStreamWriter(new FileOutputStream(needToNorm), fEncoding);
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        int i = 0;
        Tweet tweet;
        List<Tweet> tweets;
        try {
            while (scanner.hasNextLine()){
                String s = scanner.nextLine();
                tweet = new Tweet(s);
                this.tweets.add(tweet);
                i = 0;
                for (String token : s.split(" ")) {
                    i++;
                    if(isNumeric(token)|
                            token.startsWith("http") | token.matches("(\\p{Punct})+|(\\.)+")){
                        continue;
                    }
                    if(token.startsWith("#"))  {
                        tweet.addHashtag(token,i);
                    }
                    else if(token.startsWith("@"))  {
                        tweet.addMention(token,i);
                    }
                    else if(!z.kelimeDenetle(token)) {
                        tweet.addOov(token,i);
                        oovlist.put(token, "-");
                    }
                    else{
                        System.out.println(token);
                        tweet.addIv(token, i);
                    }
                }
                ;

            }
            save(oovlist,needToNorm);
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
            writer.append(entry.getKey() + " " + entry.getValue());    //TO DO space required in between
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

    }

}
