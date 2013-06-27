package net.twittchic;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import turkish.Deasciifier;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 6/27/13
 * Time: 2:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Tweet {
    private TreeMap <String, Integer>  mentions;
    private TreeMap <String, Integer> hashtags;
    private TreeMap <String, Integer> ivs;
    private TreeMap <String, Integer> oovs;
    private String text;
    private ArrayList<Integer> deasciified;

    public Tweet(String text) {
        this.text = text;
        ivs = new TreeMap <String, Integer> ();
        oovs = new TreeMap <String, Integer> ();
        mentions = new TreeMap <String, Integer> ();
        hashtags =  new TreeMap <String, Integer> ();
        deasciified = new ArrayList<Integer>();

    }

    public void deasciify(){
        Deasciifier d = new Deasciifier();
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        String iv;
        for (String oov : this.oovs.keySet()) {
            d.setAsciiString(z.asciiyeDonustur(oov));
            iv = d.convertToTurkish();
            if(z.kelimeDenetle(iv)){
                ivs.put(iv,oovs.get(oov));
                deasciified.add(oovs.get(oov));
                oovs.remove(oov);
            }
        }

    }

    @Override
    public String toString() {
        return text;
        /*
        return "Tweet{" +
                "text='" + text + '\'' +
                ", mentions=" + mentions.values().toString() +
                ", hashtags=" + hashtags.values().toString() +
                ", ivs=" + ivs.values().toString() +
                ", oovs=" + oovs.values().toString() +
                ", deasciified=" + deasciified.toString() +
                '}';    */
    }

    public void addHashtag(String hashtag, int ind){
       this.hashtags.put(hashtag,ind);
    }

    public TreeMap<String, Integer> getIvs() {
        return ivs;
    }

    public void addIv(String iv, Integer ind) {
        this.ivs.put(iv, ind);
    }

    public TreeMap<String, Integer> getOovs() {
        return oovs;
    }

    public void addOov(String oov, Integer ind) {
        this.oovs.put(oov, ind);
    }

    public void addMention(String mention, int ind){
        this.mentions.put(mention,ind);
    }


}
