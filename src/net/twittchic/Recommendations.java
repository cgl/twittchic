package net.twittchic;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;

import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 7/1/13
 * Time: 2:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class Recommendations {
    private List<Tweet> tweets;

    public Recommendations() {
    }
    /*
    zemberekDegreeOne: is B2 method, that adds zemberek recommendation to confusion set if
    the number of returned recommendations are one. It also updates the candidate if recommendation
    passes the spell checking.
     */
    public static List<Tweet>  zemberekDegreeOne(List<Tweet> tweets){
        TreeMap<Integer, String> oovs;
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        String iv;
        String [] recs;
        for (Tweet tweet : tweets) {
            oovs = tweet.getOovs();
            for (Integer ind : oovs.keySet()) {
                iv = oovs.get(ind);
                recs = z.oner(iv);
                if(recs.length > 0 & recs.length < 2){
                    tweet.addToConfusionSet(ind,recs[0]);
<<<<<<< HEAD
=======
                    if(!recs[0].trim().contains(" ") & z.kelimeDenetle(recs[0]))
                        tweet.updateOvv(recs[0],ind);
>>>>>>> origin/cagil
                }
                else
                    tweet.addToConfusionSet(ind,"");
            }
        }
        return tweets;
    }
    /*
    zemberekDegreeOne: is B3 method, that adds random selected zemberek recommendation to confusion set.
    It also updates the candidate if recommendation passes the spell checking.
    */
    public static List<Tweet>  zemberekrandom(List<Tweet> tweets){
        Random randomGenerator = new Random();
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        String iv;
        TreeMap<Integer, String> oovs;
        String [] recs;
        for (Tweet tweet : tweets) {
             oovs = tweet.getOovs();
            for (Integer ind : oovs.keySet()) {
                iv = oovs.get(ind);
                recs = z.oner(iv);
                if(recs.length > 0){
                    int randomInt = randomGenerator.nextInt(recs.length);
                    tweet.addToConfusionSet(ind,recs[randomInt]);
<<<<<<< HEAD
=======
                    if(!recs[randomInt].trim().contains(" ") & z.kelimeDenetle(recs[randomInt]))
                        tweet.updateOvv(recs[0],ind);
>>>>>>> origin/cagil
                }
                else
                    tweet.addToConfusionSet(ind,"");
            }
        }
        return tweets;
    }

}
