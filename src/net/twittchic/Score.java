package net.twittchic;

import net.twittchic.constants.Constants;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

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

    public Score(){
        create(1,1,1);
    }

    public static void main(String[] args) {
        new Score();
    }

    public void create(double a, double b, double c){
        List<Tweet> tweets = deserializeTweets(Constants.tweetsFile);
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
        return 0.00001;  //To change body of created methods use File | Settings | File Templates.
    }

    private static void identifyIllFormedWords(List<Tweet> tweets) {
        //To change body of created methods use File | Settings | File Templates.
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


}
