package net.twittchic;

import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import turkish.Deasciifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: cagilulusahin
 * Date: 6/27/13
 * Time: 2:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Tweet implements Serializable{
    private TreeMap <Integer, String> mentions;
    private TreeMap <Integer, String> hashtags;
    private TreeMap <Integer, String> ivs;
    private final TreeMap <Integer, String> oovs;
    private TreeMap <Integer, String> numbers;
    private TreeMap <Integer, ArrayList<String>> confusionSet_SND;
    private TreeMap <Integer, ArrayList<String>> confusionSet_G;
    private TreeMap <Integer, ArrayList<String>> confusionSet_LM;
    private TreeMap <Integer, ArrayList<String>> confusionSet;
    private TreeMap <Integer,String>  numResult;
    private HashMap <Integer, String> results;
    private HashMap <Integer, Integer> scores;

    public HashMap<Integer, String> getResults() {
        return results;
    }



    public String getText() {
        return text;
    }

    private String text;
    boolean deasciified;

    public TreeMap<Integer, ArrayList<String>> getConfusionSet() {
        return confusionSet;
    }

    public void setConfusionSet(TreeMap<Integer, ArrayList<String>> confusionSet) {
        this.confusionSet = confusionSet;
    }

    public Tweet(String text, boolean False) {
        this.text = text;
        this.ivs = new TreeMap <Integer, String> ();
        this.oovs = new TreeMap <Integer, String> ();
        this.mentions = new TreeMap <Integer, String> ();
        this.hashtags =  new TreeMap <Integer, String> ();
        this.numbers =  new TreeMap <Integer, String> ();
        this.results = new HashMap<Integer, String>();
        this.deasciified = False;
        this.confusionSet = new TreeMap <Integer, ArrayList<String>>();
        this.numResult = new TreeMap<Integer, String>();

    }

    public void deasciify(boolean True){
        Deasciifier d = new Deasciifier();
        Zemberek z = new Zemberek(new TurkiyeTurkcesi());
        String iv;
        for (Integer ind : this.oovs.keySet()) {
            iv = oovs.get(ind);
            d.setAsciiString(z.asciiyeDonustur(iv));
            iv = d.convertToTurkish();
            if(z.kelimeDenetle(iv)){
                this.putResult(iv, ind);
            }
        }
        this.deasciified = True;
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

    public String ovvsToString() {
        return oovs.keySet().toString()+ ";" + oovs.values().toString();
    }

    public void addHashtag(String hashtag, int ind){
       this.hashtags.put(ind, hashtag);
    }

    public TreeMap<Integer, String> getIvs() {
        return ivs;
    }

    public void addIv(String iv, Integer ind) {
        this.ivs.put(ind, iv);
    }

    public TreeMap<Integer, String> getOovs() {
        return oovs;
    }

    public void addOov(String oov, Integer ind) {
        this.oovs.put(ind, oov);
    }

    public void putResult(String oov, Integer ind) {
        this.results.put(ind, oov);
    }

    public void addNumbers(Integer ind, String oov) {
        this.numbers.put(ind, oov);
    }
    public void addNumberResults(Integer ind,String num)
    {
        this.numResult.put(ind,num);
    }
    public TreeMap<Integer,String> getNumberResults()
    {
        return  numResult;
    }
    public TreeMap<Integer, String> getNumbers() {
        return numbers;
    }

    public void addMention(String mention, int ind){
        this.mentions.put(ind, mention);
    }
    public void addToConfusionSet(Integer ind, String oov) {
        ArrayList<String> strings = this.confusionSet.get(ind);
        if(strings == null)
            strings = new ArrayList<String>();
        strings.add(oov);
        this.confusionSet.put(ind, strings);

    }

    public void updateConfusionSet(Integer ind, String oov) {
        ArrayList<String> strings = this.confusionSet.get(ind);
        if(strings == null)
            strings = new ArrayList<String>();
        strings.add(oov);
        this.confusionSet.put(ind,strings);
    }

}
