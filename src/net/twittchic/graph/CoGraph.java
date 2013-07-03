package net.twittchic.graph;

import net.twittchic.Tweet;
import net.twittchic.constants.Constants;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.TreeMap;

import static net.twittchic.Baseline.deserializeTweets;


/**
 * Created with IntelliJ IDEA.
 * User: cagil
 * Date: 7/3/13
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoGraph {

    public static void main(String [] args)
    {
        List<Tweet> tweets = deserializeTweets();
        UndirectedGraph<String, DefaultWeightedEdge> stringGraph = createStringGraph(tweets);

        // note undirected edges are printed as: {<v1>,<v2>}
        System.out.println(stringGraph.toString());
        System.out.println(stringGraph.degreeOf("ben"));

    }

    private static UndirectedGraph<String, DefaultWeightedEdge> createStringGraph(List<Tweet> tweets)
    {
        SimpleWeightedGraph <String, DefaultWeightedEdge> g =
                new SimpleWeightedGraph <String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        String [] ivsWords;
        String firstWord;
        String secondWord;
        for (Tweet tweet : tweets) {
            final TreeMap<Integer,String> ivs = tweet.getIvs();
            if(ivs.size()>0){
                ivsWords = new String[ivs.size()];
                ivs.values().toArray(ivsWords);
                addVertex(g,ivsWords[0]);
                for(int i = 0 ; i < ivsWords.length-2; i++) {
                    for(int j = i + 1 ; j < ivsWords.length-1; j++){
                        if(i==0)
                            addVertex(g,ivsWords[j]);
                        addEdge(g,ivsWords[i], ivsWords[j]);
                    }
                }
            }
        }
        return g;
    }
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

    public static SimpleWeightedGraph <String, DefaultWeightedEdge> addVertex(SimpleWeightedGraph <String, DefaultWeightedEdge> g,String e){
        e = e.toLowerCase(Constants.locale);
        if(!g.containsVertex(e)){
            g.addVertex(e);
        }
        return g;
    }
}
