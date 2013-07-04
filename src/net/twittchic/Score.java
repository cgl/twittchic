package net.twittchic;

import com.google.common.base.Charsets;
import net.twittchic.constants.Constants;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import zemberek.core.DoubleValueSet;
import zemberek.spelling.SingleWordSpellChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static net.twittchic.Baseline.deserializeTweets;
import static net.twittchic.graph.CoGraph.populateConfusionSet;

/**
 * Created with IntelliJ IDEA.
 * User: cagil
 * Date: 7/4/13
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Score {

    SimpleWeightedGraph<String, DefaultWeightedEdge> tweetGraph = null;
    public static SingleWordSpellChecker dt;

    public Score() throws IOException {
        dt = new SingleWordSpellChecker(1.4, true);
        List<String> list = Files.readAllLines(new File("resources/input/dictionary.txt").toPath(), Charsets.UTF_8);
        System.out.println("Building tree");
        dt.buildDictionary(list);
        System.out.println("Tree is ready");
        create(1,0,0);
    }

    public static void main(String[] args) throws IOException {
        new Score();
    }

    public void create(double a, double b, double c){
        List<Tweet> tweets = deserializeTweets(Constants.tweetsFile);
        Deasciifier d = new Deasciifier(tweets);
        d.process();
        feedConfusionSetL(tweets);
        feedConfusionSetG(tweets);
        feedConfusionSetLM(tweets);
        identifyIllFormedWords(tweets);
        ArrayList<Double> scores;
        int i;
        for (Tweet tweet : tweets) {
            final TreeMap<Integer,HashSet<String>> confusionSet = tweet.getConfusionSet();
            HashMap<Integer,String> results = tweet.getResults();
            for (Integer ind : confusionSet.keySet()) {
                //System.out.println(confusionSet.toString());
                final HashSet<String> confWords = confusionSet.get(ind);
                if(confWords.isEmpty())
                    continue;
                scores = new ArrayList<Double>(confWords.size());
                for (String confWord : confWords) {
                    scores.add(a * scoreL(ind, confWord, tweet) +
                            b * scoreG(ind, confWord, tweet) +
                            c * scoreLM(ind, confWord, tweet));
                }
                Double max = Collections.max(scores);
                int chosenInd = scores.indexOf(max);
                results.put(ind, (String) confWords.toArray()[chosenInd]);
            }
        }
        Control control = new Control();
        control.process_old(tweets);
    }

    private static Integer keyOfMax(HashMap<Integer, Double> scores) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private static double scoreLM(Integer ind, String confWord, Tweet tweet) {
        return 0.00001;  //To change body of created methods use File | Settings | File Templates.
    }

    private static double scoreG(Integer ind, String confWord, Tweet tweet) {
        return 0.00001;  //To change body of created methods use File | Settings | File Templates.
    }

    private static double scoreL(Integer ind, String confWord, Tweet tweet) {
        return 1/(soundLevDict.computeLevenshteinDistance(tweet.getOovs().get(ind),confWord)+0.000001);

    }

    private static void identifyIllFormedWords(List<Tweet> tweets) {
        for (Tweet tweet : tweets) {
            TreeMap<Integer, HashSet<String>> confusionSet = tweet.getConfusionSet();
            for (Integer ind : confusionSet.keySet()) {
                HashSet<String> confwords = confusionSet.get(ind);
                HashSet<String> confwords2 = new  HashSet<String>();
                for (String confword : confwords) {
                    if(denetle(confword))
                        confwords2.add(confword);
                }
                confusionSet.put(ind, confwords2);
                tweet.setConfusionSet(confusionSet);
            }

        }

    }

    private static void feedConfusionSetL(List<Tweet> tweets) {

        //To change body of created methods use File | Settings | File Templates.
    }

    private void feedConfusionSetG(List<Tweet> tweets) {
        tweetGraph = populateConfusionSet(tweets);
    }

    private static void feedConfusionSetLM(List<Tweet> tweets) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public static boolean denetle(String token){
        DoubleValueSet<String> res = dt.decode(token.toLowerCase(Constants.locale));
        for (String re : res) {
            double v = res.get(re);
            if(v == 0.0){
                return true;
            }
        }
        return false;
    }

}
