package net.twittchic.tools;

import net.twittchic.constants.Constants;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: cagil
 * Date: 7/17/13
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Helpers {

    public static void addEdge(SimpleWeightedGraph<String, DefaultWeightedEdge> g, String v1, String v2){
        v1 = v1.toLowerCase(Constants.locale);
        v2 = v2.toLowerCase(Constants.locale);
        if(v1.equals(v2))
            return;
        DefaultWeightedEdge edge;
        if(g.containsEdge(v1,v2)){
            edge = g.getEdge(v1, v2);
        }
        else
            edge = g.addEdge(v1, v2);
        g.setEdgeWeight(edge,g.getEdgeWeight(edge)+1);
    }

    public static void addToFrequencyDictionary(TreeMap<String, Integer> dict, String word){
        int freq;
        if(dict.containsKey(word)){
            freq = dict.get(word);
        }
        else
            freq = 0;
        dict.put(word,freq+1);
    }

    public static TreeMap<String,Integer> loadFrequencyDictionary(String wordlist) throws IOException {
        FileReader file = new FileReader(wordlist);
        BufferedReader reader = new BufferedReader(file);
        String word;
        TreeMap<String,Integer> dictionary = new TreeMap<String,Integer>();
        while((word = reader.readLine()) != null) {
            String []k = word.split(":");
            if(k.length == 2)
                dictionary.put(k[0],0);
            else
                System.out.println("Problem "+ Arrays.toString(k));
        }
        reader.close();
        file.close();
        return dictionary;
    }
}
