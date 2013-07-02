package net.twittchic;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.util.List;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 7/1/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class Deasciifier {
    private  List<Tweet> tweets;

    public Deasciifier(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public  void process(){
        for (Tweet tweet : this.tweets) {
            TreeMap <Integer, String> oovs = tweet.getOovs();
            turkish.Deasciifier d = new turkish.Deasciifier();
            Zemberek z = new Zemberek(new TurkiyeTurkcesi());
            String iv;
            for (Integer ind : oovs.keySet()) {
                iv = oovs.get(ind);
                d.setAsciiString(z.asciiyeDonustur(iv));
                iv = d.convertToTurkish();
                if(z.kelimeDenetle(iv))
                    tweet.addToConfusionSet(ind,iv);
                else
                    tweet.addToConfusionSet(ind,"");

            }
        }
    }

    public static String asciifyAndCheck(String word){
        turkish.Deasciifier d = new turkish.Deasciifier();
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        d.setAsciiString(z.asciiyeDonustur(word));
        word = d.convertToTurkish();
        if(z.kelimeDenetle(word))
            return word;
        return "";
    }

    public static String asciify(String word){
        turkish.Deasciifier d = new turkish.Deasciifier();
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        d.setAsciiString(z.asciiyeDonustur(word));
        word = d.convertToTurkish();
        return word  ;
    }
}
